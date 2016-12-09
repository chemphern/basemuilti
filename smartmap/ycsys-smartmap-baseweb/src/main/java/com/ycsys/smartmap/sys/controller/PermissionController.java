package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.sys.common.config.parseobject.permission.*;
import com.ycsys.smartmap.sys.common.exception.SysException;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.common.utils.JaxbMapper;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.Permission;
import com.ycsys.smartmap.sys.service.PermissionService;
import com.ycsys.smartmap.sys.service.SystemService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.Marshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by lixiaoxin on 2016/11/14.
 */
@RequestMapping("/permission")
@Controller
public class PermissionController extends BaseController{
    @Resource
    private PermissionService permissionService;

    @Resource
    private SystemService systemService;

    @Resource(name="config")
    private Properties config;

    @RequiresPermissions(value = "sys-permission-list")
    @RequestMapping("/list")
    public String list(){
        return "/permission/list";
    }

    @RequiresPermissions(value = "sys-permission-listData")
    @ResponseBody
    @RequestMapping("/listData")
    public Grid<Permission> listData(String sys_code,PageHelper page){
        Grid<Permission> g = new Grid<Permission>();
        List<Permission> l = permissionService.getAllPermissions(sys_code);
        g.setRows(l);
        return g;
    }

    @RequiresPermissions(value = "sys-permission-importPermissionv")
    @RequestMapping("/importPermissionv")
    public String importPermissionv(){
        return "/permission/importPermission";
    }

