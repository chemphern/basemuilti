package com.ycsys.smartmap.service.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.sys.common.utils.ArcGisServerUtils;
import com.ycsys.smartmap.sys.common.utils.HttpClientUtils;
import com.ycsys.smartmap.sys.common.utils.JsonMapper;
import com.ycsys.smartmap.sys.common.utils.StringUtils;

/**
 * 
 * @author liweixiong
 * @date 2016年11月9日
 */
public class ServiceUtils {
	private static Logger log = Logger.getLogger(ServiceUtils.class);
	private static final String URL = "http://172.16.10.52:6080/arcgis/admin/services/";// 后面把这个放到配置文件中维护
	
	//测试
	public static void main(String[] args) {
		//listFolder("http://172.16.10.52:6080/arcgis/admin/services","siteadmin","ld@yc2016");
		//List<Service> d = listServices("http://172.16.10.52:6080/arcgis/admin/services","siteadmin","ld@yc2016");
		getServiceInfo("172.16.10.52", "6080", "siteadmin", "ld@yc2016", "Skyline", "ZhouShan", "MapServer");
	}
	
	/**
	 * 返回服务的folder
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 */
	public static List<String> listFolder(String url,String userName,String password) {
		List<String> lists = new ArrayList<String>();
		Map<String, String> params = new HashMap<String, String>();
		String token = ArcGisServerUtils.getToken();
		//String token2 = ArcGisServerUtils.getTken(url,userName, password);
		params.put("Token", token);
		String retStr = ArcGisServerUtils.excute2(url, params);
		if (retStr != null) {
			Map map1 = (Map) JSON.parse(retStr);
			if(map1 != null && map1.size() > 0) {
				JSONArray jArray = (JSONArray) map1.get("folders");
				for (int i = 0; i < jArray.size(); i++) {
					lists.add((String)jArray.get(i));
				}
			}
		}
		//System.out.println("lists="+lists);
		return lists;
	}
	
