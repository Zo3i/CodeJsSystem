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
 * team_memberEntity
 * @author jo
 * @version 2018-12-17
 */
@Table(name="${_prefix}team_member", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="team_id", attrName="teamId", label="team_id"),
		@Column(name="user_id", attrName="userId", label="user_id"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class TeamMember extends DataEntity<TeamMember> {
	
	private static final long serialVersionUID = 1L;
	private String teamId;		// team_id
	private String userId;		// user_id
	private String mobile;

	private JsUser jsUser;
	
	public TeamMember() {
		this(null);
	}

	public TeamMember(String id){
		super(id);
	}
	
	@Length(min=0, max=32, message="team_id长度不能超过 32 个字符")
	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	
	@Length(min=0, max=32, message="user_id长度不能超过 32 个字符")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public JsUser getJsUser() {
		return jsUser;
	}

	public void setJsUser(JsUser jsUser) {
		this.jsUser = jsUser;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}