package com.ycsys.smartmap.service.controller;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.ycsys.smartmap.resource.entity.ResourceType;
import com.ycsys.smartmap.resource.service.ResourceService;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.service.utils.ServiceUtils;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.utils.ArcGisServerUtils;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.JsonMapper;
import com.ycsys.smartmap.sys.common.utils.StringUtils;
import com.ycsys.smartmap.sys.entity.ConfigServerEngine;
import com.ycsys.smartmap.sys.entity.DictionaryItem;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.ConfigServerEngineService;
import com.ycsys.smartmap.sys.util.DataDictionary;

/**
 * service controller
 * @author liweixiong
 * @date   2016年11月3日
 */
@Controller
@RequestMapping("/service")
public class ServiceController {
	@Autowired
	private ServiceService serviceService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private ConfigServerEngineService configServerEngineService;
	
	/**
	 * 准备发布服务
	 * @param model
	 * @return
	 */
	@RequestMapping("toPublish")
	public String toPublish(Model model) {
		//查找所有服务引擎
		List<ConfigServerEngine> serverEngineList = configServerEngineService.find("from ConfigServerEngine");
		model.addAttribute("serverEngineList", serverEngineList);
		model.addAttribute("clusterNames", ClusterUtils.lists());
		//Map<String, Object> i = DataDictionary.getObject("service_type");
		//服务发布目录（即是服务在那个folder下）
		model.addAttribute("listFolder", ServiceUtils.listFolder("http://172.16.10.52:6080/arcgis/admin/services","siteadmin","ld@yc2016"));
		model.addAttribute("serviceTypes", DataDictionary.getObject("service_type"));
		return "/service/service_publish";
	}
	
	/**
	 * 选择资源
	 * @param model
	 * @return
	 */
	@RequestMapping("viewResource")
	public String viewResource(Model model) {
		
		return "/service/service_select_resource";
	}
	
