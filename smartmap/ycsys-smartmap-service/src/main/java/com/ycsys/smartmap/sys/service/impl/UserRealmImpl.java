package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.utils.DateUtils;
import com.ycsys.smartmap.sys.common.utils.security.Encodes;
import com.ycsys.smartmap.sys.dao.PermissionDao;
import com.ycsys.smartmap.sys.dao.UserDao;
import com.ycsys.smartmap.sys.entity.Permission;
import com.ycsys.smartmap.sys.entity.ShiroUser;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.entity.UserRole;
import com.ycsys.smartmap.sys.util.CaptchaException;
import com.ycsys.smartmap.sys.util.UsernamePasswordCaptchaToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * 用户登录授权service(shrioRealm)
 * @author lixiaoxin
 * @date 2016年11月1日
 */
@Transactional
@Service
@DependsOn({"userDao","permissionDao","rolePermissionDao"})
public class UserRealmImpl extends AuthorizingRealm {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PermissionDao permissionDao;

	@Value("#{config.super_roles}")
	private String super_roles;
	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordCaptchaToken token = (UsernamePasswordCaptchaToken) authcToken;
		User user = (User)userDao.findByAttr("loginName", token.getUsername());
		if (user != null&&doCaptchaValidate(token)) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			ShiroUser shiroUser=new ShiroUser(user.getId(), user.getLoginName(), user.getName());
			//设置用户session
			//是否超级管理员
			boolean is_super = false;
			String [] super_roles_arr = null;
			if(super_roles != null){
				super_roles_arr = super_roles.split(",");
			}
			for(UserRole userRole:user.getUserRoles()){
				//判断是否超级管理员
				if(super_roles_arr != null){
					for(String super_role : super_roles_arr){
						String role_code = userRole.getRole().getCode();
						if(role_code != null && role_code.equals(super_role)){
							is_super = true;
						}
					}
				}
			}
			user.setSuper(is_super);
			Session session =SecurityUtils.getSubject().getSession();
			session.setAttribute(Global.SESSION_USER, user);
			return new SimpleAuthenticationInfo(shiroUser,user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = (User)userDao.findByAttr("loginName", shiroUser.loginName);
		
		//把principals放session中 key=前缀 + userId value=principals
		SecurityUtils.getSubject().getSession().setAttribute(Global.SESSION_PRINCIPALS_PREV + String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//是否超级管理员
		boolean is_super = false;
		String [] super_roles_arr = null;
		if(super_roles != null){
			super_roles_arr = super_roles.split(",");
		}
		//赋予角色
		for(UserRole userRole:user.getUserRoles()){
			String role_code = userRole.getRole().getCode();
			info.addRole(role_code);
			//判断是否超级管理员
			if(super_roles_arr != null){
				for(String super_role : super_roles_arr){
					if(role_code != null && role_code.equals(super_role)){
						is_super = true;
					}
				}
			}
		}
		if(is_super){
			info.addStringPermission("*");
		}else {
			//赋予权限
			for (Permission permission : (List<Permission>)permissionDao.findPermissions(user.getId())) {
				if (StringUtils.isNotBlank(permission.getCode()))
					info.addStringPermission(permission.getCode());
			}
		}
		//设置登录次数、时间
		user.setLoginCount((user.getLoginCount()==null?0:user.getLoginCount())+1);
		user.setPreviousVisit(user.getLastVisit());
		user.setLastVisit(DateUtils.getSysTimestamp());
		userDao.update(user);
		return info;
	}


	/**
	 * 验证码校验
	 * @param token
	 * @return boolean
	 */
	protected boolean doCaptchaValidate(UsernamePasswordCaptchaToken token)
	{
		String captcha = (String) SecurityUtils.getSubject().getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (captcha != null &&!captcha.equalsIgnoreCase(token.getCaptcha())){
			throw new CaptchaException("验证码错误！");
		}
		return true;
	}
		

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@SuppressWarnings("static-access")
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Global.HASH_ALGORITHM);
		matcher.setHashIterations(Global.HASH_INTERATIONS);
		setCredentialsMatcher(matcher);
	}
	
	@Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }
 
}
