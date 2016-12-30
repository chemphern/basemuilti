package com.ycsys.smartmap.sys.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.alibaba.druid.support.http.stat.WebAppStatManager;
import com.alibaba.fastjson.JSONArray;
import com.ycsys.smartmap.sys.common.jmx.JavaInformations;
import com.ycsys.smartmap.sys.common.schedule.JobTaskManager;
import com.ycsys.smartmap.sys.common.schedule.ScheduleJob;
import com.ycsys.smartmap.sys.common.snmp.*;
import com.ycsys.smartmap.sys.common.utils.DateUtils;
import com.ycsys.smartmap.sys.entity.ConfigServerMonitor;
import com.ycsys.smartmap.sys.service.ConfigServerMonitorService;
import com.ycsys.smartmap.sys.task.object.MonitorConstant;
import org.hibernate.service.spi.ServiceException;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @RequestMapping("/listMonitorService")
    public String listMonitorService(String id,Model model){
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
                Map<String,List<NetAnalyzeInfo>> nets = cons.getNetAnalyzeMap();
                model.addAttribute("systemInfo",systemInfo);
                model.addAttribute("diskInfo",diskInfo);
                model.addAttribute("memoryInfo",memoryInfo);
                model.addAttribute("cpuInfo",base.getCpuInfo());
                if(cpus.get(id) != null){
                    List<CpuInfo> cpulist = cpus.get(id);
                    List<String> xList = new ArrayList<>();
                    List<String> yList = new ArrayList<>();
                    for(CpuInfo tc:cpulist){
                        xList.add(format.format(new Date(tc.getTime())));
                        yList.add(tc.getSysRate());
                    }
                    model.addAttribute("cpu_xlist", JSONArray.toJSON(xList));
                    model.addAttribute("cpu_ylist",JSONArray.toJSON(yList));
                }
                if(nets.get(id) != null){
                    List<NetAnalyzeInfo> netlist = nets.get(id);
                    List<String> xList = new ArrayList<>();
                    List<String> yInList = new ArrayList<>();
                    List<String> yOutList = new ArrayList<>();
                    List<String> ySendPackList = new ArrayList<>();
                    List<String> yGetPackList = new ArrayList<>();
                    for(NetAnalyzeInfo n :netlist){
                        xList.add(format.format(new Date(n.getTime())));
                        yInList.add(n.getInkbps() + "");
                        yOutList.add(n.getOutkbps() + "");
                        ySendPackList.add(n.getPersendpack() + "");
                        yGetPackList.add(n.getPerrecpack() + "");
                    }
                    model.addAttribute("net_xlist",JSONArray.toJSON(xList));
                    model.addAttribute("net_yInlist",JSONArray.toJSON(yInList));
                    model.addAttribute("net_yOutlist",JSONArray.toJSON(yOutList));
                    model.addAttribute("net_ySendpacklist",JSONArray.toJSON(ySendPackList));
                    model.addAttribute("net_yGetpacklist",JSONArray.toJSON(yGetPackList));
                }
                model.addAttribute("netsChart",nets.get(id));
            } catch (Exception e) {
                model.addAttribute("msg","获取系统信息失败！snmp连接不通！");
            }
        }else if(type.equals("2")){

        } else if (type.equals("3")) {

        }else if(type.equals("4")){
        }
        return "/platmonitor/list" + map.get(type) + "Monitor";
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
