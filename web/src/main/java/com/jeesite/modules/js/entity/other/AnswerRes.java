package com.jeesite.modules.js.entity.other;

public class AnswerRes {


    private String id;
	private String questionId;	// 问题ID
	private String userId;		// 用户ID
	private String answer;		// 答案
	private String userMobile; //用户手机号
	private Boolean isLike;    //当前用户是否喜欢
	private Boolean isCollect; //当前用户是否点赞
	private Integer totalLike; //该答案获取的所有like数量
	private Integer totalCollect;//改答案所有搜藏数量
    private String zoneId;
    private String name;
//	private Question question; //问题详情
//	private JsUser user; //答题者详情


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnswerRes(TempAnswerRes s) {
        this.id = s.getAnswerId();
        this.questionId = s.getQuestionId();
        this.userId = s.getUserId();
        this.answer = s.getAnswer();
        this.userMobile = s.getMobile();
        this.zoneId = s.getZoneId();
        this.totalLike = s.getLikeCount();
        this.totalCollect = s.getCollectCount();
        this.name = s.getName();
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public Boolean getLike() {
        return isLike;
    }

    public void setLike(Boolean like) {
        isLike = like;
    }

    public Boolean getCollect() {
        return isCollect;
    }

    public void setCollect(Boolean collect) {
        isCollect = collect;
    }

    public Integer getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(Integer totalLike) {
        this.totalLike = totalLike;
    }

    public Integer getTotalCollect() {
        return totalCollect;
    }

    public void setTotalCollect(Integer totalCollect) {
        this.totalCollect = totalCollect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }
}
