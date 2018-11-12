/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.jeesite.modules.js.entity.JsUser;
import com.jeesite.modules.js.service.JsUserService;

/**
 * jsUserController
 * @author jo
 * @version 2018-11-05
 */
@Controller
@RequestMapping(value = "${adminPath}/js/jsUser")
public class JsUserController extends BaseController {

	@Autowired
	private JsUserService jsUserService;
	
	/**
	 * 获取数据
	 */
	@ModelAttribute
	public JsUser get(String id, boolean isNewRecord) {
		return jsUserService.get(id, isNewRecord);
	}
	
	/**
	 * 查询列表
	 */
	@RequiresPermissions("js:jsUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(JsUser jsUser, Model model) {
		model.addAttribute("jsUser", jsUser);
		return "modules/js/jsUserList";
	}
	
	/**
	 * 查询列表数据
	 */
	@RequiresPermissions("js:jsUser:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<JsUser> listData(JsUser jsUser, HttpServletRequest request, HttpServletResponse response) {
		Page<JsUser> page = jsUserService.findPage(new Page<JsUser>(request, response), jsUser); 
		return page;
	}

	/**
	 * 查看编辑表单
	 */
	@RequiresPermissions("js:jsUser:view")
	@RequestMapping(value = "form")
	public String form(JsUser jsUser, Model model) {
		model.addAttribute("jsUser", jsUser);
		return "modules/js/jsUserForm";
	}

	/**
	 * 保存js_user
	 */
	@RequiresPermissions("js:jsUser:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated JsUser jsUser) {
		jsUserService.save(jsUser);
		return renderResult(Global.TRUE, text("保存js_user成功！"));
	}
	
	/**
	 * 删除js_user
	 */
	@RequiresPermissions("js:jsUser:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(JsUser jsUser) {
		jsUserService.delete(jsUser);
		return renderResult(Global.TRUE, text("删除js_user成功！"));
	}
	
}