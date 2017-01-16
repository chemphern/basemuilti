package com.ycsys.smartmap.monitor.service;

import java.util.List;

import com.ycsys.smartmap.monitor.entity.NativeDbMonitor;

/**
 * Created by lixiaoxin on 2016/12/29.
 */
public interface NativeDbMonitorService {
    void save(NativeDbMonitor db);
    
    public List<NativeDbMonitor> find(String hql, Object[] param);
    
    public List<NativeDbMonitor> find(String hql, List<Object> param);
    
    public Object[] findArrValue(String hql, List<Object> params);
}
