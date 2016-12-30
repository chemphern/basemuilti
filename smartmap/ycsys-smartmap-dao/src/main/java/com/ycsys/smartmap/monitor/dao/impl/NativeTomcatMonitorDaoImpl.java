package com.ycsys.smartmap.monitor.dao.impl;

import com.ycsys.smartmap.monitor.dao.NativeTomcatMonitorDao;
import com.ycsys.smartmap.monitor.entity.NativeTomcatMonitor;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by lixiaoxin on 2016/12/29.
 */
@Repository("nativeTomcatMonitorDao")
public class NativeTomcatMonitorDaoImpl extends BaseDaoImpl<NativeTomcatMonitor,Integer> implements NativeTomcatMonitorDao{
}
