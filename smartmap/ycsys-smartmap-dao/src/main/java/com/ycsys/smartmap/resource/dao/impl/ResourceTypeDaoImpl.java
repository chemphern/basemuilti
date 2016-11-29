package com.ycsys.smartmap.resource.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.resource.dao.ResourceTypeDao;
import com.ycsys.smartmap.resource.entity.ResourceType;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;

/**
 * 
 * @author liweixiong
 * @date   2016年11月3日
 */
@Repository("resourceTypeDao")
public class ResourceTypeDaoImpl extends BaseDaoImpl<ResourceType, Integer>
		implements ResourceTypeDao {

}
