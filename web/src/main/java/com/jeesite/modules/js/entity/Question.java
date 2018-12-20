/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.entity;

import com.jeesite.common.collect.ListUtils;
import org.hibernate.validator.constraints.Length;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;

import java.util.Date;
import java.util.List;

/**
 * js_questionEntity
 * @author jo
 * @version 2018-11-01
 */
@Table(name="${_prefix}question", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="name", attrName="name", label="name", queryType=QueryType.LIKE),
		@Column(name="score", attrName="score", label="score"),
		@Column(name="question_init", attrName="questionInit", label="questionInit"),
		@Column(name="description", attrName="description", label="description"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class Question extends DataEntity<Question> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private String description;		// description
	private int score;
	private String questionInit;
	private Date createTime;

	private List<QuestionTasks> questionTasksList = ListUtils.newArrayList();
	
	public Question() {
		this(null);
	}

	public Question(String id){
		super(id);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getQuestionInit() {
		return questionInit;
	}

	public void setQuestionInit(String questionInit) {
		this.questionInit = questionInit;
	}

	@Length(min=0, max=50, message="name长度不能超过 50 个字符")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=2000, message="description长度不能超过 2000 个字符")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<QuestionTasks> getQuestionTasksList() {
		return questionTasksList;
	}

	public void setQuestionTasksList(List<QuestionTasks> questionTasksList) {
		this.questionTasksList = questionTasksList;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}