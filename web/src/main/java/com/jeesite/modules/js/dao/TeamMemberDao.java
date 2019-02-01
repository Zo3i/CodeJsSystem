/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.js.entity.JsUser;
import com.jeesite.modules.js.entity.TeamMember;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * team_memberDAO接口
 * @author jo
 * @version 2018-12-17
 */
@MyBatisDao
public interface TeamMemberDao extends CrudDao<TeamMember> {
	public List<JsUser> list(@Param("teamId") String teamId);
}