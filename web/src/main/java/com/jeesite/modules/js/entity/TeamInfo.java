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
 * js_team_infoEntity
 * @author jo
 * @version 2018-12-17
 */
@Table(name="${_prefix}team_info", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="team_name", attrName="teamName", label="team_name", queryType=QueryType.LIKE),
		@Column(name="team_creator_id", attrName="teamCreatorId", label="team_creator_id"),
		@Column(name="rank", attrName="rank", label="rank"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class TeamInfo extends DataEntity<TeamInfo> {
	
	private static final long serialVersionUID = 1L;
	private String teamName;		// team_name
	private String teamCreatorId;		// team_creator_id
	private Long rank;		// rank
	private String mobile;
	private String token;
	
	public TeamInfo() {
		this(null);
	}

	public TeamInfo(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="team_name长度不能超过 64 个字符")
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	@Length(min=0, max=32, message="team_creator_id长度不能超过 32 个字符")
	public String getTeamCreatorId() {
		return teamCreatorId;
	}

	public void setTeamCreatorId(String teamCreatorId) {
		this.teamCreatorId = teamCreatorId;
	}
	
	public Long getRank() {
		return rank;
	}

	public void setRank(Long rank) {
		this.rank = rank;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}