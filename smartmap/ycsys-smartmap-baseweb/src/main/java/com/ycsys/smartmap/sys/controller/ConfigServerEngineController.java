package com.ycsys.smartmap.sys.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.exception.SysException;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.DateUtils;
import com.ycsys.smartmap.sys.common.utils.FileUtils;
import com.ycsys.smartmap.sys.common.utils.JsonMapper;
import com.ycsys.smartmap.sys.common.utils.StringUtils;
import com.ycsys.smartmap.sys.entity.ConfigServerEngine;
import com.ycsys.smartmap.sys.entity.DictionaryItem;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.ConfigServerEngineService;
import com.ycsys.smartmap.sys.service.UserService;
import com.ycsys.smartmap.sys.util.DataDictionary;

/**
 * 服务器引擎配置 controller
 * 
 * @author lrr
 * @date 2016年11月16日
 */
@Controller
@RequestMapping("/configServerEngine")
public class ConfigServerEngineController extends BaseController{
	private static Logger log = LoggerFactory.getLogger(ConfigServerEngineController.class);
	@Autowired
	private UserService userService;
	
	@Autowired
	private ServiceService serviceService;
	
	@Autowired
	private ConfigServerEngineService configServerEngineService;
	
	/**
	 * 准备增加或修改
	 * @param engineTypeId
	 * @param configServerEngine
	 * @param model
	 * @return
	 */
	@RequestMapping("toEdit")
	@RequiresPermissions(value = "sys-serverEngine-edit")
	public String toEdit(String engineTypeId, ConfigServerEngine configServerEngine, Model model) {
		// 修改
		if (configServerEngine.getId() != null) {
			configServerEngine = configServerEngineService.get(ConfigServerEngine.class, configServerEngine.getId());
			model.addAttribute("configServerEngine", configServerEngine);
		}
		model.addAttribute("engineTypeId", engineTypeId);//用于设置下拉框的值
		model.addAttribute("engineType", DataDictionary.getObject("engineType_type"));
		return "/configServerEngine/configServerEngine_edit";
	}

