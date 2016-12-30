package com.ycsys.smartmap.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lixiaoxin on 2016/12/23.
 */
@RequestMapping("/serviceMonitor")
@Controller
public class ServiceMonitorController {
    @RequestMapping("/list")
    public String list(){
        return "/serviceMonitor/list";
    }
}
