package com.ycsys.smartmap.sys.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.sys.dao.ConfigServerMonitorDao;
import com.ycsys.smartmap.sys.entity.ConfigServerEngine;
import com.ycsys.smartmap.sys.entity.ConfigServerMonitor;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.ConfigServerEngineService;
import com.ycsys.smartmap.sys.service.ConfigServerMonitorService;

/**
 * 
 * @author liweixiong
 * @date   2016年11月2日
 */
@Service("configServerMonitorService")
public class ConfigServerMonitorServiceImpl implements
		ConfigServerMonitorService {

	@Autowired
	private ConfigServerMonitorDao configServerMonitorDao;

	public Integer save(ConfigServerMonitor o) {
		// TODO Auto-generated method stub
		return (Integer) configServerMonitorDao.save(o);
	}

	public void delete(ConfigServerMonitor o) {
		// TODO Auto-generated method stub
		configServerMonitorDao.delete(o);
	}

	public void update(ConfigServerMonitor o) {
		// TODO Auto-generated method stub
		configServerMonitorDao.update(o);
	}

	public void saveOrUpdate(ConfigServerMonitor o) {
		// TODO Auto-generated method stub
		configServerMonitorDao.saveOrUpdate(o);
	}

	public List<ConfigServerMonitor> find(String hql) {
		// TODO Auto-generated method stub
		return configServerMonitorDao.find(hql);
	}

	public List<ConfigServerMonitor> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return configServerMonitorDao.find(hql, param);
	}

	public List<ConfigServerMonitor> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return configServerMonitorDao.find(hql, param);
	}

	public ConfigServerMonitor get(Class<ConfigServerMonitor> c, Serializable id) {
		// TODO Auto-generated method stub
		return configServerMonitorDao.get(c, id);
	}

	public ConfigServerMonitor get(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return configServerMonitorDao.get(hql, param);
	}

	public ConfigServerMonitor get(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return configServerMonitorDao.get(hql, param);
	}

	@Override
	public List<ConfigServerMonitor> find(String hql, Object[] param,
			PageHelper page) {
		// TODO Auto-generated method stub
		return configServerMonitorDao.find(hql, param, page);
	}
	
	
}
