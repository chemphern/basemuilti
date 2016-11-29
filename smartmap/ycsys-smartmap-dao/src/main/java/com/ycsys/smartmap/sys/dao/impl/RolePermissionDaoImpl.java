package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.RolePermissionDao;
import com.ycsys.smartmap.sys.entity.RolePermission;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 角色权限DAO
 * @author ty
 * @date 2015年1月13日
 */
@Repository("rolePermissionDao")
public class RolePermissionDaoImpl  extends BaseDaoImpl<RolePermission,Integer>  implements RolePermissionDao  {
	
	/**
	 * 查询角色拥有的权限id
	 * @param roleId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> findPermissionIds(Integer roleId){
		String hql="select rp.permission.id from RolePermission rp where rp.role.id=?0";
		Query query= createQuery(hql, roleId);
		return query.list();
	}

	/**
	 * 删除角色权限
	 * @param roleId
	 * @param permissionId
	 */
	public void deleteRP(Integer roleId,Integer permissionId){
		String hql="delete RolePermission rp where rp.role.id=?0 and rp.permission.id=?1";
		createQuery(hql, roleId,permissionId).executeUpdate();
	}
	
	
}
