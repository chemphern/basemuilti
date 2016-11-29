package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.dao.UserRoleDao;
import com.ycsys.smartmap.sys.entity.Role;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.entity.UserRole;
import com.ycsys.smartmap.sys.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户角色service
 * @author ty
 * @date 2015年1月14日
 */
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

	@Resource
	private UserRoleDao userRoleDao;


	/**
	 * 添加修改用户角色
	 * 
	 * @param userId
	 * @param oldList
	 * @param newList
	 */
	public void updateUserRole(Integer userId, List<Integer> oldList,List<Integer> newList) {
		// 是否删除
		for (int i = 0, j = oldList.size(); i < j; i++) {
			if (!newList.contains(oldList.get(i))) {
				userRoleDao.deleteUR(userId, oldList.get(i));
			}
		}

		// 是否添加
		for (int m = 0, n = newList.size(); m < n; m++) {
			if (!oldList.contains(newList.get(m))) {
				userRoleDao.save(getUserRole(userId, newList.get(m)));
			}
		}
	}

	/**
	 * 构建UserRole
	 * 
	 * @param userId
	 * @param roleId
	 * @return UserRole
	 */
	private UserRole getUserRole(Integer userId, Integer roleId) {
		UserRole ur = new UserRole();
		ur.setUser(new User(userId));
		ur.setRole(new Role(roleId));
		return ur;
	}

	/**
	 * 获取用户拥有角色id集合
	 * 
	 * @param userId
	 * @return 结果集合
	 */
	public List<Integer> getRoleIdList(Integer userId) {
		return userRoleDao.findRoleIds(userId);
	}

}
