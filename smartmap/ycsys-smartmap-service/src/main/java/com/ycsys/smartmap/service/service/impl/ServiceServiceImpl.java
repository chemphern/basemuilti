package com.ycsys.smartmap.service.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.resource.entity.Resource;
import com.ycsys.smartmap.service.dao.ServiceDao;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.sys.entity.PageHelper;

/**
 * 
 * @author liweixiong
 * @date   2016年11月3日
 */
@Transactional
@org.springframework.stereotype.Service("serviceService")
public class ServiceServiceImpl implements ServiceService {
	
	@Autowired
	private ServiceDao serviceDao;
	public Integer save(Service o) {
		// TODO Auto-generated method stub
		return (Integer) serviceDao.save(o);
	}

	public void delete(Service o) {
		// TODO Auto-generated method stub
		serviceDao.delete(o);
	}

	public void update(Service o) {
		// TODO Auto-generated method stub
		serviceDao.update(o);
	}

	public void saveOrUpdate(Service o) {
		// TODO Auto-generated method stub
		serviceDao.saveOrUpdate(o);
	}

	public List<Service> find(String hql) {
		// TODO Auto-generated method stub
		return serviceDao.find(hql);
	}

	public List<Service> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return serviceDao.find(hql, param);
	}

	public List<Service> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serviceDao.find(hql, param);
	}

	public Service get(Class<Service> c, Serializable id) {
		// TODO Auto-generated method stub
		return serviceDao.get(c, id);
	}

	public Service get(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return serviceDao.get(hql, param);
	}

	public Service get(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serviceDao.get(hql, param);
	}

	@Override
	public List<Service> find(String hql, Object[] param, PageHelper page) {
		// TODO Auto-generated method stub
		return serviceDao.find(hql, param, page);
	}

	@Override
	public List<Service> find(String hql, List<Object> param, PageHelper page) {
		// TODO Auto-generated method stub
		return serviceDao.find(hql, param, page);
	}

	@Override
	public Long count(String hql, List<Object> param) {
		return serviceDao.count(hql, param);
	}

	@Override
	public void updateMonitor(Map<String, Object> saveParam) {
		int id = (int) saveParam.get("id");
		if(saveParam.get("monitorStatus")!= null){
			String status = String.valueOf(saveParam.get("monitorStatus"));
			serviceDao.executeHql("update Service set monitorStatus = ? where id = ?",new Object[]{status,id});
		}
	}

}
