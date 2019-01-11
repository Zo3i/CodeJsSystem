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
 * js_commentEntity
 * @author jo
 * @version 2018-12-21
 */
@Table(name="${_prefix}comment", alias="a", columns={
		@Column(name="id", attrName="id", label="id", isPK=true),
		@Column(name="zone", attrName="zone", label="zone"),
		@Column(name="father_comment_id", attrName="fatherCommentId", label="father_comment_id"),
		@Column(name="to_user_id", attrName="toUserId", label="to_user_id"),
		@Column(name="from_user_id", attrName="fromUserId", label="留言者，评论的用户id"),
		@Column(name="comment", attrName="comment", label="评论内容"),
		@Column(includeEntity=DataEntity.class),
	}, orderBy="a.update_date DESC"
)
public class Comment extends DataEntity<Comment> {
	
	private static final long serialVersionUID = 1L;
	private String zone;		// zone
	private String fatherCommentId;		// father_comment_id
	private String toUserId;		// to_user_id
	private String fromUserId;		// 留言者，评论的用户id
	private String comment;		// 评论内容

	private String fromMobile;



	
	public Comment() {
		this(null);
	}

	public Comment(String id){
		super(id);
	}
	
	@Length(min=0, max=32, message="zone长度不能超过 32 个字符")
	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}
	
	@Length(min=0, max=32, message="father_comment_id长度不能超过 32 个字符")
	public String getFatherCommentId() {
		return fatherCommentId;
	}

	public void setFatherCommentId(String fatherCommentId) {
		this.fatherCommentId = fatherCommentId;
	}
	
	@Length(min=0, max=32, message="to_user_id长度不能超过 32 个字符")
	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}
	
	@Length(min=0, max=32, message="留言者，评论的用户id长度不能超过 32 个字符")
	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getFromMobile() {
		return fromMobile;
	}

	public void setFromMobile(String fromMobile) {
		this.fromMobile = fromMobile;
	}
}