/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeesite.common.entity.DataEntity;
import com.jeesite.modules.js.entity.QuestionTasks;
import com.jeesite.modules.js.service.QuestionTasksService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jeesite.common.config.Global;
import com.jeesite.common.entity.Page;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.js.entity.Question;
import com.jeesite.modules.js.service.QuestionService;

import java.util.List;

/**
 * js_questionController
 * @author jo
 * @version 2018-11-01
 */
@Controller
@RequestMapping(value = "${adminPath}/js/question")
public class QuestionController extends BaseController {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private QuestionTasksService questionTasksService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public Question get(String id, boolean isNewRecord) {
		return questionService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("js:question:view")
	@RequestMapping(value = {"list", ""})
	public String list(Question question, Model model) {
		model.addAttribute("question", question);
		return "modules/js/questionList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("js:question:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<Question> listData(Question question, HttpServletRequest request, HttpServletResponse response) {
		Page<Question> page = questionService.findPage(new Page<Question>(request, response), question); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("js:question:view")
	@RequestMapping(value = "form")
	public String form(Question question, Model model) {
		model.addAttribute("question", question);
		return "modules/js/questionForm";
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("js:question:view")
	@RequestMapping(value = "editForm")
	public String editForm(Question question, Model model) {
		QuestionTasks questionTask = new QuestionTasks();
		questionTask.setQuestionId(question.getId());
		List<QuestionTasks> questionTasks = questionTasksService.findList(questionTask);
		question.setQuestionTasksList(questionTasks);
		model.addAttribute("question", question);
		return "modules/js/questionForm";
	}

	/**
	 * 保存js_question
	 */
	@RequiresPermissions("js:question:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated Question question) {
		int itemCnt = 0;
		for (QuestionTasks s : question.getQuestionTasksList()) {
			if (DataEntity.STATUS_NORMAL.equals(s.getStatus())) {
				itemCnt++;
			}
		}
		if (itemCnt < 3) {
			return renderResult(Global.FALSE, text("最少3个题目"));
		}
		String questiondes = question.getDescription();
		question.setDescription(StringEscapeUtils.unescapeHtml4(questiondes));
		question.setRightAnswer(StringEscapeUtils.unescapeHtml4(question.getRightAnswer()));
		questionService.save(question);

		//删除原有的task
		QuestionTasks questionTask = new QuestionTasks();
		questionTask.setQuestionId(question.getId());

		//保存task
		questionService.savaTask(question);
		return renderResult(Global.TRUE, text("保存成功！"));
	}
	
	/**
	 * 删除js_question
	 */
	@RequiresPermissions("js:question:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Question question) {
		questionService.delete(question);
		return renderResult(Global.TRUE, text("删除js_question成功！"));
	}
	
}