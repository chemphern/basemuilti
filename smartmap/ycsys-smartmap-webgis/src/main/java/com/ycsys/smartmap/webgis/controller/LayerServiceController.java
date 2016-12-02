package com.ycsys.smartmap.webgis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.ycsys.smartmap.service.entity.Layer;
import com.ycsys.smartmap.service.entity.LayerTheme;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.LayerService;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.service.service.ThemeService;

@Controller
@RequestMapping("/layerService")
public class LayerServiceController {
	
	@Autowired
	private ServiceService service;
	@Autowired
	private LayerService layerService;
	@Autowired
	private ThemeService themeService; 

	@ResponseBody
	@RequestMapping("/serviceList")
	public String findServiceList(HttpServletRequest request,HttpServletResponse response){
		String string=null;
		try {
			String sql="from Service where serviceStatus=1 and functionType ='MapServer' ";
			List<Service> list=service.find(sql);
			string=JSON.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}
	
	@ResponseBody
	@RequestMapping("/layerList")
	public String findLayerList(HttpServletRequest request,HttpServletResponse response){
		String string=null;
		try {
			String sql="from Layer";
			List<Layer> list=layerService.find(sql);
			string=JSON.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}
	
	@ResponseBody
	@RequestMapping("/themeList")
	public String findThemeList(HttpServletRequest request,HttpServletResponse response){
		String string=null;
		try {
			String sql="from LayerTheme";
			List<LayerTheme> list=themeService.find(sql);
			string=JSONArray.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}
	
}
