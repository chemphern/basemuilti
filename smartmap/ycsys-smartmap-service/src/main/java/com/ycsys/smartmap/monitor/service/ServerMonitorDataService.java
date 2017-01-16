package com.ycsys.smartmap.monitor.service;

import com.ycsys.smartmap.monitor.entity.ServerMonitorData;

import java.util.List;

/**
 * Created by lixiaoxin on 2017/1/10.
 */
public interface ServerMonitorDataService {
    void save(ServerMonitorData serverMonitorData);

    /**拿到前i条记录**/
    List<ServerMonitorData> findPrevData(String serverId,int i);
}
