package com.ycsys.smartmap.monitor.dao.impl;

import com.ycsys.smartmap.monitor.dao.ServerMonitorDataDao;
import com.ycsys.smartmap.monitor.entity.ServerMonitorData;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by lixiaoxin on 2017/1/10.
 */
@Repository("serverMonitorDataDao")
public class ServerMonitorDataDaoImpl extends BaseDaoImpl<ServerMonitorData,Integer> implements ServerMonitorDataDao{
}
