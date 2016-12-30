package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.common.annotation.ToLog;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.config.parseobject.user.UserRootXmlObject;
import com.ycsys.smartmap.sys.common.config.parseobject.user.UserXmlObject;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.DateUtils;
import com.ycsys.smartmap.sys.common.utils.security.Digests;
import com.ycsys.smartmap.sys.common.utils.security.Encodes;
import com.ycsys.smartmap.sys.dao.OrganizationDao;
import com.ycsys.smartmap.sys.dao.RoleDao;
import com.ycsys.smartmap.sys.dao.UserDao;
import com.ycsys.smartmap.sys.dao.UserRoleDao;
import com.ycsys.smartmap.sys.entity.*;
import com.ycsys.smartmap.sys.service.UserService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.lang.System;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户service
 * @author lixiaoxin
 * @date 2016年11月1日
 */
@Service("userService")
public class UserServiceImpl implements UserService {

	private static final int SALT_SIZE = 8;	//盐长度

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private OrganizationDao organizationDao;

	/**
	 * 保存用户
	 * @param user
	 */
	public void save(User user) {
		entryptPassword(user);
		user.setCreateTime(DateUtils.getSysTimestamp());
		userDao.save(user);
	}

	/**
	 * 修改密码
	 * @param user
	 */
	public void updatePwd(User user) {
		entryptPassword(user);
		userDao.save(user);
	}
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void delete(Integer id){
		//删除用户之前删除用户角色关系
		userRoleDao.executeHql("delete UserRole u where u.user.id = ?",new Object[]{id});
		userDao.executeHql("delete User where id = ?",new Object[]{id});

	}
	
	/**
	 * 按登录名查询用户
	 * @param loginName
	 * @return 用户对象
	 */
	public User getUser(String loginName) {
		return (User)userDao.findByAttr("loginName", loginName);
	}

	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(),salt, Global.HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 验证原密码是否正确
	 * @param user
	 * @param oldPassword
	 * @return
	 */
	public boolean checkPassword(User user,String oldPassword){
		byte[] salt =Encodes.decodeHex(user.getSalt()) ;
		byte[] hashPassword = Digests.sha1(oldPassword.getBytes(),salt, Global.HASH_INTERATIONS);
		if(user.getPassword().equals(Encodes.encodeHex(hashPassword))){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 修改用户登录
	 * @param user
	 */
	public void updateUserLogin(User user){
		user.setLoginCount((user.getLoginCount()==null?0:user.getLoginCount())+1);
		user.setPreviousVisit(user.getLastVisit());
		user.setLastVisit(DateUtils.getSysTimestamp());
		userDao.update(user);
	}

	@ToLog(name="查询所有用户",type="test")
	public List<User> findAllUsers(PageHelper page) {
		return userDao.find("from User",page);
	}

	/**初始化管理员**/
	public void initAdminuser(UserRootXmlObject urxo) {
		long count = userDao.count("select count(*) from User");
		if(count < 1){
			logger.info("=========================初始化用户开始==============================");
			for(UserXmlObject uxo :urxo.getUserXmlObjectList()){
				User user = new User();
				user.setName(uxo.getName());
				user.setLoginName(uxo.getLoginName());
				user.setPassword(uxo.getPassword());
				user.setPlainPassword(uxo.getPassword());
				user.setRemark(uxo.getRemark());
				user.setType((short)1);
				user.setCreateTime(DateUtils.getSysTimestamp());
				user.setEmail(uxo.getEmail());
				user.setPhone(uxo.getPhone());
				entryptPassword(user);
				String sex = uxo.getSex();
				short sexs = 1;
				if(sex != null) {
					if (sex.equals("男")) {
					} else if (sex.equals("女")) {
						sexs = 2;
					} else {
						sexs = Short.parseShort(sex);
					}
				}
				user.setSex(sexs);
				//机构
				String orgs = uxo.getOrg();
				if(orgs != null && !orgs.equals("")){
					Organization org = organizationDao.get("from Organization where code = ?",new Object[]{orgs});
					user.setOrganization(org);
				}
				//角色
				String roles = uxo.getRoles();
				String [] rolesArr = roles.split(",");
				userDao.save(user);
				for(String r : rolesArr){
					Role role = roleDao.get("from Role where code = ?",new Object[]{r});
					if(role != null){
						UserRole ur = new UserRole();
						ur.setRole(role);
						ur.setUser(user);
						userRoleDao.save(ur);
					}
				}
			}
			logger.info("=========================初始化用户结束==============================");
		}
	}
	
	/**根据用户id 查找用户 **/
	public User get(Class<User> c, Serializable id) {
		return (User) userDao.get(c, id);
	}

	/**保存或者修改用户**/
	@Override
	public void saveOrUpdate(User user) {
		userDao.saveOrUpdate(user);
	}

	/**根据orgId分页查询用户**/
	@Override
	public List<User> findAllUsers(String orgId, PageHelper page) {
		if(orgId != null && !"".equals(orgId)) {
			Object[] pa = {Integer.parseInt(orgId)};
			return userDao.find("from User where organization.id = ?", pa, page);
		}else{
			return userDao.find("from User");
		}
	}

	/***保存机构*/
	@Override
	public void saveOrUpdate(User user, String orgId,String roleIds) {
		Organization o = null;
		if(!orgId.equals("")) {
			o = organizationDao.get(Organization.class, Integer.parseInt(orgId));
			if(o == null){
				throw new ServiceException("机构不存在！");
			}
		}
		Set<UserRole> us = new HashSet<>();
		for(String roleid : roleIds.split(",")){
			UserRole ur = new UserRole();
			ur.setUser(user);
			Role r = roleDao.get(Role.class,Integer.parseInt(roleid));
			if(r == null){
				throw new ServiceException("角色不存在！");
			}
			ur.setRole(r);
			us.add(ur);
		};
		//登录名不能重复
		//添加
		long count = 0;
		if(user.getId() == null){
			Object[] param = {user.getLoginName()};
			count = userDao.count("select count(*) from User where loginName = ?",param);
			entryptPassword(user);
		}//修改
		else{
			Object [] param = {user.getId(),user.getLoginName()};
			count = userDao.count("select count(*) from User where id <> ? and loginName = ?",param);
			User prev = userDao.get(User.class,user.getId());
			//密码处理
			try {
				if("".equals(user.getPassword())) {
					user.setPassword(null);
				}else{
					entryptPassword(user);
				}
				String [] ignore = {"userRoles"};
				BeanExtUtils.copyProperties(prev, user, true, true,
						ignore);
				user = prev;
				//角色处理
				Object [] dp = {user.getId()};
				userRoleDao.executeHql("delete from UserRole where user.id = ?",dp);
			}catch (Exception e){
				throw  new ServiceException("对象拷贝失败");
			}
		}
		if(count > 0 ){
			throw  new ServiceException("登录名已存在！");
		}
		user.setOrganization(o);
		user.setUserRoles(us);
		userDao.saveOrUpdate(user);
	}

	@Override
	public List<User> findAllUsers(String hql) {
		return userDao.find(hql);
	}

	@Override
	public long countAll(String orgId) {
		return userDao.count("select count(*) from User where organization.id = ?",new Object[]{Integer.parseInt(orgId)});
	}

	public static void main(String [] args){
		String pwd = "ycsys123456";
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		String s = Encodes.encodeHex(salt);

		byte[] hashPassword = Digests.sha1(pwd.getBytes(),salt, Global.HASH_INTERATIONS);
		String last = Encodes.encodeHex(hashPassword);
		System.out.println(s);
		System.out.println(last);
	}

}
