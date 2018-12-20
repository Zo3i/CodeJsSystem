/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.js.entity.Answer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 答案DAO接口
 * @author jo
 * @version 2018-12-04
 */
@MyBatisDao
public interface AnswerDao extends CrudDao<Answer> {
    Answer havaPass(@Param("userId") String userId, @Param("questionId") String questionId);
    List<Answer> queryLikeAnswer(@Param("likeAnswerIds") List<String> likeAnswerIds);
    List<Answer> queryCollectAnswer(@Param("collectAnswerIds") List<String> collectAnswerIds);
}