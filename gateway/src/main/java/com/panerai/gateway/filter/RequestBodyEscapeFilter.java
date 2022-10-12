package com.panerai.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//@Component
public class RequestBodyEscapeFilter implements Ordered, GlobalFilter {

  private static Logger log = LoggerFactory.getLogger(RequestBodyEscapeFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest serverHttpRequest = exchange.getRequest();

    HttpHeaders headers = new HttpHeaders();
    headers.putAll(exchange.getRequest().getHeaders());

    // the new content type will be computed by bodyInserter
    // and then set in the request decorator
    headers.remove(HttpHeaders.CONTENT_LENGTH);

    HttpMethod method = serverHttpRequest.getMethod();
    if (method == HttpMethod.POST) {

      return DataBufferUtils.join(exchange.getRequest().getBody())
          .flatMap(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            try {
              String bodyString = new String(bytes, "utf-8");
              bytes = escape(bodyString);
              log.info("get request body: {}", bodyString);//打印请求参数
            } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
            }

            DataBufferUtils.release(dataBuffer);
            ServerHttpRequest mutatedRequest = buildServerHttpRequest(exchange, bytes, headers);
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
          });

    } else {
      return chain.filter(exchange);
    }
  }

  private ServerHttpRequest buildServerHttpRequest(ServerWebExchange exchange, byte[] bytes, HttpHeaders headers) {
    Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
      DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
      return Mono.just(buffer);
    });

    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
      @Override
      public HttpHeaders getHeaders() {
        long contentLength = headers.getContentLength();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.putAll(headers);
        if (contentLength > 0) {
          httpHeaders.setContentLength(contentLength);
        }
        else {
          // TODO: this causes a 'HTTP/1.1 411 Length Required' // on
          // httpbin.org
          httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
        }
        return httpHeaders;
      }

      @Override
      public Flux<DataBuffer> getBody() {
        return cachedFlux;
      }
    };
    return mutatedRequest;
  }

  @Override
  public int getOrder() {
    return 0;
  }

  private byte[] escape(String body) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
      JsonNode jsonNode = objectMapper.readTree(body);

      String name = jsonNode.get("name").asText();
      if (StringUtils.contains(name, "<")) {
        name = name.replaceAll("<", "lt%8");
      }
      if (StringUtils.contains(name, ">")) {
        name = name.replaceAll(">", "lt%9");
      }

      ObjectNode newNodes = ((ObjectNode) jsonNode).put("name", name);

      return objectMapper.writeValueAsString(newNodes).getBytes(StandardCharsets.UTF_8);
    } catch (JsonProcessingException e) {
      log.info("fail to add node", e);
    }

    return body.getBytes(StandardCharsets.UTF_8);
  }
}
