package com.ycsys.smartmap.sys.common.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 
 * @author liweixiong
 * @date 2016年11月9日
 */
public class ArcGisServerUtils {
	private static Logger log = Logger.getLogger(ArcGisServerUtils.class);
	
	private static final String URL = "http://172.16.10.52:6080/arcgis/admin/generateToken";
	private static final String USERNAME = "siteadmin";
	private static final String PASSWORD = "ld@yc2016";
	private static final String P = "pjson";
	private static final String CLIENT = "requestip";

	public static Map<String,String> generateToken(String url, Map<String, String> params) {
		if (StringUtils.isNotBlank(url)) {
			String result = HttpClientUtils.post(url, params);
			try {
				Map<String,String> map = JsonMapper.getInstance().readValue(result, Map.class);
				return map;
			} catch (JsonParseException e) {
				log.warn("JsonParseException="+e);
			} catch (JsonMappingException e) {
				log.warn("JsonMappingException="+e);
			} catch (IOException e) {
				log.warn("IOException="+e);
			}
		}
		return null;
	}

	public static String getToken() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("f", P);
		params.put("Username", USERNAME);
		params.put("Password", PASSWORD);
		params.put("Client", CLIENT);
		params.put("expiration", "10");
		Map<String,String> result = ArcGisServerUtils.generateToken(URL, params);
		String token = result.get("token");
		return token;

	}
	
	public static String getTken(String url,String userName,String password) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("f", P);
		params.put("Username", userName);
		params.put("Password", password);
		params.put("Client", CLIENT);
		params.put("expiration", "10");
		Map<String,String> result = ArcGisServerUtils.generateToken(url, params);
		String token = result.get("token");
		return token;
	}
	
	public static String getTken(String ip,String port,String userName,String password) {
		String url = "";
		if(StringUtils.isNotBlank(port)) {
			url = "http://" + ip + ":" + port +"/arcgis/admin/generateToken";
		}
		else {
			url = "http://" + ip +"/arcgis/admin/generateToken";
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("f", P);
		params.put("Username", userName);
		params.put("Password", password);
		params.put("Client", CLIENT);
		params.put("expiration", "10");
		Map<String,String> result = ArcGisServerUtils.generateToken(url, params);
		String token = result.get("token");
		return token;
	}
	
	/**
	 * 验证服务器连接情况
	 * @param url
	 * @param userName
	 * @param password
	 * @return true:可以访问；false:不能访问
	 */
	public static boolean checkServer(String url,String userName,String password) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("f", P);
		params.put("Username", userName);
		params.put("Password", password);
		String result = HttpClientUtils.post(url, params);
		try {
			if(StringUtils.isNotBlank(result)) {
				Map<String,String> map = JsonMapper.getInstance().readValue(result, Map.class);
				String status = map.get("status");
				if("success".equals(status)) {
					return true;
				}
			}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * 调用接口
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String excute2(String url, Map<String, String> params) {
		if (StringUtils.isNotBlank(url)) {
			params.put("f", "pjson");// 指定返回json格式的数据
			String result = HttpClientUtils.post(url, params);
			log.debug("调用arcServer的响应结果：" + result);
			return result;
		}
		return null;
	}
	
	//测试
	public static void main(String[] args) {
		System.out.println(getToken());
		String t = getTken("172.16.10.52", "6080", "siteadmin", "ld@yc2016");
		System.out.println(t);
		
	}

}
