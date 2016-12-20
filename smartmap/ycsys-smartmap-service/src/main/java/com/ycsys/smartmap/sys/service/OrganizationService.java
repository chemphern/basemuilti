package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.common.config.parseobject.org.OrgRootXmlObject;
import com.ycsys.smartmap.sys.entity.Organization;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.Permission;

import java.util.List;

/**
 * Created by lixiaoxin on 2016/11/10.
 */
public interface OrganizationService {

    /**分页查找所有机构**/
    List<Organization> findAll(PageHelper page);

    /**保存或者创建**/
    void saveOrUpdate(Organization o);

    /**删除**/
    void delete(Organization o);

    /**分页查找所有父机构**/
    List<Organization> findOrgNotChild(String pid,PageHelper page);

    /**根据id获取一个机构**/
    Organization getOrg(String id);

    /**查找所有机构**/
    List<Organization> findAll();

    /**根据机构id获取权限列表**/
    List<Permission> getPermissionByOrgId(String id);

    /**初始化机构**/
    void initOrg(OrgRootXmlObject orgs);

    /***获取所有机构总条数*/
    long countAll();
}
