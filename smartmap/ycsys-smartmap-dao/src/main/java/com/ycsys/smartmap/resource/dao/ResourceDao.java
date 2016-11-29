package com.ycsys.smartmap.resource.dao;

import com.ycsys.smartmap.resource.entity.Resource;
import com.ycsys.smartmap.sys.dao.BaseDao;

/**
 * 资源 dao
 * @author liweixiong
 * @date   2016年11月3日
 */
public interface ResourceDao extends BaseDao<Resource, Integer>{
	/**
	 * 得到最大排序号
	 * @param hql
	 * @return
	 */
	public Integer getMaxSort(String hql);
}
