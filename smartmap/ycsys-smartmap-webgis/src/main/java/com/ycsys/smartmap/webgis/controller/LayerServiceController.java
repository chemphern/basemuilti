package com.ycsys.smartmap.webgis.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.sys.common.result.ResponseEx;

@Controller
@RequestMapping("/layerService")
public class LayerServiceController {
	
	@Autowired
	private ServiceService service;

	@ResponseBody
	@RequestMapping("/serviceList")
	public String findServiceList(HttpServletRequest request,HttpServletResponse response){
		
		String string=null;
		try {
//			String sql="from Service where 1=1 ";
//			sql=sql.concat("and serviceStatus=1 and registerType");
//			List<Service> list=service.find(sql);
//			rEx=new ResponseEx(1, "success", list);
			
			//测试数据
			Service service=new Service();
			service.setId(1);
			service.setShowName("广州市林场图");
			service.setServiceVisitAddress("http://172.16.10.52:6080/arcgis/rest/services/ceshi/MapServer");
			Service service2=new Service();
			service2.setId(2);
			service2.setShowName("测试图层");
			service2.setServiceVisitAddress("http://172.16.10.52:6080/arcgis/rest/services/SampleWorldCities/MapServer");
			
			List<Service> list=new ArrayList<>();
			list.add(service);
			list.add(service2);
			string=JSON.toJSONString(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return string;
	}
}