	@RequestMapping("publish")
	@ResponseBody
	public Map<String,String> publish(HttpServletRequest request,Model model) {
		Map<String,String> map = new HashMap<String, String>();
		String serviceName = request.getParameter("serviceName");
		String serviceResource = request.getParameter("serviceResource");
		String serviceType = request.getParameter("serviceType");
		String resourceFile = request.getParameter("resourceFile");
		String resourceFileId = request.getParameter("resourceFileId");
		String clusterName = request.getParameter("clusterName");
		String folderName = request.getParameter("folderName");
		String[] extensionName = request.getParameterValues("extensionName");
		List<String> extensionNameList = Arrays.asList(extensionName);
		Resource resource = resourceService.get(Resource.class, Integer.parseInt(resourceFileId));
		String serverEngineId = request.getParameter("serverEngine");
		System.out.println("serverEngine="+serverEngineId);
		ConfigServerEngine severEngine = configServerEngineService.get(ConfigServerEngine.class, Integer.parseInt(serverEngineId));
		if(StringUtils.isNotBlank(resourceFileId)) {
			resource = resourceService.get(Resource.class, Integer.parseInt(resourceFileId));
		}
		
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
		jsonProperties.put("virtualCacheDir", "");
		jsonProperties.put("maxImageHeight", "2048");
		jsonProperties.put("maxRecordCount", "1000");
		jsonProperties.put("filePath", resource.getFilePath());
		jsonProperties.put("maxImageWidth", "2048");
		jsonProperties.put("cacheOnDemand", "false");
		jsonProperties.put("virtualOutputDir", "");
		jsonProperties.put("outputDir", "");
		jsonProperties.put("supportedImageReturnTypes", "MIME+URL");
		jsonProperties.put("isCached", "false");
		jsonProperties.put("ignoreCache", "false");
		jsonProperties.put("clientCachingAllowed", "false");
		jsonProperties.put("cacheDir", "");
		
		JSONArray jsonExtensions = new JSONArray();
		jsonService.put("extensions", jsonExtensions);
		if(extensionNameList.contains("KmlServer")) {
			JSONObject joExService = new JSONObject();
			JSONObject jpExproties = new JSONObject();
			
			joExService.put("typeName", "KmlServer");
			joExService.put("capabilities", "SingleImage,SeparateImages,Vectors");
			joExService.put("enabled", "true");
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
		if(extensionNameList.contains("WFSServer")) {
			JSONObject joExService = new JSONObject();
			JSONObject jpExproties = new JSONObject();
			joExService.put("typeName", "WFSServer");
			joExService.put("capabilities", "");
			joExService.put("enabled", "true");
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
		
		if(extensionNameList.contains("WCSServer")) {
			JSONObject joExService = new JSONObject();
			JSONObject jpExproties = new JSONObject();
			joExService.put("typeName", "WCSServer");
			joExService.put("capabilities", "null");
			joExService.put("enabled", "true");
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
			jpExproties.put("onlineResource", "http://:arcgis/services/" + serviceName + "/MapServer/WCSServer");
			

			joExService.put("properties", jpExproties);
			jsonExtensions.add(joExService);
		}
		if(extensionNameList.contains("FeatureServer")) {
			JSONObject joExService = new JSONObject();
			JSONObject jpExproties = new JSONObject();
			joExService.put("typeName", "FeatureServer");
			joExService.put("capabilities", "null");
			joExService.put("enabled", "true");
			joExService.put("maxUploadFileSize", 0);
			joExService.put("allowedUploadFileTypes", "");
			
			jpExproties.put("zDefaultValue", "");
			jpExproties.put("xssPreventionEnabled", "false");
			jpExproties.put("xssPreventionRule", "input");
			jpExproties.put("xssInputRule", "rejectInvalid");
			jpExproties.put("xssTrustedFields", "{\"Buildings\": \"Description,Name\", \"Parcels\": \"Name\"} ");
			jpExproties.put("allowOthersToQuery", "true");
			jpExproties.put("allowOthersToUpdate", "true");
			jpExproties.put("allowOthersToDelete", "true");
			
			
			joExService.put("properties", jpExproties);
			jsonExtensions.add(joExService);
		}
		if(extensionNameList.contains("WMSServer")) {
			JSONObject joExService = new JSONObject();
			JSONObject jpExproties = new JSONObject();
			joExService.put("typeName", "WMSServer");
			joExService.put("capabilities", "null");
			joExService.put("enabled", "true");
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
			jpExproties.put("onlineResource", "http://:arcgis/services/" + serviceName + "/MapServer/WCSServer");
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
		
		System.out.println("jsonService.toJSONString()======"+jsonService.toJSONString());
		
		String ip = "";
		String port = "";
		String userName = "";
		String password = "";
		if(severEngine != null) {
			ip = severEngine.getIntranetIp();
			port = severEngine.getIntranetPort()+"";
			userName = severEngine.getEngineManager();
			password = severEngine.getManagerPassword();
		}
		//发布服务
		String msg = ServiceUtils.createService(ip,port,userName,password,serviceName, serviceType, folderName.equals("/")?"":folderName, jsonService.toJSONString());
		map.put("msg", msg);
		return map;
	}
	
	/**
	 *  验证服务引擎连接情况
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/toCheckServer")
	public Map<String,String> toCheckServer(Integer id){
		Map<String,String> map = new HashMap<String, String>();
		map.put("msg", "验证服务器连接失败！");
		map.put("nextFlag", "0");
		if(id != null) {
			try {
				ConfigServerEngine serverEngine = configServerEngineService.get(ConfigServerEngine.class, id);
				String url = "http://"+serverEngine.getIntranetIp()+":"+serverEngine.getIntranetPort()+"/arcgis/admin/login";
				boolean ret = ArcGisServerUtils.checkServer(url, serverEngine.getEngineManager(), serverEngine.getManagerPassword());
				map.put("configName", serverEngine.getConfigName());
				map.put("intranetIp", serverEngine.getIntranetIp());
				map.put("runningStatus", serverEngine.getRunningStatus().equals("0") ? "启用":"禁用");
				if(ret){
					map.put("msg", "验证服务器连接成功！"); //验证成功
					map.put("nextFlag", "1"); //可以下一步
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				map.put("nextFlag", "0");
			}
		}
		return map;
	}
	
	
	@RequestMapping("toRegister")
	public String toRegister() {
		return "/service/service_register";
	}
	
	@RequestMapping("toRegisterGis")
	public String toRegisterGis(Model model) {
		//查找所有服务引擎
		List<ConfigServerEngine> serverEngineList = configServerEngineService.find("from ConfigServerEngine");
		//远程服务类型
		model.addAttribute("remoteServicesType", DataDictionary.getObject("remote_services_type"));
		//服务功能类型 service_function_type
		model.addAttribute("serviceFunctionType", DataDictionary.getObject("service_function_type"));
		//服务缓存类型 service_cache_Type
		model.addAttribute("serviceCacheType", DataDictionary.getObject("service_cache_Type"));
		//权限状态 
		model.addAttribute("permissionStatus", DataDictionary.getObject("service_permission_status"));
		//服务扩展功能类型service_extend_type
		model.addAttribute("serviceExtendType", DataDictionary.getObject("service_extend_type"));
		model.addAttribute("serverEngineList", serverEngineList);
		return "/service/service_register_gis";
	}
	
	@RequestMapping("registerGis")
	@ResponseBody
	public Map<String,String> registerGis(@RequestParam("serverEngine1")String serverEngineId,@RequestParam(value="imageFile",required=false) MultipartFile file,HttpServletRequest request,Model model) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			User user = (User) request.getSession().getAttribute(
					Global.SESSION_USER);
			String path = request.getSession().getServletContext()
					.getRealPath("upload");
			
			Map<String,String[]>  params = request.getParameterMap();
			Map datas = new HashMap();
			Iterator its = params.entrySet().iterator();
			while (its.hasNext()) {
				Map.Entry<String, String[]> entry = (Entry<String, String[]>) its.next();
				datas.put(entry.getKey(), entry.getValue()[0]);
			}
			Service s = BeanExtUtils.assignFromMap(datas, Service.class);
			if (serverEngineId != null && StringUtils.isNotBlank(s.getShowName())) {
				if (file != null && file.getSize() > 0) {
					String fileName = file.getOriginalFilename();
					//得到数字字典的图片类型, 再判断上传的文件是否是图片
					//Map<String, Object> mFileType = DataDictionary.getObject("image_type");
					path = path + File.separator + "service" + File.separator + "image";
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
				ConfigServerEngine se = configServerEngineService.get(ConfigServerEngine.class, Integer.parseInt(serverEngineId));
				s.setServerEngine(se);
				//String functionType = request.getParameter("functionType");
				//String serviceVisitAddress = request.getParameter("serviceVisitAddress");
				String[] serviceExtend = request.getParameterValues("serviceExtend");
				//System.out.println("functionType="+functionType + "extensionName= "+serviceExtend +" serviceVisitAddress="+serviceVisitAddress);
				s.setRegisterType("0");
				//s.setFunctionType(functionType);
				String tempExtend = "";
				for(String e : serviceExtend) {
					tempExtend = tempExtend + e + ",";
				}
				if(tempExtend.length() > 0) {
					s.setServiceExtend(tempExtend.substring(0, tempExtend.length()-1));
				}
				s.setCreateDate(new Date());
				s.setCreator(user);
				s.setServiceStatus("0");
				serviceService.save(s);
				map.put("flag", "0");
				map.put("msg", "注册成功！");
			}
		} catch (Exception e) {
			map.put("flag", "1");
			map.put("msg", "注册失败！");
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/listData")
	public Grid<Service> listData(Integer serverEngineId, PageHelper page) {
		//System.out.println("serverEngineId=" + serverEngineId);
		List<Service> list = null;
		if (serverEngineId != null) {
			ConfigServerEngine se = configServerEngineService.get(ConfigServerEngine.class, serverEngineId);
			//http://172.16.10.52:6080/arcgis/admin/services/
			String url = "http://" + se.getIntranetIp() + ":" + se.getIntranetPort() + "/arcgis/admin/services/";
			String userName = se.getMachineName();
			String password = se.getManagerPassword();
			List<String> folders = ServiceUtils.listFolder(url, userName, password);
			list = ServiceUtils.listServices(url, userName, password);
			//在折叠目录下的服务
			for(String f : folders) {
				list.addAll(ServiceUtils.listServices(url+f, userName, password));
			}
		}

		return new Grid<Service>(list);
	}
	
	@ResponseBody
	@RequestMapping("/listService")
	public Grid<Service> listService(String registerServerType, PageHelper page) {
		List<Service> list = null;
		if(StringUtils.isNotBlank(registerServerType)) {
			list = serviceService.find("from Service r where r.registerType = ? order by r.createDate desc",
					new Object[] {registerServerType}, page);
		}
		else {
			list = serviceService.find("from Service r order by r.createDate desc",
					null, page);
		}
		return new Grid<Service>(list);
	}
	
	@ResponseBody
	@RequestMapping("/getServiceInfo")
	public Map<String,String> getServiceInfo(Integer serverEngineId, String folderName,String showName,String functionType) {
		Map<String,String> map = new HashMap<String,String>();
		ConfigServerEngine severEngine = configServerEngineService.get(ConfigServerEngine.class, serverEngineId);
		//http://172.16.10.52:6080/arcgis/admin/services/
		String ip = "";
		String port = "";
		String userName = "";
		String password = "";
		if(severEngine != null) {
			ip = severEngine.getIntranetIp();
			port = severEngine.getIntranetPort()+"";
			userName = severEngine.getEngineManager();
			password = severEngine.getManagerPassword();
			Service s = ServiceUtils.getServiceInfo(ip, port, userName, password, folderName, showName, functionType);
			if(s != null) {
				map.put("functionType", functionType);
				map.put("serviceExtend", s.getServiceExtend());
				String serviceVisitAddress = "http://"+ip + ":" + port + "/arcgis/admin/services/";
				if(StringUtils.isNotBlank(folderName)) {
					serviceVisitAddress =  serviceVisitAddress + folderName + "/";
				}
				serviceVisitAddress = serviceVisitAddress + showName + "." + functionType;
				map.put("serviceVisitAddress", serviceVisitAddress);
			}
		}
		return map;
	}
	
	@RequestMapping("toRegisterOther")
	public String toRegisterOther(Model model) {
		//查找所有服务引擎
		List<ConfigServerEngine> serverEngineList = configServerEngineService.find("from ConfigServerEngine");
		//远程服务类型
		model.addAttribute("remoteServicesType", DataDictionary.getObject("remote_services_type"));
		//服务功能类型 service_function_type
		model.addAttribute("serviceFunctionType", DataDictionary.getObject("service_function_type"));
		//服务缓存类型 service_cache_Type
		model.addAttribute("serviceCacheType", DataDictionary.getObject("service_cache_Type"));
		//权限状态 
		model.addAttribute("permissionStatus", DataDictionary.getObject("service_permission_status"));
		//服务扩展功能类型service_extend_type
		model.addAttribute("serviceExtendType", DataDictionary.getObject("service_extend_type"));
		model.addAttribute("serverEngineList", serverEngineList);
		return "/service/service_register_other";
	}
	
	
	@RequestMapping("registerOther")
	@ResponseBody
	public Map<String,String> registerOther(@RequestParam("serverEngine1")String serverEngineId,@RequestParam(value="imageFile",required=false) MultipartFile file,HttpServletRequest request,Model model) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			User user = (User) request.getSession().getAttribute(
					Global.SESSION_USER);
			String path = request.getSession().getServletContext()
					.getRealPath("upload");
			
			Map<String,String[]>  params = request.getParameterMap();
			Map datas = new HashMap();
			Iterator its = params.entrySet().iterator();
			while (its.hasNext()) {
				Map.Entry<String, String[]> entry = (Entry<String, String[]>) its.next();
				datas.put(entry.getKey(), entry.getValue()[0]);
			}
			Service s = BeanExtUtils.assignFromMap(datas, Service.class);
			if (serverEngineId != null && StringUtils.isNotBlank(s.getShowName())) {
				if (file != null && file.getSize() > 0) {
					String fileName = file.getOriginalFilename();
					//得到数字字典的图片类型, 再判断上传的文件是否是图片
					//Map<String, Object> mFileType = DataDictionary.getObject("image_type");
					path = path + File.separator + "service" + File.separator + "image";
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
				ConfigServerEngine se = configServerEngineService.get(ConfigServerEngine.class, Integer.parseInt(serverEngineId));
				s.setServerEngine(se);
				//String functionType = request.getParameter("functionType");
				//String serviceVisitAddress = request.getParameter("serviceVisitAddress");
				String[] serviceExtend = request.getParameterValues("serviceExtend");
				//System.out.println("functionType="+functionType + "extensionName= "+serviceExtend +" serviceVisitAddress="+serviceVisitAddress);
				s.setRegisterType("1");
				//s.setFunctionType(functionType);
				String tempExtend = "";
				for(String e : serviceExtend) {
					tempExtend = tempExtend + e + ",";
				}
				if(tempExtend.length() > 0) {
					s.setServiceExtend(tempExtend.substring(0, tempExtend.length()-1));
				}
				s.setCreateDate(new Date());
				s.setCreator(user);
				s.setServiceStatus("0");
				serviceService.save(s);
				map.put("flag", "0");
				map.put("msg", "注册成功！");
			}
		} catch (Exception e) {
			map.put("flag", "1");
			map.put("msg", "注册失败！");
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		return map;
	}
	
	@RequestMapping("list")
	public String list(Model model) {
		model.addAttribute("serviceStatus", DataDictionary.getObject("service_status"));
		model.addAttribute("permissionStatus", DataDictionary.getObject("service_permission_status"));
		model.addAttribute("serviceRegisterType", DataDictionary.getObject("service_register_type"));
		
		return "/service/service_list";
	}
	
	/**
	 * 服务启动
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("start")
	@ResponseBody
	public Map<String,String> start(Integer id) {
		Map<String,String> map = new HashMap<String,String>();
		boolean result = false;
		Service service = null;
		if(id != null) {
			service =  serviceService.get(Service.class, id);
			String url = service.getServiceVisitAddress();
			ConfigServerEngine engine = service.getServerEngine();
			if(engine != null) {
				result = ServiceUtils.startService(engine.getIntranetIp(), engine.getIntranetPort()+"", engine.getEngineManager(), engine.getManagerPassword(),url);
			}
		}
		if(result) {
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
	@RequestMapping("stop")
	@ResponseBody
	public Map<String,String> stop(Integer id) {
		Map<String,String> map = new HashMap<String,String>();
		boolean result = false;
		Service service = null;
		if(id != null) {
			service =  serviceService.get(Service.class, id);
			String url = service.getServiceVisitAddress();
			ConfigServerEngine engine = service.getServerEngine();
			if(engine != null) {
				result = ServiceUtils.stopService(engine.getIntranetIp(), engine.getIntranetPort()+"", engine.getEngineManager(), engine.getManagerPassword(),url);
			}
		}
		if(result) {
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
	
	@RequestMapping("delete")
	@ResponseBody
	public Map<String,String> delete(Service service) {
		Map<String,String> map = new HashMap<String,String>();
		if(service.getId() != null) {
			try {
				serviceService.delete(service);
				map.put("msg", "删除成功！");
			} catch (Exception e) {
				map.put("msg", "删除失败！");
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return map;
	}
	
	@RequestMapping("update")
	public String update(Service service,Model model) {
		serviceService.update(service);
		return "";
	}
	
	/**
	 * 把所有资源分类转成json字符串
	 */
	@ResponseBody
	@RequestMapping(value = "listServiceType", produces = "application/json;charset=UTF-8")
	public String listServiceType(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String,Object> registerTypeMap = DataDictionary.getObject("service_register_type");
		registerTypeMap.entrySet();
		Map<String, Object> map = Maps.newHashMap();
		String rootId = UUID.randomUUID().toString();
		map.put("id", rootId);
		map.put("pid", "");
		map.put("text", "服务分类");
		mapList.add(map);
		for(Map.Entry<String,Object> entry: registerTypeMap.entrySet()) {
			map = Maps.newHashMap();
			//String key = entry.getKey();
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
	public String auditRegisterList() {
		return "/service/service_audit_register_list";
	}
	
	@RequestMapping("auditUseList")
	public String auditUseList() {
		return "/service/service_audit_use_list";
	}
	
}
