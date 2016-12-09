package com.ycsys.smartmap.sys.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.alibaba.druid.support.http.stat.WebAppStatManager;
import com.ycsys.smartmap.sys.common.jmx.JavaInformations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.management.MBeanServer;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 平台监控
 * Created by lixiaoxin on 2016/12/7.
 */
@RequestMapping("/platmonitor")
@Controller
public class PlatformMonitorController {

    final DruidStatManagerFacade facade = DruidStatManagerFacade.getInstance();

    @RequestMapping("/list")
    public String list(){
        return "/platmonitor/list";
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
    public Map<String,Object> getMBeans(HttpServletRequest request){
        JavaInformations javaInformations = new JavaInformations(request.getSession().getServletContext(),true);
        System.out.println(javaInformations);
        return null;
    }

}
