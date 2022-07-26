package com.panerai.limiter.tokenbucket;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.google.common.util.concurrent.RateLimiter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SentinelLimiterTest {
    private static void initFlowRules(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setCount(5);
        rule.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }

    public static void main(String[] args) {
        initFlowRules();
        int size = 20;
        CountDownLatch latch = new CountDownLatch(size);
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for(int i = 0; i < size; i ++) {
            executorService.submit(new XRunner(i, latch));
        }
        try {
            latch.await();
            executorService.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class XRunner implements Runnable {
    private CountDownLatch latch;
    private int id;

    public XRunner(int id, CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }

    @Override
    public void run() {
        try {
//            Thread.sleep(500);
            for (int i = 0; i < 500; i++) {
                Thread.sleep(100);
                LocalTime now = LocalTime.now();
                try (Entry entry = SphU.entry("HelloWorld")) {
                    System.out.println(now + " " + this.id  +"-" + i + " 做点什么");
                } catch (BlockException exception){
                    System.out.println(now + " " + this.id  +"-" + i  + " 被限流");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        latch.countDown();
    }
}