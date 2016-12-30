package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.monitor.entity.Alarm;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.AlarmService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lixiaoxin on 2016/12/23.
 */
@Controller
@RequestMapping("/exceptionAlarm")
public class ExceptionAlarmController {

    @Resource
    private AlarmService alarmService;

    @RequestMapping("/list")
    public String list(){
        return "/exceptionAlarm/list";
    }

    @RequestMapping("/listData")
    @ResponseBody
    public Grid<Alarm> listData(PageHelper pageHelper){
        Grid<Alarm> g = new Grid<>();
        List<Alarm> alarms = alarmService.findByPage(pageHelper);
        g.setRows(alarms);
        g.setTotal(alarmService.countAll());
        return g;
    }


}
