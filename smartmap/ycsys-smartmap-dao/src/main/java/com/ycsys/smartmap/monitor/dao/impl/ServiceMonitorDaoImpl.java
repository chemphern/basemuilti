package com.ycsys.smartmap.monitor.dao.impl;

import com.ycsys.smartmap.monitor.dao.ServiceMonitorDao;
import com.ycsys.smartmap.monitor.entity.ServiceMonitor;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by lixiaoxin on 2016/12/28.
 */
@Repository("serviceMonitorDao'")
public class ServiceMonitorDaoImpl extends BaseDaoImpl<ServiceMonitor,Integer> implements ServiceMonitorDao {
}
