package com.ycsys.smartmap.resource.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.resource.dao.ResourceTypeDao;
import com.ycsys.smartmap.resource.entity.ResourceType;
import com.ycsys.smartmap.resource.service.ResourceTypeService;
import com.ycsys.smartmap.sys.common.annotation.ToLog;
import com.ycsys.smartmap.sys.common.enums.LogType;
import com.ycsys.smartmap.sys.entity.PageHelper;
/**
 * 
 * @author liweixiong
 * @date   2016年11月3日
 */
@Service("resourceTypeService")
@Transactional
public class ResourceTypeServiceImpl implements ResourceTypeService {
	@Autowired 
	private ResourceTypeDao resourceTypeDao;
	
	@ToLog(name="新增资源分类",type= LogType.Resource)
	public Integer save(ResourceType o) {
		return (Integer)resourceTypeDao.save(o);
	}
	
	@ToLog(name="删除资源分类",type= LogType.Resource)
	public void delete(ResourceType o) {
		// TODO Auto-generated method stub
		resourceTypeDao.delete(o);

	}

	@ToLog(name="修改资源分类",type= LogType.Resource)
	public void update(ResourceType o) {
		// TODO Auto-generated method stub
		resourceTypeDao.update(o);
	}

	@ToLog(name="新增或修改资源分类",type= LogType.Resource)
	public void saveOrUpdate(ResourceType o) {
		// TODO Auto-generated method stub
		resourceTypeDao.saveOrUpdate(o);
	}

	public List<ResourceType> find(String hql) {
		// TODO Auto-generated method stub
		return resourceTypeDao.find(hql);
	}

	public List<ResourceType> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return resourceTypeDao.find(hql, param);
	}

	public List<ResourceType> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return resourceTypeDao.find(hql, param);
	}

	public ResourceType get(Class<ResourceType> c, Serializable id) {
		// TODO Auto-generated method stub
		return resourceTypeDao.get(c, id);
	}

	public ResourceType get(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return resourceTypeDao.get(hql, param);
	}

	public ResourceType get(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return resourceTypeDao.get(hql, param);
	}

	@Override
	public List<ResourceType> find(String hql, Object[] param, PageHelper page) {
		// TODO Auto-generated method stub
		return resourceTypeDao.find(hql, param, page);
	}

}
