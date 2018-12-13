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
 * js_likeEntity
 * @author jo
 * @version 2018-12-07
 */
@Table(name="${_prefix}like", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="userid", attrName="userid", label="userid"),
		@Column(name="authorid", attrName="authorid", label="authorid"),
		@Column(name="answerid", attrName="answerid", label="answerid"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class Like extends DataEntity<Like> {
	
	private static final long serialVersionUID = 1L;
	private String userid;		// userid
	private String authorid;		// authorid
	private String answerid;		// answerid
	private String mobile;
	
	public Like() {
		this(null);
	}

	public Like(String answerid){
		this.answerid = answerid;
	}

	public Like(String answerid, String userid, String authorid){
		this.answerid = answerid;
		this.userid = userid;
		this.answerid = this.authorid;
	}

	
	@Length(min=0, max=32, message="userid长度不能超过 32 个字符")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Length(min=0, max=32, message="authorid长度不能超过 32 个字符")
	public String getAuthorid() {
		return authorid;
	}

	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}
	
	@Length(min=0, max=32, message="answerid长度不能超过 32 个字符")
	public String getAnswerid() {
		return answerid;
	}

	public void setAnswerid(String answerid) {
		this.answerid = answerid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}