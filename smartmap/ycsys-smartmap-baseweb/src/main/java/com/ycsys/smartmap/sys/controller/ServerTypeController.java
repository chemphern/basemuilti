package com.ycsys.smartmap.sys.controller;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycsys.smartmap.sys.entity.ServerType;
import com.ycsys.smartmap.sys.service.ServerTypeService;
import com.ycsys.smartmap.sys.service.UserService;

/**
 * 服务器分类 controller
 * 
 * @author liweixiong
 * @date 2016年11月2日
 */
@Controller
@RequestMapping("/serverType")
public class ServerTypeController {
	@Autowired
	private ServerTypeService serverTypeService;
	@Resource
	private UserService userService;

	@RequestMapping("add")
	public String add() {
		ServerType st = new ServerType();
		st.setName("测试");
		st.setRemarks("备注");
		st.setCreateDate(new Date());
		serverTypeService.save(st);
		return "index";
	}
	
	@RequestMapping("delete")
	public String delete() {
		//serverTypeService.delete(o);
		return "";
	}
	
	@RequestMapping("update")
	public String update() {
		//serverTypeService.update(o);
		return "";
	}
	
	@RequestMapping("find")
	public String find() {
		//ServerType s1 = serverTypeService.get(ServerType.class, 54);
		return "index";
	}
}
