/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.service;

import java.util.List;

import com.jeesite.modules.js.entity.other.TempAnswerRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.js.entity.Answer;
import com.jeesite.modules.js.dao.AnswerDao;

/**
 * 答案Service
 * @author jo
 * @version 2018-12-04
 */
@Service
@Transactional(readOnly=true)
public class AnswerService extends CrudService<AnswerDao, Answer> {

	@Autowired
	private AnswerDao answerDao;

	/**
	 * 获取单条数据
	 * @param answer
	 * @return
	 */
	@Override
	public Answer get(Answer answer) {
		return super.get(answer);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param answer
	 * @return
	 */
	@Override
	public Page<Answer> findPage(Page<Answer> page, Answer answer) {
		return super.findPage(page, answer);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param answer
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Answer answer) {
		super.save(answer);
	}
	
	/**
	 * 更新状态
	 * @param answer
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Answer answer) {
		super.updateStatus(answer);
	}
	
	/**
	 * 删除数据
	 * @param answer
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Answer answer) {
		super.delete(answer);
	}

	@Transactional(readOnly=false)
	public Boolean havaPass(String userId, String questionId) {
		if (answerDao.havaPass(userId, questionId) != null) {
			return true;
		}
		return false;
	}

	@Transactional(readOnly=false)
	public List<Answer> queryLikeAnswer(List<String> likeAnswerId) {
		return answerDao.queryLikeAnswer(likeAnswerId);
	}

	@Transactional(readOnly=false)
	public List<Answer> queryCollectAnswer(List<String> collectAnswerIds) {
		return answerDao.queryCollectAnswer(collectAnswerIds);
	}

	@Transactional(readOnly=false)
	public List<TempAnswerRes> queryAnswerByUserId(String userId) {
		return answerDao.queryAnswerByUserId(userId);
	}

	@Transactional(readOnly=false)
	public List<TempAnswerRes> queryAnswerByQuestionId(String questionId) {
		return answerDao.queryAnswerByQuestionId(questionId);
	}

	@Transactional(readOnly=false)
	public List<TempAnswerRes> queryLikeAnswerByQuestionId(String userId) {
		return answerDao.queryLikeAnswerByQuestionId(userId);
	}


	@Transactional(readOnly=false)
	public List<TempAnswerRes> queryCollectAnswerByQuestionId(String userId) {
		return answerDao.queryCollectAnswerByQuestionId(userId);
	}

	@Transactional(readOnly=false)
	public List<String> likeAnswers(String userId) {
		return answerDao.likeAnswers(userId);
	}


	@Transactional(readOnly=false)
	public List<String> collectAnswers(String userId) {
		return answerDao.collectAnswers(userId);
	}




}