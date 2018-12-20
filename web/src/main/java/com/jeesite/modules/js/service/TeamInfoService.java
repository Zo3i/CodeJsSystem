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
import com.jeesite.modules.js.entity.TeamInfo;
import com.jeesite.modules.js.dao.TeamInfoDao;

/**
 * js_team_infoService
 * @author jo
 * @version 2018-12-17
 */
@Service
@Transactional(readOnly=true)
public class TeamInfoService extends CrudService<TeamInfoDao, TeamInfo> {


	@Autowired
	private TeamInfoDao teamInfoDao;
	/**
	 * 获取单条数据
	 * @param teamInfo
	 * @return
	 */
	@Override
	public TeamInfo get(TeamInfo teamInfo) {
		return super.get(teamInfo);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param teamInfo
	 * @return
	 */
	@Override
	public Page<TeamInfo> findPage(Page<TeamInfo> page, TeamInfo teamInfo) {
		return super.findPage(page, teamInfo);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param teamInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TeamInfo teamInfo) {
		super.save(teamInfo);
	}
	
	/**
	 * 更新状态
	 * @param teamInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TeamInfo teamInfo) {
		super.updateStatus(teamInfo);
	}
	
	/**
	 * 删除数据
	 * @param teamInfo
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TeamInfo teamInfo) {
		super.delete(teamInfo);
	}

	@Transactional(readOnly=false)
	public TeamInfo queryByName(String teamName){
		return teamInfoDao.queryByName(teamName);
	};


	@Transactional(readOnly=false)
	public TeamInfo queryByUserId(String userId){
		return teamInfoDao.queryByUserId(userId);
	};



}