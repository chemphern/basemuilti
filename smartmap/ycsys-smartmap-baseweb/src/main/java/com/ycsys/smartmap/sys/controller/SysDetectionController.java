package com.ycsys.smartmap.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by lixiaoxin on 2016/12/23.
 */
@RequestMapping("/sysDetection")
@Controller
public class SysDetectionController {
    @RequestMapping("/list")
    public String list(){
        return "/sysDetection/list";
    }
}
