package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.dao.RolePermissionDao;
import com.ycsys.smartmap.sys.entity.Permission;
import com.ycsys.smartmap.sys.entity.Role;
import com.ycsys.smartmap.sys.entity.RolePermission;
import com.ycsys.smartmap.sys.service.RolePermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;

/**
 * 角色权限service
 * @author ty
 * @date 2015年1月13日
 */
@Service("rolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService {
	
	@Resource
	private RolePermissionDao rolePermissionDao;

	@Resource(name="config")
	private Properties config;
	
	/**
	 * 获取角色权限id集合
	 * @param roleId
	 * @return List
	 */
	public List<Integer> getPermissionIds(Integer roleId){
		return rolePermissionDao.findPermissionIds(roleId);
	}
	
	/**
	 * 修改角色权限
	 * @param id
	 * @param oldList
	 * @param newList
	 */
	public void updateRolePermission(Integer id,List<Integer> oldList,List<Integer> newList){
		//是否删除
		for(int i=0,j=oldList.size();i<j;i++){
			if(!newList.contains(oldList.get(i))){
				rolePermissionDao.deleteRP(id,oldList.get(i));
			}
		}
		
		//是否添加
		for(int i=0,j=newList.size();i<j;i++){
			if(!oldList.contains(newList.get(i))){
				rolePermissionDao.save(getRolePermission(id,newList.get(i)));
			}
		}
	}

	@Override
	public void updateRolePermission(String id, String authorities) {
		String sys_code = config.getProperty("system.code");
		Object [] delp = {sys_code,Integer.parseInt(id)};
		rolePermissionDao.executeHql("delete from RolePermission rp  where rp.permission.id in (select id from Permission where systemCode = ? ) and rp.role.id = ?",delp);
		String [] pids = authorities.split(",");
		Role r = new Role();
		r.setId(Integer.parseInt(id));
		for(String pid :pids){
			RolePermission rp = new RolePermission();
			rp.setRole(r);
			Permission p = new Permission();
			p.setId(Integer.parseInt(pid));
			rp.setPermission(p);
			rolePermissionDao.save(rp);
		}
	}
	/**
	 * 清空该角色用户的权限缓存
	 */
	public void clearUserPermCache(PrincipalCollection pc){
		RealmSecurityManager securityManager =  (RealmSecurityManager) SecurityUtils.getSecurityManager();
		UserRealmImpl userRealm = (UserRealmImpl) securityManager.getRealms().iterator().next();
	    userRealm.clearCachedAuthorizationInfo(pc);
	}
	
	/**
	 * 构造角色权限对象
	 * @param roleId
	 * @param permissionId
	 * @return RolePermission
	 */
	public RolePermission getRolePermission(Integer roleId, Integer permissionId){
		RolePermission rp=new RolePermission();
		rp.setRole(new Role(roleId));
		rp.setPermission(new Permission(permissionId));
		return rp;
	}

}
