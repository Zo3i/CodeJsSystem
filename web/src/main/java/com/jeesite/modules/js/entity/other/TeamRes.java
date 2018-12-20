/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.entity.other;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.common.mybatis.annotation.Column;
import com.jeesite.common.mybatis.annotation.Table;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import org.hibernate.validator.constraints.Length;

/**
 * js_team_infoEntity
 * @author jo
 * @version 2018-12-17
 */
public class TeamRes {

	private String teamName;		// team_name
	private String teamCreatorName;		// team_creator_id
	private Long totleRank;		// rank

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getTeamCreatorName() {
		return teamCreatorName;
	}

	public void setTeamCreatorName(String teamCreatorName) {
		this.teamCreatorName = teamCreatorName;
	}

	public Long getTotleRank() {
		return totleRank;
	}

	public void setTotleRank(Long totleRank) {
		this.totleRank = totleRank;
	}
}