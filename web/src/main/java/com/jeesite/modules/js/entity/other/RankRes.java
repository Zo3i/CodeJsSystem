package com.jeesite.modules.js.entity.other;

public class RankRes {

    private String name;
    private String best;
    private Integer TotalRank;
    private Integer count;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBest() {
        return best;
    }

    public void setBest(String best) {
        this.best = best;
    }

    public Integer getTotalRank() {
        return TotalRank;
    }

    public void setTotalRank(Integer totalRank) {
        TotalRank = totalRank;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
