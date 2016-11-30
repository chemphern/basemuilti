package com.ycsys.smartmap.sys.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ycsys.smartmap.sys.dao.EmailDao;
import com.ycsys.smartmap.sys.entity.Email;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.EmailService;

/**
 * 
 * @author lrr
 * @date   2016年11月28日
 */
@Service("sendEmail")
public class EmailServiceImpl implements EmailService{
	
	@Autowired
	private EmailDao emailDao;
	

	/*@Value("${mail.applicationUrl}")
	private String applicationUrl;
	
	@Value("${mail.host}")
	private String host;
	@Value("${mail.username}")
	private String userName;
	@Value("${mail.password}")
	private String password;*/
	
	@Override
	public void sendEmail(String email) {
			//构建邮件内容,并发送
			StringBuilder content = new StringBuilder(100)
			.append("羽辰智慧林业综合管理平台温馨提示：这是异常报警邮件,点击<a href='").append("this.applicationUrl")
			.append("index").append("'>这里</a>,登陆本平台查看具体信息！");
			try{
				//发送邮件
				send(email, content.toString());
				System.out.println("发送邮件: "+content);
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException("发送验证验证邮件失败!!");
			}
		
	}
	
	private void send(String email, String content)
			throws MessagingException {
		List<Email> emailConfig = this.find("from Email e where 1 = 1");
		//发送邮件
		JavaMailSenderImpl sender =  new JavaMailSenderImpl();//邮件发送器
		//设置服务器地址
		//sender.setHost(host);
		sender.setHost(emailConfig.get(0).getEmailHostAddress());
		//创建一个邮件
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, "UTF-8");
		//设置邮件相关的内容
		messageHelper.setTo(email);
		//messageHelper.setFrom(userName);
		messageHelper.setFrom(emailConfig.get(0).getSenderAddress());
		messageHelper.setSubject("异常报警邮件!");
		//设置邮件内容
		messageHelper.setText(content, true);
		
		//设置发送准备
		sender.setUsername(emailConfig.get(0).getEmailName());
		sender.setPassword(emailConfig.get(0).getEmailPassword());
		
		Properties prop =  new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.timeout", "25000");
		sender.setJavaMailProperties(prop);
		sender.send(message);
	}

	public Integer save(Email o) {
		return (Integer) emailDao.save(o);
	}

	public void delete(Email o) {
		emailDao.delete(o);
	}

	public void update(Email o) {
		emailDao.update(o);
	}

	public void saveOrUpdate(Email o) {
		emailDao.saveOrUpdate(o);
	}

	public List<Email> find(String hql) {
		return emailDao.find(hql);
	}

	public List<Email> find(String hql, Object[] param) {
		return emailDao.find(hql, param);
	}

	public List<Email> find(String hql, List<Object> param) {
		return emailDao.find(hql, param);
	}

	public List<Email> find(String hql, Object[] param, PageHelper page) {
		return emailDao.find(hql, param, page);
	}

	public Email get(Class<Email> c, Serializable id) {
		return emailDao.get(c, id);
	}

	public Email get(String hql, Object[] param) {
		return emailDao.get(hql, param);
	}

	public Email get(String hql, List<Object> param) {
		return emailDao.get(hql, param);
	}
}
