package com.ycsys.smartmap.service.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.service.dao.ServiceTypeDao;
import com.ycsys.smartmap.service.entity.ServiceType;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
/**
 * 
 * @author liweixiong
 * @date   2016年11月3日
 */
@Repository("serviceTypeDao")
public class ServiceTypeDaoImpl extends BaseDaoImpl<ServiceType, Integer> implements ServiceTypeDao{

}
