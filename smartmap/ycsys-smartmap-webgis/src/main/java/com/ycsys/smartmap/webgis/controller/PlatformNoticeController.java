package com.ycsys.smartmap.webgis.controller;

import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.entity.Notice;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.*;
import com.ycsys.smartmap.sys.util.DataDictionary;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
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
        Map<String,Object> platMsg_type = DataDictionary.get("platMsg_type");
        int status = (int)notice.getStatus();
        Map<String,Object> vo = new HashMap<String,Object>();
        vo.put("id",notice.getId());
        vo.put("title",notice.getTitle());
        vo.put("content",notice.getContent());
        vo.put("type",(int)notice.getType());
        vo.put("status",status);
        vo.put("createTime",notice.getCreateTime());
        vo.put("statuss",!(status == 2)?"未发送":"已发送");
        vo.put("types",String.valueOf(platMsg_type.get(notice.getType() + "")));
        vo.put("sendNum",noticeReceiverService.countNotice(notice.getId()));
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
}
