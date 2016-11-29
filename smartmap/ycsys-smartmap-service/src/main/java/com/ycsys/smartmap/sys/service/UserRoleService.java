package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.entity.UserRole;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */
public interface UserRoleService {
    public void updateUserRole(Integer userId, List<Integer> oldList, List<Integer> newList);
    public List<Integer> getRoleIdList(Integer userId);
}
