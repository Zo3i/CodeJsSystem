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
 * js_completedEntity
 * @author jo
 * @version 2018-12-13
 */
@Table(name="${_prefix}completed", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="question_id", attrName="questionId", label="question_id"),
		@Column(name="user_id", attrName="userId", label="user_id"),
	}, orderBy="a.id DESC"
)
public class Completed extends DataEntity<Completed> {
	
	private static final long serialVersionUID = 1L;
	private String questionId;		// question_id
	private String userId;		// user_id
	
	public Completed() {
		this(null);
	}

	public Completed(String id){
		super(id);
	}
	
	@Length(min=0, max=32, message="question_id长度不能超过 32 个字符")
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	@Length(min=0, max=32, message="user_id长度不能超过 32 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}