package com.ycsys.smartmap.sys.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.ycsys.smartmap.sys.entity.Organization;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.ConfigExceptionAlarmService;
import com.ycsys.smartmap.sys.service.OrganizationService;
import com.ycsys.smartmap.sys.service.UserService;
import com.ycsys.smartmap.sys.util.DataDictionary;

/**
 * 异常报警配置 controller
 * 
 * @author lrr
 * @date 2016年11月17日
 */
@Controller
@RequestMapping("/configExceptionAlarm")
public class ConfigExceptionAlarmController {
	private static Logger log = Logger
			.getLogger(ConfigExceptionAlarmController.class);
	
	@Autowired
	private ConfigExceptionAlarmService configExceptionAlarmService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrganizationService organizationService;

	//添加服务引擎方法
	@RequestMapping("toAdd")
	public String toAdd(Model model) {
		return "/configExceptionAlarm/configExceptionAlarm_add";
	}
	
	@RequestMapping("toEdit")
	public String toEdit(ConfigExceptionAlarm configExceptionAlarm, Model model) {
		// 修改
		if (configExceptionAlarm.getId() != null) {
			configExceptionAlarm = configExceptionAlarmService.get(ConfigExceptionAlarm.class, configExceptionAlarm.getId());
			model.addAttribute("configExceptionAlarm", configExceptionAlarm);
		}
		List<User> lists = userService.findAllUsers("from User u where 1=1");
		List<Organization> orgLists = organizationService.findAll();
		
		model.addAttribute("lists", lists);
		model.addAttribute("orgLists", orgLists);
		model.addAttribute("ruleType", DataDictionary.getObject("configException_type"));
		return "/configExceptionAlarm/configExceptionAlarm_add";
	}
	
	//保存服务引擎配置方法
	@RequestMapping("save")
	@ResponseBody
	public String save(ConfigExceptionAlarm configExceptionAlarm,Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Global.SESSION_USER);
		//新增
		if(configExceptionAlarm.getId()==null){
			configExceptionAlarm.setCreateDate(new Date());
			configExceptionAlarm.setCreator(user);
			configExceptionAlarmService.save(configExceptionAlarm);
		}
		//更新
		else{
			ConfigExceptionAlarm dbConfigExceptionAlarm = configExceptionAlarmService.get(ConfigExceptionAlarm.class,
					configExceptionAlarm.getId());
			try {
				// 得到修改过的属性
				BeanExtUtils.copyProperties(dbConfigExceptionAlarm, configExceptionAlarm, true, true,
						null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			dbConfigExceptionAlarm.setUpdateDate(new Date());
			dbConfigExceptionAlarm.setUpdator(user);
			configExceptionAlarmService.update(dbConfigExceptionAlarm);
		}
		return "success";
	}

	/*
	 * 删除多条数据记录
	 */
	@RequestMapping("deletes")
	@ResponseBody
	public ResponseEx delete(String idsStr) {
		ResponseEx ex = new ResponseEx();
		String ids[] = idsStr.split(",");
		if(ids != null && ids.length > 0) {
			for(String id:ids) {
				ConfigExceptionAlarm configExceptionAlarm = configExceptionAlarmService.get(ConfigExceptionAlarm.class, Integer.parseInt(id));
				if(configExceptionAlarm != null) {
					configExceptionAlarmService.delete(configExceptionAlarm);
				}
			}
			ex.setSuccess("删除成功");
		}
		else {
			ex.setFail("删除失败");
		}
		return ex;
	}
	/**
	 * 删除单条数据记录
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public ResponseEx delete(ConfigExceptionAlarm configExceptionAlarm){
        ResponseEx ex = new ResponseEx();
        try{
        	configExceptionAlarmService.delete(configExceptionAlarm);
            ex.setSuccess("删除成功");
        }catch (Exception e){
            ex.setFail("删除失败");
        }
        return ex;
    }

	@RequestMapping("update")
	public String update(ConfigExceptionAlarm configExceptionAlarm, Model model) {
		configExceptionAlarmService.update(configExceptionAlarm);
		return "";
	}

	//列表列出所有数据
	@RequestMapping("list")
	public String list(Model model){
		List<ConfigExceptionAlarm> list = configExceptionAlarmService.find("from ConfigExceptionAlarm c where 1=1 ");
		model.addAttribute("list", list);
		model.addAttribute("ruleType", DataDictionary.getObject("configException_type"));
		return "configExceptionAlarm/configExceptionAlarm_list";
	}
	
	/*@ResponseBody
	@RequestMapping("/listData")
	public Grid<ConfigExceptionAlarm> listData(PageHelper page) {
		Grid g = new Grid(configExceptionAlarmService.find("from ConfigExceptionAlarm c where 1 = 1",
				null, page));
		return g;
	}*/
	
	//查询出页面表格需要的数据
	@ResponseBody
	@RequestMapping("/listData")
	public Grid<ConfigExceptionAlarm> listData(String ruleType, PageHelper page) {

		List<ConfigExceptionAlarm> list = null;
		if (StringUtils.isNotBlank(ruleType)) {
			list = configExceptionAlarmService.find("from ConfigExceptionAlarm c where c.ruleType = ?",
					new Object[] { ruleType }, page);
		} else {
			list = configExceptionAlarmService.find("from ConfigExceptionAlarm c where 1 = 1",
					null, page);
		}

		return new Grid<ConfigExceptionAlarm>(list);
	}
	
	/*//列出所有异常报警类型
	@ResponseBody
	@RequestMapping(value = "listAllRuleType",produces="application/json;charset=UTF-8")
	public String listAll(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ConfigExceptionAlarm> lists = configExceptionAlarmService.find("from ConfigExceptionAlarm ");
		for (ConfigExceptionAlarm e : lists) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("ruleType", e.getRuleType());
			mapList.add(map);			
		}
		String jsonStr = JsonMapper.toJsonString(mapList);
		return jsonStr;
	}*/
	
	/**
	 * 把所有异常报警类型转成json字符串
	 */
	@ResponseBody
	@RequestMapping(value = "listRuleType", produces = "application/json;charset=UTF-8")
	public String listServiceType(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String,Object> ruleTypeMap = DataDictionary.getObject("configException_type");
		ruleTypeMap.entrySet();
		Map<String, Object> map = Maps.newHashMap();
		String rootId = UUID.randomUUID().toString();
		map.put("id", rootId);
		map.put("pid", "");
		map.put("text", "异常报警规则");
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
}
