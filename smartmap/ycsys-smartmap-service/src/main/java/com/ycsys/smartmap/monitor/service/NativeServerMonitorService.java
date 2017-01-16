package com.ycsys.smartmap.monitor.service;

import java.util.List;

import com.ycsys.smartmap.monitor.entity.NativeServerMonitor;

/**
 * Created by lixiaoxin on 2016/12/29.
 */
public interface NativeServerMonitorService {
    void save(NativeServerMonitor monitor);
    public List<NativeServerMonitor> find(String hql, List<Object> param);
    public Object[] findArrValue(String hql, List<Object> params);
}
