package com.panerai.limiter.fixed;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.time.LocalTime;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class FixedWindowGuava {
    private final static int QPS = 2;
    private final static Cache<String, AtomicLong> counter =
            CacheBuilder.newBuilder().expireAfterWrite(1L, TimeUnit.SECONDS).build();

    public static boolean tryAcquire(String key, int id, int time) {
        System.out.println(LocalTime.now() + " in runner " + id + "-" + time);
        AtomicLong curCount = null;
        try {
            curCount = counter.get(key, new Callable<AtomicLong>() {
                public AtomicLong call() throws Exception {
                    System.out.println("init count " + id + "-" + time);
                    return new AtomicLong(0L);
                }
            });
        } catch (ExecutionException e) {
            return false;
        }
        long l = curCount.incrementAndGet();
        System.out.println(LocalTime.now() + " out runner " + id + "-" + time + ": " + l);
        return l <= QPS;
    }
}
