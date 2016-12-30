package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.monitor.entity.Alarm;
import com.ycsys.smartmap.sys.dao.AlarmDao;
import org.springframework.stereotype.Repository;

/**
 * Created by lixiaoxin on 2016/12/26.
 */
@Repository("alarmDao")
public class AlarmDaoImpl extends BaseDaoImpl<Alarm,Integer> implements AlarmDao {
}
