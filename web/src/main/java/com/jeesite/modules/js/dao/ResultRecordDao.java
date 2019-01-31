/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.js.entity.ResultRecord;
import org.apache.ibatis.annotations.Param;

/**
 * js_result_recordDAO接口
 * @author jo
 * @version 2019-01-29
 */
@MyBatisDao
public interface ResultRecordDao extends CrudDao<ResultRecord> {
	public void del(@Param("questionId") String questionId);
}