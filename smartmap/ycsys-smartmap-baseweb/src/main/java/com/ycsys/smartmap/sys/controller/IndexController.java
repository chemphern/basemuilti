package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.monitor.service.ServiceMonitorService;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.ServiceApplyService;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.entity.Permission;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.AlarmService;
import com.ycsys.smartmap.sys.service.NoticeReceiverService;
import com.ycsys.smartmap.sys.service.PermissionService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.util.*;

@Controller
public class IndexController{

	@Resource
	private PermissionService permissionService;

	@Resource(name="config")
	private Properties config;
	
	@Resource
	private ServiceApplyService serviceApplyService;
	
	@Resource
    private NoticeReceiverService noticeReceiverService;
	
	@Resource
    private AlarmService alarmService;
	
	@Resource
    private ServiceService serviceService;
	
	@Resource
    private ServiceMonitorService serviceMonitorService;

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
	public String welcome(Model model,HttpSession session){
		User user = (User) session.getAttribute(Global.SESSION_USER);
		//获取未读消息数量
		long receiveMsgCount = noticeReceiverService.count("select count(*) from NoticeReceiver t where t.user.id = ? and t.status = ?", new Object[]{user.getId(),(short)1});
		//获取待审核的服务数量
		long serviceApplyCount = serviceApplyService.count("select count(*) from ServiceApply t where t.auditStatus = ? ", new Object[]{"0"});
		//获取报警数量
		long alarmCount = alarmService.count("select count(*) from Alarm t where t.status = ? ", new Object[]{"0"});
		//服务停止个数
		long stopCount = serviceService.count("select count(*) from Service t where t.serviceStatus = ? ", new Object[]{"0"});
		
		model.addAttribute("alarmCount", alarmCount);
		model.addAttribute("receiveMsgCount", receiveMsgCount);
		model.addAttribute("serviceApplyCount", serviceApplyCount);
		model.addAttribute("serviceStopCount", stopCount);
		return "/main/welcome";
	}
	
}
