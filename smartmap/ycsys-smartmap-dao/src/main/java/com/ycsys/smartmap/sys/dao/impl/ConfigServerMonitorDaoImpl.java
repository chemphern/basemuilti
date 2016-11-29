package com.ycsys.smartmap.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.sys.dao.ConfigServerMonitorDao;
import com.ycsys.smartmap.sys.entity.ConfigServerMonitor;

/**
 * 服务器监控配置dao 实现
 * @author liweixiong
 * @date   2016年11月2日
 */
@Repository("configServerMonitorDao")
public class ConfigServerMonitorDaoImpl extends BaseDaoImpl<ConfigServerMonitor, Integer> implements
		ConfigServerMonitorDao {

}
