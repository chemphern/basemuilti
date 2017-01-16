package com.ycsys.smartmap.sys.util;

import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.entity.User;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 扩展认证默认过滤
 * @author lixiaoxin
 * @date 2016年11月28日
 */
public class FormAuthenticationCaptchaFilter extends FormAuthenticationFilter {

	private String captchaParam = Global.LOGIN_CAPTCHA;

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		Subject subject = getSubject(request, response);
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		String uri = httpRequest.getRequestURI();
		String ctx = httpRequest.getContextPath();
		if(uri.equals(ctx + "/login")){
			return super.isAccessAllowed(request, response, mappedValue);
		}else if(uri.equals(ctx + "/logout")){
			return true;
		}
		//当使用记住密码功能时，查询用户并存入session的处理
		if(!subject.isAuthenticated() && subject.isRemembered()){
			Session session = subject.getSession(true);
			if(session.getAttribute(Global.SESSION_USER) == null){
				String username = subject.getPrincipal().toString();
				DbUtils db = new DbUtils();
				Connection conn = null;
				Properties properties = new Properties();
				String profile = "db.properties";
				InputStream pis = null;
				try {
					pis = FormAuthenticationCaptchaFilter.class.getResource("/" + profile).openStream();
					properties.load(pis);
					Class.forName(properties.getProperty("jdbc.driver"));
					conn = DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
					User user = db.query(conn, "select * from sys_user where login_name = ?", new BeanHandler<>(User.class),new Object[]{username});
					if(user != null){
						//是否超级管理员
						boolean is_super = false;
						List<Map<String,Object>> resList = db.queryForList(conn,"select r.isSuper from sys_role r join sys_user_role ur on r.id = ur.role_id where ur.user_id = ?",new Object[]{user.getId().toString()});
						for(Map<String,Object> res:resList){
							//判断是否超级管理员
							String isSuper = res.get("isSuper").toString();
							if(isSuper.equals("Y") || isSuper.equals("y")){
								is_super = true;
								break;
							}
						}
						user.setSuper(is_super);
						//设置用户session
						session.setAttribute(Global.SESSION_USER, user);
					}
				}
				catch (Exception ex) {
					throw new RuntimeException(ex);
				}
				finally {
					db.close(conn);
					try {
						pis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			String ip = NetWorkUtil.getIpAddress((HttpServletRequest)request);
			session.setAttribute(Global.NET_ENVIRONMENT, NetWorkUtil.isInnerNet(ip));
			return true;
		}
		return super.isAccessAllowed(request, response, mappedValue);
	}


	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		String captcha = getCaptcha(request);
		boolean rememberMe = true;//isRememberMe(request);
		String host = getHost(request);
		return new UsernamePasswordCaptchaToken(username,password.toCharArray(), rememberMe, host, captcha);
	}
}