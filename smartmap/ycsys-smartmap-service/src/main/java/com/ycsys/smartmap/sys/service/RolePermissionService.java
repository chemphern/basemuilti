package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.entity.RolePermission;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */
public interface RolePermissionService {
     List<Integer> getPermissionIds(Integer roleId);
     void updateRolePermission(Integer id,List<Integer> oldList,List<Integer> newList);
     void clearUserPermCache(PrincipalCollection pc);
     RolePermission getRolePermission(Integer roleId, Integer permissionId);

    /**roleId,权限ids**/
    void updateRolePermission(String id, String authorities);
}
