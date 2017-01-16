package com.ycsys.smartmap.service.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ycsys.smartmap.cluster.utils.ClusterUtils;
import com.ycsys.smartmap.resource.entity.Resource;
import com.ycsys.smartmap.resource.service.ResourceService;
import com.ycsys.smartmap.service.entity.Layer;
import com.ycsys.smartmap.service.entity.LayerTheme;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.LayerService;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.service.service.ThemeService;
import com.ycsys.smartmap.service.utils.ServiceUtils;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.enums.ExceptionLevel;
import com.ycsys.smartmap.sys.common.enums.LogType;
import com.ycsys.smartmap.sys.common.exception.GisServerException;
import com.ycsys.smartmap.sys.common.exception.ServiceException;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.utils.ArcGisServerUtils;
import com.ycsys.smartmap.sys.common.utils.Base64Util;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.FileUtils;
import com.ycsys.smartmap.sys.common.utils.HttpClientUtils;
import com.ycsys.smartmap.sys.common.utils.JsonMapper;
import com.ycsys.smartmap.sys.common.utils.StringUtils;
import com.ycsys.smartmap.sys.controller.AreaController;
import com.ycsys.smartmap.sys.controller.BaseController;
import com.ycsys.smartmap.sys.entity.ConfigServerEngine;
import com.ycsys.smartmap.sys.entity.DictionaryItem;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.ConfigServerEngineService;
import com.ycsys.smartmap.sys.service.LogService;
import com.ycsys.smartmap.sys.util.DataDictionary;
import com.ycsys.smartmap.sys.util.DbUtils;

/**
 * service controller
 * 
 * @author liweixiong
 * @date 2016年11月3日
 */
@Controller
@RequestMapping("/service")
public class ServiceController extends BaseController {
	@Autowired
	private LogService logService;
	@Autowired
	private LayerService layerService;
	@Autowired
	private ThemeService themeService;
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private ConfigServerEngineService configServerEngineService;
	
	//服务导入模版地址
	private static final String DOWNLOADURL = "/template/服务导入模版.zip";
	
