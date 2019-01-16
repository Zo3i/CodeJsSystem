/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.service;

import java.util.List;

import com.jeesite.modules.js.dao.QuestionTasksDao;
import com.jeesite.modules.js.entity.QuestionTasks;
import com.jeesite.modules.js.entity.other.QuestionRes;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.js.entity.Question;
import com.jeesite.modules.js.dao.QuestionDao;

/**
 * js_questionService
 * @author jo
 * @version 2018-11-01
 */
@Service
@Transactional(readOnly=true)
public class QuestionService extends CrudService<QuestionDao, Question> {
	@Autowired
	private QuestionTasksDao questionTasksDao;
	@Autowired
	private QuestionDao questionDao;
	/**
	 * 获取单条数据
	 * @param question
	 * @return
	 */
	@Override
	public Question get(Question question) {
		return super.get(question);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param question
	 * @return
	 */
	@Override
	public Page<Question> findPage(Page<Question> page, Question question) {
		return super.findPage(page, question);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param question
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Question question) {
		super.save(question);
	}
	
	/**
	 * 更新状态
	 * @param question
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Question question) {
		super.updateStatus(question);
	}
	
	/**
	 * 删除数据
	 * @param question
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Question question) {
		super.delete(question);
	}

	/**
	 * 保存问题task
	 * @param
	 */
	@Transactional(readOnly=false)
	public void savaTask(Question question) {
		//保存之前先删除原有的task
		questionTasksDao.del(question.getId());
		for (QuestionTasks questionTasks : question.getQuestionTasksList()) {
			questionTasks.setQuestionId(question.getId());
			questionTasks.setTask(StringEscapeUtils.unescapeHtml4(questionTasks.getTask()));
			questionTasksDao.insert(questionTasks);
		}
	}

	/**
	 * @Desciption:
	 * @version:v-1.00
	 * @return: A random Question
	 * @author:Jo
	 */
	@Transactional(readOnly = false)
	public Question getRandomQuestion (String userId) {
		return questionDao.getRandomQuestion(userId);
	}


	@Transactional(readOnly = false)
	public List<Question> getAllQuestion(String userId) {
		return questionDao.getAllQuestion(userId);
	};

}