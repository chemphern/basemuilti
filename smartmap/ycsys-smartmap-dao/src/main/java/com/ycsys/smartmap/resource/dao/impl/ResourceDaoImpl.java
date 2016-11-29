package com.ycsys.smartmap.resource.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.resource.dao.ResourceDao;
import com.ycsys.smartmap.resource.entity.Resource;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
/**
 * 
 * @author liweixiong
 * @date   2016年11月3日
 */
@Repository("resourceDao")
public class ResourceDaoImpl extends BaseDaoImpl<Resource, Integer> implements
		ResourceDao {

	@Override
	public Integer getMaxSort(String hql) {
		// TODO Auto-generated method stub
		return (Integer) currentSession().createQuery(hql).uniqueResult();
	}

}
