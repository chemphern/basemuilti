package com.ycsys.smartmap.monitor.service.impl;

import java.util.List;

import com.ycsys.smartmap.monitor.dao.NativeDbMonitorDao;
import com.ycsys.smartmap.monitor.entity.NativeDbMonitor;
import com.ycsys.smartmap.monitor.service.NativeDbMonitorService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lixiaoxin on 2016/12/29.
 */
@Service("nativeDbMonitorService")
public class NativeDbMonitorServiceImpl implements NativeDbMonitorService {

    @Resource
    private NativeDbMonitorDao nativeDbMonitorDao;

    @Override
    public void save(NativeDbMonitor db) {
        nativeDbMonitorDao.save(db);
    }

	@Override
	public List<NativeDbMonitor> find(String hql, Object[] param) {
		return nativeDbMonitorDao.find(hql, param);
	}

	@Override
	public List<NativeDbMonitor> find(String hql, List<Object> param) {
		return nativeDbMonitorDao.find(hql, param);
	}
}
