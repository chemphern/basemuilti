package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.entity.System;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public interface SystemService {
    void initSystem();


    List<System> findAll();
}
