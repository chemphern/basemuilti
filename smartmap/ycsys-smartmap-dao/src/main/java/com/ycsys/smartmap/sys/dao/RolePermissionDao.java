package com.ycsys.smartmap.sys.dao;

import com.ycsys.smartmap.sys.entity.RolePermission;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */
public interface RolePermissionDao  extends BaseDao<RolePermission,Integer>  {
    public List<Integer> findPermissionIds(Integer roleId);
    public void deleteRP(Integer roleId,Integer permissionId);
}
