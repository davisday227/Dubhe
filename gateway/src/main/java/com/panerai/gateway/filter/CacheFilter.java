package com.panerai.gateway.filter;

import java.io.UnsupportedEncodingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CacheFilter implements Ordered, GlobalFilter {

  private static Logger log = LoggerFactory.getLogger(CacheFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest serverHttpRequest = exchange.getRequest();
    HttpMethod method = serverHttpRequest.getMethod();
    if (method == HttpMethod.POST) {

      return DataBufferUtils.join(exchange.getRequest().getBody())
          .flatMap(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            try {
              String bodyString = new String(bytes, "utf-8");
              log.info(bodyString);//打印请求参数
            } catch (UnsupportedEncodingException e) {
              e.printStackTrace();
            }

            DataBufferUtils.release(dataBuffer);
            ServerHttpRequest mutatedRequest = buildServerHttpRequest(exchange, bytes);
            return chain.filter(exchange.mutate().request(mutatedRequest).build());
          });

    } else {
      return chain.filter(exchange);
    }
  }

  private ServerHttpRequest buildServerHttpRequest(ServerWebExchange exchange, byte[] bytes) {
    Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
      DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
      return Mono.just(buffer);
    });

    ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
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
}
