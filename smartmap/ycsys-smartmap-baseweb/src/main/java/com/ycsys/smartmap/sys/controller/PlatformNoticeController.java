package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.entity.*;
import com.ycsys.smartmap.sys.service.*;
import com.ycsys.smartmap.sys.util.DataDictionary;
import com.ycsys.smartmap.sys.vo.NoticeVo;
import com.ycsys.smartmap.sys.vo.TreeVo;
import org.apache.commons.collections.list.SetUniqueList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 平台消息控制层
 * Created by lixiaoxin on 2017/1/13.
 */
@Controller
@RequestMapping("/platNotice")
public class PlatformNoticeController {

    @Resource
    private NoticeService noticeService;

    @Resource
    private NoticeReceiverService noticeReceiverService;

    @Resource
    private OrganizationService organizationService;

    @Resource
    private UserService userService;

    @Resource
    private RoleService roleService;

    @RequestMapping("/list")
    public String list(){
        return "/platNotice/list";
    }

    @RequestMapping("/listData")
    @ResponseBody
    public Grid<NoticeVo> listData(PageHelper pageHelper){
        Grid<NoticeVo> g = new Grid<>();
        List<Notice> noticeList = noticeService.findByPage(pageHelper);
        List<NoticeVo> noticeVoList = new ArrayList<>();
        Map<String,Object> platMsg_type = DataDictionary.get("platMsg_type");
        for(Notice notice:noticeList){
            NoticeVo vo = new NoticeVo(notice.getId(),notice.getTitle(),notice.getContent(),(int)notice.getType(),(int)notice.getStatus(),notice.getCreateTime());
            int status = vo.getStatus();
            vo.setStatuss(!(status == 2)?"未发送":"已发送");
            vo.setTypes(String.valueOf(platMsg_type.get(vo.getType() + "")));
            vo.setSendNum(noticeReceiverService.countNotice(vo.getId()));
            noticeVoList.add(vo);
        }
        g.setRows(noticeVoList);
        g.setTotal(noticeService.countAll());
        return g;
    }

    @RequestMapping("/listMyNoticeData")
    @ResponseBody
    public Grid<Map<String,Object>> listMyNoticeData(PageHelper pageHelper, HttpSession session){
        Grid<Map<String,Object>> g = new Grid<>();
        User user = (User) session.getAttribute(Global.SESSION_USER);
        List<Map<String,Object>> list = noticeReceiverService.findNoticesByUserId(user.getId(),pageHelper);
        Map<String,Object> platMsg_type = DataDictionary.get("platMsg_type");
        for(Map<String,Object> map:list){
            String type = String.valueOf(map.get("type"));
            map.put("types",String.valueOf(platMsg_type.get(type)));
            String status = String.valueOf(map.get("status"));
            map.put("statuss",status.equals("2")?"已读":"未读");
        }
        g.setRows(list);
        g.setTotal(noticeReceiverService.countByUserId(user.getId()));
        return g;
    }

