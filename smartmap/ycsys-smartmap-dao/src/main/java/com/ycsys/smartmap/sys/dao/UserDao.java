package com.ycsys.smartmap.sys.dao;

import com.ycsys.smartmap.sys.entity.User;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */
public interface UserDao extends BaseDao<User,Integer> {
    User findByAttr(String loginName, String loginName1);

    List<Integer> findUsersByRoleIds(List<Integer> ids);

    List<Integer> findUsersByOrgIds(List<Integer> test);
}
