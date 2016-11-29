package com.ycsys.smartmap.sys.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.sys.dao.ConfigExceptionAlarmDao;
import com.ycsys.smartmap.sys.entity.ConfigExceptionAlarm;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.ConfigExceptionAlarmService;

/**
 * 
 * @author liweixiong
 * @date   2016年11月2日
 */
@Service("configExceptionAlarmService")
public class ConfigExceptionAlarmServiceImpl implements ConfigExceptionAlarmService{
	
	@Autowired
	private ConfigExceptionAlarmDao configExceptionAlarmDao;
	public Integer save(ConfigExceptionAlarm o) {
		// TODO Auto-generated method stub
		return (Integer) configExceptionAlarmDao.save(o);
	}

	public void delete(ConfigExceptionAlarm o) {
		// TODO Auto-generated method stub
		configExceptionAlarmDao.delete(o);
	}

	public void update(ConfigExceptionAlarm o) {
		// TODO Auto-generated method stub
		configExceptionAlarmDao.update(o);
	}

	public void saveOrUpdate(ConfigExceptionAlarm o) {
		// TODO Auto-generated method stub
		configExceptionAlarmDao.saveOrUpdate(o);
	}

	public List<ConfigExceptionAlarm> find(String hql) {
		// TODO Auto-generated method stub
		return configExceptionAlarmDao.find(hql);
	}

	public List<ConfigExceptionAlarm> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return configExceptionAlarmDao.find(hql, param);
	}

	public List<ConfigExceptionAlarm> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return configExceptionAlarmDao.find(hql, param);
	}

	public ConfigExceptionAlarm get(Class<ConfigExceptionAlarm> c,
			Serializable id) {
		// TODO Auto-generated method stub
		return configExceptionAlarmDao.get(c, id);
	}

	public ConfigExceptionAlarm get(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return configExceptionAlarmDao.get(hql, param);
	}

	public ConfigExceptionAlarm get(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return configExceptionAlarmDao.get(hql, param);
	}

	@Override
	public List<ConfigExceptionAlarm> find(String hql, Object[] param,
			PageHelper page) {
		// TODO Auto-generated method stub
		return configExceptionAlarmDao.find(hql, param, page);
	}

}
