package com.ycsys.smartmap.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.common.utils.POIExcelUtil;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.entity.UserRole;
import com.ycsys.smartmap.sys.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 用户Controller
 * Created by lixiaoxin on 2016/11/9.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

    @Resource
    private UserService userService;

    @RequiresPermissions(value = "sys-user-list")
    @RequestMapping("/list")
    public String list(){
        return "/user/list";
    }

    @RequiresPermissions(value = "sys-user-listData")
    @ResponseBody
    @RequestMapping("/listData")
    public Grid<User> listData(String orgId,PageHelper page){
        Grid<User> g = new Grid<>(userService.findAllUsers(orgId,page));
        return g;
    }

    @RequiresPermissions(value = "sys-user-saveOrUpdate")
    @ResponseBody
    @RequestMapping(value = "/saveOrUpdate",method = RequestMethod.POST)
    public ResponseEx saveOrUpdate(User user,String orgId,String roleId){
        ResponseEx res = new ResponseEx();
        try{
            userService.saveOrUpdate(user,orgId,roleId);
            res.setSuccess("保存成功！");
        }catch (Exception e){
            res.setFail(e.getMessage());
        }
        return res;
    }

    @RequiresPermissions(value = "sys-user-delete")
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ResponseEx delete(User user){
        ResponseEx res = new ResponseEx();
        try{
            userService.delete(user.getId());
            res.setSuccess("删除成功！");
        }catch (Exception e){
            res.setFail(e.getMessage());
        }
        return res;
    }

    @RequiresPermissions(value = "sys-user-addUserv")
    @RequestMapping("/addUserv")
    public String addUserv(String orgId,String orgName,Model model){
        model.addAttribute("orgId",orgId);
        model.addAttribute("orgName",orgName);
        return "/user/addUser";
    }

    @RequiresPermissions(value = "sys-user-updateUserv")
    @RequestMapping("/updateUserv")
    public String updateUserv(String id,Model model){
        User user = userService.get(User.class,Integer.parseInt(id));
        model.addAttribute("user",user);
        List<String> roleIds = new ArrayList<>();
        for(UserRole ur :user.getUserRoles()){
            roleIds.add(ur.getRole().getId().toString());
        }
        model.addAttribute("roleIds", JSONArray.toJSONString(roleIds));
        return "/user/updateUser";
    }

    @RequiresPermissions(value = "sys-user-importUserv")
    @RequestMapping("/importUserv")
    public String importUserv(){
        return "/user/importUser";
    }

    @RequiresPermissions(value = "sys-user-showUserv")
    @RequestMapping("/showUserv")
    public String showUserv(String id,Model model){
        User user = userService.get(User.class,Integer.parseInt(id));
        model.addAttribute("user",user);
        StringBuffer sb = new StringBuffer();
        for(UserRole ur :user.getUserRoles()){
            sb.append(ur.getRole().getName()).append(",");
        }
        model.addAttribute("roleNames", sb.length() > 0?sb.substring(0,sb.length()-1).toString():sb.toString());
        return "/user/showUser";
    }

    @RequiresPermissions(value = "sys-user-importUser")
    @RequestMapping(value = "/importUser",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEx importUser(MultipartHttpServletRequest multipartRequest) throws Exception{
        ResponseEx ex = new ResponseEx();
        MultipartFile file = getMultipartFile(multipartRequest);
        String fileName = file.getOriginalFilename();
        List<ArrayList<String>> datas = getFileDatas(file, fileName);
        System.out.println(datas);
        return ex;
    }

}
