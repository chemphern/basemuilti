package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.entity.ConfigServerMonitor;
import com.ycsys.smartmap.sys.entity.PageHelper;

import java.util.List;
import java.util.Map;

public interface ConfigServerMonitorService {

    /**根据监控类型获取监控配置**/
    List<ConfigServerMonitor> findByType(String type, PageHelper pageHelper);

    /**根据监控类型获取监控配置的总条数**/
    long countByType(String type);

    /**保存或者修改**/
    void saveOrUpdate(ConfigServerMonitor configServerMonitor);

    /**根据id删除**/
    void delete(String id);

    /**根据id获取配置信息**/
    ConfigServerMonitor getById(String id);

    /**根据监控配置启动一个监控**/
    void startMonitor(String id);

    /**保存收集的服务器信息**/
    void saveServiceInfo(Map<String, Object> ses);

    /**根据id停止一个监控**/
    void stopMonitor(String id);
}
