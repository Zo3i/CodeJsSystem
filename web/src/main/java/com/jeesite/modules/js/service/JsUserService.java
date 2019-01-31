/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.service;

import java.util.List;

import com.jeesite.modules.js.entity.other.UserRankRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.js.entity.JsUser;
import com.jeesite.modules.js.dao.JsUserDao;
import com.jeesite.modules.file.utils.FileUploadUtils;

/**
 * jsUserService
 * @author jo
 * @version 2018-11-05
 */
@Service
@Transactional(readOnly=true)
public class JsUserService extends CrudService<JsUserDao, JsUser> {

	@Autowired
	private JsUserDao jsUserDao;

	/**
	 * 获取单条数据
	 * @param jsUser
	 * @return
	 */
	@Override
	public JsUser get(JsUser jsUser) {
		return super.get(jsUser);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param jsUser
	 * @return
	 */
	@Override
	public Page<JsUser> findPage(Page<JsUser> page, JsUser jsUser) {
		return super.findPage(page, jsUser);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param jsUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(JsUser jsUser) {
		super.save(jsUser);
		// 保存上传图片
		FileUploadUtils.saveFileUpload(jsUser.getId(), "jsUser_image");
	}
	
	/**
	 * 更新状态
	 * @param jsUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(JsUser jsUser) {
		super.updateStatus(jsUser);
	}
	
	/**
	 * 删除数据
	 * @param jsUser
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(JsUser jsUser) {
		super.delete(jsUser);
	}

	@Transactional(readOnly=false)
	public List<UserRankRes> getUserRank() {
		return jsUserDao.getUserRank();
	}
	
}