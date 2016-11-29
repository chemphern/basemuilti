package com.ycsys.smartmap.service.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycsys.smartmap.service.entity.ServiceApply;
import com.ycsys.smartmap.service.service.ServiceApplyService;

/**
 * 服务申请 controller
 * @author liweixiong
 * @date   2016年11月3日
 */
@Controller
@RequestMapping("/serviceApply")
public class ServiceApplyController {
	@Autowired
	private ServiceApplyService serviceApplyService;
	
	@RequestMapping("add")
	public String add(ServiceApply serviceApply,Model model,HttpServletRequest request) {
		serviceApplyService.save(serviceApply);
		return "";
	}
	
	@RequestMapping("delete")
	public String delete(ServiceApply serviceApply) {
		serviceApplyService.delete(serviceApply);
		return "";
	}
	
	@RequestMapping("update")
	public String update(ServiceApply serviceApply,Model model) {
		serviceApplyService.update(serviceApply);
		return "";
	}
	
	@RequestMapping("list")
	public String list() {
		return "";
	}
}
