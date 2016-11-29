package com.ycsys.smartmap.service.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.service.dao.ServiceApplyDao;
import com.ycsys.smartmap.service.dao.ServiceDao;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.entity.ServiceApply;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
/**
 * 
 * @author liweixiong
 * @date   2016年11月3日
 */
@Repository("serviceApplyDao")
public class ServiceApplyDaoImpl extends BaseDaoImpl<ServiceApply, Integer> implements ServiceApplyDao{

}
