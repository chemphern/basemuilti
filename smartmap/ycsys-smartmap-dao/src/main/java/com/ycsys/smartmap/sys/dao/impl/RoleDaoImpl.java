package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.RoleDao;
import com.ycsys.smartmap.sys.entity.Role;
import org.springframework.stereotype.Repository;


/**
 * 角色DAO
 * @author ty
 * @date 2015年1月13日
 */
@Repository("roleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role,Integer>  implements RoleDao {

}
