/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.js.entity.JsUser;

/**
 * jsUserDAO接口
 * @author jo
 * @version 2018-11-05
 */
@MyBatisDao
public interface JsUserDao extends CrudDao<JsUser> {
	
}