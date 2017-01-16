package com.ycsys.smartmap.statistics.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import sun.misc.BASE64Encoder;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ycsys.smartmap.monitor.entity.NativeDbMonitor;
import com.ycsys.smartmap.monitor.entity.NativeServerMonitor;
import com.ycsys.smartmap.monitor.entity.NativeTomcatMonitor;
import com.ycsys.smartmap.monitor.entity.ServiceMonitor;
import com.ycsys.smartmap.monitor.service.NativeDbMonitorService;
import com.ycsys.smartmap.monitor.service.NativeServerMonitorService;
import com.ycsys.smartmap.monitor.service.NativeTomcatMonitorService;
import com.ycsys.smartmap.monitor.service.ServiceMonitorService;
import com.ycsys.smartmap.monitor.service.ServiceRequestService;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.statistics.entity.StatApplicationServer;
import com.ycsys.smartmap.statistics.entity.StatDatabase;
import com.ycsys.smartmap.statistics.entity.StatOperatingSystem;
import com.ycsys.smartmap.statistics.entity.statService;
import com.ycsys.smartmap.statistics.vo.StatMaintenanceOperationVo;
import com.ycsys.smartmap.sys.common.enums.LogType;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.utils.Base64Util;
import com.ycsys.smartmap.sys.common.utils.ChartUtils;
import com.ycsys.smartmap.sys.common.utils.DateUtils;
import com.ycsys.smartmap.sys.common.utils.FileUtils;
import com.ycsys.smartmap.sys.common.utils.StringUtils;
import com.ycsys.smartmap.sys.common.utils.WordUtils;
import com.ycsys.smartmap.sys.common.utils.ChartUtils.Serie;
import com.ycsys.smartmap.sys.controller.BaseController;
import com.ycsys.smartmap.sys.entity.Log;
import com.ycsys.smartmap.sys.service.LogService;
import com.ycsys.smartmap.sys.service.UserService;