	/**
	 * 把所有引擎类型转成json字符串
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys-serverEngineType-list")
	@RequestMapping(value = "listEngineType", produces = "application/json;charset=UTF-8")
	public String listServiceType(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Map<String,Object> engineTypeMap = DataDictionary.getObject("engineType_type");
		engineTypeMap.entrySet();
		Map<String, Object> map = Maps.newHashMap();
		String rootId = UUID.randomUUID().toString();
		map.put("id", rootId);
		map.put("pid", "");
		map.put("text", "服务引擎组织");
		mapList.add(map);
		for(Map.Entry<String,Object> entry: engineTypeMap.entrySet()) {
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
	
	//列表列出所有数据
	@RequestMapping("list")
	@RequiresPermissions(value = "sys-serverEngine-list")
	public String list(Model model){
		List<ConfigServerEngine> list = configServerEngineService.find("from ConfigServerEngine c where 1=1 ");
		model.addAttribute("list", list);
		model.addAttribute("engineType", DataDictionary.getObject("engineType_type"));
		return "configServerEngine/configServerEngine_list";
	}
	
	
	//根据引擎类型查询出页面表格需要的数据
	@ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions(value = "sys-serverEngine-list-data")
	public Grid<ConfigServerEngine> listData(String engineType, PageHelper page) {

		List<ConfigServerEngine> list = null;
		if (StringUtils.isNotBlank(engineType)) {
			list = configServerEngineService.find("from ConfigServerEngine c where c.engineType = ?",
					new Object[] { engineType }, page);
		} else {
			list = configServerEngineService.find("from ConfigServerEngine c where 1 = 1",
					null, page);
		}

		return new Grid<ConfigServerEngine>(list);
	}
	
	
	//保存服务引擎配置方法
	@ResponseBody
	@RequestMapping("save")
	@RequiresPermissions(value = "sys-serverEngine-edit")
	public Map<String,String> save(ConfigServerEngine configServerEngine,Model model,HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("msg", "操作失败！");
		User user = (User) request.getSession().getAttribute(Global.SESSION_USER);
		//新增
		if(configServerEngine.getId()==null){
			configServerEngine.setCreateDate(new Date());
			configServerEngine.setCreator(user);
			configServerEngineService.save(configServerEngine);
			map.put("msg", "新增成功！");
			map.put("flag", "1");
			return map;
		}
		//更新
		else{
			ConfigServerEngine dbConfigServerEngine = configServerEngineService.get(ConfigServerEngine.class,
					configServerEngine.getId());
			try {
				// 得到修改过的属性
				BeanExtUtils.copyProperties(dbConfigServerEngine, configServerEngine, true, true,
						null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			dbConfigServerEngine.setUpdateDate(new Date());
			dbConfigServerEngine.setUpdator(user);
			configServerEngineService.update(dbConfigServerEngine);
			map.put("msg", "修改成功！");
			map.put("flag", "1");
			return map;
		}
	}

	/*
	 * 删除多条数据记录
	 */
	@ResponseBody
	@RequestMapping("deletes")
	@RequiresPermissions(value = "sys-serverEngine-delete")
	public ResponseEx delete(String idsStr) {
		ResponseEx ex = new ResponseEx();
		String ids[] = idsStr.split(",");
		if(ids != null && ids.length > 0) {
			for(String id:ids) {
				ConfigServerEngine configServerEngine = configServerEngineService.get(ConfigServerEngine.class, Integer.parseInt(id));
				if(configServerEngine != null) {
					List<Service> list = serviceService.find("from Service t where t.serverEngine.id = ?", new Object[]{configServerEngine.getId()});
					if((list != null && list.size() > 0)) {
		        		ex.setSuccess("服务引擎被引用不能删除");
		        	}else{
		        		configServerEngineService.delete(configServerEngine);
		        		ex.setSuccess("删除成功");
		        	}
				}
			}
		}
		else {
			ex.setFail("删除失败");
		}
		return ex;
	}
	/**
	 * 删除单条数据记录
	 * @return
	 */
    @ResponseBody
    @RequiresPermissions(value = "sys-serverEngine-delete")
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public ResponseEx delete(ConfigServerEngine configServerEngine){
        ResponseEx ex = new ResponseEx();
        try{
        	List<Service> list = serviceService.find("from Service t where t.serverEngine.id = ?", new Object[]{configServerEngine.getId()});
        	if((list != null && list.size() > 0)) {
        		ex.setSuccess("服务引擎被引用不能删除");
        	}
        	else {
        		configServerEngineService.delete(configServerEngine);
        		ex.setSuccess("删除成功");
        	}
        }catch (Exception e){
            ex.setFail("删除失败");
        }
        return ex;
    }
	
	
	//更新服务引擎配置
	@RequestMapping("update")
	@RequiresPermissions(value = "sys-serverEngine-edit")
	public String update(ConfigServerEngine configServerEngine, Model model) {
		configServerEngineService.update(configServerEngine);
		return "";
	}

