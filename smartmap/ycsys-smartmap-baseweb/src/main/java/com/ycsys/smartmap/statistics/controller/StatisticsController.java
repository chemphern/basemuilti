package com.ycsys.smartmap.statistics.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ycsys.smartmap.monitor.entity.NativeDbMonitor;
import com.ycsys.smartmap.monitor.entity.NativeTomcatMonitor;
import com.ycsys.smartmap.monitor.service.NativeDbMonitorService;
import com.ycsys.smartmap.monitor.service.NativeTomcatMonitorService;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.statistics.entity.StatApplicationServer;
import com.ycsys.smartmap.statistics.entity.StatDatabase;
import com.ycsys.smartmap.statistics.entity.StatOperatingSystem;
import com.ycsys.smartmap.statistics.entity.statService;
import com.ycsys.smartmap.sys.common.jmx.JavaInformations;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.utils.DateUtils;
import com.ycsys.smartmap.sys.common.utils.StringUtils;

/**
 * 
 * @author liweixiong
 * @date   2016年12月26日
 */
@Controller
@RequestMapping("/statistics")
public class StatisticsController {
	@Autowired
	private NativeDbMonitorService nativeDbMonitorService;
	@Autowired
	private NativeTomcatMonitorService nativeTomcatMonitorService;
	
	/*
	 * 平台数据库统计 列表
	 */
	@RequestMapping("/databaseList")
	@RequiresPermissions(value = "stat-database-list")
    public String databaseList(){
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
			String time = DateUtils.formatDate(data.getTime(), "MM-dd hh:mm:ss");
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
		
		//String maxDate = DateUtils.formatDate(dbList.get(0).getTime(), "MM-dd hh:mm:ss");
		//String minDate = DateUtils.formatDate(dbList.get(dbList.size()-1).getTime(), "MM-dd hh:mm:ss");
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
		String type[] = paramType.split(",");
		JSONArray xJsonArr = new JSONArray();
		xJsonArr.add("2016-12-1");
		xJsonArr.add("2016-12-2");
		xJsonArr.add("2016-12-3");
		xJsonArr.add("2016-12-4");
		xJsonArr.add("2016-12-5");
		xJsonArr.add("2016-12-6");
		xJsonArr.add("2016-12-7");
		
		JSONArray yJsonArr1 = new JSONArray();
		yJsonArr1.add("10");
		yJsonArr1.add("10");
		yJsonArr1.add("15");
		yJsonArr1.add("10");
		yJsonArr1.add("15");
		yJsonArr1.add("10");
		yJsonArr1.add("10");
		
		JSONArray yJsonArr2 = new JSONArray();
		yJsonArr2.add("20");
		yJsonArr2.add("10");
		yJsonArr2.add("15");
		yJsonArr2.add("10");
		yJsonArr2.add("15");
		yJsonArr2.add("60");
		yJsonArr2.add("90");
		
		if(type.length == 1 && type[0].equals("1")) {
			map.put("seriesData1", yJsonArr1.toJSONString());
		}
		else if(type.length == 1 && type[0].equals("2")) {
			map.put("seriesData2", yJsonArr2.toJSONString());
		}
		else if(type.length == 2){
			map.put("seriesData1", yJsonArr1.toJSONString());
			map.put("seriesData2", yJsonArr2.toJSONString());
		}
		
		map.put("xAxisData", xJsonArr.toJSONString());
		
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/listAppServerData")
	@RequiresPermissions(value = "stat-applicationServer-list")
	public Grid<StatApplicationServer> listAppServerData(HttpServletRequest request,String startTime,String endTime,String statType) {
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
		Object obj1[] = nativeTomcatMonitorService.findArrValue(hql1.toString(), params);
		Object obj2[] = nativeTomcatMonitorService.findArrValue(hql2.toString(), params);
		Object obj3[] = nativeTomcatMonitorService.findArrValue(hql3.toString(), params);
		Object obj4[] = nativeTomcatMonitorService.findArrValue(hql4.toString(), params);
		
		StatApplicationServer appServer = new StatApplicationServer();
		appServer.setServerName("tomcat");
		
		if(obj1 != null && obj1.length > 0) {
			Object[] objArr = (Object[]) obj1[0];
			appServer.setThreadMax(Integer.parseInt(objArr[0].toString()));
			appServer.setThreadMin(Integer.parseInt(objArr[1].toString()));
			BigDecimal bd = new BigDecimal(Float.parseFloat(objArr[2].toString()));
			float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
			appServer.setThreadAverage(f);
		}
		if(obj2 != null && obj2.length > 0) {
			Object[] objArr = (Object[]) obj2[0];
			appServer.setBusyThreadMax(Integer.parseInt(objArr[0].toString()));
			appServer.setBusyThreadMin(Integer.parseInt(objArr[1].toString()));
			BigDecimal bd = new BigDecimal(Float.parseFloat(objArr[2].toString()));
			float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
			appServer.setBusyThreadAverage(f);
		}
		if(obj3 != null && obj3.length > 0) {
			Object[] objArr = (Object[]) obj3[0];
			appServer.setHaveMaxMemory(Long.parseLong(objArr[0].toString()));
			appServer.setHaveMinMemory(Long.parseLong(objArr[1].toString()));
			BigDecimal bd = new BigDecimal(Float.parseFloat(objArr[2].toString()));
			float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
			appServer.setHaveAverageMemory(f);
		}
		
		if(obj4 != null && obj4.length > 0) {
			Object[] objArr = (Object[]) obj4[0];
			appServer.setFreeMaxMemory(Long.parseLong(objArr[0].toString()));
			appServer.setFreeMinMemory(Long.parseLong(objArr[1].toString()));
			BigDecimal bd = new BigDecimal(Float.parseFloat(objArr[2].toString()));
			float f = bd.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();
			appServer.setFreeAverageMemory(f);
		}
		
		List<StatApplicationServer> list = new ArrayList<StatApplicationServer>();
		int count = 1;
		list.add(appServer);
		Grid<StatApplicationServer> g = new Grid<StatApplicationServer>(count,list);
		return g;
		
	}
	
	@ResponseBody
	@RequestMapping("/getMaintenanceOperationInfo")
	@RequiresPermissions(value = "stat-service-list")
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
		
		//用户操作
		JSONArray xOpeJsonArr = new JSONArray();
		xOpeJsonArr.add("admin");
		xOpeJsonArr.add("第三");
		xOpeJsonArr.add("test");
		xOpeJsonArr.add("test2");
		xOpeJsonArr.add("test2");
		xOpeJsonArr.add("test2");
		xOpeJsonArr.add("test2");
		JSONArray yOpeJsonArr = new JSONArray();
		yOpeJsonArr.add("50");
		yOpeJsonArr.add("11");
		yOpeJsonArr.add("15");
		yOpeJsonArr.add("10");
		yOpeJsonArr.add("25");
		yOpeJsonArr.add("10");
		yOpeJsonArr.add("30");
		map.put("xOpetAxisData", xOpeJsonArr.toJSONString());
		map.put("opeSeriesData", yOpeJsonArr.toJSONString());
		
		map.put("successRate", successRateJsonArr.toJSONString());
		
		return map;
	}
	
	
	@RequestMapping("/maintenanceOperationList")
    public String maintenanceOperationLis() {
        return "/statistics/stat_maintenance_operation_list";
    }
	
