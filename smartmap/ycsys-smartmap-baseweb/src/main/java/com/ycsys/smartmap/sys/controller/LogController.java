package com.ycsys.smartmap.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.ycsys.smartmap.monitor.entity.ServiceRequest;
import com.ycsys.smartmap.monitor.service.ServiceRequestService;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.entity.Log;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.LogService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

/**
 * Created by lixiaoxin on 2016/11/14.
 */
@Controller
@RequestMapping("/log")
public class LogController extends BaseController{

    @Resource
    private LogService logService;

    @Resource
    private ServiceRequestService serviceRequestService;

    @RequestMapping("/list")
    public String list(){
        return "/log/list";
    }
    @ResponseBody
    @RequestMapping("/listData")
    public Grid<Log> listData(PageHelper page){
        Grid<Log> g = new Grid<Log>();
        g.setRows(logService.findAll(page));
        g.setTotal(logService.countAll());
        return g;
    }


    @RequestMapping("/listRequest")
    public String listRequest(){
        return "/log/listRequest";
    }

    @RequestMapping("/listRequestData")
    @ResponseBody
    public Grid<ServiceRequest> listRequestData(PageHelper page){
        Grid<ServiceRequest> g = new Grid<>();
        g.setRows(serviceRequestService.findByPage(page));
        g.setTotal(serviceRequestService.countAll());
        return g;
    }

    @RequestMapping("/export")
    public void export(long startTime,long endTime,Integer status,Integer operationType,Integer num,HttpServletRequest request, HttpServletResponse response){
        try {
            List<Log> datas = logService.findBySolution(startTime != 0 ? new Date(startTime) : null, endTime != 0 ? new Date(endTime) : null, operationType, status, num);
            int[] widths = new int[]{1500, 6000, 5000, 5000, 5000, 5000, 5000,10000};
            Map<String, String> contentReflect = new LinkedHashMap<>();
            contentReflect.put("序号", "#1");
            contentReflect.put("日志时间", "createTime|yyyy-MM-dd HH:mm:ss");
            contentReflect.put("日志内容", "operationName");
            Map<String,Object> type = new HashMap<String,Object>();
            type.put("key","operationType");
            Map<String,Object> typeMap = new HashMap<String,Object>();
            typeMap.put("1","基础类");
            typeMap.put("2","服务管理类");
            typeMap.put("3","资源管理类");
            typeMap.put("4","统计分析类");
            typeMap.put("5","图层类");
            typeMap.put("6","监控类");
            type.put("value",typeMap);
            contentReflect.put("日志类型", JSONObject.toJSONString(type));
            contentReflect.put("操作人", "username");
            contentReflect.put("请求IP", "requestIp");
            contentReflect.put("日志状态", "{\"key\":\"status\",\"value\":{\"1\":\"正常\",\"2\":\"异常\"}}");
            contentReflect.put("备注", "remark");
            HSSFWorkbook workbook = getDefaultExcel("运维监控日志数据", widths, contentReflect, datas, Log.class);
            String tmp = System.getProperty("java.io.tmpdir");
            File file = new File(tmp, request.getContextPath() + "/export_/" + System.currentTimeMillis());
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(file);
            workbook.write(fout);
            fout.close();
            doExport(file,"运维监控日志.xls","application/msexcel",request,response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping("/exportRequest")
    public void exportRequest(long startTime,long endTime,Integer num,HttpServletRequest request, HttpServletResponse response){
        try {
            List<ServiceRequest> datas = serviceRequestService.findBySolution(startTime != 0 ? new Date(startTime) : null, endTime != 0 ? new Date(endTime) : null, num);
            int[] widths = new int[]{1500, 6000, 4000, 5000, 5000, 5000, 5000, 5000, 5000,8000, 4000, 4000};
            Map<String, String> contentReflect = new LinkedHashMap<>();
            contentReflect.put("序号", "#1");
            contentReflect.put("请求时间", "requestDate|yyyy-MM-dd HH:mm:ss");
            contentReflect.put("用户名", "requestUser.name:com.ycsys.smartmap.sys.entity.User");
            contentReflect.put("用户请求IP", "requestIp");
            contentReflect.put("服务器IP", "serverIp");
            contentReflect.put("服务器端口", "serverPort");
            contentReflect.put("服务名称", "serviceName");
            contentReflect.put("服务类型", "serviceType");
            contentReflect.put("服务方法", "serviceMethod");
            contentReflect.put("请求URI", "requestUrl");
            contentReflect.put("访问时间", "visitTime");
            contentReflect.put("返回状态", "returnStatus");
            HSSFWorkbook workbook = getDefaultExcel("服务访问日志数据", widths, contentReflect, datas, ServiceRequest.class);
            String tmp = System.getProperty("java.io.tmpdir");
            File file = new File(tmp, request.getContextPath() + "/export_/" + System.currentTimeMillis());
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            FileOutputStream fout = new FileOutputStream(file);
            workbook.write(fout);
            fout.close();
            doExport(file, "服务访问日志.xls", "application/msexcel", request, response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    @RequestMapping("/exportv")
    public String exportv(){
        return "/log/export";
    }

    @RequestMapping("/exportRequestv")
    public String exportRequestv(){
        return "/log/exportRequest";
    }
}
