package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.common.config.parseobject.system.SystemRootXmlObject;
import com.ycsys.smartmap.sys.entity.System;

import java.util.List;

/**
 * Created by lixiaoxin on 2016/11/8.
 */
public interface SystemService {
    void initSystem(SystemRootXmlObject srxo);


    List<System> findAll();
}
