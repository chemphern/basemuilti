package com.ycsys.smartmap.sys.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.alibaba.druid.support.http.stat.WebAppStatManager;
import com.alibaba.fastjson.JSONArray;
import com.ycsys.smartmap.monitor.entity.ServerMonitorData;
import com.ycsys.smartmap.monitor.entity.TomcatMonitorData;
import com.ycsys.smartmap.monitor.service.ServerMonitorDataService;
import com.ycsys.smartmap.monitor.service.TomcatMonitorDataService;
import com.ycsys.smartmap.sys.common.config.parseobject.tomcat.*;
import com.ycsys.smartmap.sys.common.jmx.JavaInformations;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.common.schedule.JobTaskManager;
import com.ycsys.smartmap.sys.common.snmp.*;
import com.ycsys.smartmap.sys.common.utils.MonitorUtil;
import com.ycsys.smartmap.sys.entity.ConfigServerMonitor;
import com.ycsys.smartmap.sys.service.ConfigServerMonitorService;
import com.ycsys.smartmap.sys.task.object.MonitorConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 平台监控
 * Created by lixiaoxin on 2016/12/7.
 */
@RequestMapping("/platmonitor")
@Controller
public class PlatformMonitorController {

    final DruidStatManagerFacade facade = DruidStatManagerFacade.getInstance();

    @Resource
    private ConfigServerMonitorService configServerMonitorService;

    @Resource
    private ServerMonitorDataService serverMonitorDataService;

    @Resource
    private TomcatMonitorDataService tomcatMonitorDataService;

    @Autowired
    private JobTaskManager jobTaskManager;

    @RequestMapping("/list")
    public String list(){
        return "/platmonitor/list";
    }


    @RequestMapping("/getMonitorServices")
    @ResponseBody
    public List<ConfigServerMonitor> getMonitorServices(){
        List<ConfigServerMonitor> configs = configServerMonitorService.findAll();
        return configs;
    }

    @RequestMapping("/getMonitorServiceData")
    @ResponseBody
    public ResponseEx listMonitorService(String id){
        ResponseEx ex = new ResponseEx();
        Map<String,Object> res = new HashMap<String,Object>();
        ConfigServerMonitor config = configServerMonitorService.getById(id);
        String type = config.getMonitorType();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("1","Service");
        map.put("2","Tomcat");
        map.put("3","Oracle");
        map.put("4","Arcgis");
        MonitorConstant cons = MonitorConstant.getInstance();
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
        if(type.equals("1")){
            SnmpBase base = new SnmpBase(config.getUrl(), config.getSnmpPort(), config.getCommunicate());
            try {
                base.snmpGet(".1.3.6.1.2.1.1.1.0");
                SystemInfo systemInfo= base.getSysInfo();
                ArrayList<DiskInfo> diskInfo = base.getDiskInfo();
                MemoryInfo memoryInfo = base.getMemoryInfo();
                Map<String,List<CpuInfo>> cpus = cons.getCpuInfoMap();
                res.put("systemInfo",systemInfo);
                res.put("diskInfo",diskInfo);
                res.put("memoryInfo",memoryInfo);
                res.put("cpuInfo",base.getCpuInfo());
                if(cpus.get(id) != null){
                    List<CpuInfo> cpulist = cpus.get(id);
                    List<String> xList = new ArrayList<>();
                    List<String> yList = new ArrayList<>();
                    for(CpuInfo tc:cpulist){
                        xList.add(format.format(new Date(tc.getTime())));
                        yList.add(tc.getSysRate());
                    }
                    res.put("cpu_xlist", JSONArray.toJSON(xList));
                    res.put("cpu_ylist",JSONArray.toJSON(yList));
                }
                List<ServerMonitorData> serverDatas = serverMonitorDataService.findPrevData(id,20);
                res.put("chartData",serverDatas);
            } catch (Exception e) {
                ex.setFail("获取系统信息失败！snmp连接不通！");
                return ex;
            }
        }else if(type.equals("2")){
            TomcatStatusObject tomcatStatusObject = MonitorUtil.getTomcatStatusInfo(config.getUrl(),config.getUserName(),config.getUserPassword());
            if(tomcatStatusObject!= null){
                List<TomcatConnectorStatusObject> tomcatConnectorStatusObjects = tomcatStatusObject.getConnector();
                TomcatConnectorStatusObject httpConnector = null;
                for (TomcatConnectorStatusObject connectorStatusObject : tomcatConnectorStatusObjects) {
                    if (connectorStatusObject.getName().indexOf("http-nio") > -1) {
                        httpConnector = connectorStatusObject;
                    }
                }
                TomcatRequestInfoObject request =  httpConnector.getRequestInfo();
                TomcatThreadInfoStatusObject thread = httpConnector.getThreadInfo();
                TomcatMemoryStatusObject jvmMemory = tomcatStatusObject.getJvm().getMemory();
                List<TomcatMonitorData> tomcatDatas = tomcatMonitorDataService.findPrevData(id,20);
                res.put("chartData",tomcatDatas);
                res.put("requestInfo",request);
                res.put("threadInfo",thread);
                res.put("jvmMemoryInfo",jvmMemory);
            }
        } else if (type.equals("3")) {

        }else if(type.equals("4")){

        }
        res.put("type",type);
        ex.setRetData(res);
        return ex;
    }

    @RequestMapping("/getDatabaseBaseInfo")
    @ResponseBody
    public Map<String,Object> getDatabaseInfo(HttpServletRequest request) {
        return facade.returnJSONBasicStat();
    }

    @RequestMapping("/getDatasourceInfo")
    @ResponseBody
    public List<Map<String,Object>> getDatasourceInfo(){
        return facade.getDataSourceStatDataList();
    }

    @RequestMapping("/getActiveConnStackTrace")
    @ResponseBody
    public List<List<String>> getActiveConnStackTrace(){
        return facade.getActiveConnStackTraceList();
    }

    @RequestMapping("/getWebUri")
    @ResponseBody
    public List<Map<String, Object>> getweburi() {
        List<Map<String, Object>> array = WebAppStatManager.getInstance().getURIStatData();
        return array;
    }

    @RequestMapping("/getWebApp")
    @ResponseBody
    public List<Map<String,Object>> getWebApp(){
        List<Map<String, Object>> array = WebAppStatManager.getInstance().getWebAppStatData();
        return array;
    }

    @RequestMapping("/getWebSession")
    @ResponseBody
    public List<Map<String,Object>> getWebSession(){
        return WebAppStatManager.getInstance().getSessionStatData();
    }

    @RequestMapping("/getMBeans")
    @ResponseBody
    public JavaInformations getMBeans(HttpServletRequest request){
        Map<String,Object> res = new HashMap<String,Object>();
        JavaInformations javaInformations = new JavaInformations(request.getSession().getServletContext(),true);
        res.put("res",javaInformations);
        System.out.println(javaInformations);
        return javaInformations;
    }


}
