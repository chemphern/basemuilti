package com.ycsys.smartmap.sys.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ycsys.smartmap.sys.dao.ConfigServerEngineDao;
import com.ycsys.smartmap.sys.entity.ConfigServerEngine;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.ConfigServerEngineService;
import com.ycsys.smartmap.sys.service.UserService;
/**
 * 
 * @author 
 * @date   2016年11月17日
 */
@Service("configServerEngineService")
public class ConfigServerEngineServiceImpl implements ConfigServerEngineService{
	@Autowired
	private ConfigServerEngineDao configServerEngineDao;
	@Autowired
	private UserService userService;
	
	public Integer save(ConfigServerEngine o) {
		return (Integer) configServerEngineDao.save(o);
	}

	public void delete(ConfigServerEngine o) {
		configServerEngineDao.delete(o);
	}

	public void update(ConfigServerEngine o) {
		configServerEngineDao.update(o);
	}

	public void saveOrUpdate(ConfigServerEngine o) {
		configServerEngineDao.saveOrUpdate(o);
	}

	public List<ConfigServerEngine> find(String hql) {
		return configServerEngineDao.find(hql);
	}

	public List<ConfigServerEngine> find(String hql, Object[] param) {
		return configServerEngineDao.find(hql, param);
	}

	public List<ConfigServerEngine> find(String hql, List<Object> param) {
		return configServerEngineDao.find(hql, param);
	}

	public ConfigServerEngine get(Class<ConfigServerEngine> c, Serializable id) {
		return configServerEngineDao.get(c, id);
	}

	public ConfigServerEngine get(String hql, Object[] param) {
		return configServerEngineDao.get(hql, param);
	}

	public ConfigServerEngine get(String hql, List<Object> param) {
		return configServerEngineDao.get(hql, param);
	}
	
	@Override
	public List<ConfigServerEngine> find(String hql, Object[] param,
			PageHelper page) {
		return configServerEngineDao.find(hql, param, page);
	}

}
