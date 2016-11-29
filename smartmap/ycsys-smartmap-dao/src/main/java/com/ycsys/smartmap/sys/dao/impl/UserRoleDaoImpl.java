package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.UserRoleDao;
import com.ycsys.smartmap.sys.entity.UserRole;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 用户角色DAO
 * @author ty
 * @date 2015年1月13日
 */
@Repository("userRoleDao")
public class UserRoleDaoImpl  extends BaseDaoImpl<UserRole,Integer> implements UserRoleDao {

	/**
	 * 删除用户角色
	 * @param userId
	 * @param roleId
	 */
	public void deleteUR(Integer userId,Integer roleId){
		String hql="delete UserRole ur where ur.user.id=?0 and ur.role.id=?1";
		createQuery(hql, userId,roleId);
	}
	
	/**
	 * 查询用户拥有的角色id集合
	 * @param userId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Integer> findRoleIds(Integer userId){
		String hql="select ur.role.id from UserRole ur where ur.user.id=?0";
		Query query= createQuery(hql, userId);
		return query.list();
	}
	
}
