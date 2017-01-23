package com.ycsys.smartmap.webgis.controller;

import com.alibaba.fastjson.JSONArray;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.entity.UserRole;
import com.ycsys.smartmap.sys.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户Controller
 * Created by lixiaoxin on 2016/11/9.
 */
@Controller
@RequestMapping("/user")
public class UserController{

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
        g.setTotal(userService.countAll(orgId));
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

    @RequiresPermissions(value = "sys-user-updateUserPwd")
    @RequestMapping("/updateUserPwd")
    public String updateUserPwd(Model model,HttpSession session){
        User user = userService.get(User.class,((User)(session.getAttribute(Global.SESSION_USER))).getId());
        model.addAttribute("user",user);
        List<String> roleIds = new ArrayList<>();
        for(UserRole ur :user.getUserRoles()){
            roleIds.add(ur.getRole().getId().toString());
        }

        model.addAttribute("roleIds", StringUtils.join(roleIds,","));
        return "/user/updateUserPwd";
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

}
