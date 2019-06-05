package com.jeesite.modules.js.entity.other;

public class QuestionSearchRes {
    private String id;
    private String name;
    private Integer score;
    private Integer lowRank;
    private Integer highRank;
    private String mobile;
    private Integer count;
    private String userId;
    private boolean isAnswered;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getLowRank() {
        return lowRank;
    }

    public void setLowRank(Integer lowRank) {
        this.lowRank = lowRank;
    }

    public Integer getHighRank() {
        return highRank;
    }

    public void setHighRank(Integer highRank) {
        this.highRank = highRank;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }
    @Override
    public String toString() {
        return id;
    }
}
