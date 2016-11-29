package com.ycsys.smartmap.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.sys.dao.ConfigServerEngineDao;
import com.ycsys.smartmap.sys.entity.ConfigServerEngine;

/**
 * 服务器引擎配置dao 实现
 * @author liweixiong
 * @date   2016年11月2日
 */
@Repository("configServerEngineDao")
public class ConfigServerEngineDaoImpl extends BaseDaoImpl<ConfigServerEngine, Integer> implements
		ConfigServerEngineDao {

}
