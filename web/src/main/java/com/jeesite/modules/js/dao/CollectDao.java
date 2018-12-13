/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.js.entity.Collect;
import org.apache.ibatis.annotations.Param;

/**
 * js_collectDAO接口
 * @author jo
 * @version 2018-12-07
 */
@MyBatisDao
public interface CollectDao extends CrudDao<Collect> {
	void del(@Param("id") String id);
	Collect isCollect(@Param("answerId") String answerId, @Param("userId") String userId, @Param("authorId") String authorId);
}