package com.ycsys.smartmap.webgis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.ycsys.smartmap.service.entity.LayerTheme;
import com.ycsys.smartmap.service.service.ThemeService;

@Controller
@RequestMapping("/themeService")
public class ThemeServiceController {
	
	@Autowired
	private ThemeService themeService; 

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
