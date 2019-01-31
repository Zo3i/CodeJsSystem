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
import com.jeesite.modules.js.entity.ResultRecord;
import com.jeesite.modules.js.dao.ResultRecordDao;

/**
 * js_result_recordService
 * @author jo
 * @version 2019-01-29
 */
@Service
@Transactional(readOnly=true)
public class ResultRecordService extends CrudService<ResultRecordDao, ResultRecord> {

	@Autowired
	private ResultRecordDao resultRecordDao;

	/**
	 * 获取单条数据
	 * @param resultRecord
	 * @return
	 */
	@Override
	public ResultRecord get(ResultRecord resultRecord) {
		return super.get(resultRecord);
	}
	
	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param resultRecord
	 * @return
	 */
	@Override
	public Page<ResultRecord> findPage(Page<ResultRecord> page, ResultRecord resultRecord) {
		return super.findPage(page, resultRecord);
	}
	
	/**
	 * 保存数据（插入或更新）
	 * @param resultRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void save(ResultRecord resultRecord) {
		super.save(resultRecord);
	}
	
	/**
	 * 更新状态
	 * @param resultRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void updateStatus(ResultRecord resultRecord) {
		super.updateStatus(resultRecord);
	}
	
	/**
	 * 删除数据
	 * @param resultRecord
	 */
	@Override
	@Transactional(readOnly=false)
	public void delete(ResultRecord resultRecord) {
		super.delete(resultRecord);
	}

	@Transactional(readOnly=false)
	public void del(String questionId) {
		resultRecordDao.del(questionId);
	};
	
}