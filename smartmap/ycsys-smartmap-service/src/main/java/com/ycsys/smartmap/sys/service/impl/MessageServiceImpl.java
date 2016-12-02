package com.ycsys.smartmap.sys.service.impl;

import org.springframework.stereotype.Service;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.ycsys.smartmap.sys.service.MessageService;

@Service("sendMessage")
public class MessageServiceImpl implements MessageService {
	
	//VALUE标签可以用来注入简单值
	/*@Value("${sms.username}")
	private String username;
	@Value("${sms.password}")
	private String password;
	@Value("${sms.apikey}")
	private String apikey;
	@Value("${sms.url}")
	private String url;*/
	
	@Override
	public void sendMessage(String phoneNumber) {
		// 发送短信,短信发送成功了
		try{
			/*//创建一个URL对象
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
			System.out.println("33333333333333");*/
			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod("http://gbk.sms.webchinese.cn/"); 
			post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");//在头文件中设置转码
			NameValuePair[] data ={ new NameValuePair("Uid", "itRex"),//这个是登录网站的用户名
					new NameValuePair("Key", "5661867c152eb1fdaf57"),//注册之后，登录可以查看到密钥
					new NameValuePair("smsMob",phoneNumber),
					new NameValuePair("smsText","测试java发送信息:你好，接收到短信，说明接口调用成功了!")};
			post.setRequestBody(data);
			client.executeMethod(post);
			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			//System.out.println("statusCode:"+statusCode);
			for(Header h : headers)
			{
				System.out.println(h.toString());
			}
			String result = new String(post.getResponseBodyAsString().getBytes("gbk")); 
			System.out.println("result返回的状态码："+result); //打印返回消息状态
			post.releaseConnection();
		}catch(Exception e){
			throw new RuntimeException("发送短信失败");
		}
		
		
	}
}
