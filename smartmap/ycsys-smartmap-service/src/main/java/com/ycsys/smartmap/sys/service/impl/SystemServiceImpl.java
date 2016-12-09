package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.common.config.parseobject.system.SystemRootXmlObject;
import com.ycsys.smartmap.sys.common.config.parseobject.system.SystemXmlObject;
import com.ycsys.smartmap.sys.common.utils.DateUtils;
import com.ycsys.smartmap.sys.dao.SystemDao;
import com.ycsys.smartmap.sys.entity.System;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

/**
 * Created by lixiaoxin on 2016/11/8.
 */
@Service("systemService")
public class SystemServiceImpl implements SystemService{

    @Resource
    private SystemDao systemDao;

    private static final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

    //初始化系统表
    public void initSystem(SystemRootXmlObject srxo) {
        long count = systemDao.count("select count(*) from System");
        if(count < 1) {
            logger.info("===============初始化系统表开始==================");
            for (SystemXmlObject sxo : srxo.getSystemXmlObjectList()) {
                String system_code = sxo.getCode();
                if (system_code != null && !system_code.equals("")) {
                    com.ycsys.smartmap.sys.entity.System system = new com.ycsys.smartmap.sys.entity.System();
                    system.setCreateTime(DateUtils.getSysTimestamp());
                    system.setName(sxo.getName());
                    system.setCode(system_code);
                    system.setUrl(sxo.getUrl());
                    if (sxo.getUserId() != null && !sxo.getUserId().equals("")) {
                        User u = new User();
                        u.setId(Integer.parseInt(sxo.getUserId()));
                        system.setUser(u);
                    }
                    systemDao.saveOrUpdate(system);
                }
            }
            logger.info("===============初始化系统表完成==================");
        }

    }

    @Override
    public List<System> findAll() {
        return systemDao.find("from System");
    }
}
