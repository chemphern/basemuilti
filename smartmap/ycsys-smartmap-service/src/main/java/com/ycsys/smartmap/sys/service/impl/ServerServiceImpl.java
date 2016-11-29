package com.ycsys.smartmap.sys.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.sys.dao.ServerDao;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.Server;
import com.ycsys.smartmap.sys.service.ServerService;

@Service("serverService")
public class ServerServiceImpl implements ServerService {

	@Autowired
	private ServerDao serverDao;
	public Integer save(Server o) {
		// TODO Auto-generated method stub
		return (Integer)serverDao.save(o);
	}

	public void delete(Server o) {
		// TODO Auto-generated method stub
		serverDao.delete(o);
	}

	public void update(Server o) {
		// TODO Auto-generated method stub
		serverDao.update(o);
	}

	public void saveOrUpdate(Server o) {
		// TODO Auto-generated method stub
		serverDao.saveOrUpdate(o);
	}

	public List<Server> find(String hql) {
		// TODO Auto-generated method stub
		return serverDao.find(hql);
	}

	public List<Server> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return serverDao.find(hql, param);
	}

	public List<Server> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serverDao.find(hql, param);
	}

	public Server get(Class<Server> c, Serializable id) {
		// TODO Auto-generated method stub
		return serverDao.get(c, id);
	}

	public Server get(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return serverDao.get(hql, param);
	}

	public Server get(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serverDao.get(hql, param);
	}


	@Override
	public List<Server> find(String hql, Object[] param, PageHelper page) {
		// TODO Auto-generated method stub
		return serverDao.find(hql, param, page);
	}

}
