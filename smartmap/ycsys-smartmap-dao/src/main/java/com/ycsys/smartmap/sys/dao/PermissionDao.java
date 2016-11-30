package com.ycsys.smartmap.sys.dao;

import com.ycsys.smartmap.sys.entity.Permission;

import java.util.List;

/**
 * Created by lixiaoxin on 2016/11/2.
 */
public interface PermissionDao  extends BaseDao<Permission,Integer> {
     List<Permission> findPermissions(Integer userId);
     List<Permission> findMenus();
     List<Permission> findMenus(Integer userId,String system_code);

    List<Permission> findMenus(String sys_code);

    /**根据系统编码获取所有权限**/
    List<Permission> getAllPermissions(String sys_code);
}
