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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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

	@RequestMapping("save")
	@ResponseBody
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
			log.warn("存在相同的资源分类");
			map.put("msg", "存在相同的资源分类！");
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
			map.put("flag", "3");
		}
		return map;
	}

	@RequestMapping("delete")
	@ResponseBody
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

	@RequestMapping("update")
	public String update(ResourceType resourceType, Model model) {
		resourceTypeService.update(resourceType);
		return "";
	}

	/**
	 * 把所有资源分类转成json字符串
	 */
	@ResponseBody
	@RequestMapping(value = "listAll", produces = "application/json;charset=UTF-8")
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
	
	@RequestMapping("backupResource")
	@ResponseBody
	public Map<String,String> backupResource(Integer id,String name,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		if(id != null) {
			List<Resource> rList = resourceService.find("from Resource t where t.resourceType.id = ?", new Object[] {id});
			//System.out.println("rList = "+rList);
			String realPath = request.getSession().getServletContext()
					.getRealPath("backup" + File.separator +"资源" + File.separator + name);
			if(rList == null || rList.size() < 1) {
				map.put("msg", "该节点下没有资源");
				return map;
			}
			for(Resource r : rList) {
				if(r.getFilePath() != null) {
					try {
						String fileName = r.getFilePath().substring(r.getFilePath().lastIndexOf("\\") + 1);
						InputStream in = new FileInputStream(r.getFilePath());
						DataInputStream din = new DataInputStream(new BufferedInputStream(
								in));
						File targetFile = new File(realPath);
						if (!targetFile.exists()) {
							targetFile.mkdirs();
						}
						FileOutputStream fout = new FileOutputStream(realPath + File.separator + fileName);
						
						DataOutputStream dout = new DataOutputStream(
								new BufferedOutputStream(fout));

						int n;
						byte buf[] = new byte[8192];
						while ((n = din.read(buf)) != -1) {
							dout.write(buf, 0, n);
						}
						dout.flush();
						dout.close();
						din.close();
						in.close();
					} catch (UnsupportedEncodingException e) {
						map.put("msg", "备份失败");
						log.debug("UnsupportedEncodingException:" + e);
					} catch (FileNotFoundException e) {
						map.put("msg", "备份失败");
						log.debug("FileNotFoundException:" + e);
					} catch (IOException e) {
						map.put("msg", "备份失败");
						log.debug("写数据到文件失败!" + e);
					}
				}
			}
			map.put("msg", "成功备份了【"+name+"】分类下的资源到路径（" + realPath + "中");
		}
		return map;
		
	}
}
