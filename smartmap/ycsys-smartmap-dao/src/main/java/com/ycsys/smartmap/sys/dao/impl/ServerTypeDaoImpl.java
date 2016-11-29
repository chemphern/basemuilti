package com.ycsys.smartmap.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.sys.dao.ServerTypeDao;
import com.ycsys.smartmap.sys.entity.ServerType;

/**
 * 服务器分类Dao 实现
 * 
 * @author liweixiong
 * @date 2016年11月2日
 */
@Repository("ServerTypeDao")
public class ServerTypeDaoImpl extends BaseDaoImpl<ServerType, Integer>
		implements ServerTypeDao {

}
