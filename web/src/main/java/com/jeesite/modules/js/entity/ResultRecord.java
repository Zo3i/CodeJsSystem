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
 * js_result_recordEntity
 * @author jo
 * @version 2019-01-29
 */
@Table(name="${_prefix}result_record", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="mobile", attrName="mobile", label="mobile"),
		@Column(name="question_id", attrName="questionId", label="question_id"),
		@Column(name="result", attrName="result", label="result"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class ResultRecord extends DataEntity<ResultRecord> {
	
	private static final long serialVersionUID = 1L;
	private String mobile;		// mobile
	private String questionId;		// question_id
	private String result;		// result
	
	public ResultRecord() {
		this(null);
	}

	public ResultRecord(String id){
		super(id);
	}
	
	@Length(min=0, max=11, message="mobile长度不能超过 11 个字符")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Length(min=0, max=32, message="question_id长度不能超过 32 个字符")
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	@Length(min=0, max=255, message="result长度不能超过 255 个字符")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}


	
}