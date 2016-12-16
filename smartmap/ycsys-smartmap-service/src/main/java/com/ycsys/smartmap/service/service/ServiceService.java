package com.ycsys.smartmap.service.service;

import java.util.List;

import com.ycsys.smartmap.resource.entity.Resource;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.BaseService;
/**
 * 服务 service 接口
 * @author liweixiong
 * @date   2016年11月3日
 */
public interface ServiceService extends BaseService<Service, Integer> {
	List<Service> find(String hql, List<Object> param, PageHelper page);
	Long count(String hql, List<Object> param);
}
