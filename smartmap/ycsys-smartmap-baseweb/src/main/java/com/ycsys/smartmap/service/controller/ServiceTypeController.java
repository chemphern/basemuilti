package com.ycsys.smartmap.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycsys.smartmap.service.entity.ServiceType;
import com.ycsys.smartmap.service.service.ServiceTypeService;

/**
 * 服务分类 controller
 * @author liweixiong
 * @date   2016年11月3日
 */
@Controller
@RequestMapping("/serviceType")
public class ServiceTypeController {
	@Autowired
	private ServiceTypeService serviceTypeService;
	
	@RequestMapping("add")
	public String add(ServiceType serviceType,Model model,HttpServletRequest request) {
		serviceTypeService.save(serviceType);
		return "";
	}
	
	@RequestMapping("delete")
	public String delete(ServiceType serviceType) {
		serviceTypeService.delete(serviceType);
		return "";
	}
	
	@RequestMapping("update")
	public String update(ServiceType serviceType,Model model) {
		serviceTypeService.update(serviceType);
		return "";
	}
	
	@RequestMapping("list")
	public String list() {
		return "";
	}
}
