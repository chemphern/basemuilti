package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lixiaoxin on 2016/10/24.
 */
public interface UserService {
    public void save(User user);
    public void updatePwd(User user);
    public void delete(Integer id);
    public User getUser(String loginName);
    public boolean checkPassword(User user,String oldPassword);
    public void updateUserLogin(User user);

    public List<User> findAllUsers(PageHelper page);

    void initAdminuser(String adminLoginName,String admin_password,String admin_role);
    
    public User get(Class<User> c, Serializable id);

    void saveOrUpdate(User user);

    /**根据orgId分页查询用户**/
    List<User> findAllUsers(String orgId, PageHelper page);

    /**保存用户**/
    void saveOrUpdate(User user, String orgId,String roleIds);
    
    public List<User> findAllUsers(String hql);
}
