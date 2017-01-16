package com.ycsys.smartmap.monitor.dao.impl;

import com.ycsys.smartmap.monitor.dao.ServiceRequestDao;
import com.ycsys.smartmap.monitor.entity.ServiceRequest;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by lixiaoxin on 2017/1/3.
 */
@Repository("serviceRequestDao")
public class ServiceRequestDaoImpl extends BaseDaoImpl<ServiceRequest,Integer> implements ServiceRequestDao{
}