    @RequestMapping("/viewNotice")
    public String viewNotice(String id,Model model){
        Notice notice = noticeService.getNotice(id);
        NoticeVo vo = new NoticeVo(notice.getId(),notice.getTitle(),notice.getContent(),(int)notice.getType(),(int)notice.getStatus(),notice.getCreateTime());
        int status = vo.getStatus();
        Map<String,Object> platMsg_type = DataDictionary.get("platMsg_type");
        vo.setStatuss(!(status == 2)?"未发送":"已发送");
        vo.setTypes(String.valueOf(platMsg_type.get(vo.getType() + "")));
        vo.setSendNum(noticeReceiverService.countNotice(vo.getId()));
        model.addAttribute("notice",vo);
        return "/platNotice/viewNotice";
    }
    @RequestMapping("/deleteNoticeReceive")
    @ResponseBody
    public ResponseEx deleteNoticeReceive(String id){
        ResponseEx ex = new ResponseEx();
        try {
            noticeReceiverService.delete(id);
            ex.setSuccess("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            ex.setFail("删除失败");
            return ex;
        }
        return ex;
    }

    @RequestMapping("/delete")
    @ResponseBody
    public ResponseEx delete(String id){
        ResponseEx ex = new ResponseEx();
        try {
            noticeService.delete(id);
            ex.setSuccess("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            ex.setFail("删除失败,该消息已被使用");
            return ex;
        }
        return ex;
    }

    @RequestMapping("/saveNotice")
    @ResponseBody
    public ResponseEx saveNotice(Notice notice){
        ResponseEx ex = new ResponseEx();
        try {
            notice.setCreateTime(new Timestamp(java.lang.System.currentTimeMillis()));
            noticeService.save(notice);
            ex.setSuccess("保存成功");
        }catch (Exception e){
            e.printStackTrace();
            ex.setFail("保存失败");
            return ex;
        }
        return ex;
    }

    @RequestMapping("/updateNotice")
    @ResponseBody
    public ResponseEx updateNotice(Notice notice){
        ResponseEx ex = new ResponseEx();
        try {
            noticeService.update(notice);
            ex.setSuccess("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            ex.setFail("修改失败");
            return ex;
        }
        return ex;
    }

    @RequestMapping(value = "/getOrgData",produces="application/json;charset=UTF-8")
    @ResponseBody
    public List<TreeVo> getOrgData(){
        List<TreeVo> treeVos = new ArrayList<>();
        List<Organization> list = organizationService.findAll();
        for(Organization o:list){
            TreeVo vo = new TreeVo(o.getId() + "",o.getName(),o.getPid() + "","1");
            treeVos.add(vo);
        }
        return treeVos;
    }
    @RequestMapping(value = "/getRoleData",produces="application/json;charset=UTF-8")
    @ResponseBody
    public List<TreeVo> getRoleData(){
        List<TreeVo> treeVos = new ArrayList<>();
        List<Role> list = roleService.findAll();
        for(Role o:list){
            TreeVo vo = new TreeVo(o.getId() + "",o.getName());
            vo.setType("2");
            treeVos.add(vo);
        }
        return treeVos;
    }
    @RequestMapping(value = "/getUserData",produces="application/json;charset=UTF-8")
    @ResponseBody
    public List<TreeVo> getUserData(){
        List<TreeVo> treeVos = new ArrayList<>();
        List<User> list = userService.findAll();
        for(User o:list){
            TreeVo vo = new TreeVo(o.getId() + "",o.getName());
            vo.setType("3");
            treeVos.add(vo);
        }
        return treeVos;
    }

    @RequestMapping("/sendNoticeDo")
    @ResponseBody
    public ResponseEx sendNoticeDo(@RequestParam Map<String,Object> params){
        ResponseEx ex = new ResponseEx();
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            String companys = String.valueOf(params.get("send_companys"));
            String roles = String.valueOf(params.get("send_roles"));
            String users = String.valueOf(params.get("send_users"));
            List<String> companyList = null;
            List<String> rolesList = null;
            if(companys != null && !("".equals(companys))){
                companyList = userService.findUsersByOrgIds(companys.split(","));
            }
            if(roles != null &&  !("".equals(roles))){
                rolesList = userService.findUsersByRoleIds(roles.split(","));
            }
            List<String> userList = new ArrayList<String>();
            for(String str:users.split(",")){
                userList.add(str);
            }
            //去掉交集
            List<String> uniqueUsers = getOnlyStringList(companyList, rolesList, userList);
            params.put("useridList", uniqueUsers);
            noticeService.createNotices(params);
            ex.setSuccess("发送成功！");
        }catch (Exception e){
            e.printStackTrace();
            ex.setFail("发送失败！");
            return ex;
        }
        return ex;
    }

    @RequestMapping("/addNoticev")
    public String addNoticev(){
        return "/platNotice/addNotice";
    }
    @RequestMapping("/updateNoticev")
    public String updateNoticev(String id,Model model){
        Notice notice = noticeService.getNotice(id);
        model.addAttribute("notice",notice);
        return "/platNotice/updateNotice";
    }
    @RequestMapping("/myNotice")
    public String myNotice(){
        return "/platNotice/myNotice";
    }
    @RequestMapping("/sendNotice")
    public String sendNotice(String id,Model model){
        if(id != null && !id.equals("")){
            Notice notice = noticeService.getNotice(id);
            model.addAttribute("message",notice);
        }
        return "/platNotice/sendNotice";
    }

    @RequestMapping("/listSender")
    public String listSender(String noticeId,Model model){
        model.addAttribute("noticeId",noticeId);
        return "/platNotice/listSender";
    }

    @RequestMapping("/listSenderData")
    @ResponseBody
    public Grid<Map<String,Object>> listSenderData(String noticeId,PageHelper pageHelper){
        Grid<Map<String,Object>> g = new Grid<>();
        List<Map<String,Object>> list = noticeReceiverService.findNoticesByNoticeId(noticeId,pageHelper);
        for(Map<String,Object> map:list){
            String status = String.valueOf(map.get("status"));
            map.put("statuss",status.equals("2")?"已读":"未读");
        }
        g.setRows(list);
        g.setTotal(noticeReceiverService.countByNoticeId(noticeId));
        return g;
    }

    @RequestMapping("/updateReceiveStatus")
    @ResponseBody
    public ResponseEx updateReceiveStatus(String id){
        ResponseEx ex = new ResponseEx();
        noticeReceiverService.updateReceiveStatus(id);
        ex.setSuccess("修改状态成功！");
        return ex;
    }

    @RequestMapping("/personalNotice")
    @ResponseBody
    public ResponseEx personalNotice(HttpSession session){
        ResponseEx ex = new ResponseEx();
        User user = (User) session.getAttribute(Global.SESSION_USER);
        List<Map<String,Object>> nr = noticeReceiverService.findUnNoticeByUserId(user.getId());
        Map<String,Object> platMsg_type = DataDictionary.get("platMsg_type");
        for(Map<String,Object> map: nr){
            map.put("types",String.valueOf(platMsg_type.get(map.get("type") + "")));
        }
        ex.setRetData(nr);
        return ex;
    }

    @SuppressWarnings("unchecked")
    public List<String> getOnlyStringList(List<String> companyList,List<String> roleList,List<String> userList){
        List<String> list = SetUniqueList.decorate(new ArrayList<String>());
        if(companyList != null && companyList.size() > 0){
            for(String company:companyList){
                list.add(company);
            }
        }
        if(roleList != null && roleList.size() > 0){
            for(String role:roleList){
                list.add(role);
            }
        }
        if(userList != null && userList.size() > 0){
            for(String user:userList){
                list.add(user);
            }
        }
        return list;
    }
}
