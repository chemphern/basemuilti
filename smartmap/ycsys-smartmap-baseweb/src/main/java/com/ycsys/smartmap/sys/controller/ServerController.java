package com.ycsys.smartmap.sys.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.JsonMapper;
import com.ycsys.smartmap.sys.common.utils.StringUtils;
import com.ycsys.smartmap.sys.entity.ConfigExceptionAlarm;
import com.ycsys.smartmap.sys.entity.ConfigServerEngine;
import com.ycsys.smartmap.sys.entity.DictionaryItem;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.Server;
import com.ycsys.smartmap.sys.entity.ServerType;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.ConfigServerEngineService;
import com.ycsys.smartmap.sys.service.ServerService;
import com.ycsys.smartmap.sys.service.ServerTypeService;
import com.ycsys.smartmap.sys.util.DataDictionary;

/**
 * 服务器 controller
 * 
 * @author 
 * @date 2016年11月17日
 */
@Controller
@RequestMapping("/server")
public class ServerController {
	
	@Autowired
	private ServerService serverService;
	@Autowired
	private ServerTypeService serverTypeService;
	@Autowired
	private ConfigServerEngineService configServerEngineService;

	@RequestMapping("toEdit")
	public String toEdit(String serverTypeId, Server server, Model model) {
		// 修改
		if (server.getId() != null) {
			server = serverService.get(Server.class, server.getId());
			model.addAttribute("server", server);
		}
		List<ConfigServerEngine> lists = configServerEngineService
				.find("from ConfigServerEngine c where 1=1");
		
		model.addAttribute("lists", lists);
		model.addAttribute("serverType", DataDictionary.getObject("server_type"));
		return "/server/server_edit";
	}
	
	//保存服务引擎配置方法
	@RequestMapping("save")
	@ResponseBody
	public String save(Server server,Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Global.SESSION_USER);
		//新增
		if(server.getId()==null){
			server.setCreateDate(new Date());
			server.setCreator(user);
			serverService.save(server);
		}
		//更新
		else{
			Server dbServer = serverService.get(Server.class,
					server.getId());
			try {
				// 得到修改过的属性
				BeanExtUtils.copyProperties(dbServer, server, true, true, null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			dbServer.setUpdateDate(new Date());
			dbServer.setUpdator(user);
			serverService.update(dbServer);
		}
		
		return "success";
	}

	/**
	 * 删除单条记录
	 * 
	 * @param resource
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResponseEx delete(Server server) {
		ResponseEx ex = new ResponseEx();
		if(server.getId() != null) {
			try {
				serverService.delete(server);
				ex.setSuccess("删除成功");
			} catch (Exception e) {
				ex.setFail("删除失败");
			}
			return ex;
		}
		ex.setFail("删除失败");
		return ex;
	}
	
	/**
	 * 支持删除多条记录
	 * 
	 * @param idsStr
	 * @return
	 */
	@RequestMapping("deletes")
	@ResponseBody
	public ResponseEx delete(String idsStr) {
		ResponseEx ex = new ResponseEx();
		String ids[] = idsStr.split(",");
		if(ids != null && ids.length > 0) {
			for(String id:ids) {
				Server server = serverService.get(Server.class, Integer.parseInt(id));
				if(server != null) {
					serverService.delete(server);
				}
			}
			ex.setSuccess("删除成功");
		}
		else {
			ex.setFail("删除失败");
		}
		return ex;
	}

	@RequestMapping("update")
	public String update(Server server, Model model) {
		serverService.update(server);
		return "";
	}

	@RequestMapping("list")
	public String list(Model model) {
		//List<ServerType> list = serverTypeService.find("from ServerType s where 1 = 1");
		List<Server> list = serverService.find("from Server s where 1 = 1");
		/*if (list == null || list.size() < 1) {
			// 没有数据，在页面显示新增按钮
			model.addAttribute("emptyTreeFlag", "1");
		}*/
		model.addAttribute("list", list);
		model.addAttribute("serverType", DataDictionary.getObject("server_type"));
		return "/server/server_list";
	}
	
	//列出所有服务器的名称
	/*@ResponseBody
	@RequestMapping(value = "listAllName",produces="application/json;charset=UTF-8")
	public String listAll(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Server> lists = serverService.find("from Server ");
		Map<String, Object> map = Maps.newHashMap();
		String rootId = UUID.randomUUID().toString();
		map.put("id", rootId);
		map.put("pid", "");
		map.put("text", "平台监控配置");
		mapList.add(map);
		for (Server e : lists) {
			map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pid", rootId);
			map.put("text", e.getName());
			mapList.add(map);			
		}
		String jsonStr = JsonMapper.toJsonString(mapList);
		return jsonStr;
	}*/
	
	/**
	 * 把所有服务器类型转成json字符串
	 */
	@ResponseBody
	@RequestMapping(value = "listServerType", produces = "application/json;charset=UTF-8")
	public String listServiceType(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String,Object> ruleTypeMap = DataDictionary.getObject("server_type");
		ruleTypeMap.entrySet();
		Map<String, Object> map = Maps.newHashMap();
		String rootId = UUID.randomUUID().toString();
		map.put("id", rootId);
		map.put("pid", "");
		map.put("text", "平台监控配置");
		mapList.add(map);
		for(Map.Entry<String,Object> entry: ruleTypeMap.entrySet()) {
			map = Maps.newHashMap();
			//String key = entry.getKey();
			DictionaryItem value = (DictionaryItem) entry.getValue();
			map.put("id", value.getValue());
			map.put("pid", rootId);
			map.put("text", value.getName());
			mapList.add(map);
		}
		String jsonStr = JsonMapper.toJsonString(mapList);
		return jsonStr;
	}
	
	//查询出页面表格需要的数据
	@ResponseBody
	@RequestMapping("/listData")
	public Grid<Server> listData(String serverType, PageHelper page) {

		List<Server> list = null;
		if (StringUtils.isNotBlank(serverType)) {
			list = serverService.find("from Server s where s.serverType = ?",
					new Object[] { serverType }, page);
		} else {
			list = serverService.find("from Server s where 1 = 1",
					null, page);
		}
		return new Grid<Server>(list);
	}
	
}