	@RequestMapping("/seviceList")
    public String seviceList() {
        return "/statistics/stat_service_list";
    }
	
	@ResponseBody
	@RequestMapping("/getServiceSlowTop5")
	@RequiresPermissions(value = "stat-service-list")
	public Grid<statService> getServiceSlowTop5(String startTime,String endTime) {
		List<statService> list =  new ArrayList<statService>();
		for(int i = 0; i < 5; i++) {
			statService service = new statService();
			Service s = new Service();
			s.setId(i);
			s.setShowName("服务" + (i +1));
			service.setService(s);
			service.setAverageResponseTime(12f);
			list.add(service);
		}
		
		int count = 5;
	
		Grid<statService> g = new Grid<statService>(count,list);
		return g;
	}
	
	@ResponseBody
	@RequestMapping("/getServiceInfo")
	@RequiresPermissions(value = "stat-service-list")
	public Map<String,String> getServiceInfo(String startTime,String endTime) {
		Map<String, String> map = new HashMap<String, String>();
		//服务响应时间
		JSONArray xJsonArr = new JSONArray();
		xJsonArr.add("2016-12-1");
		xJsonArr.add("2016-12-2");
		xJsonArr.add("2016-12-3");
		xJsonArr.add("2016-12-4");
		xJsonArr.add("2016-12-5");
		xJsonArr.add("2016-12-6");
		xJsonArr.add("2016-12-7");
		System.out.println("x======" + xJsonArr.toJSONString());
		JSONArray yJsonArr = new JSONArray();
		yJsonArr.add("10");
		yJsonArr.add("10");
		yJsonArr.add("15");
		yJsonArr.add("10");
		yJsonArr.add("15");
		yJsonArr.add("10");
		yJsonArr.add("10");
		map.put("xAxisData", xJsonArr.toJSONString());
		map.put("seriesData", yJsonArr.toJSONString());
		
		//服务访问量top10
		JSONArray xVisitJsonArr = new JSONArray();
		xVisitJsonArr.add("zhongshan");
		xVisitJsonArr.add("广州地铁");
		xVisitJsonArr.add("test");
		xVisitJsonArr.add("test2");
		xVisitJsonArr.add("test2");
		xVisitJsonArr.add("test2");
		xVisitJsonArr.add("test2");
		JSONArray yVisitJsonArr = new JSONArray();
		yVisitJsonArr.add("10.5");
		yVisitJsonArr.add("11");
		yVisitJsonArr.add("15");
		yVisitJsonArr.add("10");
		yVisitJsonArr.add("25");
		yVisitJsonArr.add("10");
		yVisitJsonArr.add("30");
		map.put("xVisitAxisData", xVisitJsonArr.toJSONString());
		map.put("visitSeriesData", yVisitJsonArr.toJSONString());
		
		//服务运行状态
		JSONArray yStatusJsonArr = new JSONArray();
		//启动
		JSONObject startJson = new JSONObject();
		startJson.put("name", "服务启动");
		startJson.put("value", "150");
		yStatusJsonArr.add(startJson);
		
		//停止
		JSONObject stoptJson = new JSONObject();
		stoptJson.put("name", "服务停止");
		stoptJson.put("value", "310");
		yStatusJsonArr.add(stoptJson);
		
		//停止
		JSONObject errortJson = new JSONObject();
		errortJson.put("name", "服务异常");
		errortJson.put("value", "80");
		yStatusJsonArr.add(errortJson);
		
		map.put("serviceStatus", yStatusJsonArr.toJSONString());
		return map;
	}
	
