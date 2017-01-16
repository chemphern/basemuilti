package com.ycsys.smartmap.resource.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.resource.dao.ResourceDao;
import com.ycsys.smartmap.resource.entity.Resource;
import com.ycsys.smartmap.resource.service.ResourceService;
import com.ycsys.smartmap.sys.common.annotation.ToLog;
import com.ycsys.smartmap.sys.common.enums.LogType;
import com.ycsys.smartmap.sys.entity.PageHelper;
/**
 * 
 * @author liweixiong
 * @date   2016年11月3日
 */
@Service("resourceService")
@Transactional
public class ResourceServiceImpl implements ResourceService {
	
	@Autowired
	private ResourceDao resourceDao;
	
	@ToLog(name="新增资源",type= LogType.Resource)
	public Integer save(Resource o) {
		// TODO Auto-generated method stub
		return (Integer)resourceDao.save(o);
	}
	
	@ToLog(name="删除资源",type= LogType.Resource)
	public void delete(Resource o) {
		// TODO Auto-generated method stub
		resourceDao.delete(o);
	}

	@ToLog(name="修改资源",type= LogType.Resource)
	public void update(Resource o) {
		// TODO Auto-generated method stub
		resourceDao.update(o);
	}

	@ToLog(name="新增或修改资源",type= LogType.Resource)
	public void saveOrUpdate(Resource o) {
		// TODO Auto-generated method stub
		resourceDao.saveOrUpdate(o);
	}

	public List<Resource> find(String hql) {
		// TODO Auto-generated method stub
		return resourceDao.find(hql);
	}

	public List<Resource> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return resourceDao.find(hql, param);
	}

	public List<Resource> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return resourceDao.find(hql, param);
	}

	public Resource get(Class<Resource> c, Serializable id) {
		// TODO Auto-generated method stub
		return resourceDao.get(c, id);
	}

	public Resource get(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return resourceDao.get(hql, param);
	}

	public Resource get(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return resourceDao.get(hql, param);
	}

	@Override
	public List<Resource> find(String hql, Object[] param, PageHelper page) {
		// TODO Auto-generated method stub
		return resourceDao.find(hql, param, page);
	}

	@Override
	public Integer getMaxSort(String hql) {
		// TODO Auto-generated method stub
		return resourceDao.getMaxSort(hql);
	}

	@Override
	public List<Resource> find(String hql, List<Object> param, PageHelper page) {
		// TODO Auto-generated method stub
		return resourceDao.find(hql, param, page);
	}

	@Override
	public Long count(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return resourceDao.count(hql, param);
	}

}
