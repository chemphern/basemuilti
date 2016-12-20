package com.ycsys.smartmap.sys.controller;

import com.alibaba.fastjson.JSONArray;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.Permission;
import com.ycsys.smartmap.sys.entity.Role;
import com.ycsys.smartmap.sys.service.RolePermissionService;
import com.ycsys.smartmap.sys.service.RoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/11.
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Resource
    RoleService roleService;

    @Resource
    RolePermissionService rolePermissionService;

    @RequiresPermissions(value = "sys-role-list")
    @RequestMapping("list")
    public String list(){
        return "/role/list";
    }

    @RequiresPermissions(value = "sys-role-listData")
    @RequestMapping("listData")
    @ResponseBody
    public Grid<Role> listData(PageHelper page){
        Grid<Role> g = new Grid<>();
        g.setRows(roleService.findAll(page));
        g.setTotal(roleService.countAll());
        return g;
    }

    @RequiresPermissions(value = "sys-role-saveOrUpdate")
    @ResponseBody
    @RequestMapping(value="/saveOrUpdate",method = RequestMethod.POST)
    public ResponseEx saveOrUpdate(Role role){
        ResponseEx res = new ResponseEx();
        try{
            roleService.saveOrUpdate(role);
            res.setSuccess("保存成功");
        }catch (Exception e){
            res.setFail(e.getMessage());
        }
        return res;
    }

    @RequiresPermissions(value = "sys-role-delete")
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEx delete(Role role){
        ResponseEx res = new ResponseEx();
        try{
            roleService.delete(role);
            res.setSuccess("删除成功");
        }catch (Exception e){
            res.setFail(e.getMessage());
        }
        return res;
    }

    @RequiresPermissions(value = "sys-role-addRolev")
    @RequestMapping("/addRolev")
    public String addRolev(){
        return "/role/addRole";
    }

    @RequiresPermissions(value = "sys-role-updateRolev")
    @RequestMapping("/updateRolev")
    public String updateRolev(String id,Model model){
        Role role = roleService.getRole(id);
        model.addAttribute("role",role);
        return "/role/updateRole";
    }

    @RequiresPermissions(value = "sys-role-roleGivev")
    @RequestMapping("/roleGivev")
    public String roleGivev(String id,Model model){
        List<Permission> rps = roleService.getPermissionByRoleId(id);
        List<String> rpids = new ArrayList<>();
        for(Permission p : rps){
            rpids.add(p.getId().toString());
        }
        String ids = JSONArray.toJSONString(rpids);
        model.addAttribute("roleId",id);
        model.addAttribute("permissionList",ids);
        return "/role/roleGive";
    }

    @RequiresPermissions(value = "sys-role-updatePermission")
    @RequestMapping("/updatePermission")
    @ResponseBody
    public ResponseEx updatePermission(String authorities,String id){
        ResponseEx ex = new ResponseEx();
        try{
            rolePermissionService.updateRolePermission(id,authorities);
            ex.setSuccess("保存成功");
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }

    @RequiresPermissions(value = "sys-role-getRoles")
    @RequestMapping("/getRoles")
    @ResponseBody
    public List<Role> getRoles(){
        return roleService.findAll();
    }
}
