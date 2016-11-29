package com.ycsys.smartmap.sys.dao;

import com.ycsys.smartmap.sys.entity.*;

/**
 * Created by Administrator on 2016/10/24.
 */
public interface UserDao extends BaseDao<User,Integer> {
    User findByAttr(String loginName, String loginName1);

    void saveAdminUser(User user, Role role, UserRole userRole);
}
