package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.Permission;
import com.ycsys.smartmap.sys.entity.Role;

import java.util.List;

/**
 * Created by lixiaoxin on 2016/11/11.
 */
public interface RoleService {
    /**查找所有角色**/
    List<Role> findAll(PageHelper page);

    void saveOrUpdate(Role role);

    void delete(Role role);

    /**获取一个角色**/
    Role getRole(String id);

    /**根据角色Id获取权限**/
    List<Permission> getPermissionByRoleId(String id);

    /**获取所有角色**/
    List<Role> findAll();
}
