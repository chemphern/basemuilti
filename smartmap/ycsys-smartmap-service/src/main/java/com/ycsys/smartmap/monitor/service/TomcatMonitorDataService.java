package com.ycsys.smartmap.monitor.service;

import com.ycsys.smartmap.monitor.entity.TomcatMonitorData;

import java.util.List;

/**
 * Created by lixiaoxin on 2017/1/10.
 */
public interface TomcatMonitorDataService {
    void save(TomcatMonitorData tomcatMonitorData);

    List<TomcatMonitorData> findPrevData(String id, int i);
}
