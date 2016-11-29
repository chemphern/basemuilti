package com.ycsys.smartmap.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.service.MessageService;

/**
 * 发送短信
 * @author lrr
 *
 */
@Controller
@RequestMapping("sendMessage")
public class SendMessageController {
	@Autowired
	private MessageService messageService;
	
	@RequestMapping("sendMessage")
    @ResponseBody
    public ResponseEx sendEmail(String phoneNumber){
        ResponseEx ex = new ResponseEx();
        try{
        	this.messageService.sendMessage(phoneNumber);
            ex.setSuccess("发送成功");
        } catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }
}
