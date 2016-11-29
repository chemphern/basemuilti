package com.ycsys.smartmap.sys.dao;

import com.ycsys.smartmap.sys.entity.UserRole;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */
public interface UserRoleDao  extends BaseDao<UserRole,Integer> {
    public void deleteUR(Integer userId,Integer roleId);
    public List<Integer> findRoleIds(Integer userId);
}
