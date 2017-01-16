package com.ycsys.smartmap.monitor.dao;

import com.ycsys.smartmap.monitor.entity.ServiceMonitor;
import com.ycsys.smartmap.sys.dao.BaseDao;

import java.util.Map;

/**
 * Created by lixiaoxin on 2016/12/28.
 */
public interface ServiceMonitorDao extends BaseDao<ServiceMonitor,Integer> {
    ServiceMonitor getOne(String s,Object[] param);

    Map<String,Object> findOneToMap(String s, Object[] objects);
}
