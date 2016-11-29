package com.ycsys.smartmap.service.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.service.entity.ServiceType;
import com.ycsys.smartmap.service.service.ServiceTypeService;
import com.ycsys.smartmap.sys.entity.PageHelper;

@Service("serviceTypeService")
@Transactional
public class ServiceTypeServiceImpl implements ServiceTypeService {

	public Integer save(ServiceType o) {
		// TODO Auto-generated method stub
		return null;
	}

	public void delete(ServiceType o) {
		// TODO Auto-generated method stub

	}

	public void update(ServiceType o) {
		// TODO Auto-generated method stub

	}

	public void saveOrUpdate(ServiceType o) {
		// TODO Auto-generated method stub

	}

	public List<ServiceType> find(String hql) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ServiceType> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ServiceType> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceType get(Class<ServiceType> c, Serializable id) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceType get(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServiceType get(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServiceType> find(String hql, Object[] param, PageHelper page) {
		// TODO Auto-generated method stub
		return null;
	}

}
