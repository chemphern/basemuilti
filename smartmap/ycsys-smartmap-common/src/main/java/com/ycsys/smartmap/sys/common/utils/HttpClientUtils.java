package com.ycsys.smartmap.sys.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * httpClient 工具类
 * 
 * @author liweixiong
 * @date 2016年11月9日
 */
public class HttpClientUtils {
	private static Logger log = Logger.getLogger(HttpClientUtils.class);

	/**
	 * post 请求调用
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String post(String url, Map<String, String> params) {
		if(StringUtils.isBlank(url)) {
			log.warn("url 不能为空");
			return null;
		}
		// 创建客户端 httpclient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = null;

		log.info("create httppost:" + url);
		// 创建httpPost
		HttpPost post = createHttpPost(url, params);

		// 调用接口 并返回 响应结果
		result = execute(httpClient, post);

		try {
			httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("关闭httpClient失败：" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 根据url和参数创建httpPost
	 * @param url
	 * @param params
	 * @return
	 */
	private static HttpPost createHttpPost(String url,
			Map<String, String> params) {
		if(StringUtils.isBlank(url)) {
			log.warn("url 不能为空");
			return null;
		}
		HttpPost httPost = new HttpPost(url);
		if(params != null && params.size() > 0) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			
			Set<String> keySet = params.keySet();
			for (String key : keySet) {
				nvps.add(new BasicNameValuePair(key, params.get(key)));
			}
			
			try {
				httPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		return httPost;
	}
	/**
	 * get请求 调用
	 * 
	 * @param url
	 * @return
	 */
	public static String get(String url) {
		if(StringUtils.isBlank(url)) {
			log.warn("url 不能为空");
			return null;
		}
		// 创建客户端 httpClient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = null;

		log.info("create httppost:" + url);
		// 创建httpGet
		HttpGet get = new HttpGet(url);
		// 调用接口 并返回 响应结果
		result = execute(httpClient, get);
		try {
			httpClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.info("关闭httpClient失败：" + e.getMessage());
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * httpClient 调用接口
	 * 
	 * @param httpClient
	 * @param httpost
	 * @return
	 */
	private static String execute(CloseableHttpClient httpClient,
			HttpUriRequest httpost) {
		HttpResponse response = null;
		String result = "";

		try {
			response = httpClient.execute(httpost);
			result = paseResponse(response);
		} catch (ClientProtocolException e) {
			log.info("ClientProtocolException:" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			log.info("IOException：" + e.getMessage());
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 解析响应结果
	 * 
	 * @param response
	 * @return
	 */
	private static String paseResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();

		log.info("response status: " + response.getStatusLine());
		String result = null;
		try {
			//成功
			//if(response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(entity);
				log.info("httpClient result:"+result);
			//}
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

}