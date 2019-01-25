/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.js.entity.Question;
import com.jeesite.modules.js.entity.other.QuestionRes;
import com.jeesite.modules.js.entity.other.QuestionSearchRes;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * js_questionDAO接口
 * @author jo
 * @version 2018-11-01
 */
@MyBatisDao
public interface QuestionDao extends CrudDao<Question> {
	Question getRandomQuestion(@Param("userId") String userId);
	List<Question> getAllQuestion(@Param("userId") String userId);
	List<QuestionSearchRes> queryByScore(@Param("userId") String userId, @Param("low") Integer low, @Param("high") Integer high);
}