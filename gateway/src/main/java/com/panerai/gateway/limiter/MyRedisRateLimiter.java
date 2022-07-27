package com.panerai.gateway.limiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Component
public class MyRedisRateLimiter {

  private static final Logger logger = LoggerFactory.getLogger(MyRedisRateLimiter.class);

  private final String keyNamespace = "gateway:demo:limit:";

  private final RedisTemplate<String, Long> redisTemplate;

  private final RedisScript<List<Long>> redisScript;

  public MyRedisRateLimiter(RedisTemplate redisTemplate, RedisScript<List<Long>> redisScript) {
    this.redisTemplate = redisTemplate;
    this.redisScript = redisScript;
  }

  public boolean isAllowed(String key, int replenishRate, int burstCapacity) {
    List<String> keys = Arrays.asList(keyNamespace + key + "tokens",
        keyNamespace + key + "timestamp");
    try {
      List<Long> response = this.redisTemplate.execute(this.redisScript, keys, replenishRate + "",
          burstCapacity + "", Instant.now().getEpochSecond() + "", 1 + "");
      if (response.get(0) == 0) {
        return false;
      } else {
        return true;
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      return true;
    }

  }
}
