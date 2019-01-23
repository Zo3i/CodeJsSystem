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
 * js_question_tasksEntity
 * @author jo
 * @version 2019-01-22
 */
@Table(name="${_prefix}question_tasks", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="question_id", attrName="questionId", label="question_id"),
		@Column(name="task_question", attrName="taskQuestion", label="task_question"),
		@Column(name="task_answer", attrName="taskAnswer", label="task_answer"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class QuestionTasks extends DataEntity<QuestionTasks> {
	
	private static final long serialVersionUID = 1L;
	private String questionId;		// question_id
	private String taskQuestion;		// task_question
	private String taskAnswer;		// task_answer
	
	public QuestionTasks() {
		this(null);
	}

	public QuestionTasks(String id){
		super(id);
	}
	
	@Length(min=0, max=32, message="question_id长度不能超过 32 个字符")
	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	@Length(min=0, max=500, message="task_question长度不能超过 500 个字符")
	public String getTaskQuestion() {
		return taskQuestion;
	}

	public void setTaskQuestion(String taskQuestion) {
		this.taskQuestion = taskQuestion;
	}
	
	@Length(min=0, max=500, message="task_answer长度不能超过 500 个字符")
	public String getTaskAnswer() {
		return taskAnswer;
	}

	public void setTaskAnswer(String taskAnswer) {
		this.taskAnswer = taskAnswer;
	}
	
}