package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.entity.Permission;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class IndexController{

	@Resource
	private PermissionService permissionService;

	@Resource(name="config")
	private Properties config;

	@RequestMapping("/index")
	public String index(Model model,HttpSession session){
		User user = (User) session.getAttribute(Global.SESSION_USER);
		List<Permission> p = null;
		String sys_code = config.getProperty("system.code");
		if(user.isSuper()) {
			p = permissionService.getMenus(sys_code);
		}else{
			p = permissionService.getMenus(user.getId(),sys_code);
		}
		model.addAttribute("index_permissions",p);
		return "/main/index";
	}
	@RequestMapping("/welcome")
	public String welcome(){
		return "/main/welcome";
	}

}
