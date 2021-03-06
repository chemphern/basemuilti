package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.common.config.parseobject.user.UserRootXmlObject;
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

    void initAdminuser(UserRootXmlObject users);
    
    public User get(Class<User> c, Serializable id);

    void saveOrUpdate(User user);

    /**根据orgId分页查询用户**/
    List<User> findAllUsers(String orgId, PageHelper page);

    /**保存用户**/
    void saveOrUpdate(User user, String orgId,String roleIds);
    
    public List<User> findAllUsers(String hql);

    /**所有用户总条数
     * @param orgId**/
    long countAll(String orgId);
    
    /**
     * 根据条件统计数量
     * @param hql
     * @param param
     * @return
     */
    long count(String hql, Object[] param);

    List<User> findAll();

    /****/
    List<String> findUsersByOrgIds(String[] split);

    List<String> findUsersByRoleIds(String[] split);
}
