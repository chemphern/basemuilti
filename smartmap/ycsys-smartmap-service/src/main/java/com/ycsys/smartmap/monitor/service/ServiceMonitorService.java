package com.ycsys.smartmap.monitor.service;

import java.util.List;

import com.ycsys.smartmap.monitor.entity.ServiceMonitor;

/**
 * Created by lixiaoxin on 2016/12/28.
 */
public interface ServiceMonitorService {
    void save(ServiceMonitor m);
    
    public List<ServiceMonitor> find(String hql, List<Object> param);
}