	@RequestMapping("/operatingSystemList")
    public String operatingSystemList() {
        return "/statistics/stat_operating_sys_list";
    }
	
	@RequestMapping("/applicationServerList")
    public String applicationServerList() {
        return "/statistics/stat_application_server_list";
    }
	
	@ResponseBody
	@RequestMapping("/getOperratingSysInfo")
	@RequiresPermissions(value = "stat-service-list")
	public Map<String,String> getOperratingSysInfo(String startTime,String endTime,String statType,String paramType) {
		Map<String, String> map = new HashMap<String, String>();
		String type[] = paramType.split(",");
		JSONArray xJsonArr = new JSONArray();
		xJsonArr.add("2016-12-1");
		xJsonArr.add("2016-12-2");
		xJsonArr.add("2016-12-3");
		xJsonArr.add("2016-12-4");
		xJsonArr.add("2016-12-5");
		xJsonArr.add("2016-12-6");
		xJsonArr.add("2016-12-7");
		JSONArray yJsonArr1 = new JSONArray();
		yJsonArr1.add("10");
		yJsonArr1.add("10");
		yJsonArr1.add("15");
		yJsonArr1.add("10");
		yJsonArr1.add("15");
		yJsonArr1.add("10");
		yJsonArr1.add("10");
		String yJsonStr1 = yJsonArr1.toJSONString();
		
		JSONArray yJsonArr2 = new JSONArray();
		yJsonArr2.add("20");
		yJsonArr2.add("10");
		yJsonArr2.add("15");
		yJsonArr2.add("10");
		yJsonArr2.add("15");
		yJsonArr2.add("60");
		yJsonArr2.add("90");
		String yJsonStr2 = yJsonArr2.toJSONString();
		
		JSONArray yJsonArr3 = new JSONArray();
		yJsonArr3.add("30");
		yJsonArr3.add("10");
		yJsonArr3.add("15");
		yJsonArr3.add("10");
		yJsonArr3.add("15");
		yJsonArr3.add("60");
		yJsonArr3.add("90");
		String yJsonStr3 = yJsonArr3.toJSONString();
		
		JSONArray yJsonArr4 = new JSONArray();
		yJsonArr4.add("40");
		yJsonArr4.add("10");
		yJsonArr4.add("15");
		yJsonArr4.add("10");
		yJsonArr4.add("15");
		yJsonArr4.add("60");
		yJsonArr4.add("90");
		String yJsonStr4 = yJsonArr4.toJSONString();
		
		Map<String,String> yJsonMap = new HashMap<String,String>();
		yJsonMap.put("1", yJsonStr1);
		yJsonMap.put("2", yJsonStr2);
		yJsonMap.put("3", yJsonStr3);
		yJsonMap.put("4", yJsonStr4);
		if("1".equals(statType)) {
			if(type.length == 4) {
				map.put("seriesData1", yJsonArr1.toJSONString());
				map.put("seriesData2", yJsonArr2.toJSONString());
				map.put("seriesData3", yJsonArr3.toJSONString());
				map.put("seriesData4", yJsonArr4.toJSONString());
			}
			else {
				for(int i = 0; i < type.length; i++) {
					map.put("seriesData"+type[i], yJsonMap.get(type[i]));
				}
			}
		}
		else if("2".equals(statType)) {
			map.put("seriesData1", yJsonArr1.toJSONString());
			map.put("seriesData2", yJsonArr2.toJSONString());
		}
		
		
		map.put("xAxisData", xJsonArr.toJSONString());
		return map;
	}
	
	
	@ResponseBody
	@RequestMapping("/listOperatingSysData")
	@RequiresPermissions(value = "stat-applicationServer-list")
	public Grid<StatOperatingSystem> listOperatingSysData(HttpServletRequest request,String startTime,String endTime,String statType) {
		JavaInformations javaInformations = new JavaInformations(request.getSession().getServletContext(),true);
		System.out.println("javaInformations = " + javaInformations);
		//javaInformations.getTomcatInformationsList().get(0);
		List<StatOperatingSystem> list =  new ArrayList<StatOperatingSystem>();
		StatOperatingSystem operSys = new StatOperatingSystem();
		operSys.setServerName("arcServer");
		long count = 1;
		list.add(operSys);
		Grid<StatOperatingSystem> g = new Grid<StatOperatingSystem>(count,list);
		return g;
	}
}
