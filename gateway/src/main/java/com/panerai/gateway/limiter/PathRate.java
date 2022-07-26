package com.panerai.gateway.limiter;

public class PathRate {

    private String path;

    private Integer burstCapacity;

    private Integer replenishRate;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getBurstCapacity() {
        return burstCapacity;
    }

    public void setBurstCapacity(Integer burstCapacity) {
        this.burstCapacity = burstCapacity;
    }

    public Integer getReplenishRate() {
        return replenishRate;
    }

    public void setReplenishRate(Integer replenishRate) {
        this.replenishRate = replenishRate;
    }
}
