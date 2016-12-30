package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.monitor.entity.Alarm;
import com.ycsys.smartmap.sys.entity.PageHelper;

import java.util.List;

/**
 * 异常报警Service
 * Created by lixiaoxin on 2016/12/26.
 */
public interface AlarmService {

    void save(Alarm alarm);

    List<Alarm> findByPage(PageHelper pageHelper);

    long countAll();
}
