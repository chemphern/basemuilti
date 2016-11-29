package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.entity.Log;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by lixiaoxin on 2016/11/14.
 */
@Controller
@RequestMapping("/log")
public class LogController {

    @Resource
    private LogService logService;

    @RequestMapping("/list")
    public String list(){
        return "/log/list";
    }
    @ResponseBody
    @RequestMapping("/listData")
    public Grid<Log> listData(PageHelper page){
        Grid<Log> g = new Grid<Log>();
        g.setRows(logService.findAll(page));
        return g;
    }
}
