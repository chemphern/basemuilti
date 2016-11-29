package com.ycsys.smartmap.sys.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.sys.dao.ServerTypeDao;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.ServerType;
import com.ycsys.smartmap.sys.service.ServerTypeService;

/**
 * 
 * @author liweixiong
 * @date   2016年11月2日
 */

@Service("serverTypeService")
public class ServerTypeServiceImpl implements ServerTypeService {
	@Autowired 
	private ServerTypeDao serverTypeDao;

	public Integer save(ServerType o) {
		// TODO Auto-generated method stub
		return (Integer) serverTypeDao.save(o);
	}

	public void delete(ServerType o) {
		// TODO Auto-generated method stub
		serverTypeDao.delete(o);
		
	}

	public void update(ServerType o) {
		// TODO Auto-generated method stub
		serverTypeDao.update(o);
	}

	public void saveOrUpdate(ServerType o) {
		// TODO Auto-generated method stub
		serverTypeDao.saveOrUpdate(o);
	}

	public List<ServerType> find(String hql) {
		// TODO Auto-generated method stub
		return serverTypeDao.find(hql);
	}

	public List<ServerType> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return serverTypeDao.find(hql, param);
	}

	public List<ServerType> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return null;
	}

	public ServerType get(Class<ServerType> c, Serializable id) {
		// TODO Auto-generated method stub
		return serverTypeDao.get(c, id);
	}

	public ServerType get(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return serverTypeDao.get(hql, param);
	}

	public ServerType get(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serverTypeDao.get(hql, param);
	}

	@Override
	public List<ServerType> find(String hql, Object[] param, PageHelper page) {
		// TODO Auto-generated method stub
		return serverTypeDao.find(hql, param, page);
	}
	
}
