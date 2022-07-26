package com.panerai.limiter;

import com.panerai.limiter.fixed.FixedWindowCaffeine;
import com.panerai.limiter.fixed.FixedWindowGuava;

import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LimiterTest {
    public static void main(String[] args) {
        int size = 10;
        CountDownLatch latch = new CountDownLatch(size);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for(int i = 0; i < size; i ++) {
            executorService.submit(new Runner(i, latch));
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

    public Runner(int id, CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(500);
            for (int i = 0; i < 10; i++) {
                Thread.sleep(250);
                LocalTime now = LocalTime.now();
                if (!FixedWindowCaffeine.tryAcquireWithFunction("xxx", this.id, i)) {
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