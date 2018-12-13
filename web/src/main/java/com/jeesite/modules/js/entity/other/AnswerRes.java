package com.jeesite.modules.js.entity.other;

import com.jeesite.modules.js.entity.Answer;
import com.jeesite.modules.js.entity.JsUser;
import com.jeesite.modules.js.entity.Question;

public class AnswerRes {


    private String id;
	private String questionId;		// 问题ID
	private String userId;		// 用户ID
	private String answer;		// 答案
	private String userMobile; //用户手机号
	private Boolean isLike;    //当前用户是否喜欢
	private Boolean isCollect; //当前用户是否点赞
	private Integer totalLike; //该答案获取的所有like数量
	private Integer totalCollect;//改答案所有搜藏数量
	private Question question; //问题详情
	private JsUser user; //答题者详情


    public AnswerRes(Answer s) {
        this.id = s.getId();
        this.questionId = s.getQuestionId();
        this.userId = s.getUserId();
        this.answer = s.getAnswer();
        this.userMobile = s.getUserMobile();
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

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public JsUser getUser() {
        return user;
    }

    public void setUser(JsUser user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
