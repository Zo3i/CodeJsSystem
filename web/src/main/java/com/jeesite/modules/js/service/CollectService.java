/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.service;

import java.util.List;

import com.jeesite.modules.js.entity.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.js.entity.Collect;
import com.jeesite.modules.js.dao.CollectDao;

/**
 * js_collectService
 * @author jo
 * @version 2018-12-07
 */
@Service
@Transactional(readOnly=true)
public class CollectService extends CrudService<CollectDao, Collect> {

	@Autowired
	private CollectDao collectDao;
	/**
	 * 获取单条数据
	 * @param collect
	 * @return
	 */
	@Override
	public Collect get(Collect collect) {
		return super.get(collect);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param collect
	 * @return
	 */
	@Override
	public Page<Collect> findPage(Page<Collect> page, Collect collect) {
		return super.findPage(page, collect);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param collect
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Collect collect) {
		super.save(collect);
	}
	
	/**
	 * 更新状态
	 * @param collect
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Collect collect) {
		super.updateStatus(collect);
	}
	
	/**
	 * 删除数据
	 * @param collect
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Collect collect) {
		super.delete(collect);
	}

	@Transactional(readOnly=false)
	public void del(Collect collect) {
		collectDao.del(collectDao.findList(collect).get(0).getId());
	}

	@Transactional(readOnly = false)
	public Collect isCollect (String answerId, String userId, String authorId){
		return collectDao.isCollect(answerId, userId, authorId);
	}


}