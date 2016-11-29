package com.ycsys.smartmap.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.sys.dao.ConfigExceptionAlarmDao;
import com.ycsys.smartmap.sys.entity.ConfigExceptionAlarm;

/**
 * 异常报警配置 dao 实现
 * 
 * @author liweixiong
 * @date 2016年11月2日
 */
@Repository("configExceptionAlarmDao")
public class ConfigExceptionAlarmDaoImpl extends
		BaseDaoImpl<ConfigExceptionAlarm, Integer> implements
		ConfigExceptionAlarmDao {

}
