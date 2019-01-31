/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.js.entity.TeamInfo;
import com.jeesite.modules.js.entity.other.RankRes;
import com.jeesite.modules.js.entity.other.TeamRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * js_team_infoDAO接口
 * @author jo
 * @version 2018-12-17
 */
@MyBatisDao
public interface TeamInfoDao extends CrudDao<TeamInfo> {
	public TeamInfo queryByName(@Param("teamName") String teamName);
	public TeamInfo queryByUserId(@Param("userId") String userId);
	public List<RankRes> rank();

}