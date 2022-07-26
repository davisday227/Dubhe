package com.panerai.gateway.limiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 接口限流过滤器
 */
@Component
public class PathRateGlobalFilter implements Ordered, GlobalFilter {
    private static Logger log = LoggerFactory.getLogger(PathRateGlobalFilter.class);
    private AtomicLong totalCount = new AtomicLong(0L);
    private AtomicLong successCount = new AtomicLong(0L);
    private AtomicLong failCount = new AtomicLong(0L);

    @Autowired
    private MyRedisRateLimiter myRedisRateLimiter;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getPath().value();

        PathRate pathRate = new PathRate();
        pathRate.setPath("users/test");
        pathRate.setReplenishRate(100);
        pathRate.setBurstCapacity(100);
        totalCount.incrementAndGet();

        //如果允许同行，没有超过该接口的流量限制
        if(myRedisRateLimiter.isAllowed("path:"+path+":",
                pathRate.getReplenishRate(),
                pathRate.getBurstCapacity())){
            successCount.incrementAndGet();
            log.info("total: {}, success: {}, fail: {}", totalCount.get(), successCount.get(), failCount.get());
            return chain.filter(exchange);
        }else{
            exchange.getResponse().setStatusCode(HttpStatus.TOO_MANY_REQUESTS);
            failCount.incrementAndGet();
            log.info("total: {}, success: {}, fail: {}", totalCount.get(), successCount.get(), failCount.get());
            return exchange.getResponse().setComplete();
        }
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
