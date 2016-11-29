package com.ycsys.smartmap.cluster.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mysql.fabric.xmlrpc.base.Array;
import com.ycsys.smartmap.sys.common.utils.ArcGisServerUtils;
import com.ycsys.smartmap.sys.common.utils.HttpClientUtils;
import com.ycsys.smartmap.sys.common.utils.JsonMapper;
import com.ycsys.smartmap.sys.common.utils.StringUtils;

/**
 * 
 * @author liweixiong
 * @date 2016年11月9日
 */
public class ClusterUtils {
	private static Logger log = Logger.getLogger(ClusterUtils.class);
	private static final String URL = "http://172.16.10.52:6080/arcgis/admin/clusters/";// 后面把这个放到配置文件中维护

	/**
	 * 查找所有集群
	 * 
	 * @return
	 */
	public static List<String> lists() {
		Map<String, String> params = new HashMap<String, String>();
		String token = ArcGisServerUtils.getToken();
		params.put("Token", token);
		String url = URL.substring(0, URL.length() - 1);
		String retStr = excute2(url, params);
		List<String> lists = new ArrayList<>();
		// System.out.println("retStr="+retStr);
		if (retStr != null) {
			Map map1 = (Map) JSON.parse(retStr);
			// System.out.println("map1="+map1);
			if(map1 != null && map1.size() > 0) {
				JSONArray jArray = (JSONArray) map1.get("clusters");
				for (int i = 0; i < jArray.size(); i++) {
					Map map = (Map) jArray.get(i);
					String clusterName = (String) map.get("clusterName");
					// System.out.println("clusterName="+clusterName);
					lists.add(clusterName);
				}
			}
		}
		return lists;
	}

	public static void main(String[] args) {
		lists();
	}

	/**
	 * 创建集群
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String createCluster(String clusterName, String machineNames,
			String tcpClusterPort) {
		if (StringUtils.isNotBlank(clusterName)
				&& StringUtils.isNotBlank(tcpClusterPort)) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("clusterName", clusterName);
			params.put("machineNames", machineNames);
			params.put("tcpClusterPort", tcpClusterPort);
			String token = ArcGisServerUtils.getToken();
			params.put("Token", token);
			String url = URL + "create";
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
	 * 启动集群
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String startCluster(String clusterName) {
		if (StringUtils.isNotBlank(clusterName)) {
			Map<String, String> params = new HashMap<String, String>();
			String token = ArcGisServerUtils.getToken();
			params.put("Token", token);
			String url = URL + "clusterName" + "/start";
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
	 * 停止集群
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String stopCluster(String clusterName) {
		if (StringUtils.isNotBlank(clusterName)) {
			Map<String, String> params = new HashMap<String, String>();
			String token = ArcGisServerUtils.getToken();
			params.put("Token", token);
			String url = URL + "clusterName" + "/stop";
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
	 * 删除集群
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public static String deleteCluster(String clusterName) {
		if (StringUtils.isNotBlank(clusterName)) {
			Map<String, String> params = new HashMap<String, String>();
			params.put("clusterName", clusterName);
			String token = ArcGisServerUtils.getToken();
			params.put("Token", token);
			String url = URL + "clusterName" + "/delete";
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

	/**
	 * 调用接口
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	private static String excute2(String url, Map<String, String> params) {
		if (StringUtils.isNotBlank(url)) {
			params.put("f", "pjson");// 指定返回json格式的数据
			String result = HttpClientUtils.post(url, params);
			log.debug("调用arcServer的响应结果：" + result);
			return result;
		}
		return null;
	}
}
