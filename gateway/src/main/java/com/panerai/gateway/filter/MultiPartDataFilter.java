package com.panerai.gateway.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.core.Ordered;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.http.codec.multipart.Part;
import org.springframework.http.codec.multipart.SynchronossPartHttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class MultiPartDataFilter implements Ordered, GlobalFilter {

  private static Logger log = LoggerFactory.getLogger(MultiPartDataFilter.class);

  private final List<HttpMessageReader<?>> messageReaders =
      HandlerStrategies.withDefaults().messageReaders();


  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerRequest serverRequest = ServerRequest.create(exchange, messageReaders);

    ServerHttpRequest request = exchange.getRequest();
    Mono<MultiValueMap<String, String>> modifiedBody = serverRequest.bodyToMono(
        new ParameterizedTypeReference<MultiValueMap<String, Part>>() {
        }).flatMap(
        originalBody -> {
          Map<String, List<String>> newValues = new HashMap<>();

          originalBody.keySet().forEach(key -> {
            List<String> newParts = new ArrayList<>();
            List<Part> parts = originalBody.get(key);
            for (Part part : parts) {
              boolean isFile = StringUtils.contains(part.headers().getFirst("Content-Disposition"),
                  "filename");
              if (isFile) {
                //newParts.add(((FormFieldPart) part).value());

              } else {
                newParts.add("test");
              }
            }
            newValues.put(key, newParts);
          });

          return Mono.just(new LinkedMultiValueMap<String, String>(newValues));
        });

    BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody,
        new ParameterizedTypeReference<MultiValueMap<String, String>>() {
        });
    HttpHeaders headers = new HttpHeaders();
    headers.putAll(request.getHeaders());
    headers.remove(HttpHeaders.CONTENT_LENGTH);
    CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
    return bodyInserter.insert(outputMessage, new BodyInserterContext()).then(Mono.defer(() -> {
      ServerHttpRequest decorator = new ServerHttpRequestDecorator(request) {
        @Override
        public HttpHeaders getHeaders() {
          long contentLength = headers.getContentLength();
          HttpHeaders httpHeaders = new HttpHeaders();
          httpHeaders.putAll(headers);
          if (contentLength > 0) {
            httpHeaders.setContentLength(contentLength);
          } else {
            httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
          }
          return httpHeaders;
        }

        @Override
        public Flux<DataBuffer> getBody() {
          return outputMessage.getBody();
        }
      };
      return chain.filter(exchange.mutate().request(decorator).build());
    }));
  }


  @Override
  public int getOrder() {
    return 0;
  }
}
