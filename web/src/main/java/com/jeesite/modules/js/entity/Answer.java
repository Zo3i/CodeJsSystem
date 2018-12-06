/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.entity;

import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

/**
 * 答案Entity
 * @author jo
 * @version 2018-12-04
 */
@Table(name="${_prefix}answer", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="question_id", attrName="questionId", label="问题ID"),
		@Column(name="user_id", attrName="userId", label="用户ID"),
		@Column(name="answer", attrName="answer", label="答案"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class Answer extends DataEntity<Answer> {
	
	private static final long serialVersionUID = 1L;
	private String questionId;		// 问题ID
	private String userId;		// 用户ID
	private String answer;		// 答案
	private String userMobile;
	
	public Answer() {
		this(null);
	}

	public Answer(String id){
		super(id);
	}
	
	@Length(min=0, max=50, message="问题ID长度不能超过 50 个字符")
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	@Length(min=0, max=32, message="用户ID长度不能超过 32 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=5000, message="答案长度不能超过 5000 个字符")
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
}