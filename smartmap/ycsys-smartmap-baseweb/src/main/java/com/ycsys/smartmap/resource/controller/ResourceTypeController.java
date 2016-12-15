package com.ycsys.smartmap.resource.controller;

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
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ycsys.smartmap.resource.entity.Resource;
import com.ycsys.smartmap.resource.entity.ResourceType;
import com.ycsys.smartmap.resource.service.ResourceService;
import com.ycsys.smartmap.resource.service.ResourceTypeService;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.FileUtils;
import com.ycsys.smartmap.sys.common.utils.JsonMapper;
import com.ycsys.smartmap.sys.common.utils.StringUtils;
import com.ycsys.smartmap.sys.entity.User;

@Controller
@RequestMapping("/resourceType")
public class ResourceTypeController {
	private static Logger log = Logger.getLogger(ResourceTypeController.class);
	@Autowired
	private ResourceTypeService resourceTypeService;
	@Autowired
	private ResourceService resourceService;

	@RequestMapping("toEdit")
	@RequiresPermissions(value = "resource-type-edit")
	public String toEdit(String flag, String actionNodeID, Model model) {
		log.debug("flag="+flag);
		ResourceType resourceType = null;
		//新增
		if ("1".equals(flag)) {
			resourceType = new ResourceType();
			if(StringUtils.isNotBlank(actionNodeID)) {
				ResourceType parent = resourceTypeService.get(ResourceType.class,
						Integer.parseInt(actionNodeID));
				resourceType.setParent(parent);
			}
			model.addAttribute("resourceType", resourceType);
		}
		//修改
		else {
			resourceType = resourceTypeService.get(
					ResourceType.class, Integer.parseInt(actionNodeID));
			model.addAttribute("resourceType", resourceType);
		}
		return "/resource/resource_type_edit";
	}

	@ResponseBody
	@RequestMapping("save")
	@RequiresPermissions(value = "resource-type-edit")
	public Map<String,String> save(ResourceType resourceType, Model model,
			HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		User user = (User) request.getSession().getAttribute(
				Global.SESSION_USER);
		//判断是否存在相同名字的资源分类
		System.out.println("parent="+resourceType.getParent().getId());
		List<ResourceType> rtLists = resourceTypeService.find("from ResourceType t where t.parent.id="+resourceType.getParent().getId() +" and t.name='"+resourceType.getName()+"' and t.id <>" +resourceType.getId());
		//List<ResourceType> rtLists2 = resourceTypeService.find("from ResourceType t where t.parent.id=? and t.name = ? and t.id <> ?",new Object[]{resourceType.getParent().getId(),resourceType.getName(),resourceType.getId()});
		if(rtLists != null && rtLists.size() > 0) {
			log.warn("该资源分类下已有这个结点名称的分类！");
			map.put("msg", "该资源分类下已有这个结点名称的分类！");
			map.put("flag", "2");
			return map;
		}
		// 新增
		if(null == resourceType.getId()) {
			resourceType.setCreator(user);
			resourceType.setCreateDate(new Date());
			Integer parentId = resourceType.getParent().getId();
			//根结点没有父亲结点，所以为空
			if(parentId == null) {
				resourceType.setParent(null);
			}
			resourceTypeService.save(resourceType);
			map.put("msg", "新增成功！");
			map.put("flag", "1");
		}
		// 修改
		else {
			ResourceType dBResourceType = resourceTypeService.get(
					ResourceType.class, resourceType.getId());
			ResourceType parent = dBResourceType.getParent();
			try {
				// 得到修改过的属性
				BeanExtUtils.copyProperties(dBResourceType, resourceType, true,
						true, null);
			} catch (IllegalAccessException | InvocationTargetException e) {
				map.put("msg", "修改失败！");
				return map;
			}
			//根结点没有父亲结点，所以为空
			if(parent == null) {
				dBResourceType.setParent(null);
			}
			dBResourceType.setUpdator(user);
			dBResourceType.setUpdateDate(new Date());
			resourceTypeService.update(dBResourceType);
			map.put("msg", "修改成功！");
			map.put("flag", "1");
		}
		return map;
	}

