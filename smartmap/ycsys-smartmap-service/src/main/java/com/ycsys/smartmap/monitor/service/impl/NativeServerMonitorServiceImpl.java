package com.ycsys.smartmap.monitor.service.impl;

import com.ycsys.smartmap.monitor.dao.NativeServerMonitorDao;
import com.ycsys.smartmap.monitor.entity.NativeServerMonitor;
import com.ycsys.smartmap.monitor.service.NativeServerMonitorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lixiaoxin on 2016/12/29.
 */
@Service("nativeServerMonitorService")
public class NativeServerMonitorServiceImpl implements NativeServerMonitorService {
    @Resource
    private NativeServerMonitorDao nativeServerMonitorDao;

    @Override
    public void save(NativeServerMonitor monitor) {
        nativeServerMonitorDao.save(monitor);
    }
}
