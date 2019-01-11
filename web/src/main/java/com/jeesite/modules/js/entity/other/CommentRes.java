package com.jeesite.modules.js.entity.other;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jeesite.modules.js.entity.Answer;
import com.jeesite.modules.js.entity.Comment;
import com.jeesite.modules.js.entity.JsUser;
import com.jeesite.modules.js.entity.Question;

import javax.xml.crypto.Data;
import java.util.Date;

public class CommentRes {

	private String zone;		// zone
	private String fatherCommentId;		// father_comment_id
	private String toUserId;		// to_user_id
	private String fromUserId;		// 留言者，评论的用户id
	private String comment;		// 评论内容

    private String fromName;
    private String ToName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public CommentRes(Comment comment) {
        this.zone = comment.getZone();
        this.fatherCommentId = comment.getFatherCommentId();
        this.toUserId = comment.getToUserId();
        this.fromUserId = comment.getFromUserId();
        this.comment = comment.getComment();
        this.createTime = comment.getCreateDate();
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getFatherCommentId() {
        return fatherCommentId;
    }

    public void setFatherCommentId(String fatherCommentId) {
        this.fatherCommentId = fatherCommentId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

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

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getToName() {
        return ToName;
    }

    public void setToName(String toName) {
        ToName = toName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
