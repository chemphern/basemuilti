package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.dao.LogDao;
import com.ycsys.smartmap.sys.entity.Log;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.LogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 日志service
 * @author ty
 * @date 2015年1月14日
 */
@Service("logService")
public class LogServiceImpl implements LogService {
	
	@Resource
	private LogDao logDao;
	/**
	 * 批量删除日志
	 * @param idList
	 */
	public void deleteLog(List<Integer> idList){
		logDao.deleteBatch(idList);
	}

	/***
	 * 查询所有日志
	 * */
	@Override
	public List<Log> findAll(PageHelper page) {
		return logDao.find("from Log",page);
	}

}