    @RequiresPermissions(value = "sys-permission-importPermission")
    @RequestMapping(value = "/importPermission",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEx importPermission(MultipartHttpServletRequest multipartRequest){
        ResponseEx ex = new ResponseEx();
        try {
            MultipartFile file = getMultipartFile(multipartRequest);
            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.matches("^.+\\.(?i)((xml))$"))
            {
                throw new SysException("文件不是xml格式");
            }
            permissionService.importPermission(file.getInputStream(),"UTF-8");
            ex.setSuccess("导入成功！");
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }

    @RequiresPermissions(value = "sys-permission-findPermissions")
    @RequestMapping("/findPermissions")
    @ResponseBody
    public List<Permission> findPermissions(){
        //获取系统
        List<com.ycsys.smartmap.sys.entity.System> systems = systemService.findAll();
        List<Permission> res = new ArrayList<>();
        for(com.ycsys.smartmap.sys.entity.System system:systems){
            Permission permission = new Permission();
            permission.setPid(-1);
            permission.setName(system.getName());
            permission.setId(-1);
            List<Permission> ps = permissionService.getAllPermissions(system.getCode());
            permission.setChildPermission(ps);
            res.add(permission);
        }
        return res;
    }

    @RequiresPermissions(value = "sys-permission-findSystems")
    @RequestMapping("findSystems")
    @ResponseBody
    public Grid<com.ycsys.smartmap.sys.entity.System> findSystems(){
        Grid<com.ycsys.smartmap.sys.entity.System> g = new Grid<>();
        g.setRows(systemService.findAll());
        return g;
    }

    /**
     * 导出
     * **/
    @RequiresPermissions(value = "sys-permission-exportPermission")
    @RequestMapping("exportPermission")
    public void exportPermission(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PermissionXmlObject pxml = new PermissionXmlObject();
        //系统
        List<SystemXmlObject> systemXmlObjectList = new ArrayList<>();
        List<com.ycsys.smartmap.sys.entity.System> sysList = systemService.findAll();
        for(com.ycsys.smartmap.sys.entity.System sys:sysList){
            SystemXmlObject sxb = new SystemXmlObject(sys.getName(),sys.getCode(),sys.getUrl());
            //模块
            List<ModuleXmlObject> moduleXmlObjectList = new ArrayList<>();
            List<Permission> moduleList = permissionService.getAllPermissions(sys.getCode());
            for (Permission module :moduleList){
                ModuleXmlObject mxo = new ModuleXmlObject(module.getName(),module.getSort().toString());
                List<MenuXmlObject> menuXmlObjectList = new ArrayList<>();
                List<UrlXmlObject> urlXmlObjectList = new ArrayList<>();
                for(Permission childPermission :module.getChildPermission()){
                    //菜单
                    if(childPermission.getType().equals("menu")){
                        MenuXmlObject mexo = new MenuXmlObject(childPermission.getName(),childPermission.getSort().toString());
                        //链接
                        List<UrlXmlObject> meUrlList = new ArrayList<>();
                        for(Permission menuChild: childPermission.getChildPermission()){
                            UrlXmlObject uxo = new UrlXmlObject(menuChild.getName(),menuChild.getSort().toString(),menuChild.getUrl(),menuChild.getCode());
                            if(menuChild.getChildPermission()!= null) {
                                //功能点
                                List<FuncXmlObject> ufcs = new ArrayList<>();
                                for (Permission urlChild : menuChild.getChildPermission()) {
                                    FuncXmlObject fxo = new FuncXmlObject(urlChild.getName(), urlChild.getSort().toString(), urlChild.getCode());
                                    ufcs.add(fxo);
                                }
                                if (ufcs.size() > 0) {
                                    uxo.setFuncXmlObjects(ufcs);
                                }
                            }
                            meUrlList.add(uxo);
                        }
                        if(meUrlList.size()>0){
                            mexo.setUrlXmlObjects(meUrlList);
                        }
                        menuXmlObjectList.add(mexo);
                        //链接
                    }else if(childPermission.getType().equals("url")){
                        UrlXmlObject urlObj = new UrlXmlObject(childPermission.getName(),childPermission.getSort().toString(),childPermission.getUrl(),childPermission.getCode());
                        if(childPermission.getChildPermission() != null) {
                            //功能点
                            List<FuncXmlObject> funcs = new ArrayList<>();
                            for (Permission urlChild : childPermission.getChildPermission()) {
                                FuncXmlObject fxo = new FuncXmlObject(urlChild.getName(), urlChild.getSort().toString(), urlChild.getCode());
                                funcs.add(fxo);
                            }
                            if (funcs.size() > 0) {
                                urlObj.setFuncXmlObjects(funcs);
                            }
                        }
                        urlXmlObjectList.add(urlObj);
                    }
                }
                if(menuXmlObjectList.size()>0){
                    mxo.setMenuXmlObject(menuXmlObjectList);
                }
                if(urlXmlObjectList.size()>0){
                    mxo.setUrlXmlObject(urlXmlObjectList);
                }
                moduleXmlObjectList.add(mxo);
            }
            systemXmlObjectList.add(sxb);
            if(moduleXmlObjectList.size() >0){
                sxb.setModuleXmlObjectList(moduleXmlObjectList);
            }
        }
        if(systemXmlObjectList.size()>0){
            pxml.setSystemXmlObjectList(systemXmlObjectList);
        }
        String tmp = System.getProperty("java.io.tmpdir");
        File file = new File(tmp, request.getContextPath() + "/export_/" + System.currentTimeMillis());
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        Marshaller m = JaxbMapper.createMarshaller(PermissionXmlObject.class,"UTF-8");
        m.marshal(pxml,new FileOutputStream(file));
        ServletServerHttpResponse outputMessage = new ServletServerHttpResponse(response);
        BufferedOutputStream o = new BufferedOutputStream(outputMessage.getBody());
        response.reset();
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition",
                "attachment;filename=permission.xml");
        response.setCharacterEncoding("UTF-8");
        InputStream is = new FileInputStream(file);
        byte[] buffer = new byte[20480];
        int len = 0;
        while ((len = is.read(buffer)) != -1) {
            o.write(buffer, 0, len);
        }
        o.flush();
        o.close();
        is.close();
    }
}
