package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.sys.common.exception.ServiceException;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.entity.ConfigServerMonitor;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.ConfigServerMonitorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台监控配置
 * Created by lixiaoxin on 2016/12/16.
 */
@RequestMapping("/platMonitorConfig")
@Controller
public class PlatformMonitorConfigController {

    @Resource
    private ConfigServerMonitorService configServerMonitorService;

    @RequestMapping("/list")
    public String list(){
        return "/platmonitorConfig/list";
    }

    @RequestMapping("/listMonitorService")
    public String listMonitorServie(String type){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("1","Service");
        map.put("2","Tomcat");
        map.put("3","Oracle");
        map.put("4","Arcgis");
        return "/platmonitorConfig/list" + map.get(type) + "Config";
    }

    @RequestMapping("/listMonitorConfigData")
    @ResponseBody
    public Grid<ConfigServerMonitor> listServiceData(String type, PageHelper pageHelper){
        List<ConfigServerMonitor> csm = configServerMonitorService.findByType(type,pageHelper);
        Grid<ConfigServerMonitor> g = new Grid<>();
        g.setRows(csm);
        g.setTotal(configServerMonitorService.countByType(type));
        return g;
    }

    @RequestMapping("/addConfigv")
    public String addConfigv(String type){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("1","Service");
        map.put("2","Tomcat");
        map.put("3","Oracle");
        map.put("4","Arcgis");
        return "/platmonitorConfig/add" +map.get(type) + "Config" ;
    }
    @RequestMapping("/updateConfigv")
    public String updateConfigv(String type,String id,Model model){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("1","Service");
        map.put("2","Tomcat");
        map.put("3","Oracle");
        map.put("4","Arcgis");
        ConfigServerMonitor configServerMonitor = configServerMonitorService.getById(id);
        model.addAttribute("config",configServerMonitor);
        return "/platmonitorConfig/update" + map.get(type) + "Config";
    }

    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public ResponseEx saveOrUpdate(ConfigServerMonitor configServerMonitor){
        ResponseEx ex = new ResponseEx();
        try{
            configServerMonitor.setStatus("0");
            configServerMonitorService.saveOrUpdate(configServerMonitor);
            ex.setSuccess("保存成功!");
        }catch (Exception e){
            ex.setFail("保存失败");
            return ex;
        }
        return ex;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseEx delete(String id){
        ResponseEx ex = new ResponseEx();
        try{
            configServerMonitorService.delete(id);
            ex.setSuccess("删除成功");
        }catch (Exception e){
            ex.setFail("删除失败");
            return ex;
        }
        return ex;
    }

    /**启动监控**/
    @RequestMapping("/startMonitor")
    @ResponseBody
    public ResponseEx startMonitor(String id){
        ResponseEx ex = new ResponseEx();
        try{
            configServerMonitorService.startMonitor(id);
            ex.setSuccess("启动监控成功！");
        }catch (ServiceException e){
            ex.setFail(e.getContents());
            return ex;
        }
        return ex;
    }

    /**停止监控**/
    @RequestMapping("/stopMonitor")
    @ResponseBody
    public ResponseEx stopMonitor(String id){
        ResponseEx ex = new ResponseEx();
        try{
            configServerMonitorService.stopMonitor(id);
            ex.setSuccess("停止监控成功！");
        }catch (Exception e){
            ex.setFail(e.getMessage());
            return ex;
        }
        return ex;
    }

}
