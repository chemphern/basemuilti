package com.ycsys.smartmap.monitor.dao.impl;

import com.ycsys.smartmap.monitor.dao.NativeServerMonitorDao;
import com.ycsys.smartmap.monitor.entity.NativeServerMonitor;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by lixiaoxin on 2016/12/29.
 */
@Repository("nativeServerMonitorDao")
public class NativeServerMonitorDaoImpl extends BaseDaoImpl<NativeServerMonitor,Integer> implements NativeServerMonitorDao{
}
