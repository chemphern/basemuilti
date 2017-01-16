package com.ycsys.smartmap.sys.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.sys.common.annotation.ToLog;
import com.ycsys.smartmap.sys.common.enums.LogType;
import com.ycsys.smartmap.sys.dao.ConfigServiceExtendPropertyDao;
import com.ycsys.smartmap.sys.entity.ConfigServiceExtendProperty;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.ConfigServiceExtendPropertyService;

@Service("configServiceExtendPropertyService")
public class ConfigServiceExtendPropertyServiceImpl implements
		ConfigServiceExtendPropertyService {
	
	@Autowired
	private ConfigServiceExtendPropertyDao configServiceExtendPropertyDao;
	
	@ToLog(name="新增服务扩展属性",type= LogType.System)
	public Integer save(ConfigServiceExtendProperty o) {
		// TODO Auto-generated method stub
		return (Integer)configServiceExtendPropertyDao.save(o);
	}

	@ToLog(name="删除服务扩展属性",type= LogType.System)
	public void delete(ConfigServiceExtendProperty o) {
		// TODO Auto-generated method stub
		configServiceExtendPropertyDao.delete(o);
	}
	
	@ToLog(name="修改服务扩展属性",type= LogType.System)
	public void update(ConfigServiceExtendProperty o) {
		// TODO Auto-generated method stub
		configServiceExtendPropertyDao.update(o);
	}
	
	@ToLog(name="新增或修改服务扩展属性",type= LogType.System)
	public void saveOrUpdate(ConfigServiceExtendProperty o) {
		// TODO Auto-generated method stub
		configServiceExtendPropertyDao.saveOrUpdate(o);
	}

	public List<ConfigServiceExtendProperty> find(String hql) {
		// TODO Auto-generated method stub
		return configServiceExtendPropertyDao.find(hql);
	}

	public List<ConfigServiceExtendProperty> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return configServiceExtendPropertyDao.find(hql, param);
	}

	public List<ConfigServiceExtendProperty> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return configServiceExtendPropertyDao.find(hql, param);
	}

	public ConfigServiceExtendProperty get(
			Class<ConfigServiceExtendProperty> c, Serializable id) {
		// TODO Auto-generated method stub
		return configServiceExtendPropertyDao.get(c, id);
	}

	public ConfigServiceExtendProperty get(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return configServiceExtendPropertyDao.get(hql, param);
	}

	public ConfigServiceExtendProperty get(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return configServiceExtendPropertyDao.get(hql, param);
	}


	@Override
	public List<ConfigServiceExtendProperty> find(String hql, Object[] param,
			PageHelper page) {
		// TODO Auto-generated method stub
		return configServiceExtendPropertyDao.find(hql, param, page);
	}

}
