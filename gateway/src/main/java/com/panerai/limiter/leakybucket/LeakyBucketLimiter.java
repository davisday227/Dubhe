package com.panerai.limiter.leakybucket;

import java.time.LocalDateTime;

public class LeakyBucketLimiter {
    //流水速率  固定
    private int rate;
    //桶的大小
    private int burst;
    //最后更新时间
    private int refreshTime;
    //private Long refreshTime;
    //桶里面的水量
    private int water;

    public LeakyBucketLimiter(int rate, int burst) {
        this.rate = rate;
        this.burst = burst;
    }

    /**
     * 刷新桶的水量
     */
    private void refreshWater() {
        //long now = System.currentTimeMillis(); //毫秒生成
        LocalDateTime time = LocalDateTime.now(); //每秒生成
        int now = time.getSecond();
        //现在时间-上次更新的时间   中间花费的时间(秒)*流水速率=流水量(处理的请求的数量)  通过上次水总量减去流水量等于现在的水量
        //如果流水量太多导致桶里都没那么多水就应该置0, 所以通过math.max函数实现
        water = (int) Math.max(0, water - (now - refreshTime) * rate);
        //更新上次时间
        refreshTime = now;
    }

    /**
     * 获取令牌
     */
    public synchronized boolean tryAcquire() {
        //刷新桶的水量
        refreshWater();
        //如果桶的水量小于桶的容量就可以添加进来
        if (water < burst) {
            water++;
            return true;
        } else {
            return false;
        }
    }
}
