package com.ycsys.smartmap.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.sys.dao.ServerDao;
import com.ycsys.smartmap.sys.entity.Server;

/**
 * 服务器dao 实现
 * 
 * @author liweixiong
 * @date   2016年11月2日
 */
@Repository("serverDao")
public class ServerDaoImpl extends BaseDaoImpl<Server, Integer> implements
		ServerDao {

}
