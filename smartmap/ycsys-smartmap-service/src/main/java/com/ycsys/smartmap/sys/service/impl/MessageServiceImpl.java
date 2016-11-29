package com.ycsys.smartmap.sys.service.impl;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.ycsys.smartmap.sys.service.MessageService;

@Service("sendMessage")
public class MessageServiceImpl implements MessageService {


	//VALUE标签可以用来注入简单值
	@Value("${sms.username}")
	private String username;
	@Value("${sms.password}")
	private String password;
	@Value("${sms.apikey}")
	private String apikey;
	@Value("${sms.url}")
	private String url;
	
	@Override
	public void sendMessage(String phoneNumber) {
		// 发送短信,短信发送成功了
		try{
			//创建一个URL对象
			URL targetUrl = new URL(this.url);
			//从URL对象中获得一个连接对象
			HttpURLConnection conn =  (HttpURLConnection) targetUrl.openConnection();
			//设置输入参数
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			StringBuilder params = new StringBuilder(100)
			.append("username=").append(username)
			.append("&password=").append(password)
			.append("&apikey=").append(apikey).append("&mobile=")
			.append(phoneNumber).append("&content=")
			.append("短信通知：羽辰智慧温馨提示：这是异常报警邮件信息,").append("请登陆管理本平台查看具体信息！");
			//写入参数
			conn.getOutputStream().write(params.toString().getBytes());
			//读入响应
			String response = StreamUtils.copyToString(conn.getInputStream(), Charset.forName("UTF-8"));
			System.out.println("1111111111111111");
			System.out.println(params);
			System.out.println("222222222222222");
			System.out.println(response);
			System.out.println("33333333333333");
		}catch(Exception e){
			throw new RuntimeException("发送短信失败");
		}
		
	}
}
