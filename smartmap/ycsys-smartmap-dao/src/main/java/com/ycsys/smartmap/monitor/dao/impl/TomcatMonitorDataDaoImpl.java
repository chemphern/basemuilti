package com.ycsys.smartmap.monitor.dao.impl;

import com.ycsys.smartmap.monitor.dao.TomcatMonitorDataDao;
import com.ycsys.smartmap.monitor.entity.TomcatMonitorData;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

/**
 * Created by lixiaoxin on 2017/1/10.
 */
@Repository("tomcatMonitorDataDao")
public class TomcatMonitorDataDaoImpl extends BaseDaoImpl<TomcatMonitorData,Integer> implements TomcatMonitorDataDao{
}
