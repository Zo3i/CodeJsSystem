/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.dao;

import com.jeesite.common.dao.CrudDao;
import com.jeesite.common.mybatis.annotation.MyBatisDao;
import com.jeesite.modules.js.entity.Answer;
import com.jeesite.modules.js.entity.other.TempAnswerRes;
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
    List<TempAnswerRes> queryAnswerByUserId(@Param("userId") String userId);
    List<TempAnswerRes> queryAnswerByQuestionId(@Param("questionId") String questionId);
    List<TempAnswerRes> queryLikeAnswerByQuestionId(@Param("userId") String userId);
    List<TempAnswerRes> queryCollectAnswerByQuestionId(@Param("userId") String userId);
    List<String> likeAnswers(@Param("userId") String userId);
    List<String> collectAnswers(@Param("userId") String userId);
}