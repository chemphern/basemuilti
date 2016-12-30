package com.ycsys.smartmap.monitor.service.impl;

import java.util.List;

import com.ycsys.smartmap.monitor.dao.NativeTomcatMonitorDao;
import com.ycsys.smartmap.monitor.entity.NativeTomcatMonitor;
import com.ycsys.smartmap.monitor.service.NativeTomcatMonitorService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lixiaoxin on 2016/12/29.
 */
@Service("nativeTomcatMonitorService")
public class NativeTomcatMonitorServiceImpl  implements NativeTomcatMonitorService{

    @Resource
    private NativeTomcatMonitorDao nativeTomcatMonitorDao;

    @Override
    public void save(NativeTomcatMonitor t) {
        nativeTomcatMonitorDao.save(t);
    }
    
    @Override
	public List<NativeTomcatMonitor> find(String hql, List<Object> param) {
		return nativeTomcatMonitorDao.find(hql, param);
	}

	@Override
	public Object[] findArrValue(String hql, List<Object> params) {
		// TODO Auto-generated method stub
		return nativeTomcatMonitorDao.findArrValue(hql, params);
	}
}
