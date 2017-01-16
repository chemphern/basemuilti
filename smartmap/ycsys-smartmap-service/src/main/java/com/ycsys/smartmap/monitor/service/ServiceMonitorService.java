package com.ycsys.smartmap.monitor.service;

import com.ycsys.smartmap.monitor.entity.ServiceMonitor;
import com.ycsys.smartmap.sys.entity.PageHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixiaoxin on 2016/12/28.
 */
public interface ServiceMonitorService {
    void save(ServiceMonitor m);
    
    public List<ServiceMonitor> find(String hql, List<Object> param);
    
    public List<ServiceMonitor> find(String hql, List<Object> param,Integer page, Integer rows);
    
    public Object[] findArrValue(String hql, List<Object> params);
    
    public Long count(String hql, List<Object> param);

    Map<String,Object> getMonitorInfo(Integer id);

    List<ServiceMonitor> findByPage(PageHelper page);

    long countAll();
}
