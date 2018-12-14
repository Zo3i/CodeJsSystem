/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.service;

import java.util.List;

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



}