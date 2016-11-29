package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.OrganizationDao;
import com.ycsys.smartmap.sys.entity.Organization;
import org.springframework.stereotype.Repository;

/**
 * Created by lixiaoxin on 2016/11/10.
 */
@Repository("organizationDao")
public class OrganizationDaoImpl extends BaseDaoImpl<Organization,Integer> implements OrganizationDao{
}
