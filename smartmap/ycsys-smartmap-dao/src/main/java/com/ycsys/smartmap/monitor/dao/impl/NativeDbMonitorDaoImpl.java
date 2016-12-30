package com.ycsys.smartmap.monitor.dao.impl;

import com.ycsys.smartmap.monitor.dao.NativeDbMonitorDao;
import com.ycsys.smartmap.monitor.entity.NativeDbMonitor;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by lixiaoxin on 2016/12/29.
 */
@Repository("nativeDbMonitorDao")
public class NativeDbMonitorDaoImpl extends BaseDaoImpl<NativeDbMonitor,Integer> implements NativeDbMonitorDao {
}
