/**
 * Copyright (c) 2013-Now http://jeesite.com All rights reserved.
 */
package com.jeesite.modules.js.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jeesite.common.entity.Page;
import com.jeesite.common.service.CrudService;
import com.jeesite.modules.js.entity.TeamMember;
import com.jeesite.modules.js.dao.TeamMemberDao;

/**
 * team_memberService
 * @author jo
 * @version 2018-12-17
 */
@Service
@Transactional(readOnly=true)
public class TeamMemberService extends CrudService<TeamMemberDao, TeamMember> {
	
	/**
	 * 获取单条数据
	 * @param teamMember
	 * @return
	 */
	@Override
	public TeamMember get(TeamMember teamMember) {
		return super.get(teamMember);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param teamMember
	 * @return
	 */
	@Override
	public Page<TeamMember> findPage(Page<TeamMember> page, TeamMember teamMember) {
		return super.findPage(page, teamMember);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param teamMember
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(TeamMember teamMember) {
		super.save(teamMember);
	}
	
	/**
	 * 更新状态
	 * @param teamMember
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(TeamMember teamMember) {
		super.updateStatus(teamMember);
	}
	
	/**
	 * 删除数据
	 * @param teamMember
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(TeamMember teamMember) {
		super.delete(teamMember);
	}
	
}