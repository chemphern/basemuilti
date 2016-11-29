package com.ycsys.smartmap.service.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.service.dao.ServiceDao;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
/**
 * 
 * @author liweixiong
 * @date   2016年11月3日
 */
@Repository("serviceDao")
public class ServiceDaoImpl extends BaseDaoImpl<Service, Integer> implements ServiceDao{

}
