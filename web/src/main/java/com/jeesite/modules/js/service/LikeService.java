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
import com.jeesite.modules.js.entity.Like;
import com.jeesite.modules.js.dao.LikeDao;

/**
 * js_likeService
 * @author jo
 * @version 2018-12-07
 */
@Service
@Transactional(readOnly=true)
public class LikeService extends CrudService<LikeDao, Like> {

	@Autowired
	private LikeDao likeDao;
	/**
	 * 获取单条数据
	 * @param like
	 * @return
	 */
	@Override
	public Like get(Like like) {
		return super.get(like);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param like
	 * @return
	 */
	@Override
	public Page<Like> findPage(Page<Like> page, Like like) {
		return super.findPage(page, like);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param like
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(Like like) {
		super.save(like);
	}
	
	/**
	 * 更新状态
	 * @param like
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(Like like) {
		super.updateStatus(like);
	}
	
	/**
	 * 删除数据
	 * @param like
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(Like like) {
		likeDao.deleteByEntity(like);
	}

	@Transactional(readOnly=false)
	public void del(Like like) {
		likeDao.del(likeDao.findList(like).get(0).getId());
	}

	@Transactional(readOnly = false)
	public Like isLike (String answerId, String userId, String authorId){
		return likeDao.isLike(answerId, userId, authorId);
	}

}