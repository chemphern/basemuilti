package com.ycsys.smartmap.resource.service;

import java.util.List;

import com.ycsys.smartmap.resource.entity.Resource;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.BaseService;
/**
 * 资源 service
 * @author liweixiong
 * @date   2016年11月3日
 */
public interface ResourceService extends BaseService<Resource, Integer> {
	/**
	 * 得到最大排序号
	 * @param hql
	 * @return
	 */
	Integer getMaxSort(String hql);
	
	List<Resource> find(String hql, List<Object> param, PageHelper page);
	
	public Long count(String hql, List<Object> param);

}
