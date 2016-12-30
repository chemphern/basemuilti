package com.ycsys.smartmap.monitor.service.impl;

import java.util.List;

import com.ycsys.smartmap.monitor.dao.ServiceMonitorDao;
import com.ycsys.smartmap.monitor.entity.ServiceMonitor;
import com.ycsys.smartmap.monitor.service.ServiceMonitorService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by lixiaoxin on 2016/12/28.
 */
@Service("serviceMonitorService")
@Transactional
public class ServiceMonitorServiceImpl implements ServiceMonitorService{

    @Resource
    private ServiceMonitorDao serviceMonitorDao;

    @Override
    public void save(ServiceMonitor m) {
        serviceMonitorDao.save(m);
    }

	@Override
	public List<ServiceMonitor> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serviceMonitorDao.find(hql, param);
	}

}
