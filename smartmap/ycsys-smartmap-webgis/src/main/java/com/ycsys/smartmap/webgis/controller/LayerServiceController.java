package com.ycsys.smartmap.webgis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ycsys.smartmap.service.entity.Layer;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.LayerService;
import com.ycsys.smartmap.service.service.ServiceService;

@Controller
@RequestMapping("/layerService")
public class LayerServiceController {
	
	@Autowired
	private ServiceService service;
	@Autowired
	private LayerService layerService;

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
			String sql="select new com.ycsys.smartmap.service.entity.Layer(l.id,l.name,l.address,l.geometryType,l.pId,l.type,l.nameField,l.summaryFields,l.displayFields,l.businessType) from Layer l where l.type is not null";
			List<Layer> list=layerService.find(sql);
			string=JSON.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}
	
}
