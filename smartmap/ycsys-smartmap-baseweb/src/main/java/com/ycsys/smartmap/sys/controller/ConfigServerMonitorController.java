package com.ycsys.smartmap.sys.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycsys.smartmap.sys.entity.ConfigServerMonitor;
import com.ycsys.smartmap.sys.service.ConfigServerMonitorService;

/**
 * 服务器监控配置 controller
 * 
 * @author lrr
 * @date 2016年11月28日
 */
@Controller
@RequestMapping("/serverMonitor")
public class ConfigServerMonitorController {
	@Autowired
	private ConfigServerMonitorService configServerMonitorService;

	@RequestMapping("add")
	public String add(ConfigServerMonitor configServerMonitor, Model model, HttpServletRequest request) {
		configServerMonitorService.save(configServerMonitor);
		return "";
	}

	@RequestMapping("delete")
	public String delete(ConfigServerMonitor configServerMonitor) {
		configServerMonitorService.delete(configServerMonitor);
		return "";
	}

	@RequestMapping("update")
	public String update(ConfigServerMonitor configServerMonitor, Model model) {
		configServerMonitorService.update(configServerMonitor);
		return "";
	}

	@RequestMapping("list")
	public String list() {
		return "serverMonitor/serverMonitor_list";
	}
}
