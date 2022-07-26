package com.panerai.limiter.fixed;

import java.util.concurrent.atomic.AtomicLong;

public class FixedWindow {
    private static final int QPS = 2;

    private static final long TIME_WINDOW = 1000L; // 毫秒

    private static AtomicLong REQ_COUNT = new AtomicLong(0L);

    private static long START_TIME = System.currentTimeMillis();

    public synchronized static boolean tryAcquire() {
        if ((System.currentTimeMillis() - START_TIME) > TIME_WINDOW) {
            START_TIME = System.currentTimeMillis();
            REQ_COUNT.set(0L);
        }
        return REQ_COUNT.incrementAndGet() <= QPS;
    }
}
