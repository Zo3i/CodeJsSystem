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
public class MyInfo {
	private Integer totleRank;
	private Integer totleLike;
	private Integer totleCollect;
	private String team;

	public Integer getTotleRank() {
		return totleRank;
	}

	public void setTotleRank(Integer totleRank) {
		this.totleRank = totleRank;
	}

	public Integer getTotleLike() {
		return totleLike;
	}

	public void setTotleLike(Integer totleLike) {
		this.totleLike = totleLike;
	}

	public Integer getTotleCollect() {
		return totleCollect;
	}

	public void setTotleCollect(Integer totleCollect) {
		this.totleCollect = totleCollect;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
}