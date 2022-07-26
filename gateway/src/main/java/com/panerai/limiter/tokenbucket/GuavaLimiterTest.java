package com.panerai.limiter.tokenbucket;

import com.google.common.util.concurrent.RateLimiter;

import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GuavaLimiterTest {
    public static void main(String[] args) {
        int size = 20;
        CountDownLatch latch = new CountDownLatch(size);
        RateLimiter limiter = RateLimiter.create(5);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0; i < size; i ++) {
            executorService.submit(new Runner(i, latch, limiter));
        }
        try {
            latch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Runner implements Runnable {
    private CountDownLatch latch;
    private int id;
    private RateLimiter limiter;

    public Runner(int id, CountDownLatch latch, RateLimiter limiter) {
        this.id = id;
        this.latch = latch;
        this.limiter = limiter;
    }

    @Override
    public void run() {
        try {
//            Thread.sleep(500);
            for (int i = 0; i < 10; i++) {
                Thread.sleep(100);
                LocalTime now = LocalTime.now();
                if (!limiter.tryAcquire()) {
                    System.out.println(now + " " + this.id  +"-" + i + " 被限流");
                } else {
                    System.out.println(now + " " + this.id  +"-" + i  + " 做点什么");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
    }
}