	/**
	 * 
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 */
	public static List<Service> listServices(String url,String userName,String password) {
		if(StringUtils.isNotBlank(url) && StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password)) {
			List<Service> lists = new ArrayList<Service>();
			Map<String, String> params = new HashMap<String, String>();
			String token = ArcGisServerUtils.getToken();
			//String token2 = ArcGisServerUtils.getTken(url,userName, password);
			params.put("Token", token);
			String retStr = ArcGisServerUtils.excute2(url, params);
			if (retStr != null) {
				Map map1 = (Map) JSON.parse(retStr);
				if(map1 != null && map1.size() > 0) {
					JSONArray jArray = (JSONArray) map1.get("services");
					for (int i = 0; i < jArray.size(); i++) {
						Map map = (Map) jArray.get(i);
						if(map != null && map.size() > 0) {
							Service s = new Service();
							s.setFunctionType((String)map.get("type"));
							s.setFolderName((String)map.get("folderName"));
							s.setRemarks((String)map.get("description"));
							s.setShowName((String)map.get("serviceName"));
							lists.add(s);
						}
					}
				}
			}
			//System.out.println("lists="+lists);
			return lists;
		}
		return null;
	}
	
	/**
	 * 得到服务信息
	 * @param url
	 * @param userName
	 * @param password
	 * @param folderName
	 * @param showName
	 * @param arcServiceType
	 * @return
	 */
	public static Service getServiceInfo(String ip,String port,String userName,String password,String folderName,String serviceName,String type) {
		if(StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(port) && StringUtils.isNotBlank(userName) && StringUtils.isNotBlank(password) 
				&& StringUtils.isNotBlank(serviceName) && StringUtils.isNotBlank(type)) {
			//http://172.16.10.52:6080/arcgis/admin/generateToken
			String tokenUrl = "http://" + ip + ":" + port + "/arcgis/admin/generateToken";
			String token = ArcGisServerUtils.getTken(tokenUrl,userName, password);
			String url = "http://" + ip + ":" + port + "/arcgis/admin/services/";
			if(StringUtils.isNotBlank(folderName)) {
				url = url + folderName + "/";
			}
			url = url + serviceName + "." + type;
			Map<String, String> params = new HashMap<String, String>();
			params.put("Token", token);
			String retStr = ArcGisServerUtils.excute2(url, params);
			if (retStr != null) {
				Service service = new Service();
				Map map = (Map) JSON.parse(retStr);
				service.setShowName((String)map.get("serviceName"));
				service.setFunctionType((String)map.get("type"));
				service.setClusterName((String)map.get("clusterName"));
				if(map.get("extensions") != null) {
					JSONArray jArray = (JSONArray) map.get("extensions");
					String typeName = "";
					for(int i = 0; i < jArray.size(); i++) {
						Map map2 = (Map) jArray.get(i);
						typeName = typeName + (String)map2.get("typeName") + ",";
					}
					if(typeName.length() > 0) {
						service.setServiceExtend(typeName.substring(0, typeName.length() - 1));
					}
				}
				service.setFolderName(folderName);
				service.setRemarks((String)map.get("description"));
				return service;
			}
		}
		return null;
	}
	
	/**
	 * 创建 folder
	 * 
	 * @param folderName
	 * @param description
	 * @param token
	 * @return
	 */
	public static String createFolder(String folderName, String description) {
		if (StringUtils.isNotBlank(folderName)) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("folderName", folderName);
			params.put("description", description);
			String token = ArcGisServerUtils.getToken();
			params.put("Token", token);
			String url = URL + "createFolder";
			Map map = excute(url, params);
			if (map != null && map.size() > 0) {
				if ("success".equals(map.get("status"))) {
					return "success";
				} else {
					return map.get("messages").toString();
				}
			}
		}
		return "empty folderName";

	}

	/**
	 * 修改 folder,只能修改描述和是否加密2项
	 * 
	 * @param folderName
	 * @param description
	 * @param webEncrypted
	 * @param token
	 * @return
	 */
	public static String editFolder(String folderName, String description,
			boolean webEncrypted) {
		if (StringUtils.isNotBlank(description)) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("webEncrypted", webEncrypted + "");
			params.put("description", description);
			String token = ArcGisServerUtils.getToken();
			params.put("Token", token);
			String url = URL + folderName + "/editFolder";
			Map map = excute(url, params);
			if (map != null && map.size() > 0) {
				if ("success".equals(map.get("status"))) {
					return "success";
				} else {
					return map.get("messages").toString();
				}
			}
		}
		return "empty folderName";

	}

	/**
	 * 删除 folder
	 * 
	 * @param folderName
	 * @param token
	 * @return
	 */
	public static String deleteFolder(String folderName) {
		if (StringUtils.isNotBlank(folderName)) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("folderName", folderName);
			String token = ArcGisServerUtils.getToken();
			params.put("Token", token);
			String url = URL + folderName + "/deleteFolder";
			Map map = excute(url, params);
			if (map != null && map.size() > 0) {
				if ("success".equals(map.get("status"))) {
					return "success";
				} else {
					return map.get("messages").toString();
				}
			}
		}
		return "empty folderName";

	}

	/**
	 * 创建服务
	 * 
	 * @param folderName
	 * @param token
	 * @param serviceInfo
	 *            json格式的数据
	 *            参考例子：http://resources.arcgis.com/en/help/arcgis-rest-api/#/Create_Service/02r3000001tr000000/
	 * @return
	 */
	public static String createService(String ip,String port,String userName,String password,String serviceName, String type,
			String folderName,String serviceInfo) {
		//String token = ArcGisServerUtils.getToken();
		//http://172.16.10.52:6080/arcgis/admin/generateToken
		String tokenUrl = "http://" + ip + ":" + port +"/arcgis/admin/generateToken";
		String token = ArcGisServerUtils.getTken(tokenUrl, userName, password);
		//http://172.16.10.52:6080/arcgis/admin/services/
		if (StringUtils.isNotBlank(serviceInfo)) {
			String url = "";
			if (StringUtils.isBlank(folderName)) {
				url ="http://"+ip+":"+ port +"/arcgis/admin/services/createService";
			} else {
				// 判断folderName是否存在
				String url2 = URL + folderName;
				Map<String, String> param2 = new HashMap<String, String>();
				param2.put("Token", token);
				Map map2 = excute(url2, param2);
				if (!exists(folderName, "", "", token)) {
					return "folderName not exists";
				}
				url = "http://"+ip+":"+ port +"/arcgis/admin/services/" + folderName + "/createService";
			}
			// 判断服务是否存在
			if (exists(folderName, serviceName, type, token)) {
				return "serviceName exists ";
			}
			Map<String, String> param = new HashMap<String, String>();
			param.put("Token", token);
			param.put("service", serviceInfo);
			Map map = excute(url, param);
			if (map != null && map.size() > 0) {
				if ("success".equals(map.get("status"))) {
					return "success";
				} else {
					return map.get("messages").toString();
				}
			}
		}
		return "empty serviceInfo";
	}

	/**
	 * 
	 * @param folderName
	 * @param serviceName
	 * @param type
	 *            服务的类型
	 * @return true：存在；false：不存在
	 */
	public static boolean exists(String folderName, String serviceName,
			String type,String token) {
		if (StringUtils.isBlank(folderName) && StringUtils.isBlank(serviceName)
				&& StringUtils.isBlank(type)) {
			return false;
		}
		if (StringUtils.isNotBlank(serviceName) && StringUtils.isBlank(type)) {
			return false;
		}
		if (StringUtils.isBlank(serviceName) && StringUtils.isNotBlank(type)) {
			return false;
		}
		Map<String, String> param = new HashMap<String, String>();
		param.put("Token", token);
		param.put("folderName", folderName);
		param.put("serviceName", serviceName);
		param.put("type", type);
		Map map = excute(URL + "/exists", param);
		boolean result = (Boolean) map.get("exists");
		return result;
	}

	/**
	 * 启动服务
	 * @param folderName
	 * @param serviceName
	 * @param serviceType
	 * @param token
	 * @return
	 */
	public static String startService(String folderName, String serviceName,
			String serviceType) {
		String url = "";
		String token = ArcGisServerUtils.getToken();
		if (StringUtils.isNotBlank(serviceName)
				&& StringUtils.isNotBlank(serviceType)) {
			
			//判断是否存在
			if(!exists(folderName, serviceName, serviceType, token)) {
				return serviceName+"  not exists";
			}
			
			if (StringUtils.isNotBlank(folderName)) {
				url = URL + folderName + "/" + serviceName + "." + serviceType
						+ "/start";
			} else {
				url = URL + serviceName + "." + serviceType + "/start";
			}
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("Token", token);
			Map map = excute(url, params);
			if (map != null && map.size() > 0) {
				if ("success".equals(map.get("status"))) {
					return "success";
				} else {
					return map.get("messages").toString();
				}
			}
		}

		return null;
	}
	
	/**
	 * 停止服务
	 * @param folderName
	 * @param serviceName
	 * @param serviceType
	 * @param token
	 * @return
	 */
	public static String stopService(String folderName, String serviceName,
			String serviceType) {
		String url = "";
		String token = ArcGisServerUtils.getToken();
		if (StringUtils.isNotBlank(serviceName)
				&& StringUtils.isNotBlank(serviceType)) {
			
			//判断是否存在
			if(!exists(folderName, serviceName, serviceType, token)) {
				return "serviceName not exists";
			}
			
			if (StringUtils.isNotBlank(folderName)) {
				url = URL + folderName + "/" + serviceName + "." + serviceType
						+ "/stop";
			} else {
				url = URL + serviceName + "." + serviceType + "/stop";
			}
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("Token", token);
			Map map = excute(url, params);
			if (map != null && map.size() > 0) {
				if ("success".equals(map.get("status"))) {
					return "success";
				} else {
					return map.get("messages").toString();
				}
			}
		}

		return null;
	}
	

	/**
	 * 删除服务
	 * @param folderName
	 * @param serviceName
	 * @param serviceType
	 * @param token
	 * @return
	 */
	public static String deleteService(String folderName, String serviceName,
			String serviceType) {
		String url = "";
		String token = ArcGisServerUtils.getToken();
		if (StringUtils.isNotBlank(serviceName)
				&& StringUtils.isNotBlank(serviceType)) {
			
			//判断是否存在
			if(!exists(folderName, serviceName, serviceType, token)) {
				return "serviceName not exists";
			}
			
			if (StringUtils.isNotBlank(folderName)) {
				url = URL + folderName + "/" + serviceName + "." + serviceType
						+ "/delete";
			} else {
				url = URL + serviceName + "." + serviceType + "/delete";
			}
			//把服务先停止
			String retStr = stopService(folderName, serviceName, serviceType);
			if("success".equals(retStr)) {
				Map<String, String> params = new HashMap<String, String>();
				params.put("Token", token);
				Map map = excute(url, params);
				if (map != null && map.size() > 0) {
					if ("success".equals(map.get("status"))) {
						return "success";
					} else {
						return map.get("messages").toString();
					}
				}
			}
		}

		return null;
	}
	
	
	/**
	 * 查看服务状态
	 * @param folderName
	 * @param serviceName
	 * @param serviceType
	 * @param token
	 * @return 返回例子：{configuredState=STOPPED, realTimeState=STOPPED}
	 */
	public static Map getStatus(String folderName, String serviceName,
			String serviceType) {
		String url = "";
		String token = ArcGisServerUtils.getToken();
		if (StringUtils.isNotBlank(serviceName)
				&& StringUtils.isNotBlank(serviceType)) {
			
			//判断是否存在
			if(!exists(folderName, serviceName, serviceType, token)) {
				Map<String,String> map = new HashMap<String, String>();
				map.put("exists", "serviceName not exists");
				return map;
			}
			
			if (StringUtils.isNotBlank(folderName)) {
				url = URL + folderName + "/" + serviceName + "." + serviceType
						+ "/status";
			} else {
				url = URL + serviceName + "." + serviceType + "/status";
			}
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("Token", token);
			return excute(url, params);
			
		}

		return null;
	}
	
	/**
	 * 调用接口
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private static Map excute(String url, Map<String, String> params) {
		if (StringUtils.isNotBlank(url)) {
			params.put("f", "pjson");// 指定返回json格式的数据
			String result = HttpClientUtils.post(url, params);
			log.debug("调用arcServer的响应结果：" + result);
			try {
				if(StringUtils.isNotBlank(result)) {
					Map map = JsonMapper.getInstance().readValue(result, Map.class);
					log.debug("调用arcServer的响应结果：" + map);
					return map;
				}
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				log.warn("JsonParseException=" + e.getMessage());
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				log.warn("JsonMappingException=" + e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.warn("IOException=" + e.getMessage());
				e.printStackTrace();
			}

		}
		return null;
	}
}
