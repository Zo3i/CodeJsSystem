/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.entity.other;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.modules.js.entity.Question;

import java.util.Date;

/**
 * js_team_infoEntity
 * @author jo
 * @version 2018-12-17
 */
public class QuestionRes {

	private String name;		// team_name
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;		// team_creator_id
	private Integer rank;		// rank

	public QuestionRes(Question question) {
		this.name = question.getName();
		this.createTime = question.getCreateTime();
		this.rank = question.getScore();
	}

	public QuestionRes() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}
}