package com.ycsys.smartmap.monitor.service;

import java.util.List;

import com.ycsys.smartmap.monitor.entity.NativeTomcatMonitor;

/**
 * Created by lixiaoxin on 2016/12/29.
 */
public interface NativeTomcatMonitorService {
    void save(NativeTomcatMonitor t);
    public List<NativeTomcatMonitor> find(String hql, List<Object> param);
    public Object[] findArrValue(String hql, List<Object> params);
}
