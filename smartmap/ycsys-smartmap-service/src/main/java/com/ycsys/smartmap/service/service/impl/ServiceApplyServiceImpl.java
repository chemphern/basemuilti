package com.ycsys.smartmap.service.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.service.dao.ServiceApplyDao;
import com.ycsys.smartmap.service.dao.ServiceDao;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.entity.ServiceApply;
import com.ycsys.smartmap.service.service.ServiceApplyService;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.sys.entity.PageHelper;

/**
 * 
 * @author liweixiong
 * @date   2016年11月3日
 */
@Transactional
@org.springframework.stereotype.Service("serviceApplyService")
public class ServiceApplyServiceImpl implements ServiceApplyService {
	
	@Autowired
	private ServiceApplyDao serviceApplyDao;
	public Integer save(ServiceApply o) {
		// TODO Auto-generated method stub
		return (Integer) serviceApplyDao.save(o);
	}

	public void delete(ServiceApply o) {
		// TODO Auto-generated method stub
		serviceApplyDao.delete(o);
	}

	public void update(ServiceApply o) {
		// TODO Auto-generated method stub
		serviceApplyDao.update(o);
	}

	public void saveOrUpdate(ServiceApply o) {
		// TODO Auto-generated method stub
		serviceApplyDao.saveOrUpdate(o);
	}

	public List<ServiceApply> find(String hql) {
		// TODO Auto-generated method stub
		return serviceApplyDao.find(hql);
	}

	public List<ServiceApply> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return serviceApplyDao.find(hql, param);
	}

	public List<ServiceApply> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serviceApplyDao.find(hql, param);
	}

	public ServiceApply get(Class<ServiceApply> c, Serializable id) {
		// TODO Auto-generated method stub
		return serviceApplyDao.get(c, id);
	}

	public ServiceApply get(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return serviceApplyDao.get(hql, param);
	}

	public ServiceApply get(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serviceApplyDao.get(hql, param);
	}

	@Override
	public List<ServiceApply> find(String hql, Object[] param, PageHelper page) {
		// TODO Auto-generated method stub
		return serviceApplyDao.find(hql, param, page);
	}

}
