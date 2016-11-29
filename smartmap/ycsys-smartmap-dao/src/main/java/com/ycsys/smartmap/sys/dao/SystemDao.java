package com.ycsys.smartmap.sys.dao;

/**
 * Created by lixiaoxin on 2016/11/8.
 */
public interface SystemDao extends BaseDao<com.ycsys.smartmap.sys.entity.System,Integer>{
    com.ycsys.smartmap.sys.entity.System getSystemByAttr(String code, String system_code);
}
