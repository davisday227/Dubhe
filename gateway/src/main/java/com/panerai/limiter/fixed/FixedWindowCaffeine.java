package com.panerai.limiter.fixed;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;

public class FixedWindowCaffeine {
    private final static int QPS = 2;
    private final static Cache<String, AtomicLong> counter =
            Caffeine.newBuilder().expireAfterWrite(1000L, TimeUnit.MILLISECONDS).build();

    private static Function function = k -> {
        System.out.println("init count ");
        return new AtomicLong(0L);
    };

    public static boolean tryAcquireWithFunction(String key, int id, int time) {
        System.out.println(LocalTime.now() + " in runner " + id + "-" + time);

        AtomicLong curCount = counter.get(key, function);

        long l = curCount.incrementAndGet();
        System.out.println(LocalTime.now() + " out runner " + id + "-" + time + ": " + l);
        return l <= QPS;
    }
}
