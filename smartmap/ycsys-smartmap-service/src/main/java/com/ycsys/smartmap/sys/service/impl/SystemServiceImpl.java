package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.common.utils.DateUtils;
import com.ycsys.smartmap.sys.dao.SystemDao;
import com.ycsys.smartmap.sys.entity.System;
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


    public void initSystem(String system_code,String system_name,String system_url) {
        if(system_code != null && !system_code.equals("")) {
            com.ycsys.smartmap.sys.entity.System system = systemDao.getSystemByAttr("code", system_code);
            if (system_name != null && !system_name.equals("") && system_url != null && !system_url.equals("")) {

                if (system == null) {
                   system = new com.ycsys.smartmap.sys.entity.System();
                    system.setCreateTime(DateUtils.getSysTimestamp());
                }
                system.setName(system_name);
                system.setCode(system_code);
                system.setUrl(system_url);
                systemDao.saveOrUpdate(system);
                logger.info("===============初始化系统表==================");
            }
        }

    }

    @Override
    public List<System> findAll() {
        return systemDao.find("from System");
    }
}
