package com.ycsys.smartmap.sys.controller;

import java.lang.reflect.InvocationTargetException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.entity.Email;
import com.ycsys.smartmap.sys.service.EmailService;

/**
 * 发送邮件
 * @author lrr
 *
 */
@Controller
@RequestMapping("/sendEmail")
public class SendEmailController {
	private static Logger log = Logger.getLogger(Email.class);
	
	@Autowired
	private EmailService emailService;
	
	@RequestMapping("sendemail")
    @ResponseBody
    public ResponseEx sendEmail(String email){
        ResponseEx ex = new ResponseEx();
        try{
        	this.emailService.sendEmail(email);
            ex.setSuccess("发送成功");
        } catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }
	
	@RequestMapping("toEdit")
	public String toEdit(Model model) {
		return "/configExceptionAlarm/configExceptionAlarm_emailServer";
	}
	
	//保存邮件服务器配置参数方法
	@RequestMapping("save")
	@ResponseBody
	public ResponseEx save(Email email,Model model) {
		ResponseEx ex = new ResponseEx();
        try{
        	//新增
    		if(email.getId()==null){
    			emailService.save(email);
    		}
    		//更新
    		else{
    			Email dbEmail = emailService.get(Email.class,email.getId());
    			try {
    				// 得到修改过的属性
    				BeanExtUtils.copyProperties(dbEmail, email, true, true,null);
    			} catch (IllegalAccessException e) {
    				e.printStackTrace();
    			} catch (InvocationTargetException e) {
    				e.printStackTrace();
    			}
    			emailService.update(email);
    		}
            ex.setSuccess("保存成功");
        } catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
	}
	
	@RequestMapping("toSMS")
	public String toSMS(Model model) {
		return "/configExceptionAlarm/configExceptionAlarm_sms";
	}
	
}
