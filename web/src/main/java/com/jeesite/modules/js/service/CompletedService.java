/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.js.entity.Completed;
import com.jeesite.modules.js.dao.CompletedDao;

/**
 * js_completedService
 * @author jo
 * @version 2018-12-13
 */
@Service
@Transactional(readOnly=true)
public class CompletedService extends CrudService<CompletedDao, Completed> {
	
	/**
	 * 获取单条数据
	 * @param completed
	 * @return
	 */
	@Override
	public Completed get(Completed completed) {
		return super.get(completed);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param completed
	 * @return
	 */
	@Override
	public Page<Completed> findPage(Page<Completed> page, Completed completed) {
		return super.findPage(page, completed);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param completed
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Completed completed) {
		super.save(completed);
	}
	
	/**
	 * 更新状态
	 * @param completed
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Completed completed) {
		super.updateStatus(completed);
	}
	
	/**
	 * 删除数据
	 * @param completed
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Completed completed) {
		super.delete(completed);
	}
	
}