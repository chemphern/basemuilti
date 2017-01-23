package com.ycsys.smartmap.sys.service;

import java.util.List;

import com.ycsys.smartmap.sys.entity.ConfigServiceExtendProperty;
import com.ycsys.smartmap.sys.entity.PageHelper;

public interface ConfigServiceExtendPropertyService extends BaseService<ConfigServiceExtendProperty, Integer> {
	
	/**
	 * 模糊查询
	 */
	List<ConfigServiceExtendProperty> findSelect(
			ConfigServiceExtendProperty configServiceExtendProperty);

	
	
	List<ConfigServiceExtendProperty> find(String hql, List<Object> params,
			PageHelper page);

	long count(String hql, List<Object> params);

}
