package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.monitor.service.ServiceMonitorService;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.vo.ServiceMonitorVo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lixiaoxin on 2016/12/23.
 */
@RequestMapping("/serviceMonitor")
@Controller
public class ServiceMonitorController {

    @Resource
    private ServiceMonitorService serviceMonitorService;

    @Resource
    private ServiceService serviceService;
    @RequestMapping("/list")
    public String list(){
        return "/serviceMonitor/list";
    }

    @RequestMapping("/listData")
    @ResponseBody
    public Grid<ServiceMonitorVo> listData(PageHelper pageHelper){
        Grid<ServiceMonitorVo> g = new Grid<>();
        List<Service> services  = serviceService.findByPage(pageHelper);
        List<ServiceMonitorVo> serviceVo = new ArrayList<>();
        try {
            for (Service service : services) {
                ServiceMonitorVo vo = new ServiceMonitorVo();
                vo.setId(service.getId());
                vo.setServiceName(service.getRegisterName());
                vo.setServiceShowName(service.getShowName());
                vo.setMonitorType("HTTP");
                vo.setMonitorUrl(service.getServiceVisitAddress());
                vo.setMonitorStatus(service.getMonitorStatus().equals("1") ? "启动" : "停止");
                vo.setMonitorRate(service.getMonitorRate() == null ||service.getMonitorRate().equals("")?"30":service.getMonitorRate());
                Map<String, Object> res = serviceMonitorService.getMonitorInfo(service.getId());
                vo.setStatus(String.valueOf(res.get("status")));
                vo.setAvailableRate(String.valueOf(res.get("availableRate")));
                vo.setAverageRespTime(String.valueOf(res.get("averageRespTime")));
                serviceVo.add(vo);
            }
        }catch (Exception e){

        }
        g.setRows(serviceVo);
        g.setTotal(serviceService.count("select count(*) from Service"));
        return g;
    }

    @RequestMapping("/editv")
    public String editv(String id,Model model){
        Service service = serviceService.get("from Service where id = ?",new Object[]{Integer.parseInt(id)});
        service.setMonitorRate(service.getMonitorRate() == null ||service.getMonitorRate().equals("")?"30":service.getMonitorRate());
        model.addAttribute("service",service);
        return "/serviceMonitor/edit";
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResponseEx update(Service service){
        ResponseEx ex = new ResponseEx();
        try{
            serviceService.updateMonitorConfig(service);
            ex.setSuccess("修改成功！");
        }catch (Exception e){
            e.printStackTrace();
            ex.setFail("修改失败！");
            return ex;
        }
        return ex;
    }
}
