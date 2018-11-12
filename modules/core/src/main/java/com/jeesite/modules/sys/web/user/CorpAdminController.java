/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.sys.web.user;

import java.util.List;

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
import com.jeesite.common.lang.StringUtils;
import com.jeesite.common.mybatis.mapper.query.QueryType;
import com.jeesite.common.web.BaseController;
import com.jeesite.modules.sys.entity.EmpUser;
import com.jeesite.modules.sys.entity.User;
import com.jeesite.modules.sys.service.UserService;
import com.jeesite.modules.sys.utils.UserUtils;

/**
 * 系统管理员Controller
 * @author ThinkGem
 * @version 2017-03-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/corpAdmin")
public class CorpAdminController extends BaseController {

	@Autowired
	private UserService userService;

	@ModelAttribute
	public User get(String userCode, boolean isNewRecord) {
		return userService.get(userCode, isNewRecord);
	}
	
	@RequiresPermissions("sys:corpAdmin:view")
	@RequestMapping(value = "list")
	public String list(User user, Model model) {
		user.setCorpCode(null);
		user.setCorpName(null);
		return "modules/sys/user/corpAdminList";
	}

	@RequiresPermissions("sys:corpAdmin:view")
	@RequestMapping(value = "listData")
	@ResponseBody
	public Page<User> listData(User user, HttpServletRequest request, HttpServletResponse response) {
		user.setMgrType(User.MGR_TYPE_CORP_ADMIN);	// 租户管理员
		// 禁用自动添加租户代码条件，添加自定义租户查询条件
		user.getSqlMap().getWhere().disableAutoAddCorpCodeWhere()
			.and("corp_code", QueryType.EQ, user.getCorpCode_())
			.and("corp_name", QueryType.LIKE, user.getCorpName_());
		Page<User> page = userService.findPage(new Page<User>(request, response), user);
		return page;
	}

	@RequiresPermissions("sys:corpAdmin:view")
	@RequestMapping(value = "form")
	public String form(User user, String op, Model model) {
		if (user.getIsNewRecord()){
			// 新增租户管理员，如果已存在，则不能保存
			if ("addCorp".equals(op)){
				user.setCorpCode_(StringUtils.EMPTY);  // 租户代码
				user.setCorpName_(StringUtils.EMPTY);  // 租户名称
			}
		}
		// 操作类型：addCorp: 添加租户； addAdmin: 添加管理员； edit: 编辑
		model.addAttribute("op", op);
		model.addAttribute("user", user);
		return "modules/sys/user/corpAdminForm";
	}

	@RequiresPermissions("sys:corpAdmin:edit")
	@PostMapping(value = "save")
	@ResponseBody
	public String save(@Validated User user, String oldLoginCode, String op) {
		if (!user.getCurrentUser().isSuperAdmin()){
			return renderResult(Global.FALSE, "越权操作，只有超级管理员才能修改此数据！");
		}
		if (User.isSuperAdmin(user.getUserCode())) {
			return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
		}
		if (!EmpUser.USER_TYPE_EMPLOYEE.equals(user.getUserType())){
			return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
		}
		if (StringUtils.isBlank(user.getCorpCode_())){
			return renderResult(Global.FALSE, "租户代码不能为空！");
		}
		if (!Global.TRUE.equals(userService.checkLoginCode(oldLoginCode, user.getLoginCode()/*, user.getCorpCode_()*/))) {
			return renderResult(Global.FALSE, "保存用户'" + user.getLoginCode() + "'失败，登录账号已存在");
		}
		if (user.getIsNewRecord()){
			user.setUserType(User.USER_TYPE_NONE); // 仅登录用户
		}
		user.setMgrType(User.MGR_TYPE_CORP_ADMIN); // 租户管理员
		// 如果新增，则验证租户代码合法性
		if (user.getIsNewRecord()){
			User where = new User();
			where.setCorpCode_(user.getCorpCode_());
			List<User> list = userService.findCorpList(where);
			if (list.size() > 0){
				// 新增租户，如果已存在，则不能保存
				if ("addCorp".equals(op)){
					return renderResult(Global.FALSE, "保存用户失败，租户代码已存在");
				}
				// 新增管理员，则使用已有的租户代码和名称
				else if ("addAdmin".equals(op)){
					user.setCorpCode_(list.get(0).getCorpCode_());
					user.setCorpName_(list.get(0).getCorpName_());
				}
				// 非法操作
				else{
					return renderResult(Global.FALSE, "非法操作，参数错误。");
				}
			}
		}
		userService.save(user);
		// 如果修改的是当前用户，则清除当前用户缓存
		if (user.getUserCode().equals(UserUtils.getUser().getUserCode())) {
			UserUtils.clearCache();
		}
		return renderResult(Global.TRUE, "保存管理员'" + user.getUserCode() + "'成功");
	}

	/**
	 * 停用用户
	 * @param user
	 * @return
	 */
	@RequiresPermissions("sys:corpAdmin:edit")
	@ResponseBody
	@RequestMapping(value = "disable")
	public String disable(User user) {
		if (User.isSuperAdmin(user.getUserCode())) {
			return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
		}
		if (user.getCurrentUser().getUserCode().equals(user.getUserCode())) {
			return renderResult(Global.FALSE, "停用用户失败, 不允许停用当前用户");
		}
		user.setStatus(User.STATUS_DISABLE);
		userService.updateStatus(user);
		return renderResult(Global.TRUE, "停用用户成功");
	}
	
	/**
	 * 启用用户
	 * @param user
	 * @return
	 */
	@RequiresPermissions("sys:corpAdmin:edit")
	@ResponseBody
	@RequestMapping(value = "enable")
	public String enable(User user) {
		if (User.isSuperAdmin(user.getUserCode())) {
			return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
		}
		user.setStatus(User.STATUS_NORMAL);
		userService.updateStatus(user);
		return renderResult(Global.TRUE, "启用用户成功");
	}
	
	/**
	 * 密码重置
	 * @param user
	 * @return
	 */
	@RequiresPermissions("sys:corpAdmin:edit")
	@RequestMapping(value = "resetpwd")
	@ResponseBody
	public String resetpwd(User user) {
		if (User.isSuperAdmin(user.getUserCode())) {
			return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
		}
		userService.updatePassword(user.getUserCode(), null);
		return renderResult(Global.TRUE, "重置用户密码成功");
	}

	/**
	 * 删除用户
	 * @param user
	 * @return
	 */
	@RequiresPermissions("sys:corpAdmin:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(User user) {
		if (User.isSuperAdmin(user.getUserCode())) {
			return renderResult(Global.FALSE, "非法操作，不能够操作此用户！");
		}
		if (user.getCurrentUser().getUserCode().equals(user.getUserCode())) {
			return renderResult(Global.FALSE, "删除用户失败，不允许删除当前用户");
		}
		if (User.USER_TYPE_NONE.equals(user.getUserType())){
			// 删除系统管理员
			userService.delete(user);
			return renderResult(Global.TRUE, "删除用户'" + user.getUserName() + "'成功！");
		}else{
			// 取消系统管理员身份
			user.setMgrType(User.MGR_TYPE_NOT_ADMIN);
			userService.updateMgrType(user);
			return renderResult(Global.TRUE, "取消用户'" + user.getUserName() + "'管理员身份成功！");
		}
	}

}
