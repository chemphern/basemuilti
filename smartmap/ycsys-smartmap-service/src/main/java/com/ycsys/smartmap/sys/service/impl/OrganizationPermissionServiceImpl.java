package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.dao.OrganizationPermissionDao;
import com.ycsys.smartmap.sys.entity.Organization;
import com.ycsys.smartmap.sys.entity.OrganizationPermission;
import com.ycsys.smartmap.sys.entity.Permission;
import com.ycsys.smartmap.sys.service.OrganizationPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by lixiaoxin on 2016/11/30.
 */
@Service("organizationPermissionService")
public class OrganizationPermissionServiceImpl implements OrganizationPermissionService {

    @Resource
    private OrganizationPermissionDao organizationPermissionDao;

    @Override
    public void updateOrgPermission(String id, String authorities) {
        Object [] delp = {Integer.parseInt(id)};
        organizationPermissionDao.executeHql("delete from OrganizationPermission op  where op.organization.id = ?",delp);
        String [] pids = authorities.split(",");
        Organization o = new Organization();
        o.setId(Integer.parseInt(id));
        for(String pid :pids){
            OrganizationPermission op = new OrganizationPermission();
            op.setOrganization(o);
            Permission p = new Permission();
            p.setId(Integer.parseInt(pid));
            op.setPermission(p);
            organizationPermissionDao.save(op);
        }
    }
}
