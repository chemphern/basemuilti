package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.entity.Email;

/**
 * 发送邮件相关服务
 * @author Administrator
 *
 */
public interface EmailService extends BaseService<Email, Integer> {
	
	void sendEmail(String email);
}