	/**
	 * 导出
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("export")
	@RequiresPermissions(value = "sys-serverEngine-export")
	public String export(HttpServletRequest request,
			HttpServletResponse response) {
		// 创建文档
		Document document = DocumentHelper.createDocument();
		// 创建根元素节点
		Element root = DocumentHelper.createElement("engines");
		root.addAttribute("配置信息", "配置信息");
		// 设置文档的根元素节点
		document.setRootElement(root);
		// 查询需要导出的数据
		List<ConfigServerEngine> lists = configServerEngineService
				.find(" from ConfigServerEngine where 1 = 1 ");

		// 循环把数据写进xml的元素结点中
		for (ConfigServerEngine engine : lists) {
			Element e0 = root.addElement("engine");

			Element e1 = e0.addElement("配置名称");
			e1.addText(engine.getConfigName());

			Element e2 = e0.addElement("引擎类型");
			e2.addText(engine.getEngineType());

			Element e3 = e0.addElement("集成模式");
			e3.addText(engine.getIntegrationModel());

			Element e4 = e0.addElement("机器名");
			e4.addText(engine.getMachineName());

			Element e5 = e0.addElement("内网IP");
			e5.addText(engine.getIntranetIp());

			Element e6 = e0.addElement("内网端口");
			e6.addText(engine.getIntranetPort() + "");

			Element e7 = e0.addElement("运行状态");
			e7.addText(engine.getRunningStatus());

			Element e8 = e0.addElement("数据上传服务地址");
			e8.addText(engine.getDataUploadPath() == null ? "" : engine.getDataUploadPath());

			Element e9 = e0.addElement("数据上传绝对路径");
			e9.addText(engine.getDataUploadRealPath() == null ? "" : engine.getDataUploadRealPath());

			Element e10 = e0.addElement("引擎管理员");
			e10.addText(engine.getEngineManager() == null ? "" : engine.getEngineManager());

			Element e11 = e0.addElement("管理密码");
			e11.addText(engine.getManagerPassword() == null ? "" : engine.getManagerPassword());

			Element e12 = e0.addElement("创建日期");
			String createDate = "";
			if(engine.getCreateDate() != null) {
				createDate = DateUtils.formatDate(engine.getCreateDate() , "yyyy-MM-dd HH:mm:ss");
			}
			e12.addText(createDate);

			Element e13 = e0.addElement("创建者");
			e13.addText(engine.getCreator() == null ? "" : engine.getCreator()
					.getName());
			e13.addAttribute("id", engine.getCreator() == null ? "" : engine
					.getCreator().getId() + "");

			Element e14 = e0.addElement("更新日期");
			String updateDate = "";
			if(engine.getUpdateDate() != null) {
				updateDate = DateUtils.formatDate(engine.getUpdateDate() , "yyyy-MM-dd HH:mm:ss");
			}else{
				updateDate = DateUtils.formatDate(new Date() , "yyyy-MM-dd HH:mm:ss");
			}
			e14.addText(updateDate);

			Element e15 = e0.addElement("更新者");
			e15.addText(engine.getUpdator() == null ? "" : engine.getUpdator()
					.getName());
			e15.addAttribute("id", engine.getUpdator() == null ? "" : engine
					.getUpdator().getId() + "");

			Element e16 = e0.addElement("Id");
			e16.addText(engine.getId() + "");
		}

		// 保存数据到xml文档中，并且临时保存在临时文件夹中，之后会删除
		try {
			String realPath = request.getSession().getServletContext()
					.getRealPath("temp");
			File targetFile = new File(realPath);
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			// 把生成的xml文档存放在硬盘上 true代表是否换行
			OutputFormat format = new OutputFormat("    ", true);
			format.setEncoding("UTF-8");// 设置编码格式

			FileOutputStream fout = new FileOutputStream(realPath
					+ File.separator + "engine.xml");
			// PrintWriter out = response.getWriter();
			response.reset();
			response.setContentType("text/xml");
			response.setHeader("Content-Disposition",
					"attachment;filename=engine.xml");
			response.setCharacterEncoding("UTF-8");
			XMLWriter xmlWriter = new XMLWriter(fout, format);
			// XMLWriter xmlWriter = new XMLWriter(out, format);
			xmlWriter.write(document);
			xmlWriter.flush();
			xmlWriter.close();

			File file = new File(realPath + File.separator + "engine.xml");
			InputStream in = new FileInputStream(file);
			DataInputStream din = new DataInputStream(new BufferedInputStream(
					in));
			OutputStream out = response.getOutputStream();
			DataOutputStream dout = new DataOutputStream(
					new BufferedOutputStream(out));
			response.reset();
			response.setContentType("text/xml; charset=utf-8");
			response.setHeader("Content-Disposition",
					"attachment;filename=engine.xml");

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
			FileUtils.deleteDirectory(realPath);

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.debug("UnsupportedEncodingException:" + e);
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			log.debug("FileNotFoundException:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.debug("写数据到文件失败!" + e);
			e.printStackTrace();
		}

		return "configServerEngine/configServerEngine_list";
	}

	@RequestMapping("importConfigServerEngine")
	@RequiresPermissions(value = "sys-serverEngine-import")
	public String toImport() {
		return "/configServerEngine/configServerEngine_import";
	}
	
	@RequestMapping("outputConfigServerEngine")
	@RequiresPermissions(value = "sys-serverEngine-import")
	public String Output() {
		return "/configServerEngine/configServerEngine_import";
	}

	/**
	 * 导入
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	/*@RequestMapping("importFile")
	public String importFile(MultipartFile file, HttpServletRequest request,
			HttpServletResponse response) {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(file.getInputStream());
			Element root = document.getRootElement();
			List<Element> child1 = root.elements();
			for (Element e : child1) {
				ConfigServerEngine engine = new ConfigServerEngine();
				List<Element> child2 = e.elements();
				engine.setConfigName(child2.get(0).getText());
				Integer engineTypeId =  Integer.parseInt(child2.get(1).attributeValue("id"));
				engine.setEngineType(engineTypeService.get(EngineType.class, engineTypeId));
				engine.setIntegrationModel(child2.get(2).getText());
				engine.setMachineName(child2.get(3).getText());
				engine.setIntranetIp(child2.get(4).getText());
				engine.setIntranetPort(Integer.parseInt(child2.get(5).getText()));
				engine.setRunningStatus(child2.get(6).getText());
				engine.setDataUploadPath(child2.get(7).getText());
				engine.setDataUploadRealPath(child2.get(8).getText());
				engine.setEngineManager(child2.get(9).getText());
				engine.setManagerPassword(child2.get(10).getText());
				String createDate = child2.get(11).getText();
				String updateDate = child2.get(13).getText();
				try {
					Date d1 = DateUtils.parseDate(createDate, "yyyy-MM-dd HH:mm:ss");
					Date d2 = DateUtils.parseDate(updateDate, "yyyy-MM-dd HH:mm:ss");
					engine.setCreateDate(d1);
					engine.setUpdateDate(d2);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				String userId = child2.get(12).attributeValue("id");
				if(StringUtils.isNotBlank(userId)) {
					User u = userService.get(User.class,Integer.parseInt(userId));
					engine.setCreator(u);
				}
				String userId2 = child2.get(14).attributeValue("id");
				if(StringUtils.isNotBlank(userId2)) {
					User u2 = userService.get(User.class,Integer.parseInt(userId2));
					engine.setUpdator(u2);
				}
				configServerEngineService.save(engine);
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "configServerEngine/configServerEngine_list";
	}*/
	@ResponseBody
	@RequestMapping("/importFile")
	@RequiresPermissions(value = "sys-serverEngine-import")
    public ResponseEx importFile(MultipartFile file, HttpServletRequest request,
			HttpServletResponse response){
        ResponseEx ex = new ResponseEx();
        try {
            String fileName = file.getOriginalFilename();
            if (fileName == null || !fileName.matches("^.+\\.(?i)((xml))$"))
            {
                throw new SysException("文件不是xml格式");
            }
            SAXReader reader = new SAXReader();
    		try {
    			Document document = reader.read(file.getInputStream());
    			Element root = document.getRootElement();
    			List<Element> child1 = root.elements();
    			for (Element e : child1) {
    				ConfigServerEngine engine = new ConfigServerEngine();
    				List<Element> child2 = e.elements();
    				engine.setConfigName(child2.get(0).getText());
    				engine.setEngineType(child2.get(1).getText());
    				engine.setIntegrationModel(child2.get(2).getText());
    				engine.setMachineName(child2.get(3).getText());
    				engine.setIntranetIp(child2.get(4).getText());
    				engine.setIntranetPort(Integer.parseInt(child2.get(5).getText()));
    				engine.setRunningStatus(child2.get(6).getText());
    				engine.setDataUploadPath(child2.get(7).getText());
    				engine.setDataUploadRealPath(child2.get(8).getText());
    				engine.setEngineManager(child2.get(9).getText());
    				engine.setManagerPassword(child2.get(10).getText());
    				String createDate = child2.get(11).getText();
    				String updateDate = child2.get(13).getText();
    				System.out.println("createDate=============="+createDate);
    				System.out.println("updateDate=============="+updateDate);
    				try {
    					/*if (updateDate==null) {
    						updateDate = DateUtils.formatDate(new Date() , "yyyy-MM-dd HH:mm:ss");
						}
						if (createDate==null) {
							createDate = DateUtils.formatDate(new Date() , "yyyy-MM-dd HH:mm:ss");
						}	*/
						Date d1 = DateUtils.parseDate(createDate, "yyyy-MM-dd HH:mm:ss");
    					Date d2 = DateUtils.parseDate(updateDate, "yyyy-MM-dd HH:mm:ss");
    					
    					engine.setCreateDate(d1);
    					engine.setUpdateDate(d2);
    				} catch (ParseException e1) {
    					// TODO Auto-generated catch block
    					e1.printStackTrace();
    				}
    				
    				String userId = child2.get(12).attributeValue("id");
    				if(StringUtils.isNotBlank(userId)) {
    					User u = userService.get(User.class,Integer.parseInt(userId));
    					engine.setCreator(u);
    				}
    				String userId2 = child2.get(14).attributeValue("id");
    				if(StringUtils.isNotBlank(userId2)) {
    					User u2 = userService.get(User.class,Integer.parseInt(userId2));
    					engine.setUpdator(u2);
    				}
    				configServerEngineService.save(engine);
    			}

    		} catch (DocumentException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
            ex.setSuccess("导入成功！");
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }
	public static void main(String[] args) {
		String str = "2016-11-29 12:27:12";
		System.out.println(DateUtils.parseDate(str));
		new Date();
		System.out.println(new Date());
	}

}