	//服务引擎的数据插入
    public static void main(String [] args){
        try {
			//读取配置文件
			Properties properties = new Properties();
			String profile = "db.properties";
			InputStream pis = AreaController.class.getResource("/" + profile).openStream();
			properties.load(pis);
			//创建数据库连接
			DbUtils db = new DbUtils();
			String driver = properties.getProperty("jdbc.driver");
			//如果是oracle，则sql需要特殊处理
			boolean isOracle = driver.indexOf("oracle") > -1;
			String insertSql = "";
			if(isOracle){
			    String sequence = "HIBERNATE_SEQUENCE";
			    insertSql = "insert into sys_config_server_engine (id,config_name,engineType_type,integration_model,machine_name,intranet_ip,intranet_port,running_status,engine_manager,manager_password) values (" +sequence +  ".nextval,?,?,?,?,?,?,?,?,?)";
			}else {
			    insertSql = "insert into sys_config_server_engine (config_name,engineType_type,integration_model,machine_name,intranet_ip,intranet_port,running_status,engine_manager,manager_password) values (?,?,?,?,?,?,?,?,?)";
			}
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
			conn.setAutoCommit(false);
			Object obj [] = new Object[]{"arcGisServer10.3","0","0","arcGisServer10.3","172.16.10.52",6080,"0","siteadmin","ld@yc2016"};
			db.execute(conn, insertSql, obj);
			db.commit(conn);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
    }
	/**
	 * 准备发布服务
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("toPublish")
	@RequiresPermissions(value = "service-publish")
	public String toPublish(Model model) {
		// 查找所有服务引擎
		List<ConfigServerEngine> serverEngineList = configServerEngineService
				.find("from ConfigServerEngine");
		model.addAttribute("serverEngineList", serverEngineList);
		// 服务功能类型 service_function_type
		model.addAttribute("serviceFunctionType",DataDictionary.getObject("service_function_type"));
		// 服务扩展功能类型service_extend_type
		model.addAttribute("serviceExtendType",DataDictionary.getObject("service_extend_type"));
		model.addAttribute("serviceResource",DataDictionary.getObject("service_resource"));
		return "/service/service_publish";
	}

	/**
	 * 找服务器引擎的相关信息（集群和服务发布的目录）
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getClusterAndFolder")
	@RequiresPermissions(value = "service-publish")
	public Map<String, String> getClusterAndFolder(Integer id) {
		Map<String, String> map = new HashMap<String, String>();
		ConfigServerEngine engine = configServerEngineService.get(
				ConfigServerEngine.class, id);
		if (engine != null) {
			String ip = engine.getIntranetIp();
			String port = engine.getIntranetPort() + "";
			String userName = engine.getEngineManager();
			String password = engine.getManagerPassword();
			List<String> folderList = ServiceUtils.listFolder(ip, port,
					userName, password);
			List<String> clusterList = ClusterUtils.lists(ip, port, userName,
					password);
			StringBuffer sb1 = new StringBuffer();
			StringBuffer sb2 = new StringBuffer();
			// 拼接成下拉的option串
			for (String i : folderList) {
				sb1.append("<option value='").append(i).append("'>").append(i)
						.append("</option>").append("<br>");
			}
			for (String j : clusterList) {
				sb2.append("<option value='").append(j).append("'>").append(j)
						.append("</option>").append("<br>");
			}
			map.put("folderList", sb1.toString());
			map.put("clusterList", sb2.toString());
		}
		return map;
	}

	/**
	 * 选择资源
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("viewResource")
	@RequiresPermissions(value = "service-publish")
	public String viewResource(Model model) {
		model.addAttribute("fileType", DataDictionary.getObject("arc_file_type"));
		model.addAttribute("resourceType", DataDictionary.getObject("resource_type"));
		return "/resource/resource_select";
		//return "/service/service_select_resource";
	}
	
	/**
	 * 服务发布
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("publish")
	@RequiresPermissions(value = "service-publish")
	public Map<String, String> publish(HttpServletRequest request, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		String serviceName = request.getParameter("serviceName");
		//String serviceResource = request.getParameter("serviceResource");
		String serviceType = request.getParameter("serviceType");
		// String resourceFile = request.getParameter("resourceFile");
		String resourceFileId = request.getParameter("resourceFileId");
		String clusterName = request.getParameter("clusterName");
		String folderName = request.getParameter("folderName");
		String[] extensionName = request.getParameterValues("extensionName");
		List<String> extensionNameList = null;
		long startTime = System.currentTimeMillis();
		if (extensionName != null && extensionName.length > 0) {
			extensionNameList = Arrays.asList(extensionName);
		}
		Resource resource = resourceService.get(Resource.class,
				Integer.parseInt(resourceFileId));
		String serverEngineId = request.getParameter("serverEngine");
		//System.out.println("serverEngine=" + serverEngineId);
		ConfigServerEngine severEngine = configServerEngineService.get(
				ConfigServerEngine.class, Integer.parseInt(serverEngineId));
		if (StringUtils.isNotBlank(resourceFileId)) {
			resource = resourceService.get(Resource.class,
					Integer.parseInt(resourceFileId));
		}
		// 把资源文件上传到ArcGisServer上
		String path = "";
		String fileName = "";
		if (resource.getFilePath() != null) {
			path = "\\\\172.16.10.52\\smartMap\\" + "资源";
			if (!"/".equals(folderName)) {
				path = path + "\\" + folderName;
			}
			File targetFloder = new File(path);
			if (!targetFloder.exists()) {
				targetFloder.mkdirs();
			}
			fileName = resource.getFilePath().substring(
					resource.getFilePath().lastIndexOf("\\"));
			File targetPath = new File(path, fileName);
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(resource.getFilePath());
				out = new FileOutputStream(targetPath);
				byte[] bs = new byte[1024];
				int len = -1;
				while ((len = in.read(bs)) != -1) {
					out.write(bs, 0, len);
				}
			} catch (Exception e) {
				map.put("flag", "0");
				map.put("msg", "发布失败！");
				//return map;
				long endTime = System.currentTimeMillis();
				logService.saveLogInfo("上传服务资源到arcServer上失败", LogType.Server, "上传服务资源到arcServer上失败", 2, "failure", endTime-startTime);
				throw new GisServerException("上传服务资源到arcServer上失败", "上传服务资源到arcServer上失败", ExceptionLevel.SERIOUS.getValue(), "上传服务资源到arcServer上失败");
				
			} finally {
				try {
					out.close();
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		String onlineResource = "http://" + severEngine.getIntranetIp() + ":"
				+ severEngine.getIntranetPort() + "/arcgis/rest/services/";
		if (StringUtils.isNotBlank(folderName)) {
			onlineResource = onlineResource + folderName + "/";
		}
		onlineResource = onlineResource + serviceName + "/" + serviceType;
		JSONObject jsonService = new JSONObject();

		jsonService.put("serviceName", serviceName);
		jsonService.put("type", "MapServer");
		jsonService.put("description", "程序发布的服务");
		jsonService.put("capabilities", "map,query,data");
		jsonService.put("clusterName", clusterName);
		jsonService.put("minInstancesPerNode", 1);
		jsonService.put("maxInstancesPerNode", 2);
		jsonService.put("maxWaitTime", 60);
		jsonService.put("maxStartupTime", 300);
		jsonService.put("maxIdleTime", 1800);
		jsonService.put("maxUsageTime", 600);
		jsonService.put("recycleInterval", 24);
		jsonService.put("loadBalancing", "ROUND_ROBIN");
		jsonService.put("isolationLevel", "HIGH");

		JSONObject jsonProperties = new JSONObject();
		jsonService.put("properties", jsonProperties);
		jsonProperties.put("maxBufferCount", "100");
		jsonProperties.put("virtualCacheDir", "/rest/directories/arcgiscache");
		jsonProperties.put("maxImageHeight", "2048");
		jsonProperties.put("maxRecordCount", "1000");
		String filePath = "E:\\smartMap\\资源";
		if (!"/".equals(folderName)) {
			filePath = filePath + "\\" + folderName;
		}
		filePath = filePath + fileName;
		jsonProperties.put("filePath", filePath);
		jsonProperties.put("maxImageWidth", "2048");
		jsonProperties.put("cacheOnDemand", "false");
		jsonProperties.put("virtualOutputDir", "/rest/directories/arcgisoutput");
		jsonProperties.put("outputDir", "");
		jsonProperties.put("supportedImageReturnTypes", "MIME+URL");
		jsonProperties.put("isCached", "false");
		jsonProperties.put("ignoreCache", "false");
		jsonProperties.put("clientCachingAllowed", "false");
		jsonProperties.put("cacheDir", "");

		JSONArray jsonExtensions = new JSONArray();
		jsonService.put("extensions", jsonExtensions);
		if (extensionNameList != null) {
			JSONObject joExService = new JSONObject();
			JSONObject jpExproties = new JSONObject();

			joExService.put("typeName", "KmlServer");
			joExService.put("capabilities",
					"SingleImage,SeparateImages,Vectors");
			if(extensionNameList.contains("KmlServer")) {
				joExService.put("enabled", "true");
			}
			else {
				joExService.put("enabled", "false");
			}
			joExService.put("maxUploadFileSize", 100);
			joExService.put("allowedUploadFileTypes", "");

			jpExproties.put("dpi", "96");
			jpExproties.put("imageSize", "1024");
			jpExproties.put("linkDescription", "ss");
			jpExproties.put("linkName", "ss");
			jpExproties.put("message", "msg");
			jpExproties.put("minRefreshPeriod", "30");
			jpExproties.put("featureLimit", "1000000");
			jpExproties.put("useNetworkLinkControlTag", "xx");
			jpExproties.put("compatibilityMode", "GoogleEarth");
			jpExproties.put("endPointURL", "");
			jpExproties.put("useDefaultSnippets", "false");

			joExService.put("properties", jpExproties);

			jsonExtensions.add(joExService);
		}
		if (extensionNameList != null) {
			JSONObject joExService = new JSONObject();
			JSONObject jpExproties = new JSONObject();
			joExService.put("typeName", "WFSServer");
			joExService.put("capabilities", "");
			if(extensionNameList.contains("WFSServer")) {
				joExService.put("enabled", "true");
			}
			else {
				joExService.put("enabled", "false");
			}
			joExService.put("maxUploadFileSize", 0);
			joExService.put("allowedUploadFileTypes", "");

			jpExproties.put("city", "");
			jpExproties.put("contactOrganization", "");
			jpExproties.put("abstract", "");
			jpExproties.put("accessConstraints", "");
			jpExproties.put("fees", "");
			jpExproties.put("address", "");
			jpExproties.put("country", "中国");
			jpExproties.put("contactFacsimileTelephone", "");
			jpExproties.put("contactPosition", "中国");
			jpExproties.put("pathToCustomSLDFile", "");
			jpExproties.put("pathToCustomGetCapabilitiesFiles", "");
			jpExproties.put("addressType", "");
			jpExproties.put("contactVoiceTelephone", "");
			jpExproties.put("keyword", "xx");
			jpExproties.put("postCode", "");
			jpExproties.put("onlineResource", "11");
			jpExproties.put("title", "xxx");
			jpExproties.put("contactPerson", "xx");
			jpExproties.put("stateOrProvince", "ss");
			jpExproties.put("contactElectronicMailAddress", "ss");
			jpExproties.put("customGetCapabilities", "ss");
			jpExproties.put("inheritLayerNames", "ss");
			jpExproties.put("name", "ss");

			joExService.put("properties", jpExproties);
			jsonExtensions.add(joExService);
		}

		if (extensionNameList != null) {
			JSONObject joExService = new JSONObject();
			JSONObject jpExproties = new JSONObject();
			joExService.put("typeName", "WCSServer");
			joExService.put("capabilities", "null");
			if(extensionNameList.contains("WCSServer")) {
				joExService.put("enabled", "true");
			}
			else {
				joExService.put("enabled", "false");
			}
			joExService.put("maxUploadFileSize", 0);
			joExService.put("allowedUploadFileTypes", "");

			jpExproties.put("pathToCustomGetCapabilitiesFiles", "");
			jpExproties.put("name", serviceName);
			jpExproties.put("title", serviceName);
			jpExproties.put("abstraction", "");
			jpExproties.put("fees", "");
			jpExproties.put("accessConstraints", "无");
			jpExproties.put("providerName", "");
			jpExproties.put("providerWebSite", "");
			jpExproties.put("responsiblePerson", "");
			jpExproties.put("responsiblePosition", "");
			jpExproties.put("phone", "");
			jpExproties.put("fax", "");
			jpExproties.put("email", "");
			jpExproties.put("address", "");
			jpExproties.put("city", "");
			jpExproties.put("province", "");
			jpExproties.put("zipcode", "");
			jpExproties.put("country", "");
			jpExproties.put("serviceHour", "serviceHour");
			jpExproties.put("contactInstructions", "");
			jpExproties.put("role", "");
			// onlineResource = onlineResource + serviceName + "/" +
			// serviceType;
			jpExproties.put("onlineResource", onlineResource);

			joExService.put("properties", jpExproties);
			jsonExtensions.add(joExService);
		}
		if (extensionNameList != null) {
			JSONObject joExService = new JSONObject();
			JSONObject jpExproties = new JSONObject();
			joExService.put("typeName", "FeatureServer");
			joExService.put("capabilities", "null");
			if( extensionNameList.contains("FeatureServer")) {
				joExService.put("enabled", "true");
			}
			else {
				joExService.put("enabled", "false");
			}
			joExService.put("maxUploadFileSize", 0);
			joExService.put("allowedUploadFileTypes", "");

			jpExproties.put("zDefaultValue", "");
			jpExproties.put("xssPreventionEnabled", "false");
			jpExproties.put("xssPreventionRule", "input");
			jpExproties.put("xssInputRule", "rejectInvalid");
			jpExproties
					.put("xssTrustedFields",
							"{\"Buildings\": \"Description,Name\", \"Parcels\": \"Name\"} ");
			jpExproties.put("allowOthersToQuery", "true");
			jpExproties.put("allowOthersToUpdate", "true");
			jpExproties.put("allowOthersToDelete", "true");

			joExService.put("properties", jpExproties);
			jsonExtensions.add(joExService);
		}
		if (extensionNameList != null) {
			JSONObject joExService = new JSONObject();
			JSONObject jpExproties = new JSONObject();
			joExService.put("typeName", "WMSServer");
			joExService.put("capabilities", "null");
			if(extensionNameList.contains("WMSServer")) {
				joExService.put("enabled", "true");
			}
			else {
				joExService.put("enabled", "false");
			}
			joExService.put("maxUploadFileSize", 0);
			joExService.put("allowedUploadFileTypes", "");

			jpExproties.put("city", "");
			jpExproties.put("individualName", "");
			jpExproties.put("abstract", "");
			jpExproties.put("fees", "");
			jpExproties.put("accessConstraints", "无");
			jpExproties.put("country", "");
			jpExproties.put("facsimile", "");
			jpExproties.put("positionName", "");
			jpExproties.put("pathToCustomGetCapabilitiesFiles", "");
			jpExproties.put("serviceType", "WMSServer");

			jpExproties.put("serviceTypeVersion", "v1.0");
			jpExproties.put("phone", "");
			jpExproties.put("keyword", "");
			jpExproties.put("postalCode", "");
			jpExproties.put("onlineResource", onlineResource);
			jpExproties.put("title", "");
			jpExproties.put("electronicMailAddress", "");
			jpExproties.put("enableTransactions", "");
			jpExproties.put("appSchemaPrefix", "");
			jpExproties.put("appSchemaURI", "");
			jpExproties.put("hoursOfService", "");

			jpExproties.put("providerSite", "");
			jpExproties.put("role", "");
			jpExproties.put("customGetCapabilities", "");
			jpExproties.put("contactInstructions", "");
			jpExproties.put("providerName", "");
			jpExproties.put("deliveryPoint", "");
			jpExproties.put("administrativeArea", "");
			jpExproties.put("name", "");

			joExService.put("properties", jpExproties);
			jsonExtensions.add(joExService);
		}

		System.out.println("jsonService.toJSONString()=="
				+ jsonService.toJSONString());

		String ip = "";
		String port = "";
		String userName = "";
		String password = "";
		if (severEngine != null) {
			ip = severEngine.getIntranetIp();
			port = severEngine.getIntranetPort() + "";
			userName = severEngine.getEngineManager();
			password = severEngine.getManagerPassword();
		}
		// 发布服务
		String msg = ServiceUtils.createService(ip, port, userName, password,
				serviceName, serviceType, folderName.equals("/") ? ""
						: folderName, jsonService.toJSONString());
		if ("success".equals(msg)) {
			resource.setStatus("1");
			map.put("flag", "1");
			map.put("msg", "发布成功！");
			logService.saveLogInfo("服务发布成功成功", LogType.Server, "服务发布成功成功", 1, map, System.currentTimeMillis()-startTime);
			String managerServiceUrl = "http://" + ip + ":" + port
					+ "/arcgis/admin/services/";
			if (StringUtils.isNotBlank(folderName) && !("/".equals(folderName))) {
				managerServiceUrl = managerServiceUrl + folderName + "/";
			}
			managerServiceUrl = managerServiceUrl + serviceName + "."
					+ serviceType;
			boolean f = ServiceUtils.startService(ip, port, userName, password, managerServiceUrl);
			System.out.println("启动服务是否成功=" + f);
			//资源发布成服务的日志记录
			
		} else {
			map.put("flag", "0");
			map.put("msg", "发布失败！");
		}
		return map;
	}
	
	/**
	 * 验证服务引擎连接情况
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getServerEngineInfo")
	@RequiresPermissions(value = "service-publish")
	public Map<String, String> getServerEngineInfo(Integer id) {
		Map<String, String> map = new HashMap<String, String>();
		if (id != null) {
			try {
				ConfigServerEngine serverEngine = configServerEngineService
						.get(ConfigServerEngine.class, id);
				map.put("configName", serverEngine.getConfigName());
				map.put("intranetIp", serverEngine.getIntranetIp());
				map.put("runningStatus", serverEngine.getRunningStatus()
						.equals("0") ? "启用" : "禁用");
			} catch (Exception e) {
				
			}
		}
		return map;
	}
	
	/**
	 * 验证服务引擎连接情况
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/toCheckServer")
	@RequiresPermissions(value = "service-publish")
	public Map<String, String> toCheckServer(Integer id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", "验证服务器连接失败！");
		map.put("nextFlag", "0");
		if (id != null) {
			try {
				ConfigServerEngine serverEngine = configServerEngineService
						.get(ConfigServerEngine.class, id);
				String url = "http://" + serverEngine.getIntranetIp() + ":"
						+ serverEngine.getIntranetPort()
						+ "/arcgis/admin/login";
				boolean ret = ArcGisServerUtils.checkServer(url,
						serverEngine.getEngineManager(),
						serverEngine.getManagerPassword());
				map.put("configName", serverEngine.getConfigName());
				map.put("intranetIp", serverEngine.getIntranetIp());
				map.put("runningStatus", serverEngine.getRunningStatus()
						.equals("0") ? "启用" : "禁用");
				if (ret) {
					map.put("msg", "验证服务器连接成功！"); // 验证成功
					map.put("nextFlag", "1"); // 可以下一步
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				map.put("nextFlag", "0");
			}
		}
		return map;
	}

	@RequestMapping("toRegister")
	@RequiresPermissions(value = "service-register")
	public String toRegister() {
		return "/service/service_register";
	}

	@RequestMapping("toRegisterGis")
	@RequiresPermissions(value = "service-register")
	public String toRegisterGis(Model model) {
		// 查找所有服务引擎
		List<ConfigServerEngine> serverEngineList = configServerEngineService
				.find("from ConfigServerEngine");
		// 远程服务类型
		model.addAttribute("remoteServicesType",
				DataDictionary.getObject("remote_services_type"));
		// 服务功能类型 service_function_type
		model.addAttribute("serviceFunctionType",
				DataDictionary.getObject("service_function_type"));
		// 服务缓存类型 service_cache_Type
		model.addAttribute("serviceCacheType",
				DataDictionary.getObject("service_cache_Type"));
		// 权限状态
		model.addAttribute("permissionStatus",
				DataDictionary.getObject("service_permission_status"));
		// 服务扩展功能类型service_extend_type
		model.addAttribute("serviceExtendType",
				DataDictionary.getObject("service_extend_type"));
		model.addAttribute("serverEngineList", serverEngineList);
		return "/service/service_register_gis";
	}
	
	@RequestMapping("morePropertySelect")
	@RequiresPermissions(value = "service-register")
	public String morePropertySelect(Model model) {
		return "/service/service_more_property";
	}

	@ResponseBody
	@RequestMapping("registerGis")
	@RequiresPermissions(value = "service-register")
	public Map<String, String> registerGis(Service s,@RequestParam(value = "imageFile", required = false) MultipartFile file,
			HttpServletRequest request, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			User user = (User) request.getSession().getAttribute(
					Global.SESSION_USER);
			//String path = request.getSession().getServletContext().getRealPath("upload");
			if (s.getServerEngine() != null
					&& StringUtils.isNotBlank(s.getShowName())) {
				if (file != null && file.getSize() > 0) {
					String fileName = file.getOriginalFilename();
					// 得到数字字典的图片类型, 再判断上传的文件是否是图片
					// Map<String, Object> mFileType =
					// DataDictionary.getObject("image_type");
					//path = path + File.separator + "service" + File.separator + "image";
					String tomcatHome = System.getProperty("catalina.home"); //D:\software1\tomcat\apache-tomcat-8.5.6
					tomcatHome = tomcatHome.substring(0, tomcatHome.lastIndexOf("\\"));
					String imagePath =  tomcatHome +File.separator + "项目文件" + File.separator + "服务";
					File targetFile = new File(imagePath, fileName);
					//判断是否存在相同的图片文件,存在则在名字后面加上数字，如xxx(2).png
					List<Service> serviceList = serviceService.find("from Service t where t.imagePath = ? ", new Object[]{targetFile.getAbsolutePath()});
					if(serviceList != null && serviceList.size() > 0) {
						String temp[] = fileName.split("\\.");
						if(temp != null && temp.length == 2) {
							fileName = temp[0]+"(" + serviceList.size() + ")." + temp[1];
							targetFile = new File(imagePath, fileName);
						}
					}
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					try {
						file.transferTo(targetFile);
						s.setImagePath(targetFile.getAbsolutePath());
					} catch (IllegalStateException | IOException e) {
						map.put("flag", "2");
						map.put("msg", "上传文件失败！");
						return map;
					}
				}
				ConfigServerEngine se = configServerEngineService.get(
						ConfigServerEngine.class,
						s.getServerEngine().getId());
				s.setServerEngine(se);
				s.setRegisterType("0");
				s.setRemoteServicesType("0");
				// 设置服务状态
				boolean seviceStatus = ServiceUtils.getStatus(
						s.getManagerServiceUrl(), se.getIntranetIp(),
						se.getIntranetPort() + "", se.getEngineManager(),
						se.getManagerPassword());
				if (seviceStatus) {
					s.setServiceStatus("1");
				}
				else {
					s.setServiceStatus("0");
				}
				s.setCreateDate(new Date());
				s.setCreator(user);
				s.setMaxVersionNum(1);
				s.setAuditStatus("0");
				serviceService.save(s);
				map.put("flag", "0");
				map.put("msg", "注册成功！");
			}
		} catch (Exception e) {
			map.put("flag", "1");
			map.put("msg", null == s.getId() ? "注册失败！" : "编辑失败！");
		}
		return map;
	}
	
	
	/**
	 * 列出服务信息（用于服务注册）
	 * 
	 * @param serverEngineId
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions(value = "service-listData")
	public Grid<Service> listData(Integer serverEngineId, PageHelper page) {
		// System.out.println("serverEngineId=" + serverEngineId);
		List<Service> list = null;
		if (serverEngineId != null) {
			ConfigServerEngine se = configServerEngineService.get(
					ConfigServerEngine.class, serverEngineId);
			// http://172.16.10.52:6080/arcgis/admin/services/
			// String url = "http://" + se.getIntranetIp() + ":" +
			// se.getIntranetPort() + "/arcgis/admin/services/";
			String userName = se.getEngineManager();
			String password = se.getManagerPassword();
			String ip = se.getIntranetIp();
			String port = se.getIntranetPort() + "";
			List<String> folders = ServiceUtils.listFolder(ip, port, userName,
					password);
			list = ServiceUtils
					.listServices(ip, port, null, userName, password);
			// 在折叠目录下的服务
			for (String f : folders) {
				list.addAll(ServiceUtils.listServices(ip, port, f, userName,
						password));
			}
		}

		return new Grid<Service>(list);
	}

	@ResponseBody
	@RequestMapping("/listService")
	@RequiresPermissions(value = "service-list")
	public Grid<Service> listService(String registerServerType,String registerName,String showName,String serviceStatus,String permissionStatus, PageHelper page) {
		List<Service> list = null;
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from Service t where 1 = 1 ");
		if (StringUtils.isNotBlank(registerServerType)) {
			hql.append("and t.registerType = ? ");
			params.add(registerServerType);
		} 
		if (StringUtils.isNotBlank(registerName)) {
			hql.append("and t.registerName like ? ");
			params.add('%' + registerName + '%');
		}
		if (StringUtils.isNotBlank(showName)) {
			hql.append("and t.showName = ? ");
			params.add('%' + showName + '%');
		} 
		if (StringUtils.isNotBlank(serviceStatus)) {
			hql.append("and t.serviceStatus = ? ");
			params.add(serviceStatus);
		} 
		if (StringUtils.isNotBlank(permissionStatus)) {
			hql.append("and t.permissionStatus = ? ");
			params.add(permissionStatus);
		} 
		list = serviceService.find(hql.toString(),params, page);
		long count = serviceService.count(hql.toString(), params);
		hql.append(" order by r.createDate desc");
		Grid<Service> g = new Grid<Service>(count,list);
		return g;
	}

	@ResponseBody
	@RequestMapping("/getServiceInfo")
	public Map<String, String> getServiceInfo(HttpServletRequest request,Integer serverEngineId,
			String folderName, String showName, String functionType) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			//String hostAddress = InetAddress.getLocalHost().getHostAddress();
			//String contextPath = request.getContextPath();
			//String serverPort = request.getServerPort() + "";
			ConfigServerEngine severEngine = configServerEngineService.get(
					ConfigServerEngine.class, serverEngineId);
			// http://172.16.10.52:6080/arcgis/admin/services/
			String ip = "";
			String port = "";
			String userName = "";
			String password = "";
			if (severEngine != null) {
				ip = severEngine.getIntranetIp();
				port = severEngine.getIntranetPort() + "";
				userName = severEngine.getEngineManager();
				password = severEngine.getManagerPassword();
				Service s = ServiceUtils.getServiceInfo(ip, port, userName,
						password, folderName, showName, functionType);
				if (s != null) {
					map.put("functionType", functionType);
					map.put("serviceExtend", s.getServiceExtend());
					String managerServiceUrl = "http://" + ip + ":" + port + "/arcgis/admin/services/";
					String serviceVisitAddress = "http://" + ip + ":" + port + "/arcgis/rest/services/";
					String ipAddress = ip + ":" + port;
					//String serviceVisitAddressOpen = "http://" + hostAddress + ":" + serverPort + contextPath + "/arcgis/rest/services/";
					//String serviceVisitAddressOpen = "/arcgis/rest/services/";
					String serviceVisitAddressOpen = "/arcgis/";
					String encodeStr = Base64Util.encode(ip + ":" + port + "/arcgis"); //base64 编码
					serviceVisitAddressOpen = serviceVisitAddressOpen + encodeStr + "/rest/services/";
					
					if (StringUtils.isNotBlank(folderName) && !("/".equals(folderName))) {
						managerServiceUrl = managerServiceUrl + folderName + "/";
						serviceVisitAddress = serviceVisitAddress + folderName + "/";
						serviceVisitAddressOpen = serviceVisitAddressOpen + folderName + "/";
					}
					managerServiceUrl = managerServiceUrl + showName + "." + functionType;
					serviceVisitAddress = serviceVisitAddress + showName + "/" + functionType;
					serviceVisitAddressOpen = serviceVisitAddressOpen +  showName + "/" + functionType;
					map.put("ipAddress", ipAddress);
					map.put("managerServiceUrl", managerServiceUrl);
					map.put("serviceVisitAddress", serviceVisitAddress);
					map.put("serviceVisitAddressOpen", serviceVisitAddressOpen);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}

	@RequestMapping("toRegisterOther")
	public String toRegisterOther(Model model) {
		// 查找所有服务引擎
		List<ConfigServerEngine> serverEngineList = configServerEngineService
				.find("from ConfigServerEngine");
		// 远程服务类型
		model.addAttribute("remoteServicesType",
				DataDictionary.getObject("remote_services_type"));
		// 服务功能类型 service_function_type
		model.addAttribute("serviceFunctionType",
				DataDictionary.getObject("service_function_type"));
		// 服务缓存类型 service_cache_Type
		model.addAttribute("serviceCacheType",
				DataDictionary.getObject("service_cache_Type"));
		// 权限状态
		model.addAttribute("permissionStatus",
				DataDictionary.getObject("service_permission_status"));
		// 服务扩展功能类型service_extend_type
		model.addAttribute("serviceExtendType",
				DataDictionary.getObject("service_extend_type"));
		model.addAttribute("serverEngineList", serverEngineList);
		return "/service/service_register_other";
	}

	/**
	 * 第三方服务注册
	 * @param s
	 * @param file
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("registerOther")
	@ResponseBody
	public Map<String, String> registerOther(Service s,@RequestParam(value = "imageFile", required = false) MultipartFile file,
			HttpServletRequest request, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			User user = (User) request.getSession().getAttribute(
					Global.SESSION_USER);
			String path = request.getSession().getServletContext()
					.getRealPath("upload");

			if (StringUtils.isNotBlank(s.getShowName())) {
				//上传文件处理
				if (file != null && file.getSize() > 0) {
					String fileName = file.getOriginalFilename();
					// 得到数字字典的图片类型, 再判断上传的文件是否是图片
					// Map<String, Object> mFileType =
					// DataDictionary.getObject("image_type");
					path = path + File.separator + "service" + File.separator
							+ "image";
					File targetFile = new File(path, fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					try {
						file.transferTo(targetFile);
						s.setImagePath(path + File.separator + fileName);
					} catch (IllegalStateException | IOException e) {
						map.put("msg", "上传文件失败！");
						return map;
					}
				}
				//设置服务状态
				/*String result = HttpClientUtils.get(s.getServiceVisitAddress() + "?f=json");
				if(StringUtils.isNotBlank(result)) {
					Map retMap = JsonMapper.getInstance().readValue(result, Map.class);
					if (retMap != null && retMap.size() > 0) {
						System.out.println("error==="+retMap.get("error"));
						if(retMap.get("error") != null && StringUtils.isNotBlank(retMap.get("error").toString())) {
							s.setServiceStatus("0");
						}
						else {
							s.setServiceStatus("1");
						}
					}
					else {
						s.setServiceStatus("0");
					}
				}
				else {
					s.setServiceStatus("0");
				}*/
				s.setServiceStatus("1");
				/*String hostAddress = InetAddress.getLocalHost().getHostAddress();
				String contextPath = request.getContextPath();
				String serverPort = request.getServerPort() + "";
				String serviceVisitAddressOpen = "http://" + hostAddress + ":" + serverPort + contextPath;
				serviceVisitAddressOpen = serviceVisitAddressOpen + getUrl(s.getServiceVisitAddress());
				*/
				String serviceVisitAddressOpen = getUrlSuffix(s.getServiceVisitAddress());
				String str2 = serviceVisitAddressOpen.substring(0, serviceVisitAddressOpen.indexOf("/"));
				String str3 = serviceVisitAddressOpen.substring(serviceVisitAddressOpen.indexOf("/"));
				
				String ipAddress = getUrlPrefix(s.getServiceVisitAddress());
				String encodeStr = Base64Util.encode(ipAddress + "/" + str2);
				serviceVisitAddressOpen = "/arcgis/" + encodeStr + str3;
				s.setIpAddress(ipAddress);
				s.setServiceVisitAddressOpen(serviceVisitAddressOpen);
				s.setRegisterType("1");
				s.setCreateDate(new Date());
				s.setCreator(user);
				s.setMaxVersionNum(1);
				s.setAuditStatus("0");
				serviceService.save(s);
				map.put("flag", "0");
				map.put("msg", "注册成功！");
			}
		} catch (Exception e) {
			map.put("flag", "1");
			map.put("msg", "注册失败！");
		}
		return map;
	}
	
	/**
	 * 得到url第一个“/”后面的内容
	 * @param url
	 * @return
	 */
	private String getUrlSuffix(String url) {
		if(StringUtils.isNotBlank(url)) {
			if(url.indexOf("//") > 0) {
				url = url.substring(url.indexOf("//") + 2);
			}
			boolean b = url.contains(":");
			if(b) {
				url = url.substring(url.indexOf(":") + 1);
			}
			url = url.substring(url.indexOf("/") + 1);
		}
		return url;
	}
	
	/**
	 * 得到url的ip和端口
	 * @param url
	 * @return
	 */
	private static String getUrlPrefix(String url) {
		if(StringUtils.isNotBlank(url)) {
			//String temp = "";
			if(url.indexOf("//") > 0) {
				//temp = url.substring(0,url.indexOf("//") + 2);
				url = url.substring(url.indexOf("//") + 2);
			}
			url = url.substring(0,url.indexOf("/"));
			//url = temp + url;
		}
		return url;
	}
	
	@RequestMapping("list")
	@RequiresPermissions(value = "service-list")
	public String list(Model model) {
		model.addAttribute("serviceStatus",
				DataDictionary.getObject("service_status"));
		model.addAttribute("permissionStatus",
				DataDictionary.getObject("service_permission_status"));
		model.addAttribute("serviceRegisterType",
				DataDictionary.getObject("service_register_type"));

		return "/service/service_list";
	}

	/**
	 * 服务启动
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("start")
	@RequiresPermissions(value = "service-start")
	public Map<String, String> start(Integer id) {
		Map<String, String> map = new HashMap<String, String>();
		boolean result = false;
		Service service = null;
		if (id != null) {
			service = serviceService.get(Service.class, id);
			String url = service.getManagerServiceUrl();
			ConfigServerEngine engine = service.getServerEngine();
			if (engine != null) {
				result = ServiceUtils.startService(engine.getIntranetIp(),
						engine.getIntranetPort() + "",
						engine.getEngineManager(), engine.getManagerPassword(),
						url);
			}
		}
		if (result) {
			map.put("flag", "0");
			map.put("msg", "启动成功！");
			service.setServiceStatus("1");
			serviceService.update(service);
			return map;
		}
		map.put("flag", "1");
		map.put("msg", "启动失败！");
		return map;
	}

	/**
	 * 服务停止
	 * 
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("stop")
	@RequiresPermissions(value = "service-stop")
	public Map<String, String> stop(Integer id) {
		Map<String, String> map = new HashMap<String, String>();
		boolean result = false;
		Service service = null;
		if (id != null) {
			service = serviceService.get(Service.class, id);
			String url = service.getManagerServiceUrl();
			ConfigServerEngine engine = service.getServerEngine();
			if (engine != null) {
				result = ServiceUtils.stopService(engine.getIntranetIp(),
						engine.getIntranetPort() + "",
						engine.getEngineManager(), engine.getManagerPassword(),
						url);
			}
		}
		if (result) {
			map.put("flag", "0");
			map.put("msg", "停止成功！");
			service.setServiceStatus("0");
			serviceService.update(service);
			return map;
		}
		map.put("flag", "1");
		map.put("msg", "停止失败！");
		return map;
	}

	@ResponseBody
	@RequestMapping("delete")
	@RequiresPermissions(value = "service-delete")
	public Map<String, String> delete(Service service) {
		Map<String, String> map = new HashMap<String, String>();
		if (service.getId() != null) {
			try {
				//判断是否被引用(后面设计成捕获异常来判断是否被引用)
				Object[] obj = new Object[] {service.getId()};
				List<Layer> layerList = layerService.find("from Layer t where t.service.id = ?", obj);
				List<LayerTheme> layerThemeList = themeService.find("from LayerTheme t where t.showService.id = ?", obj);
				List<LayerTheme> layerThemeList2 = themeService.find("from LayerTheme t where t.queryService.id = ?", obj);
				if(layerList != null && layerList.size() > 0) {
					map.put("msg", "服务被图层引用，不能删除！");
					return map;
				}
				if(layerThemeList != null && layerThemeList.size() > 0) {
					map.put("msg", "服务被专题图引用，不能删除！");
					return map;
				}
				if(layerThemeList2 != null && layerThemeList2.size() > 0) {
					map.put("msg", "服务被专题图引用，不能删除！");
					return map;
				}
				serviceService.delete(service);
				map.put("msg", "删除成功！");
			} 
			catch (Exception e) {
				map.put("msg", "删除失败！");
			}
		}
		return map;
	}

	/**
	 * 支持删除多条记录
	 * 
	 * @param idsStr
	 * @return
	 */
	@RequestMapping("deletes")
	@ResponseBody
	@RequiresPermissions(value = "service-delete")
	public Map<String, String> deletes(String idsStr) {
		Map<String, String> map = new HashMap<String, String>();
		String ids[] = idsStr.split(",");
		if (ids != null && ids.length > 0) {
			try {
				for (String id : ids) {
					Service service = serviceService.get(Service.class,
							Integer.parseInt(id));
					if (service != null) {
						Object[] obj = new Object[] {service.getId()};
						List<Layer> layerList = layerService.find("from Layer t where t.service.id = ?", obj);
						List<LayerTheme> layerThemeList = themeService.find("from LayerTheme t where t.service.id = ?", obj);
						if(layerList != null && layerList.size() > 0) {
							map.put("msg", "服务被图层引用，不能删除！");
							return map;
						}
						if(layerThemeList != null && layerThemeList.size() > 0) {
							map.put("msg", "服务被专题图引用，不能删除！");
							return map;
						}
						serviceService.delete(service);
					}
				}
				map.put("msg", "删除成功！");
				return map;
			} catch (Exception e) {
				map.put("msg", "删除失败！");
				return map;
			}
		} else {
			map.put("msg", "删除失败！");
		}
		return map;
	}

	@RequestMapping("toEditGis")
	@RequiresPermissions(value = "service-edit")
	public String toEditGis(Service service, Model model) {
		if (service.getId() != null) {
			service = serviceService.get(Service.class, service.getId());
			model.addAttribute("service", service);
		}
		// 查找所有服务引擎
		List<ConfigServerEngine> serverEngineList = configServerEngineService
				.find("from ConfigServerEngine");
		// 远程服务类型
		model.addAttribute("remoteServicesType",
				DataDictionary.getObject("remote_services_type"));
		// 服务功能类型 service_function_type
		model.addAttribute("serviceFunctionType",
				DataDictionary.getObject("service_function_type"));
		// 服务缓存类型 service_cache_Type
		model.addAttribute("serviceCacheType",
				DataDictionary.getObject("service_cache_Type"));
		// 权限状态
		model.addAttribute("permissionStatus",
				DataDictionary.getObject("service_permission_status"));
		// 服务扩展功能类型service_extend_type
		model.addAttribute("serviceExtendType",
				DataDictionary.getObject("service_extend_type"));
		model.addAttribute("serverEngineList", serverEngineList);
		return "/service/service_gis_edit";
	}
	
	@ResponseBody
	@RequestMapping("updateService")
	@RequiresPermissions(value = "service-edit")
	public Map<String, String> updateService(Service s,@RequestParam(value = "imageFile", required = false) MultipartFile file,
			HttpServletRequest request, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			User user = (User) request.getSession().getAttribute(
					Global.SESSION_USER);
			String path = request.getSession().getServletContext()
					.getRealPath("upload");

			if (StringUtils.isNotBlank(s.getShowName())) {
				if (file != null && file.getSize() > 0) {
					String fileName = file.getOriginalFilename();
					// 得到数字字典的图片类型, 再判断上传的文件是否是图片
					// Map<String, Object> mFileType =
					// DataDictionary.getObject("image_type");
					path = path + File.separator + "service" + File.separator
							+ "image";
					File targetFile = new File(path, fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					try {
						file.transferTo(targetFile);
						s.setImagePath(path + File.separator + fileName);
					} catch (IllegalStateException | IOException e) {
						map.put("flag", "2");
						map.put("msg", "上传文件失败！");
						return map;
					}
				}
				
				// 修改
				Service dbService = serviceService.get(Service.class,
						s.getId());
				BeanExtUtils.copyProperties(dbService, s, true, true, null);
				dbService.setRegisterType("0"); //为gis注册
				dbService.setUpdateDate(new Date());
				dbService.setUpdator(user);
				serviceService.update(dbService);
				map.put("flag", "1");
				map.put("msg", "编辑成功！");

			}
		} catch (Exception e) {
			map.put("flag", "3");
			map.put("msg", "编辑失败！");
		}
		return map;
	}
	
	@RequestMapping("toEditOther")
	@RequiresPermissions(value = "service-edit")
	public String toEditOther(Service service, Model model) {
		if (service.getId() != null) {
			service = serviceService.get(Service.class, service.getId());
			model.addAttribute("service", service);
		}
		// 远程服务类型
		model.addAttribute("remoteServicesType",
				DataDictionary.getObject("remote_services_type"));
		// 服务功能类型 service_function_type
		model.addAttribute("serviceFunctionType",
				DataDictionary.getObject("service_function_type"));
		// 服务缓存类型 service_cache_Type
		model.addAttribute("serviceCacheType",
				DataDictionary.getObject("service_cache_Type"));
		// 权限状态
		model.addAttribute("permissionStatus",
				DataDictionary.getObject("service_permission_status"));
		// 服务扩展功能类型service_extend_type
		model.addAttribute("serviceExtendType",
				DataDictionary.getObject("service_extend_type"));
		return "/service/service_other_edit";
	}
	
	@ResponseBody
	@RequestMapping("updateServiceOther")
	@RequiresPermissions(value = "service-edit")
	public Map<String, String> updateServiceOther(Service s,@RequestParam(value = "imageFile", required = false) MultipartFile file,
			HttpServletRequest request, Model model) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			User user = (User) request.getSession().getAttribute(
					Global.SESSION_USER);
			String path = request.getSession().getServletContext()
					.getRealPath("upload");

			if (StringUtils.isNotBlank(s.getShowName())) {
				if (file != null && file.getSize() > 0) {
					String fileName = file.getOriginalFilename();
					// 得到数字字典的图片类型, 再判断上传的文件是否是图片
					// Map<String, Object> mFileType =
					// DataDictionary.getObject("image_type");
					path = path + File.separator + "service" + File.separator
							+ "image";
					File targetFile = new File(path, fileName);
					if (!targetFile.exists()) {
						targetFile.mkdirs();
					}
					try {
						file.transferTo(targetFile);
						s.setImagePath(path + File.separator + fileName);
					} catch (IllegalStateException | IOException e) {
						// TODO Auto-generated catch block
						map.put("msg", "上传文件失败！");
						return map;
					}
				}
				Service dbService = serviceService.get(Service.class,
						s.getId());
				BeanExtUtils.copyProperties(dbService, s, true, true, null);
				
				/*String hostAddress = InetAddress.getLocalHost().getHostAddress();
				String contextPath = request.getContextPath();
				String serverPort = request.getServerPort() + "";
				String serviceVisitAddressOpen = "http://" + hostAddress + ":" + serverPort + contextPath;
				serviceVisitAddressOpen = serviceVisitAddressOpen + getUrlSuffix(s.getServiceVisitAddress());*/
				//String serviceVisitAddressOpen = getUrlSuffix(s.getServiceVisitAddress());
				
				String serviceVisitAddressOpen = getUrlSuffix(s.getServiceVisitAddress());
				String str2 = serviceVisitAddressOpen.substring(0, serviceVisitAddressOpen.indexOf("/"));
				String str3 = serviceVisitAddressOpen.substring(serviceVisitAddressOpen.indexOf("/"));
				
				String ipAddress = getUrlPrefix(s.getServiceVisitAddress());
				String encodeStr = Base64Util.encode(ipAddress + str2);
				serviceVisitAddressOpen = "/arcgis/" + encodeStr + str3;
				
				dbService.setServiceVisitAddressOpen(serviceVisitAddressOpen);
				dbService.setIpAddress(ipAddress);
				dbService.setUpdateDate(new Date());
				dbService.setUpdator(user);
				serviceService.update(dbService);
				map.put("flag", "0");
				map.put("msg", "编辑成功！");
			}
		} catch (Exception e) {
			map.put("flag", "1");
			map.put("msg", "编辑失败！");
		}
		return map;
	}
	
	


	@RequestMapping("toRefreshVersion")
	@RequiresPermissions(value = "service-refresh-version")
	public String toEdit(Service service, Model model) {
		if (service.getId() != null) {
			service = serviceService.get(Service.class, service.getId());
			model.addAttribute("service", service);
		}
		return "/service/service_refresh_version";
	}

	/**
	 * 保存版本刷新
	 * 
	 * @param service
	 * @return
	 */
	@ResponseBody
	@RequestMapping("saveVersion")
	@RequiresPermissions(value = "service-refresh-version")
	public Map<String, String> saveVersion(Service service) {
		Map<String, String> map = new HashMap<String, String>();
		if (service.getId() != null) {
			try {
				Service dbService = serviceService.get(Service.class,
						service.getId());
				String remark = service.getVersiomnRemarks();
				dbService.setVersiomnRemarks(remark);
				dbService.setMaxVersionNum(dbService.getMaxVersionNum() + 1);
				serviceService.update(dbService);
				map.put("msg", "更新版本成功！");
			} catch (Exception e) {
				map.put("msg", "更新版本失败！");
			}
		}
		return map;
	}

	/**
	 * 把所有资源分类转成json字符串
	 */
	@ResponseBody
	@RequestMapping("listServiceType")
	@RequiresPermissions(value = "service-type-list")
	public String listServiceType(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String, Object> registerTypeMap = DataDictionary
				.getObject("service_register_type");
		registerTypeMap.entrySet();
		Map<String, Object> map = Maps.newHashMap();
		String rootId = UUID.randomUUID().toString();
		map.put("id", rootId);
		map.put("pid", "");
		map.put("text", "服务分类");
		mapList.add(map);
		for (Map.Entry<String, Object> entry : registerTypeMap.entrySet()) {
			map = Maps.newHashMap();
			// String key = entry.getKey();
			DictionaryItem value = (DictionaryItem) entry.getValue();
			map.put("id", value.getValue());
			map.put("pid", rootId);
			map.put("text", value.getName());
			mapList.add(map);
		}
		String jsonStr = JsonMapper.toJsonString(mapList);
		return jsonStr;
	}

	@RequestMapping("auditRegisterList")
	public String auditRegisterList(Model model) {
		model.addAttribute("auditStatus", DataDictionary.getObject("audit_status"));
		model.addAttribute("serviceStatus",DataDictionary.getObject("service_status"));
		model.addAttribute("permissionStatus",DataDictionary.getObject("service_permission_status"));
		model.addAttribute("serviceRegisterType",DataDictionary.getObject("service_register_type"));
		return "/service/service_audit_register_list";
	}
	
	@ResponseBody
	@RequestMapping("/listAuditService")
	@RequiresPermissions(value = "service-auditRegister")
	public Grid<Service> listAuditService(String registerServerType,String registerName,String showName,String auditStatus,String serviceStatus,String permissionStatus, PageHelper page) {
		List<Service> list = null;
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from Service t where 1 = 1 ");
		if (StringUtils.isNotBlank(registerServerType)) {
			hql.append("and t.registerType = ? ");
			params.add(registerServerType);
		} 
		if (StringUtils.isNotBlank(registerName)) {
			hql.append("and t.registerName like ? ");
			params.add('%' + registerName + '%');
		}
		if (StringUtils.isNotBlank(showName)) {
			hql.append("and t.showName = ? ");
			params.add('%' + showName + '%');
		} 
		
		if (StringUtils.isNotBlank(serviceStatus)) {
			hql.append("and t.serviceStatus = ? ");
			params.add(serviceStatus);
		} 
		
		if (StringUtils.isNotBlank(auditStatus)) {
			hql.append("and t.auditStatus = ? ");
			params.add(auditStatus);
		} 
		
		if (StringUtils.isNotBlank(permissionStatus)) {
			hql.append("and t.permissionStatus = ? ");
			params.add(permissionStatus);
		} 
		list = serviceService.find(hql.toString(),params, page);
		long count = serviceService.count(hql.toString(), params);
		hql.append(" order by r.createDate desc ");
		Grid<Service> g = new Grid<Service>(count,list);
		return g;
	}
	
	@RequestMapping("toAuditRegister")
	public String toAuditRegister(Service service,Model model) {
		if(null != service.getId()) {
			service = serviceService.get(Service.class, service.getId());
			model.addAttribute("auditStatus", DataDictionary.getObject("audit_status"));
			model.addAttribute("service", service);
			//设置注册类型
			model.addAttribute("regType", DataDictionary.getObject("service_register_type"));
		}
		return "/service/service_audit_register";
	}
	
	@RequestMapping("auditRegister")
	@ResponseBody
	public Map<String,String> auditRegister(Service service, Model model,
			HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			Service dbObj = serviceService.get(Service.class, service.getId());
			BeanExtUtils.copyProperties(dbObj, service, true, true,null);
			User user = (User) request.getSession().getAttribute(
					Global.SESSION_USER);
			dbObj.setAuditor(user);
			dbObj.setAuditDate(new Date());
			serviceService.update(dbObj);
			map.put("flag", "0");
			map.put("msg", "审核成功！");
		} catch (Exception e) {
			map.put("flag", "1");
			map.put("msg", "审核失败！");
		} 
		
		return map;
	}

	@RequestMapping("auditUseList")
	public String auditUseList() {
		return "/service/service_audit_use_list";
	}

	@RequestMapping("toImportFile")
	public String toImportFile() {
		return "/service/service_import";
	}
	
	/*
	 * 下载模版
	 * @param request
	 * @param response
	 * @return
	 */
    @ResponseBody
    @RequestMapping("/downLoadTemplate")
    public String downLoadTemplate(HttpServletRequest request,HttpServletResponse response){
		try {
			//文件名称
			String fileName = "服务导入模版";
			//防止乱码
			if (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0){
				fileName = URLEncoder.encode(fileName,"UTF-8");
				fileName = fileName.replace(".", "%2e");
			}else if(request.getHeader("USER-AGENT").toLowerCase().indexOf("firefox") > 0){
				fileName="=?UTF-8?B?"+(new String(Base64.encodeBase64(fileName.getBytes("UTF-8"))))+"?=";
			}else{
				fileName = URLEncoder.encode(fileName,"UTF-8");
			}
			InputStream in = ServiceController.class.getResourceAsStream(DOWNLOADURL);
			//InputStream in = ServiceController.class.getResource(DOWNLOADURL).openStream();
			DataInputStream din = new DataInputStream(new BufferedInputStream(in));
			OutputStream out = response.getOutputStream();
			DataOutputStream dout = new DataOutputStream(new BufferedOutputStream(out));
			response.reset();
			response.setContentType("application/zip"); //zip格式的压缩包
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment;filename=" +fileName+".zip");
			
			int n;
			byte buf[] = new byte[8192];
			while ((n = din.read(buf)) != -1) {
				dout.write(buf, 0, n);
			}
			dout.flush();
			dout.close();
			din.close();
			in.close();
		} catch (Exception e) {
			return "文件下载出错";
		}
		return "success";
	}
	
	/**
	 * 导入服务
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/importFile")
	public Map<String, String> importFile(MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		String houzui = file.getOriginalFilename().substring(
				file.getOriginalFilename().lastIndexOf(".") + 1);
		if (!"zip".equals(houzui)) {
			map.put("flag", "1");
			map.put("msg", "请选择zip格式的数据！");
			return map;
		}
		long count = 0;
		long sum = 0;
		// 有上传文件，则进行导入操作
		if (file != null && file.getSize() > 0) {
			String tempPath = request.getSession().getServletContext()
					.getRealPath("temp");
			File targetFile = new File(tempPath, file.getOriginalFilename());
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				file.transferTo(targetFile);
				// 将文件解压到临时文件夹
				FileUtils.unZipFiles(
						tempPath + File.separator + file.getOriginalFilename(),
						tempPath);
				String fileName = file.getOriginalFilename().substring(0,
						file.getOriginalFilename().lastIndexOf("."));
				File tempFile = new File(tempPath, fileName);
				File[] fileArr = tempFile.listFiles();
				for (int i = 0; i < fileArr.length; i++) {
					System.out.println("fileNmae=" + fileArr[i].getName());
					if (fileArr[i].getName().endsWith(".xls")) {
						try {
							InputStream in = new FileInputStream(fileArr[i]);
							List<ArrayList<String>> datas = getFileDatas(in,
									fileArr[i].getName());
							//System.out.println("datas size=" + datas.size());
							sum = datas.size();
							for (int j = 0; j < datas.size(); j++) {
								Service s = new Service();
								ArrayList<String> serviceInfo = datas.get(j);								
								String registerName = serviceInfo.get(0);
								String showName = serviceInfo.get(1);
								String engineName = serviceInfo.get(2);
								List<ConfigServerEngine> list = configServerEngineService
										.find("from ConfigServerEngine t where t.configName = ?",
												new Object[] { engineName });
								ConfigServerEngine engine = null;
								if (list != null && list.size() > 0) {
									engine = list.get(0);
									//String cName = list.get(0).getConfigName();
									s.setServerEngine(list.get(0));
								}
								String folderName = serviceInfo.get(3);
								String remarks = serviceInfo.get(4);
								String functionType = serviceInfo.get(5);
								String serviceExtend = serviceInfo.get(6);
								String cacheType = serviceInfo.get(7);
								String permissionStatus = serviceInfo.get(8);
								String serviceVisitAddress = serviceInfo.get(9);
								String imageName = serviceInfo.get(10);
								String metadataVisitAddress = serviceInfo.get(11);
								String registerType = serviceInfo.get(12);
								String remoteType = serviceInfo.get(13);
								
								if(StringUtils.isNotBlank(registerName) && registerName.replaceAll("[\u4e00-\u9fa5]", "xx").length()>30) {
									continue;
								}
								if(StringUtils.isBlank(registerName)) {
									continue;
								}
								
								if(StringUtils.isNotBlank(showName) && showName.replaceAll("[\u4e00-\u9fa5]", "xx").length()>50) {
									continue;
								}
								if(StringUtils.isBlank(showName)) {
									continue;
								}
								s.setRegisterName(registerName);
								s.setShowName(showName);
								String managerServiceUrl = "";
								if (StringUtils.isBlank(folderName)) {
									s.setFolderName("/");
								} else {
									s.setFolderName(folderName);
									managerServiceUrl = "http://"
											+ engine.getIntranetIp() + ":"
											+ engine.getIntranetPort()
											+ "/arcgis/admin/services/";
									if (StringUtils.isNotBlank(folderName)) {
										managerServiceUrl = managerServiceUrl
												+ folderName + "/";
									}
									managerServiceUrl = managerServiceUrl
											+ showName + "." + functionType;
								}
								s.setRemarks(remarks);

								// 设置权限状态
								Map<String, Object> permiMap = DataDictionary
										.getObject("service_permission_status");
								for (Entry<String, Object> h : permiMap
										.entrySet()) {
									DictionaryItem value = (DictionaryItem) h
											.getValue();
									if (value.getName()
											.equals(permissionStatus)) {
										s.setPermissionStatus(value.getValue());
										break;
									}

								}
								
								s.setFunctionType(functionType);
								// 设置缓存类型
								Map<String, Object> cacheMap = DataDictionary
										.getObject("service_cache_Type");
								for (Entry<String, Object> h : cacheMap
										.entrySet()) {
									DictionaryItem value = (DictionaryItem) h
											.getValue();
									if (value.getName().equals(cacheType)) {
										s.setCacheType(value.getValue());
										break;
									}

								}

								s.setServiceExtend(serviceExtend);

								s.setServiceVisitAddress(serviceVisitAddress);

								s.setManagerServiceUrl(managerServiceUrl);

								// 服务有上传图片的处理
								if (StringUtils.isNotBlank(imageName)) {
									String imagePath = tempPath
											+ File.separator + fileName;
									File imageFile = new File(imagePath,
											imageName);
									if (imageFile.exists()) {
										String descImagePath = request
												.getSession()
												.getServletContext()
												.getRealPath("upload");
										String descPath = descImagePath
												+ File.separator + "service"
												+ File.separator + "image";
										File targetFile1 = new File(descPath,
												imageName);
										if (!targetFile1.exists()) {
											targetFile1.mkdirs();
										}
										boolean uploadimage = FileUtils
												.copyFileCover(
														imageFile
																.getAbsolutePath(),
														targetFile1
																.getAbsolutePath(),
														true);
										if (uploadimage) {
											s.setImagePath(targetFile1
													.getAbsolutePath());
										}
									}
								}
								s.setMetadataVisitAddress(metadataVisitAddress);
								
								//设置注册类型
								Map<String, Object> regTypeMap = DataDictionary
										.getObject("service_register_type");
								for (Entry<String, Object> h : regTypeMap
										.entrySet()) {
									DictionaryItem value = (DictionaryItem) h
											.getValue();
									if (value.getName().equals(registerType)) {
										s.setRegisterType(value.getValue());										
										break;
									}

								}
								
								//设置远程服务类型
								Map<String, Object> remoteTypeMap = DataDictionary
										.getObject("remote_services_type");
								for (Entry<String, Object> h : remoteTypeMap
										.entrySet()) {
									DictionaryItem value = (DictionaryItem) h
											.getValue();
									if (value.getName().equals(remoteType)) {
										s.setRemoteServicesType(value.getValue());
										break;
									}

								}
								
								s.setMaxVersionNum(1);
								s.setAuditStatus("0");
								s.setServiceStatus("0");
								s.setCreateDate(new Date());
								s.setCreator((User) request.getSession()
										.getAttribute(Global.SESSION_USER));
								serviceService.save(s);
								count++;
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 操作完把临时文件的数据删除
			FileUtils.deleteDirectory(tempPath);
		}

		map.put("msg", "一共【" + sum + "】条数据，成功导入【" + count + "】条数据");
		return map;
	}

	/**
	 * 导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	// @ResponseBody
	@RequestMapping("export")
	@RequiresPermissions(value = "service-import")
	public void export(String idsStr,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		String ids[] = idsStr.split(",");
		if (ids != null && ids.length > 0) {
			List<Service> list = new ArrayList<Service>();
			for (String id : ids) {
				Service service = serviceService.get(Service.class,
						Integer.parseInt(id));
				if (service != null) {
					// 导出
					list.add(service);
				}
			}
			String[] head = { "序号", "服务注册名称", "服务显示名称","服务引擎名字", "服务所有目录",
					"服务描述", "服务功能类型", "拓展功能类型", "服务缓存", "服务权限类型", "服务访问地址",
					"服务缩略图名字", "元数据访问地址", "服务注册类型","远程服务类型" };
			createExcel(list, head, request, response);
			map.put("msg", "删除成功！");
			//return map;
		} else {
			map.put("msg", "导出失败！");
		}
		//return map;
	}

	private void createExcel(List<Service> list, String head[],
			HttpServletRequest request, HttpServletResponse response) {
		// 创建一个webbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 在webbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet("导出数据");
		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow((short) 0);
		row.setHeight((short) 400);

		HSSFFont font = wb.createFont();
		font.setFontName("宋体");
		font.setFontHeight((short) 180);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

		// 标题样式
		HSSFCellStyle headStyle = wb.createCellStyle();
		headStyle.setFont(font);
		headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		// headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 边框
		headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		headStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		headStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		headStyle.setRightBorderColor(HSSFColor.BLACK.index);
		headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		headStyle.setTopBorderColor(HSSFColor.BLACK.index);

		// 数据样式
		HSSFCellStyle dataStyle = wb.createCellStyle();
		dataStyle.setWrapText(true);
		dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

		// 边框
		dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		dataStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		dataStyle.setLeftBorderColor(HSSFColor.BLACK.index);
		dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		dataStyle.setRightBorderColor(HSSFColor.BLACK.index);
		dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		dataStyle.setTopBorderColor(HSSFColor.BLACK.index);

		sheet.setColumnWidth(0, 1500);// 序号
		sheet.setColumnWidth(1, 5000);// 服务注册名称
		sheet.setColumnWidth(2, 8000);// 服务显示名称
		sheet.setColumnWidth(3, 5000);// 服务引擎名字
		sheet.setColumnWidth(4, 5000);// 服务所有目录
		sheet.setColumnWidth(5, 8000);// 服务描述
		sheet.setColumnWidth(6, 4000);// 服务功能类型
		sheet.setColumnWidth(7, 3000);// 拓展功能类型
		sheet.setColumnWidth(8, 4000);// 服务缓存
		sheet.setColumnWidth(9, 3500);// 服务权限类型
		sheet.setColumnWidth(10, 3500);// 服务访问地址
		sheet.setColumnWidth(11, 3500);// 服务缩略图名字
		sheet.setColumnWidth(12, 3500);// 元数据访问地址
		sheet.setColumnWidth(13, 3500);// 服务注册类型
		sheet.setColumnWidth(14, 3500);// 远程服务类型

		// 标题
		for (int i = 0; i < head.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(head[i]);
			cell.setCellStyle(headStyle);
		}

		int sheetCount = 1;// Excel文件中的工作簿的个数
		int rowIndex = 1;// 当前行
		for (int i = 0; i < list.size(); i++) {
			// 超出10000条数据 创建新的工作簿
			if ((i + 1) % 10000 == 0) {
				rowIndex = 1;
				sheetCount++;
				sheet = wb.createSheet("sheet" + sheetCount);

				sheet.setColumnWidth(0, 1500);// 序号
				sheet.setColumnWidth(1, 5000);// 服务注册名称
				sheet.setColumnWidth(2, 8000);// 服务显示名称
				sheet.setColumnWidth(3, 5000);// 服务引擎名字
				sheet.setColumnWidth(4, 5000);// 服务所有目录
				sheet.setColumnWidth(5, 8000);// 服务描述
				sheet.setColumnWidth(6, 4000);// 服务功能类型
				sheet.setColumnWidth(7, 3000);// 拓展功能类型
				sheet.setColumnWidth(8, 4000);// 服务缓存
				sheet.setColumnWidth(9, 3500);// 服务权限类型
				sheet.setColumnWidth(10, 3500);// 服务访问地址
				sheet.setColumnWidth(11, 3500);// 服务缩略图名字
				sheet.setColumnWidth(12, 3500);// 元数据访问地址
				sheet.setColumnWidth(13, 3500);// 服务注册类型
				sheet.setColumnWidth(14, 3500);// 远程服务类型
				
				HSSFRow row1 = sheet.createRow((short) 0);
				row1.setHeight((short) 400);

				// 标题
				for (int n = 0; n < head.length; n++) {
					HSSFCell cell = row1.createCell(n);
					cell.setCellValue(head[n]);
					cell.setCellStyle(headStyle);
				}
			}

			Service s = list.get(i);
			HSSFRow row1 = sheet.createRow((short) rowIndex);

			String[] rowValues = new String[15];
			rowValues[0] = (i + 1) + "";			
			rowValues[1] = s.getRegisterName();
			rowValues[2] = s.getShowName();
			rowValues[3] = s.getServerEngine() != null ? s.getServerEngine()
					.getConfigName() : "";
			rowValues[4] = s.getFolderName();
			rowValues[5] = s.getRemarks();
			rowValues[6] = s.getFunctionType();
			rowValues[7] = s.getServiceExtend();
			rowValues[8] = "";
			// 设置缓存类型
			Map<String, Object> cacheMap = DataDictionary
					.getObject("service_cache_Type");
			for (Entry<String, Object> h : cacheMap
					.entrySet()) {
				DictionaryItem value = (DictionaryItem) h
						.getValue();
				if (value.getValue().equals(s.getCacheType())) {
					rowValues[8] = value.getName();
					break;
				}

			}
			
			rowValues[9] = "";
			// 设置权限状态
			Map<String, Object> permiMap = DataDictionary
					.getObject("service_permission_status");
			for (Entry<String, Object> h : permiMap
					.entrySet()) {
				DictionaryItem value = (DictionaryItem) h
						.getValue();
				if (value.getValue()
						.equals(s.getPermissionStatus())) {
					rowValues[9] = value.getName();
					break;
				}

			}
			
			rowValues[10] = s.getServiceVisitAddress();
			rowValues[11] = "";
			if(StringUtils.isNotBlank(s.getImagePath())) {
				rowValues[11] = s.getImagePath().substring(s.getImagePath().lastIndexOf("\\") + 1);
				String realPath = request.getSession().getServletContext()
						.getRealPath("/");
				File outFile = new File(realPath + "temp" + File.separator + "serviceInfo");
				if (!outFile.exists()) {
					outFile.mkdirs();
				}
				FileUtils.copyFile(s.getImagePath(), realPath + "temp" + File.separator + "serviceInfo" + File.separator + rowValues[11]);
			}
			rowValues[12] = s.getMetadataVisitAddress();
			
			rowValues[13] = "";
			// 设置注册类型
			Map<String, Object> reTypeMap = DataDictionary
					.getObject("service_register_type");
			for (Entry<String, Object> h : reTypeMap
					.entrySet()) {
				DictionaryItem value = (DictionaryItem) h
						.getValue();
				if (value.getValue()
						.equals(s.getPermissionStatus())) {
					rowValues[13] = value.getName();
					break;
				}

			}

			rowValues[14] = "";
			// 设置远程服务类型
			Map<String, Object> remoteTypeMap = DataDictionary
					.getObject("remote_services_type");
			for (Entry<String, Object> h : remoteTypeMap
					.entrySet()) {
				DictionaryItem value = (DictionaryItem) h
						.getValue();
				if (value.getValue()
						.equals(s.getRemoteServicesType())) {
					rowValues[14] = value.getName();
					break;
				}

			}
			
			// 写数据
			for (int j = 0; j < rowValues.length; j++) {
				HSSFCell cell = row1.createCell(j);
				cell.setCellValue(rowValues[j]);
				cell.setCellStyle(dataStyle);
			}
			rowIndex++;
		}

		try {
			String realPath = request.getSession().getServletContext()
					.getRealPath("/");
			File outFile = new File(realPath + "temp" + File.separator + "serviceInfo");
			if (!outFile.exists()) {
				outFile.mkdirs();
			}
			//下载设置
			File file = new File(realPath + "temp" + File.separator + "serviceInfo" + File.separator + "服务信息.xls");
			FileOutputStream fout = new FileOutputStream(file);
			wb.write(fout); //写数据到serviceInfo.xls
			fout.close();
			
			//压缩文件
			String tempZipPath = realPath + "temp" + File.separator + "serviceInfos" + File.separator  + "tempZipFile.zip";
			//创建一个临时的压缩文件
			FileUtils.createFile(tempZipPath);
			//压缩文件操作
			FileUtils.zipFiles(realPath + "temp" + File.separator + "serviceInfo", "*",tempZipPath);
			
			String filename = "服务信息";
			if (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0) {
				filename = URLEncoder.encode(filename, "UTF-8");
				filename = filename.replace(".", "%2e");
			} else if (request.getHeader("USER-AGENT").toLowerCase()
					.indexOf("firefox") > 0) {
				filename = "=?UTF-8?B?"
						+ (new String(Base64.encodeBase64(filename
								.getBytes("UTF-8")))) + "?=";
			} else {
				filename = URLEncoder.encode(filename, "UTF-8");
			}

			
			InputStream in = new FileInputStream(tempZipPath);
			DataInputStream din = new DataInputStream(new BufferedInputStream(
					in));
			OutputStream out = response.getOutputStream();
			DataOutputStream dout = new DataOutputStream(
					new BufferedOutputStream(out));
			response.reset();
			response.setContentType("application/zip");
			response.setContentType("application/x-download");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ filename + ".zip");

			int n;
			byte buf[] = new byte[8192];
			while ((n = din.read(buf)) != -1) {
				dout.write(buf, 0, n);
			}
			dout.flush();
			dout.close();
			din.close();
			in.close();
			// 删除文件
			FileUtils.deleteDirectory(new File(realPath + "temp"));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 查看详情
	 * 
	 * @param resource
	 * @param model
	 * @return
	 */
	@RequestMapping("view")
	@RequiresPermissions(value = "resource-view")
	public String view(Service service, Model model) {
		if (service.getId() != null) {
			service = serviceService.get(Service.class, service.getId());
			model.addAttribute("service", service);
			// 远程服务类型
			model.addAttribute("remoteServicesType",DataDictionary.getObject("remote_services_type"));
			// 服务功能类型 service_function_type
			model.addAttribute("serviceFunctionType",DataDictionary.getObject("service_function_type"));
			// 服务缓存类型 service_cache_Type
			model.addAttribute("serviceCacheType",DataDictionary.getObject("service_cache_Type"));
			// 权限状态
			model.addAttribute("permissionStatus",DataDictionary.getObject("service_permission_status"));
			// 服务扩展功能类型service_extend_type
			model.addAttribute("serviceExtendType",DataDictionary.getObject("service_extend_type"));
			//服务注册类型 
			model.addAttribute("registerType",DataDictionary.getObject("service_register_type"));
		}
		return "/service/service_view";
	}
	
	/**
	 * 选择服务
	 * @param flag(1:图层选择服务；2：专题图选择服务)
	 * @param model
	 * @return
	 */
	@RequestMapping("toSelectService")
	@RequiresPermissions(value = "service-select")
	public String toSelectService(String flag,Model model) {
		model.addAttribute("serviceStatus",
				DataDictionary.getObject("service_status"));
		model.addAttribute("permissionStatus",
				DataDictionary.getObject("service_permission_status"));
		model.addAttribute("serviceRegisterType",
				DataDictionary.getObject("service_register_type"));
		model.addAttribute("flag", flag);
		return "/service/service_select";
	}
	
	/**
	 * 选择服务的图层
	 * @param service
	 * @param model
	 * @return
	 */
	@RequestMapping("toSelectLayer")
	@RequiresPermissions(value = "service-layer-select")
	public String toSelectLayer(Service service, Model model) {
		model.addAttribute("serviceVisitAddress",service.getServiceVisitAddress());
		model.addAttribute("serviceId",service.getId());
		return "/service/service_select_layer";
	}
	
	@ResponseBody
	@RequestMapping("/listLayer")
	@RequiresPermissions(value = "service-layer-select")
	public Grid<Layer> listLayer(Service service,String serviceVisitAddress, PageHelper page) {
		service = serviceService.get(Service.class, service.getId());
		List<Layer> layers = ServiceUtils.getLayers(service.getServiceVisitAddress());
		Grid<Layer> g = new Grid<Layer>(layers);
		return g;
	}
}