	@ResponseBody
	@RequestMapping("delete")
	@RequiresPermissions(value = "resource-type-delete")
	public Map<String, String> delete(String id) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", "0");
		if (StringUtils.isNotBlank(id)) {
			ResourceType resourceType = resourceTypeService.get(
					ResourceType.class, Integer.parseInt(id));
			if (resourceType != null) {
				// 判断该结点下是否有子结点 或 已上传资源
				List<ResourceType> children = resourceTypeService.find(
						"from ResourceType t where t.parent = ?",
						new Object[] { resourceType });
				List<Resource> resourceLists = resourceService.find(
						"from Resource t where t.resourceType = ?",
						new Object[] { resourceType });
				if (children != null && children.size() > 0 || resourceLists != null && resourceLists.size() > 0) {
					resultMap.put("result", "1");
					return resultMap;
				}
				resourceTypeService.delete(resourceType);
				List<ResourceType> lists = resourceTypeService
						.find("from ResourceType t");
				if(lists == null || lists.size() < 1) {
					resultMap.put("showBtnFlag", "1"); //把所有结点都删除了，这时需要把新增按钮显示
				}
			}
		}
		else {
			resultMap.put("result", "2");
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * 把所有资源分类转成json字符串
	 */
	@ResponseBody
	@RequestMapping(value = "listAll", produces = "application/json;charset=UTF-8")
	@RequiresPermissions(value = "resource-type-list")
	public String listAll(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ResourceType> lists = resourceTypeService
				.find("from ResourceType t order by t.createDate asc");
		for (ResourceType e : lists) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pid", e.getParent() != null ? e.getParent().getId() : 0);
			map.put("text", e.getName());
			mapList.add(map);
		}
		String jsonStr = JsonMapper.toJsonString(mapList);
		return jsonStr;
	}
	/**
	 * 得到资源分类的所有父亲节点名称包含自己（把它们拼成串）
	 * @param resourceType
	 * @return
	 */
	private String getResourceTypeParent(ResourceType resourceType) {
		if(resourceType.getParent() != null) {
			return resourceType.getName() + "##"+getResourceTypeParent(resourceType.getParent());
		}
		else {
			return resourceType.getName();
		}
		
	}
	
	/**
	 * 备份资源分类下面的所有资源
	 * @param id
	 * @param name
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("backupResource")
	@RequiresPermissions(value = "resource-type-backup")
	public Map<String,String> backupResource(Integer id,String name,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		if(id != null) {
			List<Resource> rList = resourceService.find("from Resource t where t.resourceType.id = ?", new Object[] {id});
			String destPath = request.getSession().getServletContext()
					.getRealPath("backup" + File.separator +"资源");
			if(rList == null || rList.size() < 1) {
				map.put("msg", "该节点下没有资源");
				return map;
			}
			//得到资源分类的所有父亲结点名称（包含自己的），上传的文件要根据所在资源分类来存储
			ResourceType resourceType = resourceTypeService.get(ResourceType.class,id);
			String rtParent = getResourceTypeParent(resourceType);
			String parentName[] = rtParent.split("##");
			List<String> parentNameList = Arrays.asList(parentName);
			//反转这个集合的元素
			Collections.reverse(parentNameList); 
			StringBuffer sb = new StringBuffer();
			for(String s:parentNameList) {
				sb.append(File.separator).append(s);
			}
			destPath = destPath + sb.toString() + File.separator;
			String srcPath = rList.get(0).getFilePath().substring(0,rList.get(0).getFilePath().lastIndexOf("\\"));
			//复制
			boolean flag = FileUtils.copyDirectoryCover(srcPath, destPath,true);
			if(flag) {
				map.put("msg", "成功备份了【"+name+"】分类下的资源到路径（" + destPath + "）中");
				//设置备份时间和标记
				resourceType.setBackupDate(new Date());
				resourceType.setBackupsFlag("1");
			}
			else {
				map.put("msg", "备份失败！");
			}
		}
		return map;
		
	}
}
