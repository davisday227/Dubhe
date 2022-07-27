package com.panerai.gateway.filter;

import com.panerai.gateway.infra.FilterRequestResponseUtil;
import io.netty.buffer.ByteBufAllocator;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.NettyDataBufferFactory;
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
      String bodyStr = FilterRequestResponseUtil.resolveBodyFromRequest(
          serverHttpRequest.getBody());

      // 处理bodyStr
      log.info("get body: {}", bodyStr);

      // 再次封装request
      ServerHttpRequest request = buildServerHttpRequest(serverHttpRequest, bodyStr);

      return chain.filter(exchange.mutate().request(request).build());
    } else {
      return chain.filter(exchange);
    }
  }

  private ServerHttpRequest buildServerHttpRequest(ServerHttpRequest request, String bodyStr) {
    URI uri = request.getURI();
    ServerHttpRequest newRequest = request.mutate().uri(uri).build();
    DataBuffer bodyDataBuffer = stringBuffer(bodyStr);
    Flux<DataBuffer> bodyFlux = Flux.just(bodyDataBuffer);
    newRequest = new ServerHttpRequestDecorator(newRequest) {
      @Override
      public Flux<DataBuffer> getBody() {
        return bodyFlux;
      }
    };
    return newRequest;
  }

  private DataBuffer stringBuffer(String value) {
    byte[] bytes = value.getBytes(StandardCharsets.UTF_8);

    NettyDataBufferFactory nettyDataBufferFactory = new NettyDataBufferFactory(
        ByteBufAllocator.DEFAULT);
    DataBuffer buffer = nettyDataBufferFactory.allocateBuffer(bytes.length);
    buffer.write(bytes);
    return buffer;
  }

  @Override
  public int getOrder() {
    return 0;
  }
}