/**
 * 
 * @author liweixiong
 * @date   2016年12月26日
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController extends BaseController{
	@Autowired
	private NativeDbMonitorService nativeDbMonitorService;
	@Autowired
	private NativeTomcatMonitorService nativeTomcatMonitorService;
	@Autowired
	private ServiceMonitorService serviceMonitorService;
	@Autowired
	private NativeServerMonitorService nativeServerMonitorService;
	@Autowired
	private ServiceRequestService serviceRequestService;
	@Autowired
	private ServiceService serviceService;
	@Autowired 
	private UserService userService;
	@Autowired
	private LogService logService;
	
	/*
	 * 平台数据库统计 列表
	 */
	@RequestMapping("/databaseList")
	@RequiresPermissions(value = "stat-database-list")
    public String databaseList(Model model){
		//得到当前时间
		Date curDate = new Date(); 
		String curDateStr = DateUtils.formatDate(curDate, "yyyy-MM-dd");
		String curDateToStr = DateUtils.formatDate(curDate, "yyyy-MM-dd");
		model.addAttribute("curDate", curDateStr+" 00:00");
		model.addAttribute("curDateTo", curDateToStr+" 23:59");
        return "/statistics/stat_database_list";
    }
	
	/**
	 * 得到数据库会话数量信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getSessionInfo")
	@RequiresPermissions(value = "stat-database-list")
	public Map<String,String> getSessionInfo(String startTime,String endTime) {
		Map<String, String> map = new HashMap<String, String>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from NativeDbMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append("order by t.time asc ");
		List<NativeDbMonitor> dbList = nativeDbMonitorService.find(hql.toString(), params);
		JSONArray xJsonArr = new JSONArray();
		JSONArray yJsonArr = new JSONArray();
		for(NativeDbMonitor data : dbList) {
			String time = DateUtils.formatDate(data.getTime(), "MM-dd HH:mm:ss");
			xJsonArr.add(time);
			yJsonArr.add(data.getConnect());
		}
		map.put("xAxisData", xJsonArr.toJSONString());
		map.put("seriesData", yJsonArr.toJSONString());
		return map;
	}
	
	/**
	 * 得到数据库统计信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listDatabaseData")
	@RequiresPermissions(value = "stat-database-list")
	public Grid<StatDatabase> listDatabaseData(String startTime,String endTime) {
		/*List<Object> params = new ArrayList<Object>();
		StringBuffer hql1 = new StringBuffer(); //会话最大hql
		StringBuffer hql2 = new StringBuffer(); //会话最小hql
		StringBuffer hql3 = new StringBuffer(); //会话平均hql
		hql1.append("select max(t.connect),t.time,t.type from NativeDbMonitor t where 1 = 1 ");
		hql2.append("select min(t.connect),t.time from NativeDbMonitor t where 1 = 1 ");
		hql3.append("select avg(t.connect) from NativeDbMonitor t where 1 = 1 ");
		
		if(StringUtils.isNotBlank(startTime)) {
			hql1.append("and t.time >= ? ");
			hql2.append("and t.time >= ? ");
			hql3.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql1.append("and t.time <= ? ");
			hql2.append("and t.time <= ? ");
			hql3.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		Object obj1[] = nativeDbMonitorService.findArrValue(hql1.toString(), params);
		Object obj2[] = nativeDbMonitorService.findArrValue(hql2.toString(), params);
		Object obj3[] = nativeDbMonitorService.findArrValue(hql3.toString(), params);
		
		List<StatDatabase> list =  new ArrayList<StatDatabase>();
		StatDatabase dbMonitor = new StatDatabase();
		
		if(obj1 != null && obj1.length > 0) {
			Object[] objArr = (Object[]) obj1[0];
			if(objArr[0] != null) {
				dbMonitor.setSessionMaxCount(Integer.parseInt(objArr[0].toString()));
			}
			if(objArr[1] != null) {
				String str = objArr[1].toString();
				str = str.substring(0, str.indexOf(".")); //把时间多出的.xx去掉
				dbMonitor.setSessionMaxDate(DateUtils.parseDate(str));
			}
			if(objArr[2] != null) {
				dbMonitor.setDatabaseName(objArr[2].toString());
			}
			
		}
		if(obj2 != null && obj2.length > 0) {
			Object[] objArr = (Object[]) obj2[0];
			if(objArr[0] != null) {
				dbMonitor.setSessionMinCount(Integer.parseInt(objArr[0].toString()));
			}
			if(objArr[1] != null) {
				String str = objArr[1].toString();
				str = str.substring(0, str.indexOf("."));
				dbMonitor.setSessionMinDate(DateUtils.parseDate(str));
			}
		}
		if(obj3 != null && obj3.length > 0) {
			Object objArr =  obj3[0];
			if(objArr != null) {
				float f = Float.parseFloat(objArr.toString());
				BigDecimal bd = new BigDecimal(f);
				f = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
				dbMonitor.setSessionAverage(f);
			}
		}
		
		int count = 1;
		list.add(dbMonitor);
		Grid<StatDatabase> g = new Grid<StatDatabase>(count,list);
		return g;*/
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from NativeDbMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append("order by t.connect desc,t.time desc ");
		List<NativeDbMonitor> dbList = nativeDbMonitorService.find(hql.toString(), params);
		
		StatDatabase sd = new StatDatabase();
		sd.setDatabaseName(dbList.get(0).getType());
		long connect = 0;
		for(NativeDbMonitor data : dbList) {
			connect = connect + data.getConnect();
		}
		List<StatDatabase> list =  new ArrayList<StatDatabase>();
		
		float f = connect/(dbList.size()*1f);
		BigDecimal bd = new BigDecimal(f);
		f = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); //保留2位小数
		sd.setSessionAverage(f);
		sd.setSessionMaxDate(dbList.get(0).getTime());
		sd.setSessionMinDate(dbList.get(dbList.size()-1).getTime());
		sd.setSessionMinCount(dbList.get(dbList.size()-1).getConnect());
		sd.setSessionMaxCount(dbList.get(0).getConnect());
		int count = 1;
		list.add(sd);
		Grid<StatDatabase> g = new Grid<StatDatabase>(count,list);
		return g;
	}
	
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @param statType 统计类型（1：线程池信息；2：JVM内存信息）
	 * @param paramType 参数类型（1：当前线程数｜已占用内存；2：繁忙线程数｜空闲内存）
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getAppServerInfo")
	@RequiresPermissions(value = "stat-applicationServer-list")
	public Map<String,String> getAppServerInfo(String startTime,String endTime,String statType,String paramType) {
		Map<String, String> map = new HashMap<String, String>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from NativeTomcatMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append("order by t.time asc ");
		List<NativeTomcatMonitor> dbList = nativeTomcatMonitorService.find(hql.toString(), params);
		JSONArray xJsonArr = new JSONArray();
		JSONArray seriesData1 = new JSONArray();
		JSONArray seriesData2 = new JSONArray();
		JSONArray seriesData3 = new JSONArray();
		JSONArray seriesData4 = new JSONArray();
		for(NativeTomcatMonitor data : dbList) {
			String time = DateUtils.formatDate(data.getTime(), "MM-dd HH:mm:ss");
			xJsonArr.add(time);
			seriesData1.add(data.getThread());
			seriesData2.add(data.getThreadBusy());
			double userMemory = data.getUsedMemory()/(1024*1024);
			BigDecimal bd = new BigDecimal(userMemory);
			userMemory = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			seriesData3.add(userMemory);
			double freeMemory = data.getFreeMemory()/(1024*1024);
			BigDecimal bd2 = new BigDecimal(freeMemory);
			freeMemory = bd2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			seriesData4.add(freeMemory);
			
		}
		String type[] = paramType.split(",");
		if("1".equals(statType)) {
			if(type.length == 1 && type[0].equals("1")) {
				map.put("seriesData1", seriesData1.toJSONString());
			}
			else if(type.length == 1 && type[0].equals("2")) {
				map.put("seriesData2", seriesData2.toJSONString());
			}
			else if(type.length == 2){
				map.put("seriesData1", seriesData1.toJSONString());
				map.put("seriesData2", seriesData2.toJSONString());
			}
		}
		else if("2".equals(statType)) {
			if(type.length == 1 && type[0].equals("1")) {
				map.put("seriesData1", seriesData3.toJSONString());
			}
			else if(type.length == 1 && type[0].equals("2")) {
				map.put("seriesData2", seriesData4.toJSONString());
			}
			else if(type.length == 2){
				map.put("seriesData1", seriesData3.toJSONString());
				map.put("seriesData2", seriesData4.toJSONString());
			}
		}
		map.put("xAxisData", xJsonArr.toJSONString());
		
		return map;
	
	}
	
	/**
	 * tomcat统计
	 * @param request
	 * @param startTime
	 * @param endTime
	 * @param statType(1:线程池信息;2:JVM内存信息)
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listAppServerData")
	@RequiresPermissions(value = "stat-applicationServer-list")
	public Grid<StatApplicationServer> listAppServerData(HttpServletRequest request,String startTime,String endTime,String statType) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql1 = new StringBuffer(); //thread hql
		StringBuffer hql2 = new StringBuffer(); //threadBusy hql
		StringBuffer hql3 = new StringBuffer(); //usedMemory hql
		StringBuffer hql4 = new StringBuffer(); //freeMemory hql
		if("1".equals(statType)) {
			hql1.append("select max(t.thread),min(t.thread),avg(t.thread) from NativeTomcatMonitor t where 1 = 1 ");
			hql2.append("select max(t.threadBusy),min(t.threadBusy),avg(t.threadBusy) from NativeTomcatMonitor t where 1 = 1 ");
		}
		else if("2".equals(statType)) {
			hql3.append("select max(t.usedMemory),min(t.usedMemory),avg(t.usedMemory) from NativeTomcatMonitor t where 1 = 1 ");
			hql4.append("select max(t.freeMemory),min(t.freeMemory),avg(t.freeMemory) from NativeTomcatMonitor t where 1 = 1 ");
		}
		
		if(StringUtils.isNotBlank(startTime)) {
			hql1.append("and t.time >= ? ");
			hql2.append("and t.time >= ? ");
			hql3.append("and t.time >= ? ");
			hql4.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql1.append("and t.time <= ? ");
			hql2.append("and t.time <= ? ");
			hql3.append("and t.time <= ? ");
			hql4.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		
		StatApplicationServer appServer = new StatApplicationServer();
		appServer.setServerName("tomcat");
		
		if("1".equals(statType)) {
			Object obj1[] = nativeTomcatMonitorService.findArrValue(hql1.toString(), params);
			Object obj2[] = nativeTomcatMonitorService.findArrValue(hql2.toString(), params);
			
			if(obj1 != null && obj1.length > 0) {
				Object[] objArr = (Object[]) obj1[0];
				if(objArr[0] != null) {
					appServer.setThreadMax(Integer.parseInt(objArr[0].toString()));
				}
				if(objArr[1] != null) {
					appServer.setThreadMin(Integer.parseInt(objArr[1].toString()));
				}
				if(objArr[2] != null) {
					BigDecimal bd = new BigDecimal(Float.parseFloat(objArr[2].toString()));
					float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
					appServer.setThreadAverage(f);
				}
			}
			if(obj2 != null && obj2.length > 0) {
				Object[] objArr = (Object[]) obj2[0];
				if(objArr[0] != null) {
					appServer.setBusyThreadMax(Integer.parseInt(objArr[0].toString()));
				}
				if(objArr[1] != null) {
					appServer.setBusyThreadMin(Integer.parseInt(objArr[1].toString()));
				}
				if(objArr[2] != null) {
					BigDecimal bd = new BigDecimal(Float.parseFloat(objArr[2].toString()));
					float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
					appServer.setBusyThreadAverage(f);
				}
			}
		}
		else if("2".equals(statType)) {
			Object obj3[] = nativeTomcatMonitorService.findArrValue(hql3.toString(), params);
			Object obj4[] = nativeTomcatMonitorService.findArrValue(hql4.toString(), params);
			
			if(obj3 != null && obj3.length > 0) {
				Object[] objArr = (Object[]) obj3[0];
				if(objArr[0] != null) {
					long maxMemory = Long.parseLong(objArr[0].toString())/(1024*1024);
					appServer.setHaveMaxMemory(maxMemory);
				}
				if(objArr[1] != null) {
					long minMemory = Long.parseLong(objArr[1].toString())/(1024*1024);
					appServer.setHaveMinMemory(minMemory);
				}
				if(objArr[2] != null) {
					BigDecimal bd = new BigDecimal(Float.parseFloat(objArr[2].toString())/1024);
					float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
					appServer.setHaveAverageMemory(f);
				}
			}
			
			if(obj4 != null && obj4.length > 0) {
				Object[] objArr = (Object[]) obj4[0];
				if(objArr[0] != null) {
					appServer.setFreeMaxMemory(Long.parseLong(objArr[0].toString())/(1024*1024));
				}
				if(objArr[1] != null) {
					appServer.setFreeMinMemory(Long.parseLong(objArr[1].toString())/(1024*1024));
				}
				if(objArr[2] != null) {
					BigDecimal bd = new BigDecimal(Float.parseFloat(objArr[2].toString())/(1024*1024));
					float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
					appServer.setFreeAverageMemory(f);
				}
			}
		}
		
		List<StatApplicationServer> list = new ArrayList<StatApplicationServer>();
		int count = 1;
		list.add(appServer);
		Grid<StatApplicationServer> g = new Grid<StatApplicationServer>(count,list);
		return g;
		
	}
	
	@ResponseBody
	@RequestMapping("/getMaintenanceOperationInfo")
	@RequiresPermissions(value = "stat-maintenanceOperation-listt")
	public Map<String,String> getMaintenanceOperationInfo(String startTime,String endTime) {
		Map<String, String> map = new HashMap<String, String>();
		//成功率
		JSONArray successRateJsonArr = new JSONArray();
		//正常
		JSONObject rightJson = new JSONObject();
		rightJson.put("name", "正常");
		rightJson.put("value", "150");
		successRateJsonArr.add(rightJson);
		//错误
		JSONObject errorJson = new JSONObject();
		errorJson.put("name", "错误");
		errorJson.put("value", "310");
		successRateJsonArr.add(errorJson);
		map.put("successRate", successRateJsonArr.toJSONString());
		
		//用户操作
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql1 = new StringBuffer(); //用户操作 hql
		StringBuffer hql2 = new StringBuffer(); //操作类别 hql
		hql1.append("select t.user.loginName, count(*) from Log t where 1 = 1 ");
		hql2.append("select t.operationType, count(*) from Log t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql1.append("and t.createTime >= ? ");
			hql2.append("and t.createTime >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql1.append("and t.createTime <= ? ");
			hql2.append("and t.createTime <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql1.append("group by t.user.loginName ");
		hql2.append("group by t.operationType ");
		Object[] logObj = logService.findArrValue(hql1.toString(), params, 1, 10);
		Object[] logTypObj = logService.findArrValue(hql2.toString(), params, 1, 10);
		JSONArray xOpeJsonArr = new JSONArray();
		JSONArray yOpeJsonArr = new JSONArray();
		JSONArray xOpeTypeJsonArr = new JSONArray();
		JSONArray yOpeTypeJsonArr = new JSONArray();
		if(logObj != null && logObj.length > 0) {
			for(int i = 0; i < logObj.length; i++) {
				Object[] objArr = (Object[]) logObj[i];
				if(objArr != null && objArr.length > 0) {
					if(objArr[0] != null && objArr[1] != null) {
						xOpeJsonArr.add(objArr[0]);
						yOpeJsonArr.add(objArr[1]);
					}
				}
			}
			map.put("xOpetAxisData", xOpeJsonArr.toJSONString());
			map.put("opeSeriesData", yOpeJsonArr.toJSONString());
		}
		
		if(logTypObj != null && logTypObj.length > 0) {
			for(int i = 0; i < logTypObj.length; i++) {
				Object[] objArr = (Object[]) logTypObj[i];
				if(objArr != null && objArr.length > 0) {
					if(objArr[0] != null && objArr[1] != null) {
						xOpeTypeJsonArr.add(LogType.findValueByType(Integer.parseInt(objArr[0].toString())));
						yOpeTypeJsonArr.add(objArr[1]);
					}
				}
			}
			map.put("xOpetTypeAxisData", xOpeTypeJsonArr.toJSONString());
			map.put("opeTypeSeriesData", yOpeTypeJsonArr.toJSONString());
		}
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/listMaintenanceOperationData")
	@RequiresPermissions(value = "stat-maintenanceOperation-list")
	public Grid<StatMaintenanceOperationVo> listMaintenanceOperationData(String startTime,String endTime,int type) {
		List<StatMaintenanceOperationVo> operationVoList = new ArrayList<StatMaintenanceOperationVo>();
		//用户操作
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql1 = new StringBuffer(); //用户操作 hql
		StringBuffer hql2 = new StringBuffer(); //操作类别 hql
		hql1.append("select t.user.loginName, count(*) from Log t where 1 = 1 ");
		hql2.append("select t.operationType, count(*) from Log t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql1.append("and t.createTime >= ? ");
			hql2.append("and t.createTime >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql1.append("and t.createTime <= ? ");
			hql2.append("and t.createTime <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql1.append("group by t.user.loginName ");
		hql2.append("group by t.operationType ");
		if(1 == type){
			Object[] logObj = logService.findArrValue(hql1.toString(), params, 1, 10);
			if(logObj != null && logObj.length > 0) {
				for(int i = 0; i < logObj.length; i++) {
					Object[] objArr = (Object[]) logObj[i];
					if(objArr != null && objArr.length > 0) {
						if(objArr[0] != null && objArr[1] != null) {
							StatMaintenanceOperationVo operration = new StatMaintenanceOperationVo();
							operration.setOperateType(objArr[0].toString());
							operration.setOperateCount(Long.parseLong(objArr[1].toString()));
							operationVoList.add(operration);
						}
					}
				}
			}
		}
		else if(type == 2) {
			Object[] logTypObj = logService.findArrValue(hql2.toString(), params, 1, 10);
			if(logTypObj != null && logTypObj.length > 0) {
				for(int i = 0; i < logTypObj.length; i++) {
					Object[] objArr = (Object[]) logTypObj[i];
					if(objArr != null && objArr.length > 0) {
						if(objArr[0] != null && objArr[1] != null) {
							StatMaintenanceOperationVo operration = new StatMaintenanceOperationVo();
							operration.setOperateType(LogType.findValueByType(Integer.parseInt(objArr[0].toString())));
							operration.setOperateCount(Long.parseLong(objArr[1].toString()));
							operationVoList.add(operration);
						}
					}
				}
			}
		}
		Grid<StatMaintenanceOperationVo> g = new Grid<StatMaintenanceOperationVo>(operationVoList.size(),operationVoList);
		return g;
	}
	
	@RequestMapping("/maintenanceOperationList")
    public String maintenanceOperationLis(Model model) {
		//得到当前时间
		Date curDate = new Date(); 
		String curDateStr = DateUtils.formatDate(curDate, "yyyy-MM-dd");
		String curDateToStr = DateUtils.formatDate(curDate, "yyyy-MM-dd");
		model.addAttribute("curDate", curDateStr+" 00:00");
		model.addAttribute("curDateTo", curDateToStr+" 23:59");
        return "/statistics/stat_maintenance_operation_list";
    }
	
	@RequestMapping("/seviceList")
    public String seviceList(Model model) {
		//得到当前时间
		Date curDate = new Date(); 
		String curDateStr = DateUtils.formatDate(curDate, "yyyy-MM-dd");
		String curDateToStr = DateUtils.formatDate(curDate, "yyyy-MM-dd");
		model.addAttribute("curDate", curDateStr+" 00:00");
		model.addAttribute("curDateTo", curDateToStr+" 23:59");
        return "/statistics/stat_service_list";
    }
	
	/**
	 * 响应最慢的服务列表(top 5)
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getServiceSlowTop5")
	@RequiresPermissions(value = "stat-service-list")
	public Grid<statService> getServiceSlowTop5(String startTime,String endTime) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql2 = new StringBuffer();
		hql2.append("from ServiceMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql2.append(" and t.monitorDate >= ?");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql2.append(" and t.monitorDate <= ?");
			params.add(DateUtils.parseDate(endTime));
		}
		hql2.append(" and t.service.id in (");
		hql2.append("select t2.service.id from ServiceMonitor t2 where 1 = 1 group by t2.service.id ");
		hql2.append(")");
		hql2.append(" and t.respTime in (");
		hql2.append("select max(t2.respTime) from ServiceMonitor t2 where 1 = 1 group by t2.service.id ");
		hql2.append(")");
		hql2.append(" order by t.respTime desc ");
		List<ServiceMonitor> dbList2 = serviceMonitorService.find(hql2.toString(), params,1,5);//只查5条记录
		List<statService> list =  new ArrayList<statService>();
		for(ServiceMonitor sm:dbList2){
			statService sd = new statService();
			sd.setService(sm.getService());
			sd.setAverageResponseTime(sm.getRespTime());
			list.add(sd);
		}
		
		int count = list.size();
		Grid<statService> g = new Grid<statService>(count,list);
		return g;
	}
	
	@ResponseBody
	@RequestMapping("/getServiceInfo")
	@RequiresPermissions(value = "stat-service-list")
	public Map<String,String> getServiceInfo(String startTime,String endTime) {
		List<Object> params = new ArrayList<Object>();
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer hql1 = new StringBuffer(); //服务平均响应时间hql
		StringBuffer hql2 = new StringBuffer(); //服务状态hql
		StringBuffer hql3 = new StringBuffer(); //服务访问量统计top10
		StringBuffer hql4 = new StringBuffer(); //用户访问量统计
		StringBuffer hql5 = new StringBuffer(); //ip访问量统计
		hql1.append("select t.monitorDate,avg(t.respTime) from ServiceMonitor t where 1 = 1 ");
		hql2.append("select t.service.id from ServiceMonitor t where 1 = 1 ");
		hql3.append("select t.serviceName ,count(*) from ServiceRequest t where 1 = 1 ");
		hql4.append("select t.requestUser.loginName ,count(*) from ServiceRequest t where 1 = 1 ");
		hql5.append("select t.requestIp ,count(*) from ServiceRequest t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql1.append("and t.monitorDate >= ? ");
			hql2.append("and t.monitorDate >= ? ");
			hql3.append("and t.createDate >= ? ");
			hql4.append("and t.createDate >= ? ");
			hql5.append("and t.createDate >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql1.append("and t.monitorDate <= ? ");
			hql2.append("and t.monitorDate <= ? ");
			hql3.append("and t.createDate <= ? ");
			hql4.append("and t.createDate <= ? ");
			hql5.append("and t.createDate <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql1.append(" group by t.monitorDate order by t.monitorDate asc ");
		hql3.append(" group by t.serviceName ");
		hql4.append(" group by t.requestUser.loginName ");
		hql5.append(" group by t.requestIp ");
		
		//服务总数
		long sumCount = serviceMonitorService.count(hql2.toString() + "  group by t.service.id ", params);
		hql2.append(" and t.status = '200' ");
		hql2.append(" group by t.service.id ");
		//服务启动个数
		long startCount = serviceMonitorService.count(hql2.toString(), params);
		//服务停止个数
		long stopCount = sumCount - startCount;
		
		//服务运行状态
		JSONArray yStatusJsonArr = new JSONArray();
		//启动json
		JSONObject startJson = new JSONObject();
		startJson.put("name", "服务启动");
		startJson.put("value", startCount);
		yStatusJsonArr.add(startJson);
		//停止json
		JSONObject stoptJson = new JSONObject();
		stoptJson.put("name", "服务停止");
		stoptJson.put("value", stopCount);
		yStatusJsonArr.add(stoptJson);
		map.put("serviceStatus", yStatusJsonArr.toJSONString());
		
		JSONArray xResTimeJsonArr = new JSONArray();
		JSONArray resTimeSeriesDataJsonArr = new JSONArray();
		//服务平均响应时间
		Object[] respTimeObj = serviceMonitorService.findArrValue(hql1.toString(), params);
		if(respTimeObj != null && respTimeObj.length > 0) {
			for(int i = 0; i < respTimeObj.length; i++) {
				Object[] objArr = (Object[]) respTimeObj[i];
				if(objArr != null && objArr.length > 0) {
					if(objArr[0] != null && objArr[1] != null) {
						Date d = (Date)objArr[0];
						String date = DateUtils.formatDate(d, "MM-dd HH:mm:ss");
						xResTimeJsonArr.add(date);
						double resTime = (double) objArr[1];
						BigDecimal bd1 = new BigDecimal(resTime);
						resTime = bd1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //四舍五入到2位小数
						resTimeSeriesDataJsonArr.add(resTime);
					}
				}
			}
			map.put("xResTimeData", xResTimeJsonArr.toJSONString());
			map.put("resTimeSeriesData", resTimeSeriesDataJsonArr.toJSONString());
		}
		//服务访问量top10
		Object[] visitObj = serviceRequestService.findArrValue(hql3.toString(), params, 1, 10);
		if(visitObj != null) {
			JSONArray xVisitJsonArr = new JSONArray();
			JSONArray visitSeriesDataJsonArr = new JSONArray();
			for(int i = 0; i < visitObj.length; i++) {
				Object objArr[] = (Object[]) visitObj[i];
				if(objArr != null && objArr.length > 0) {
					if(objArr[0] != null) {
						xVisitJsonArr.add(objArr[0]);
					}
					if(objArr[1] != null) {
						visitSeriesDataJsonArr.add(objArr[1]);
					}
				}
			}
			map.put("xVisitAxisData", xVisitJsonArr.toJSONString());
			map.put("visitSeriesData", visitSeriesDataJsonArr.toJSONString());
		}
		
		//用户访问量统计top10
		Object[] userVisitObj = serviceRequestService.findArrValue(hql4.toString(), params, 1, 10);
		if(userVisitObj != null) {
			JSONArray xVisitJsonArr = new JSONArray();
			JSONArray visitSeriesDataJsonArr = new JSONArray();
			for(int i = 0; i < userVisitObj.length; i++) {
				Object objArr[] = (Object[]) userVisitObj[i];
				if(objArr != null && objArr.length > 0) {
					if(objArr[0] != null) {
						xVisitJsonArr.add(objArr[0]);
					}
					if(objArr[1] != null) {
						visitSeriesDataJsonArr.add(objArr[1]);
					}
				}
			}
			map.put("xUserVisitAxisData", xVisitJsonArr.toJSONString());
			map.put("userVisitSeriesData", visitSeriesDataJsonArr.toJSONString());
		}
		
		//ip访问量统计top10
		Object[] ipVisitObj = serviceRequestService.findArrValue(hql5.toString(), params, 1, 10);
		if(ipVisitObj != null) {
			JSONArray xVisitJsonArr = new JSONArray();
			JSONArray visitSeriesDataJsonArr = new JSONArray();
			for(int i = 0; i < ipVisitObj.length; i++) {
				Object objArr[] = (Object[]) ipVisitObj[i];
				if(objArr != null && objArr.length > 0) {
					if(objArr[0] != null) {
						xVisitJsonArr.add(objArr[0]);
					}
					if(objArr[1] != null) {
						visitSeriesDataJsonArr.add(objArr[1]);
					}
				}
			}
			map.put("xIpVisitAxisData", xVisitJsonArr.toJSONString());
			map.put("ipVisitSeriesData", visitSeriesDataJsonArr.toJSONString());
		}
		
		
		
		return map;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/operatingSystemList")
	@RequiresPermissions(value = "stat-operatingSystem-list")
    public String operatingSystemList(Model model) {
		//得到当前时间
		Date curDate = new Date(); 
		String curDateStr = DateUtils.formatDate(curDate, "yyyy-MM-dd");
		String curDateToStr = DateUtils.formatDate(curDate, "yyyy-MM-dd");
		model.addAttribute("curDate", curDateStr+" 00:00");
		model.addAttribute("curDateTo", curDateToStr+" 23:59");
		return "/statistics/stat_operating_sys_list";
    }
	
	/**
	 * 综合分析页面
	 * @return
	 */
	@RequestMapping("/analysisList")
	@RequiresPermissions(value = "stat-analysis-list")
    public String analysisList(Model model) {
		//得到当前时间
		Date curDate = new Date(); 
		String curDateStr = DateUtils.formatDate(curDate, "yyyy-MM-dd");
		String curDateToStr = DateUtils.formatDate(curDate, "yyyy-MM-dd");
		model.addAttribute("curDate", curDateStr+" 00:00");
		model.addAttribute("curDateTo", curDateToStr+" 23:59");
        return "/statistics/stat_analysis_list";
    }
	
	@RequestMapping("/applicationServerList")
	@RequiresPermissions(value = "stat-applicationServer-list")
    public String applicationServerList(Model model) {
		//得到当前时间
		Date curDate = new Date(); 
		String curDateStr = DateUtils.formatDate(curDate, "yyyy-MM-dd");
		String curDateToStr = DateUtils.formatDate(curDate, "yyyy-MM-dd");
		model.addAttribute("curDate", curDateStr+" 00:00");
		model.addAttribute("curDateTo", curDateToStr+" 23:59");
        return "/statistics/stat_application_server_list";
    }
	
	@ResponseBody
	@RequestMapping("/getOperratingSysInfo")
	@RequiresPermissions(value = "stat-operatingSystem-list")
	public Map<String,String> getOperratingSysInfo(String startTime,String endTime,String statType,String paramType) {
		Map<String, String> map = new HashMap<String, String>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from NativeServerMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append("order by t.time asc ");
		List<NativeServerMonitor> dbList = nativeServerMonitorService.find(hql.toString(), params);
		JSONArray xJsonArr = new JSONArray();
		JSONArray seriesData1 = new JSONArray();
		JSONArray seriesData2 = new JSONArray();
		JSONArray seriesData3 = new JSONArray();
		JSONArray seriesData4 = new JSONArray();
		JSONArray seriesData5 = new JSONArray();
		JSONArray seriesData6 = new JSONArray();
		JSONArray seriesData7 = new JSONArray();
		for(NativeServerMonitor data : dbList) {
			String time = DateUtils.formatDate(data.getTime(), "MM-dd HH:mm:ss");
			xJsonArr.add(time);
			seriesData1.add(data.getUsedRate());
			seriesData2.add(100 - data.getUsedRate());
			seriesData3.add(data.getSendPackage());
			seriesData4.add(data.getRecPackage());
			seriesData5.add(data.getSendByte());
			seriesData6.add(data.getRecByte());
			seriesData7.add(data.getUsedMemory());
		}
		String type[] = paramType.split(",");
		if("1".equals(statType)) {
			if(type.length == 1 && type[0].equals("1")) {
				map.put("seriesData1", seriesData1.toJSONString());
			}
			else if(type.length == 1 && type[0].equals("2")) {
				map.put("seriesData2", seriesData2.toJSONString());
			}
			else if(type.length == 2){
				map.put("seriesData1", seriesData1.toJSONString());
				map.put("seriesData2", seriesData2.toJSONString());
			}
		}
		else if("2".equals(statType)) {
			if(type.length == 1 && type[0].equals("1")) {
				map.put("seriesData1", seriesData3.toJSONString());
			}
			else if(type.length == 1 && type[0].equals("2")) {
				map.put("seriesData2", seriesData4.toJSONString());
			}
			else if(type.length == 2){
				map.put("seriesData1", seriesData3.toJSONString());
				map.put("seriesData2", seriesData4.toJSONString());
			}
		}
		else if("3".equals(statType)) {
			if(type.length == 1 && type[0].equals("1")) {
				map.put("seriesData1", seriesData5.toJSONString());
			}
			else if(type.length == 1 && type[0].equals("2")) {
				map.put("seriesData2", seriesData6.toJSONString());
			}
			else if(type.length == 2){
				map.put("seriesData1", seriesData5.toJSONString());
				map.put("seriesData2", seriesData6.toJSONString());
			}
		}
		else if("4".equals(statType)) {
			map.put("seriesData1", seriesData7.toJSONString());
		}
		if(xJsonArr.size() > 0) {
			map.put("xAxisData", xJsonArr.toJSONString());
		}
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping("/listOperatingSysData")
	@RequiresPermissions(value = "stat-applicationServer-list")
	public Grid<StatOperatingSystem> listOperatingSysData(HttpServletRequest request,String startTime,String endTime,String statType) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql1 = new StringBuffer();
		StringBuffer hql2 = new StringBuffer();
		StringBuffer hql3 = new StringBuffer();
		StringBuffer hql4 = new StringBuffer();
		hql1.append("select max(t.usedRate),min(t.usedRate),avg(t.usedRate) from NativeServerMonitor t where 1 = 1 ");
		hql2.append("select max(t.sendPackage),min(t.sendPackage),avg(t.sendPackage),max(t.recPackage),min(t.recPackage),avg(t.recPackage) from NativeServerMonitor t where 1 = 1 ");
		hql3.append("select max(t.sendByte),min(t.sendByte),avg(t.sendByte),max(t.recByte),min(t.recByte),avg(t.recByte) from NativeServerMonitor t where 1 = 1 ");
		hql4.append("select max(t.usedMemory),min(t.usedMemory),avg(t.usedMemory) from NativeServerMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql1.append("and t.time >= ? ");
			hql2.append("and t.time >= ? ");
			hql3.append("and t.time >= ? ");
			hql4.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql1.append("and t.time <= ? ");
			hql2.append("and t.time <= ? ");
			hql3.append("and t.time <= ? ");
			hql4.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		
		StatOperatingSystem operSys = new StatOperatingSystem();
		operSys.setServerName("windwos");
		
		if("1".equals(statType)) {
			Object obj1[] = nativeServerMonitorService.findArrValue(hql1.toString(), params);
			if(obj1 != null && obj1.length > 0) {
				Object[] objArr = (Object[]) obj1[0];
				float userMax;
				float userMin;
				float userAverrage;
				if(objArr[0] != null) {
					userMax = Float.parseFloat(objArr[0].toString());
					BigDecimal bd1 = new BigDecimal(userMax);
					userMax = bd1.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
					operSys.setUseMax(userMax);
					operSys.setFreeMin(100 - operSys.getUseMax());
				}
				if(objArr[1] != null) {
					userMin = Float.parseFloat(objArr[1].toString());
					BigDecimal bd2 = new BigDecimal(userMin);
					userMin = bd2.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
					operSys.setUseMin(userMin);
					operSys.setFreeMax(100 - operSys.getUseMin());
				}
				if(objArr[2] != null) {
					userAverrage = Float.parseFloat(objArr[2].toString());
					BigDecimal bd3 = new BigDecimal(userAverrage);
					userAverrage = bd3.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
					operSys.setUseAverage(userAverrage);
					operSys.setFreeAverage(100 - operSys.getUseAverage());
				}
			}
		}
		else if("2".equals(statType)) {
			Object obj[] = nativeServerMonitorService.findArrValue(hql2.toString(), params);
			if(obj != null && obj.length > 0) {
				Object[] objArr = (Object[]) obj[0];
				if(objArr[0] != null) {
					operSys.setSendPackageMax(Long.parseLong(objArr[0].toString()));
				}
				if(objArr[1] != null) {
					operSys.setSendPackageMin(Long.parseLong(objArr[1].toString()));
				}
				if(objArr[2] != null) {
					float f = Float.parseFloat(objArr[2].toString());
					BigDecimal bd = new BigDecimal(f);
					f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
					operSys.setSendPackageAverage(f);
				}
				if(objArr[3] != null) {
					operSys.setRecPackageMax(Long.parseLong(objArr[3].toString()));
				}
				if(objArr[4] != null) {
					operSys.setRecPackageMin(Long.parseLong(objArr[4].toString()));
				}
				if(objArr[5] != null) {
					float f = Float.parseFloat(objArr[5].toString());
					BigDecimal bd = new BigDecimal(f);
					f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
					operSys.setRecPackageAverage(f);
				}
			}
		}
		else if("3".equals(statType)) {
			Object obj[] = nativeServerMonitorService.findArrValue(hql3.toString(), params);
			if(obj != null && obj.length > 0) {
				Object[] objArr = (Object[]) obj[0];
				if(objArr[0] != null) {
					operSys.setSendByteMax(Long.parseLong(objArr[0].toString()));
				}
				if(objArr[1] != null) {
					operSys.setSendByteMin(Long.parseLong(objArr[1].toString()));
				}
				if(objArr[2] != null) {
					float f = Float.parseFloat(objArr[2].toString());
					BigDecimal bd = new BigDecimal(f);
					f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
					operSys.setSendByteAverage(f);
				}
				if(objArr[3] != null) {
					operSys.setRecByteMax(Long.parseLong(objArr[3].toString()));
				}
				if(objArr[4] != null) {
					operSys.setRecByteMin(Long.parseLong(objArr[4].toString()));
				}
				if(objArr[5] != null) {
					float f = Float.parseFloat(objArr[5].toString());
					BigDecimal bd = new BigDecimal(f);
					f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
					operSys.setRecByteAverage(f);
				}
			}
		}
		
		else if("4".equals(statType)) {
			Object obj[] = nativeServerMonitorService.findArrValue(hql4.toString(), params);
			if(obj != null && obj.length > 0) {
				Object[] objArr = (Object[]) obj[0];
				if(objArr[0] != null) {
					double d = Double.parseDouble(objArr[0].toString());
					BigDecimal bd = new BigDecimal(d);
					d = bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					operSys.setUsedMemoryMax(d);
				}
				if(objArr[1] != null) {
					double d = Double.parseDouble(objArr[1].toString());
					BigDecimal bd = new BigDecimal(d);
					d = bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					operSys.setUsedMemoryMin(d);
				}
				if(objArr[2] != null) {
					double d = Double.parseDouble(objArr[2].toString());
					BigDecimal bd = new BigDecimal(d);
					d = bd.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
					operSys.setUsedMemoryAverage(d);
				}
			}
		}
		
		List<StatOperatingSystem> list =  new ArrayList<StatOperatingSystem>();
		
		list.add(operSys);
		int count = 1;
		Grid<StatOperatingSystem> g = new Grid<StatOperatingSystem>(count,list);
		return g;
		
	}
	
	/**
	 * 导出Word
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportWordTest", method = RequestMethod.GET)
	@ResponseBody
	public void exportWordTest(HttpServletRequest request,HttpServletResponse response){
		File file = null;
		//获得导出数据
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("name", "xx");
		map.put("phone", "lvy");
		map.put("imageStr", getImageStr("D:/111.png"));
		String name = "测试";
		
		file = WordUtils.createDoc(name, name, map);
		doExport(file,name+".doc","application/msword",request, response);
	}
	
	 /**
     * 得到图片
     * @return
     */
    private String getImageStr(String url) {
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(url);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
       // return  Base64Util.encode(data);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);
    }
    
    /**
	 * 导出综合分析数据到word中
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "exportAnalysisDatas")
	@ResponseBody
	public void exportAnalysisDatas(String startTime,String endTime,HttpServletRequest request,HttpServletResponse response){
		File file = null;
		//获得导出数据
		long pre = System.currentTimeMillis();
		Map<String, Object> map = getAnalysisDatas(startTime,endTime,request);
		String name = "综合分析报告模版";
		file = WordUtils.createDoc(name, name, map);
		long end = System.currentTimeMillis();
		long usedTime = end - pre;
		logService.saveLogInfo("导出综合分析数据到word中", LogType.Statistics, "", 1, "", usedTime);
		doExport(file,name+".doc","application/msword",request, response);
	}
	
	private Map<String,Object> getAnalysisDatas(String startTime,String endTime,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		//平台服务
		long serviceCount = serviceService.count("from Service t where 1 = 1", null);
		long gisServiceCount = serviceService.count("from Service t where 1 = 1 and t.registerType = '0' ", null);
		map.put("serviceCount", serviceCount);
		map.put("gisServiceCount", gisServiceCount);
		map.put("otherServiceCount", serviceCount-gisServiceCount);
		
		Map<String, Number> servicePerMap = new HashMap<String,Number>();
		servicePerMap.put("GIS服务", gisServiceCount);
		servicePerMap.put("第三方服务", serviceCount-gisServiceCount);
		String url = ChartUtils.createPieChart("服务占比", servicePerMap, request);
		map.put("servicePerImage", getImageStr(url));
		FileUtils.deleteFile(url);
		//平台用户
		long userCount = userService.count("select count(*) from User", null);
		//后台用户数
		short userType = 1;
		long houTaiUserCount = userService.count("select count(*) from User t where t.type = ?", new Object[]{userType});
		map.put("userCount", userCount);
		map.put("houTaiUserCount", houTaiUserCount);
		map.put("menHuUserCount", userCount-houTaiUserCount);
		Map<String, Number> userPerMap = new HashMap<String,Number>();
		userPerMap.put("后台用户", houTaiUserCount);
		userPerMap.put("门户用户", userCount-houTaiUserCount);
		String userPerImageUrl = ChartUtils.createPieChart("用户占比", userPerMap, request);
		map.put("userPerImage", getImageStr(userPerImageUrl));
		FileUtils.deleteFile(userPerImageUrl);
		
		//平台服务器
		map.put("serverCount", 3);
		map.put("tomcatCount", 1);
		map.put("databaseCount", 1);
		map.put("arcGisServerCount", 1);
		
		//服务访问量统计
		Map<String,Number> serviceVisitMap = getServiceVisit(startTime, endTime);
		String visitCountImageUrl = ChartUtils.createColumnChart("服务访问量", "服务", "服务访问次数", serviceVisitMap, request);
		map.put("serviceVisitImage", getImageStr(visitCountImageUrl));
		FileUtils.deleteFile(visitCountImageUrl);
		
		//服务平均响应时间统计
		Map<String,Number> serviceResTimeMap = getServiceResTimeAvg(startTime, endTime);
		String serviceResTimeImageUrl = ChartUtils.createColumnChart("服务平均响应时间统计", "服务", "平均响应时间 ms", serviceResTimeMap, request);
		map.put("serviceResTimeImage", getImageStr(serviceResTimeImageUrl));
		FileUtils.deleteFile(serviceResTimeImageUrl);
		
		//服务启动个数
		long serviceStartCount = getServiceRunStatus(startTime, endTime, 2);
		//服务停止个数
		long serviceStopCount = getServiceRunStatus(startTime, endTime, 3);
		map.put("serviceSumCount", serviceStartCount + serviceStopCount);
		map.put("serviceStartCount", serviceStartCount);
		map.put("serviceStopCount", serviceStopCount);
		Map<String,Number> serviceStatusMap = new HashMap<String,Number>();
		serviceStatusMap.put("服务启动", serviceStartCount);
		serviceStatusMap.put("服务停止", serviceStopCount);
		String serviceStatusImageUrl = ChartUtils.createPieChart("服务状态", serviceStatusMap, request);
		map.put("serviceStatusImage", getImageStr(serviceStatusImageUrl));
		FileUtils.deleteFile(serviceStatusImageUrl);
		
		//用户访问量统计
		Map<String,Number> userVisitMap = getUserVisit(startTime, endTime);
		String userVisitImageUrl = ChartUtils.createColumnChart("用户访问量统计", "用户", "访问次数", userVisitMap, request);
		map.put("userVisitImage", getImageStr(userVisitImageUrl));
		FileUtils.deleteFile(userVisitImageUrl);
		
		//ip流量统计
		Map<String,Number> ipVisitMap = getIprVisit(startTime, endTime);
		String ipVisitImageUrl = ChartUtils.createColumnChart("ip访问量统计", "ip", "访问次数", ipVisitMap, request);
		map.put("ipVisitImage", getImageStr(ipVisitImageUrl));
		FileUtils.deleteFile(ipVisitImageUrl);
		
		//运维系统操作统计
		//用户操作
		Map<String,Number> yunWeiUserOpeMap = getYunWeiOperationInfo(startTime, endTime,1);
		String yunWeiUserOpeImageUrl = ChartUtils.createColumnChart("用户操作统计", "用户", "操作次数", yunWeiUserOpeMap, request);
		map.put("yunWeiUserOpeImage", getImageStr(yunWeiUserOpeImageUrl));
		FileUtils.deleteFile(yunWeiUserOpeImageUrl);
		//操作类别
		Map<String,Number> yunWeiOpeTypeMap = getYunWeiOperationInfo(startTime, endTime,2);
		String yunWeiOpeTypeImageUrl = ChartUtils.createColumnChart("操作类别统计", "类别", "操作次数", yunWeiOpeTypeMap, request);
		map.put("yunWeiOpeTypeImage", getImageStr(yunWeiOpeTypeImageUrl));
		FileUtils.deleteFile(yunWeiOpeTypeImageUrl);
		
		//操作系统统计
		String[] categories = getOperrateSysXinfo(startTime, endTime);
		//cpu参数
		Vector<ChartUtils.Serie> cupSeries = getOperrateSysSeriesinfo(startTime, endTime, "1");
		String sysCpuImageUrl = ChartUtils.createLineChart("CPU参数", "时间", "使用率", categories, cupSeries, request);
		map.put("sysCpuImageImage", getImageStr(sysCpuImageUrl));
		FileUtils.deleteFile(sysCpuImageUrl);
		//网络包裹
		Vector<ChartUtils.Serie> packSeries = getOperrateSysSeriesinfo(startTime, endTime, "2");
		String sysPackImageUrl = ChartUtils.createLineChart("网络包裹", "时间", "流量  MB", categories, packSeries, request);
		map.put("sysPackImageImage", getImageStr(sysPackImageUrl));
		FileUtils.deleteFile(sysPackImageUrl);
		//网络字节流量
		Vector<ChartUtils.Serie> byteSeries = getOperrateSysSeriesinfo(startTime, endTime, "3");
		String sysByteImageUrl = ChartUtils.createLineChart("网络字节流量", "时间", "流量  MB", categories, byteSeries, request);
		map.put("sysPackImageImage", getImageStr(sysByteImageUrl));
		FileUtils.deleteFile(sysByteImageUrl);
		//内存
		Vector<ChartUtils.Serie> memorySeries = getOperrateSysSeriesinfo(startTime, endTime, "4");
		String sysMemoryImageUrl = ChartUtils.createLineChart("内存", "时间", "流量 MB", categories, memorySeries, request);
		map.put("sysMemoryImageImage", getImageStr(sysMemoryImageUrl));
		FileUtils.deleteFile(sysMemoryImageUrl);
		
		//数据库图表统计
		Map<String,Number> databaseMap = getDataBaseSessionInfo(startTime, endTime);
		String databaseImageUrl = ChartUtils.createLineChart("数据库统计", "时间", "会话个数", databaseMap, request);
		map.put("databaseImage", getImageStr(databaseImageUrl));
		FileUtils.deleteFile(databaseImageUrl);
		//数据库列表统计
		StatDatabase databaseInfo = listDatabaseInfo(startTime, endTime);
		map.put("databaseName", databaseInfo.getDatabaseName());
		map.put("sessionMaxDate", databaseInfo.getSessionMaxDate());
		map.put("sessionMaxCount", databaseInfo.getSessionMaxCount());
		map.put("sessionMinDate", databaseInfo.getSessionMinDate());
		map.put("sessionMinCount", databaseInfo.getSessionMinCount());
		map.put("sessionAverage", databaseInfo.getSessionAverage());
		
		//平台应用服务器图表统计
		String[] appServerCategories = getAppServerXInfo(startTime, endTime);
		//线程池统计
		Vector<Serie> appServerSeries = getAppServer(startTime, endTime, "1");
		String appServerImageUrl = ChartUtils.createLineChart("线程池统计", "时间", "个数", appServerCategories, appServerSeries, request);
		map.put("appServerImage", getImageStr(appServerImageUrl));
		FileUtils.deleteFile(appServerImageUrl);
		//jvm内存统计
		Vector<Serie> appServerSeries2 = getAppServer(startTime, endTime, "2");
		String appServerImageUrl2 = ChartUtils.createLineChart("JVM内存统计", "时间", "内存 MB", appServerCategories, appServerSeries2, request);
		map.put("appServerImage2", getImageStr(appServerImageUrl2));
		FileUtils.deleteFile(appServerImageUrl2);
		//平台应用服务器 列表统计
		StatApplicationServer appServer = listAppServerData(startTime, endTime);
		map.put("serverName", appServer.getServerName());
		map.put("threadMax", appServer.getThreadMax());
		map.put("threadMin", appServer.getThreadMin());
		map.put("threadAverage", appServer.getThreadAverage());
		map.put("busyThreadMax", appServer.getBusyThreadMax());
		map.put("busyThreadMin", appServer.getBusyThreadMin());
		map.put("busyThreadAverage", appServer.getBusyThreadAverage());
		map.put("haveMaxMemory", appServer.getHaveMaxMemory());
		map.put("haveMinMemory", appServer.getHaveMinMemory());
		map.put("haveAverageMemory", appServer.getHaveAverageMemory());
		map.put("freeMaxMemory", appServer.getFreeMaxMemory());
		map.put("freeMinMemory", appServer.getFreeMinMemory());
		map.put("freeAverageMemory", appServer.getFreeAverageMemory());
				
		return map;
	}
	
	/**
	 * 服务访问量统计
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private Map<String,Number> getServiceVisit(String startTime,String endTime) {
		Map<String,Number> map = new HashMap<String,Number>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(); //服务访问量统计top10
		hql.append("select t.serviceName ,count(*) from ServiceRequest t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.createDate >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.createDate <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append(" group by t.serviceName ");
		
		Object[] visitObj = serviceRequestService.findArrValue(hql.toString(), params, 1, 10);
		if(visitObj != null) {
			for(int i = 0; i < visitObj.length; i++) {
				Object objArr[] = (Object[]) visitObj[i];
				if(objArr != null && objArr.length > 0) {
					if(objArr[0] != null && objArr[1] != null) {
						map.put(objArr[0].toString(), Integer.parseInt(objArr[1].toString()));
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 服务平均响应统计
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private Map<String,Number> getServiceResTimeAvg(String startTime,String endTime) {
		Map<String,Number> map = new HashMap<String,Number>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(); //服务访问量统计top10
		hql.append("select t.monitorDate,avg(t.respTime) from ServiceMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.monitorDate >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.monitorDate <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append(" group by t.monitorDate order by t.monitorDate asc ");
		
		Object[] visitObj = serviceRequestService.findArrValue(hql.toString(), params, 1, 10);
		if(visitObj != null) {
			for(int i = 0; i < visitObj.length; i++) {
				Object objArr[] = (Object[]) visitObj[i];
				if(objArr != null && objArr.length > 0) {
					if(objArr[0] != null && objArr[1] != null) {
						Date d = (Date)objArr[0];
						String date = DateUtils.formatDate(d, "MM-dd HH:mm:ss");
						map.put(date, (double)objArr[1]);
					}
				}
			}
		}
		return map;
	}
    
	/**
	 * 服务运行状态个数
	 * @param startTime
	 * @param endTime
	 * @param flag(1:总数；2：启动个数；3:停止个数)
	 * @return
	 */
	private long getServiceRunStatus(String startTime,String endTime,int flag) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(); //服务状态hql
		hql.append("select t.service.id from ServiceMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.monitorDate >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.monitorDate <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		//服务总数
		long sumCount = serviceMonitorService.count(hql.toString() + "  group by t.service.id ", params);
		hql.append(" and t.status = '200' ");
		hql.append(" group by t.service.id ");
		//服务启动个数
		long startCount = serviceMonitorService.count(hql.toString(), params);
		//服务停止个数
		long stopCount = sumCount - startCount;
		
		if(1 == flag) {
			return sumCount;
		}
		else if(2 == flag) {
			return startCount;
		}
		else if(3 == flag){
			return stopCount;
		}
		return 0;
	}
	
	/**
	 * 用户访问量统计
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private Map<String,Number> getUserVisit(String startTime,String endTime) {
		Map<String,Number> map = new HashMap<String,Number>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(); //用户访问量统计top10
		hql.append("select t.requestUser.loginName ,count(*) from ServiceRequest t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.createDate >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.createDate <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append(" group by t.requestUser.loginName ");
		
		//用户访问量统计top10
		Object[] userVisitObj = serviceRequestService.findArrValue(hql.toString(), params, 1, 10);
		if(userVisitObj != null) {
			for(int i = 0; i < userVisitObj.length; i++) {
				Object objArr[] = (Object[]) userVisitObj[i];
				if(objArr != null && objArr.length > 0) {
					if(objArr[0] != null && objArr[1] != null) {
						map.put(objArr[0].toString(), Integer.parseInt(objArr[1].toString()));
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * IP流量统计
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private Map<String,Number> getIprVisit(String startTime,String endTime) {
		Map<String,Number> map = new HashMap<String,Number>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(); //用户访问量统计top10
		hql.append("select t.requestIp ,count(*) from ServiceRequest t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.createDate >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.createDate <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append(" group by t.requestUser.loginName ");
		
		//IP流量统计top10
		Object[] ipVisitObj = serviceRequestService.findArrValue(hql.toString(), params, 1, 10);
		if(ipVisitObj != null) {
			for(int i = 0; i < ipVisitObj.length; i++) {
				Object objArr[] = (Object[]) ipVisitObj[i];
				if(objArr != null && objArr.length > 0) {
					if(objArr[0] != null && objArr[1] != null) {
						map.put(objArr[0].toString(), Integer.parseInt(objArr[1].toString()));
					}
				}
			}
		}
		return map;
	}
	
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @param flag(1：用户操作；2：操作类别)
	 * @return
	 */
	private Map<String,Number> getYunWeiOperationInfo(String startTime,String endTime,int flag) {
		Map<String, Number> map = new HashMap<String, Number>();
		//用户操作
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql1 = new StringBuffer(); //用户操作 hql
		StringBuffer hql2 = new StringBuffer(); //操作类别 hql
		hql1.append("select t.user.loginName, count(*) from Log t where 1 = 1 ");
		hql2.append("select t.operationType, count(*) from Log t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql1.append("and t.createTime >= ? ");
			hql2.append("and t.createTime >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql1.append("and t.createTime <= ? ");
			hql2.append("and t.createTime <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql1.append("group by t.user.loginName order by count(*) desc ");
		hql2.append("group by t.operationType order by count(*) desc");
		
		if(1 == flag) {
			Object[] logObj = logService.findArrValue(hql1.toString(), params, 1, 10);
			if(logObj != null && logObj.length > 0) {
				for(int i = 0; i < logObj.length; i++) {
					Object[] objArr = (Object[]) logObj[i];
					if(objArr != null && objArr.length > 0) {
						if(objArr[0] != null && objArr[1] != null) {
							map.put(objArr[0].toString(), Long.parseLong(objArr[1].toString()));
						}
					}
				}
			}
		}
		else if(2 == flag) {
			Object[] logTypObj = logService.findArrValue(hql2.toString(), params, 1, 10);
			if(logTypObj != null && logTypObj.length > 0) {
				for(int i = 0; i < logTypObj.length; i++) {
					Object[] objArr = (Object[]) logTypObj[i];
					if(objArr != null && objArr.length > 0) {
						if(objArr[0] != null && objArr[1] != null) {
							map.put(LogType.findValueByType(Integer.parseInt(objArr[0].toString())), Long.parseLong(objArr[1].toString()));
						}
					}
				}
			}
		}
		
		return map;
	}
	
	/**
	 * 操作系统统计数据（x轴数据）
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private String[] getOperrateSysXinfo(String startTime,String endTime) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from NativeServerMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append("order by t.time asc ");
		List<NativeServerMonitor> dbList = nativeServerMonitorService.find(hql.toString(), params);
		String str[] = new String[dbList.size()];
		for(int i = 0; i < dbList.size(); i++) {
			String time = DateUtils.formatDate(dbList.get(i).getTime(), "MM-dd HH:mm:ss");
			str[i] = time;
		}
		
		return str;
	}
	
	/**
	 * 操作系统统计数据（series data）
	 * @param startTime
	 * @param endTime
	 * @param statType
	 * @return
	 */
	private Vector<ChartUtils.Serie> getOperrateSysSeriesinfo(String startTime,String endTime,String statType) {
		Vector<ChartUtils.Serie> series = new Vector<Serie>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from NativeServerMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append("order by t.time asc ");
		List<NativeServerMonitor> dbList = nativeServerMonitorService.find(hql.toString(), params);
		JSONArray seriesData1 = new JSONArray();
		JSONArray seriesData2 = new JSONArray();
		JSONArray seriesData3 = new JSONArray();
		JSONArray seriesData4 = new JSONArray();
		JSONArray seriesData5 = new JSONArray();
		JSONArray seriesData6 = new JSONArray();
		JSONArray seriesData7 = new JSONArray();
		for(NativeServerMonitor data : dbList) {
			seriesData1.add(data.getUsedRate());
			seriesData2.add(100 - data.getUsedRate());
			seriesData3.add(data.getSendPackage());
			seriesData4.add(data.getRecPackage());
			seriesData5.add(data.getSendByte());
			seriesData6.add(data.getRecByte());
			seriesData7.add(data.getUsedMemory());
		}
		if("1".equals(statType)) {
			ChartUtils.Serie cS1 = new Serie("CPU使用率", seriesData1.toArray());
			ChartUtils.Serie cS2 = new Serie("CPU空闲率", seriesData2.toArray());
			series.add(cS1);
			series.add(cS2);
		}
		else if("2".equals(statType)) {
			ChartUtils.Serie cS1 = new Serie("网络发送包裹", seriesData3.toArray());
			ChartUtils.Serie cS2 = new Serie("网络接收包裹", seriesData4.toArray());
			series.add(cS1);
			series.add(cS2);
		}
		else if("3".equals(statType)) {
			ChartUtils.Serie cS1 = new Serie("发送流量", seriesData5.toArray());
			ChartUtils.Serie cS2 = new Serie("接收流量", seriesData6.toArray());
			series.add(cS1);
			series.add(cS2);
		}
		else if("4".equals(statType)) {
			ChartUtils.Serie cS1 = new Serie("内存使用", seriesData5.toArray());
			series.add(cS1);
		}
		return series;
	}
	
	/**
	 * 得到数据库会话数量信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Map<String,Number> getDataBaseSessionInfo(String startTime,String endTime) {
		Map<String,Number> map = new HashMap<String,Number>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from NativeDbMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append("order by t.time asc ");
		List<NativeDbMonitor> dbList = nativeDbMonitorService.find(hql.toString(), params);
		for(NativeDbMonitor data : dbList) {
			String time = DateUtils.formatDate(data.getTime(), "MM-dd HH:mm:ss");
			map.put(time, data.getConnect());
		}
		return map;
	}
	
	/**
	 * 数据库统计信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private StatDatabase listDatabaseInfo(String startTime,String endTime) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from NativeDbMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append("order by t.connect desc,t.time desc ");
		List<NativeDbMonitor> dbList = nativeDbMonitorService.find(hql.toString(), params);
		
		StatDatabase sd = new StatDatabase();
		sd.setDatabaseName(dbList.get(0).getType());
		long connect = 0;
		for(NativeDbMonitor data : dbList) {
			connect = connect + data.getConnect();
		}
		float f = connect/(dbList.size()*1f);
		BigDecimal bd = new BigDecimal(f);
		f = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(); //保留2位小数
		sd.setSessionAverage(f);
		sd.setSessionMaxDate(dbList.get(0).getTime());
		sd.setSessionMinDate(dbList.get(dbList.size()-1).getTime());
		sd.setSessionMinCount(dbList.get(dbList.size()-1).getConnect());
		sd.setSessionMaxCount(dbList.get(0).getConnect());
		return sd;
	}
	
	/**
	 * 得到平台应用服务器的x轴信息
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private String[] getAppServerXInfo(String startTime,String endTime) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from NativeTomcatMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append("order by t.time asc ");
		List<NativeTomcatMonitor> dbList = nativeTomcatMonitorService.find(hql.toString(), params);
		String str[] = new String[dbList.size()];
		for(int i = 0; i < dbList.size(); i++) {
			String time = DateUtils.formatDate(dbList.get(i).getTime(), "MM-dd HH:mm:ss");
			str[i] = time;
		}
		return str;
		
	}
	
	/**
	 * 得到平台应用服务器的series data
	 * @param startTime
	 * @param endTime
	 * @param statType
	 * @return
	 */
	private Vector<ChartUtils.Serie> getAppServer(String startTime,String endTime,String statType) {
		Vector<ChartUtils.Serie> series = new Vector<Serie>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from NativeTomcatMonitor t where 1 = 1 ");
		if(StringUtils.isNotBlank(startTime)) {
			hql.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		hql.append("order by t.time asc ");
		List<NativeTomcatMonitor> dbList = nativeTomcatMonitorService.find(hql.toString(), params);
		JSONArray xJsonArr = new JSONArray();
		JSONArray seriesData1 = new JSONArray();
		JSONArray seriesData2 = new JSONArray();
		JSONArray seriesData3 = new JSONArray();
		JSONArray seriesData4 = new JSONArray();
		for(NativeTomcatMonitor data : dbList) {
			String time = DateUtils.formatDate(data.getTime(), "MM-dd HH:mm:ss");
			xJsonArr.add(time);
			seriesData1.add(data.getThread());
			seriesData2.add(data.getThreadBusy());
			double userMemory = data.getUsedMemory()/(1024*1024);
			BigDecimal bd = new BigDecimal(userMemory);
			userMemory = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			seriesData3.add(userMemory);
			double freeMemory = data.getFreeMemory()/(1024*1024);
			BigDecimal bd2 = new BigDecimal(freeMemory);
			freeMemory = bd2.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			seriesData4.add(freeMemory);
			
		}
		if("1".equals(statType)) {
			ChartUtils.Serie cS1 = new Serie("CPU使用率", seriesData1.toArray());
			ChartUtils.Serie cS2 = new Serie("CPU空闲率", seriesData2.toArray());
			series.add(cS1);
			series.add(cS2);
		}
		else if("2".equals(statType)) {
			ChartUtils.Serie cS1 = new Serie("CPU使用率", seriesData3.toArray());
			ChartUtils.Serie cS2 = new Serie("CPU空闲率", seriesData4.toArray());
			series.add(cS1);
			series.add(cS2);
		}
		
		return series;
		
	}
	
	/**
	 * 得到平台应用服务器的列表统计信息
	 * @param startTime
	 * @param endTime
	 * @param statType
	 * @return
	 */
	private StatApplicationServer listAppServerData(String startTime,String endTime) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql1 = new StringBuffer(); //thread hql
		StringBuffer hql2 = new StringBuffer(); //threadBusy hql
		StringBuffer hql3 = new StringBuffer(); //usedMemory hql
		StringBuffer hql4 = new StringBuffer(); //freeMemory hql
		hql1.append("select max(t.thread),min(t.thread),avg(t.thread) from NativeTomcatMonitor t where 1 = 1 ");
		hql2.append("select max(t.threadBusy),min(t.threadBusy),avg(t.threadBusy) from NativeTomcatMonitor t where 1 = 1 ");
		hql3.append("select max(t.usedMemory),min(t.usedMemory),avg(t.usedMemory) from NativeTomcatMonitor t where 1 = 1 ");
		hql4.append("select max(t.freeMemory),min(t.freeMemory),avg(t.freeMemory) from NativeTomcatMonitor t where 1 = 1 ");
		
		if(StringUtils.isNotBlank(startTime)) {
			hql1.append("and t.time >= ? ");
			hql2.append("and t.time >= ? ");
			hql3.append("and t.time >= ? ");
			hql4.append("and t.time >= ? ");
			params.add(DateUtils.parseDate(startTime));
		}
		if(StringUtils.isNotBlank(endTime)) {
			hql1.append("and t.time <= ? ");
			hql2.append("and t.time <= ? ");
			hql3.append("and t.time <= ? ");
			hql4.append("and t.time <= ? ");
			params.add(DateUtils.parseDate(endTime));
		}
		
		StatApplicationServer appServer = new StatApplicationServer();
		appServer.setServerName("tomcat");
		
		Object obj1[] = nativeTomcatMonitorService.findArrValue(hql1.toString(), params);
		Object obj2[] = nativeTomcatMonitorService.findArrValue(hql2.toString(), params);
		
		if(obj1 != null && obj1.length > 0) {
			Object[] objArr = (Object[]) obj1[0];
			if(objArr[0] != null) {
				appServer.setThreadMax(Integer.parseInt(objArr[0].toString()));
			}
			if(objArr[1] != null) {
				appServer.setThreadMin(Integer.parseInt(objArr[1].toString()));
			}
			if(objArr[2] != null) {
				BigDecimal bd = new BigDecimal(Float.parseFloat(objArr[2].toString()));
				float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
				appServer.setThreadAverage(f);
			}
		}
		if(obj2 != null && obj2.length > 0) {
			Object[] objArr = (Object[]) obj2[0];
			if(objArr[0] != null) {
				appServer.setBusyThreadMax(Integer.parseInt(objArr[0].toString()));
			}
			if(objArr[1] != null) {
				appServer.setBusyThreadMin(Integer.parseInt(objArr[1].toString()));
			}
			if(objArr[2] != null) {
				BigDecimal bd = new BigDecimal(Float.parseFloat(objArr[2].toString()));
				float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
				appServer.setBusyThreadAverage(f);
			}
		}
		
		Object obj3[] = nativeTomcatMonitorService.findArrValue(hql3.toString(), params);
		Object obj4[] = nativeTomcatMonitorService.findArrValue(hql4.toString(), params);
		
		if(obj3 != null && obj3.length > 0) {
			Object[] objArr = (Object[]) obj3[0];
			if(objArr[0] != null) {
				appServer.setHaveMaxMemory(Long.parseLong(objArr[0].toString())/(1024*1024));
			}
			if(objArr[1] != null) {
				appServer.setHaveMinMemory(Long.parseLong(objArr[1].toString())/1024);
			}
			if(objArr[2] != null) {
				BigDecimal bd = new BigDecimal(Float.parseFloat(objArr[2].toString())/(1024*1024));
				float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
				appServer.setHaveAverageMemory(f);
			}
		}
		
		if(obj4 != null && obj4.length > 0) {
			Object[] objArr = (Object[]) obj4[0];
			if(objArr[0] != null) {
				appServer.setFreeMaxMemory(Long.parseLong(objArr[0].toString())/(1024*1024));
			}
			if(objArr[1] != null) {
				appServer.setFreeMinMemory(Long.parseLong(objArr[1].toString())/(1024*1024));
			}
			if(objArr[2] != null) {
				BigDecimal bd = new BigDecimal(Float.parseFloat(objArr[2].toString())/(1024*1024));
				float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
				appServer.setFreeAverageMemory(f);
			}
		}
		return appServer;
	}
	
	@ResponseBody
	@RequestMapping("/getIndexChatInfo")
	@RequiresPermissions(value = "stat-service-list")
	public Map<String,String> getIndexChatInfo(String startTime,String endTime) {
		List<Object> params = new ArrayList<Object>();
		Map<String, String> map = new HashMap<String, String>();
		StringBuffer hql1 = new StringBuffer(); //服务平均响应时间hql
		StringBuffer hql2 = new StringBuffer(); //服务状态hql
		StringBuffer hql3 = new StringBuffer(); //服务访问量统计top10
		hql1.append("select t.monitorDate,avg(t.respTime) from ServiceMonitor t where 1 = 1 ");
		hql2.append("select t.service.id from ServiceMonitor t where 1 = 1 ");
		hql3.append("select t.serviceName ,count(*) from ServiceRequest t where 1 = 1 ");
		
		//得到当前时间
		Date curDate = new Date(); 
		String curDateStr = DateUtils.formatDate(curDate, "yyyy-MM-dd") + " 00:00";
		String curDateToStr = DateUtils.formatDate(curDate, "yyyy-MM-dd") + " 23:59";
		//curDateStr = curDateStr + " 00:00";
		//curDateToStr = curDateToStr + " 23:59";
		params.add(DateUtils.parseDate(curDateStr));
		params.add(DateUtils.parseDate(curDateToStr));
		
		hql1.append("and t.monitorDate >= ? ");
		hql2.append("and t.monitorDate >= ? ");
		hql3.append("and t.createDate >= ? ");
		hql1.append("and t.monitorDate <= ? ");
		hql2.append("and t.monitorDate <= ? ");
		hql3.append("and t.createDate <= ? ");
		
		hql1.append(" group by t.monitorDate order by t.monitorDate asc ");
		//hql1.append(" order by t.monitorDate asc ");
		hql3.append(" group by t.serviceName order by count(*) desc ");
		
		//服务总数
		long sumCount = serviceMonitorService.count(hql2.toString() + "  group by t.service.id ", params);
		hql2.append(" and t.status = '200' ");
		hql2.append(" group by t.service.id ");
		//服务启动个数
		long startCount = serviceMonitorService.count(hql2.toString(), params);
		//服务停止个数
		long stopCount = sumCount - startCount;
		
		//服务运行状态
		JSONArray yStatusJsonArr = new JSONArray();
		//启动json
		JSONObject startJson = new JSONObject();
		startJson.put("name", "服务启动");
		startJson.put("value", startCount);
		yStatusJsonArr.add(startJson);
		//停止json
		JSONObject stoptJson = new JSONObject();
		stoptJson.put("name", "服务停止");
		stoptJson.put("value", stopCount);
		yStatusJsonArr.add(stoptJson);
		map.put("serviceStatus", yStatusJsonArr.toJSONString());
		
		JSONArray xResTimeJsonArr = new JSONArray();
		JSONArray resTimeSeriesDataJsonArr = new JSONArray();
		//服务平均响应时间
		Object[] respTimeObj = serviceMonitorService.findArrValue(hql1.toString(), params);
		if(respTimeObj != null && respTimeObj.length > 0) {
			long scale = getScale(respTimeObj.length);
			System.out.println(respTimeObj.length);
			System.out.println("scale=" + scale);
			for(int i = 0; i < respTimeObj.length; i++) {
				Object[] objArr = (Object[]) respTimeObj[i];
				if(objArr != null && objArr.length > 0) {
					if(objArr[0] != null && objArr[1] != null) {
						Date d = (Date)objArr[0];
						String date = DateUtils.formatDate(d, "MM-dd HH:mm:ss");
						xResTimeJsonArr.add(date);
						double resTime = (double) objArr[1];
						BigDecimal bd1 = new BigDecimal(resTime);
						resTime = bd1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(); //四舍五入到2位小数
						resTimeSeriesDataJsonArr.add(resTime);
					}
				}
			}
			map.put("xResTimeData", xResTimeJsonArr.toJSONString());
			map.put("resTimeSeriesData", resTimeSeriesDataJsonArr.toJSONString());
		}
		//服务访问量top10
		Object[] visitObj = serviceRequestService.findArrValue(hql3.toString(), params, 1, 5);
		if(visitObj != null) {
			JSONArray xVisitJsonArr = new JSONArray();
			JSONArray visitSeriesDataJsonArr = new JSONArray();
			for(int i = 0; i < visitObj.length; i++) {
				Object objArr[] = (Object[]) visitObj[i];
				if(objArr != null && objArr.length > 0) {
					if(objArr[0] != null) {
						xVisitJsonArr.add(objArr[0]);
					}
					if(objArr[1] != null) {
						visitSeriesDataJsonArr.add(objArr[1]);
					}
				}
			}
			map.put("xVisitAxisData", xVisitJsonArr.toJSONString());
			map.put("visitSeriesData", visitSeriesDataJsonArr.toJSONString());
		}
		
		
		return map;
	}
	
	private long getScale(long data){
		if(data <= 100) {
			data = 10;
		}
		else if(data <= 1000) {
			data = 100;
		}
		else if(data <= 10000) {
			data = 1000;
		}
		else if(data <= 100000) {
			data = 10000;
		}
		else if(data <= 1000000) {
			data = 100000;
		}
		return data;
	}
}
