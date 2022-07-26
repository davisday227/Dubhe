package com.panerai.limiter.leakybucket;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.LocalTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class LeakyBucketGuava {
    private static final Cache<String, LeakyBucketLimiter> cache =
            CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.SECONDS).build();

    public static boolean tryAcquire(String key, int id, int time, int rate, int burst) {
        System.out.println(LocalTime.now() + " in runner " + id + "-" + time);
        LeakyBucketLimiter curCount = null;
        try {
            curCount = cache.get(key, new Callable<LeakyBucketLimiter>() {
                public LeakyBucketLimiter call() throws Exception {
                    System.out.println("init count " + id + "-" + time);
                    return new LeakyBucketLimiter(rate, burst);
                }
            });
        } catch (ExecutionException e) {
            return false;
        }
        boolean acquire = curCount.tryAcquire();
        System.out.println(LocalTime.now() + " out runner " + id + "-" + time + ": " + acquire);
        return acquire;
    }
}
