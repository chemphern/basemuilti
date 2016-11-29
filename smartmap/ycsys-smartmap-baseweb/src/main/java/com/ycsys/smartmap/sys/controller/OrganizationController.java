package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.common.utils.DateUtils;
import com.ycsys.smartmap.sys.common.utils.Exceptions;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.Organization;
import com.ycsys.smartmap.sys.service.OrganizationService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lixiaoxin on 2016/11/10.
 */
@RequestMapping("/org")
@Controller
public class OrganizationController {

    @Resource
    private OrganizationService organizationService;

    @RequiresPermissions(value = "sys-org-list")
    @RequestMapping("/list")
    public String list(){
        return "/organization/list";
    }

    @RequiresPermissions(value = "sys-org-listData")
    @ResponseBody
    @RequestMapping(value = "/listData")
    public Grid<Organization> listData(PageHelper page,String pid){
        Grid<Organization> g = new Grid<>();
        g.setRows(organizationService.findOrgNotChild(pid,page));
        return g;
    }

    @RequiresPermissions(value = "sys-org-getOrgs")
    @ResponseBody
    @RequestMapping(value = "getOrgs")
    public List<Organization> getOrgs(){
        return organizationService.findAll();
    }
    @ResponseBody
    @RequestMapping(value="/saveOrUpdate",method= RequestMethod.POST)
    public ResponseEx saveOrUpdate(Organization o){
        ResponseEx res = new ResponseEx();
        try{
            if(o.getId() == null)
            o.setCreateTime(DateUtils.getSysTimestamp());
            organizationService.saveOrUpdate(o);
            res.setSuccess("保存成功");
        }catch (Exception e){
            res.setFail(e.getMessage());
        }
        return res;
    }

    @RequiresPermissions(value = "sys-org-delete")
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ResponseEx delete(Organization o){
        ResponseEx res = new ResponseEx();
        try{
            organizationService.delete(o);
            res.setSuccess("删除成功");
        }catch (Exception e){
            res.setFail(e.getMessage());
        }
        return res;
    }

    @RequiresPermissions(value = "sys-org-addOrgv")
    @RequestMapping("/addOrgv")
    public String addOrgv(){
        return "/organization/addOrg";
    }

    @RequiresPermissions(value = "sys-org-addChildOrgv")
    @RequestMapping("/addChildOrgv")
    public String addChildOrgv(String id,String name, Model model){
        model.addAttribute("pid",id);
        model.addAttribute("pname",name);
        return "/organization/addChildOrg";
    }

    @RequiresPermissions(value = "sys-org-updateOrgv")
    @RequestMapping("/updateOrgv")
    public String updateOrgv(String id,Model model){
        model.addAttribute("org",organizationService.getOrg(id));
        return "/organization/updateOrg";
    }
}
