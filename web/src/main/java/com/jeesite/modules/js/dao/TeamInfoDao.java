/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.js.entity.TeamInfo;
import org.apache.ibatis.annotations.Param;

/**
 * js_team_infoDAO接口
 * @author jo
 * @version 2018-12-17
 */
@MyBatisDao
public interface TeamInfoDao extends CrudDao<TeamInfo> {
	public TeamInfo queryByName(@Param("teamName") String teamName);
}