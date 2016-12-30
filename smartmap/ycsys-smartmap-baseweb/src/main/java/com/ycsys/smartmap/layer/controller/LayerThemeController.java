package com.ycsys.smartmap.layer.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ycsys.smartmap.service.entity.LayerTheme;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.service.service.ThemeService;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.JsonMapper;
import com.ycsys.smartmap.sys.common.utils.StringUtils;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.util.DataDictionary;

/**
 * 专题图 controller
 * @author liweixiong
 * @date   2016年12月21日
 */
@Controller
@RequestMapping("/layerTheme")
public class LayerThemeController {
	private static Logger log = LoggerFactory.getLogger(LayerThemeController.class);
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private ServiceService serviceService;
	
	/**
	 * 列表列出所有数据
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	@RequiresPermissions(value = "sys-layerTheme-list")
	public String list(Model model){
		model.addAttribute("serviceRegisterType",DataDictionary.getObject("service_register_type"));
		return "layerTheme/layerTheme_list";
	}
		
	@ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions(value = "sys-layerTheme-list-data")
	public Grid<LayerTheme> listData(String pId, PageHelper page) {
		
		List<LayerTheme> list = null;
		list = themeService.find("from LayerTheme t where 1 = 1 and t.type = 'n' ",null, page);
		if (StringUtils.isNotBlank(pId)) {
			Integer id = Integer.parseInt(pId);
			list = themeService.find("from LayerTheme t where 1 = 1 and t.type = ? and  t.pId = ? ",new Object[] {"n",id }, page);
		} 
		return new Grid<LayerTheme>(list);
	}
	
	/**
	 * 把所有专题图转成json字符串
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys-layerTheme-list-data")
	@RequestMapping(value = "listLayerThemeType", produces = "application/json;charset=UTF-8")
	public String listLayerThemeType(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<LayerTheme> lists = themeService
				.find("from LayerTheme l where 1 = 1");
		for (LayerTheme e : lists) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			//map.put("pid", e.getPId());
			map.put("pid", e.getParent() != null ? e.getParent().getId() : -1);
			map.put("text", e.getName());
			mapList.add(map);
		}
		String jsonStr = JsonMapper.toJsonString(mapList);
		return jsonStr;
	}
	
	/**
	 * 准备编辑图层树
	 * @param layer
	 * @param actionNodeID(父亲结点 id)
	 * @param model
	 * @return
	 */
	@RequestMapping("toEditLayerThemeType")
	@RequiresPermissions(value = "sys-layerTheme-edit")
	public String toEditLayerThemeType(LayerTheme layerTheme, String layerThemeTypeId, Model model) {
		//新增
		if(layerTheme.getId() == null) {
			if(StringUtils.isNotBlank(layerThemeTypeId)) {
				LayerTheme parent = themeService.get(LayerTheme.class, Integer.parseInt(layerThemeTypeId));
				layerTheme.setParent(parent);
			}
		}
		//修改
		else {
			layerTheme = themeService.get(LayerTheme.class, layerTheme.getId());
		}
		model.addAttribute("layerTheme", layerTheme);
		
		return "/layerTheme/layerTheme_type_edit";
	}
	
	/**
	 * 保存专题图树
	 */
	@ResponseBody
	@RequestMapping("saveLayerThemeType")
	@RequiresPermissions(value = "sys-layerTheme-edit")
	public Map<String,String> saveLayerThemeType(LayerTheme layerTheme, Model model,
			HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		//判断是否存在相同名字的专题图目录
		List<LayerTheme> rtLists = themeService.find("from LayerTheme t where t.parent.id = " + layerTheme.getParent().getId() + " and t.name='"+layerTheme.getName()+"' and t.id <>" +layerTheme.getId());
		if(rtLists != null && rtLists.size() > 0) {
			log.warn("存在相同的专题图目录");
			map.put("msg", "存在相同的专题图目录！");
			map.put("flag", "2");
			return map;
		}
		// 新增
		if(null == layerTheme.getId()) {
			Integer pId = layerTheme.getParent().getId();
			if(pId != null ) {
				//判断是否可以增加
				LayerTheme parent = themeService.get(LayerTheme.class, pId);
				if(parent != null && "n".equals(parent.getType())) {
					map.put("msg", "专题图结点不能增加专题图目录！");
					return map;
				}
				layerTheme.setPId(pId);
			}else{
				layerTheme.setParent(null);
				layerTheme.setPId(-1);
			}
			layerTheme.setType("r");
			themeService.save(layerTheme);
			map.put("msg", "新增成功！");
			map.put("flag", "1");
		}
		// 修改
		else {
			LayerTheme dBLayer = themeService.get(LayerTheme.class, layerTheme.getId());
			LayerTheme parent = dBLayer.getParent();
			try {
				// 得到修改过的属性
				BeanExtUtils.copyProperties(dBLayer, layerTheme, true,
						true, null);
			} catch (IllegalAccessException | InvocationTargetException e) {
				map.put("msg", "修改失败！");
				return map;
			}
			//根结点没有父亲结点，所以为空
			if(parent == null) {
				dBLayer.setParent(null);
			}
			themeService.update(dBLayer);
			map.put("msg", "修改成功！");
			map.put("flag", "1");
		}
		return map;
	}
	
	/**
	 * 准备增加专题图
	 * @param layerThemeTypeId
	 * @param model
	 * @return
	 */
	@RequestMapping("toAddLayerTheme")
	@RequiresPermissions(value = "sys-layerTheme-create")
	public String toAddLayerTheme(String layerThemeTypeId, Model model) {
		//model.addAttribute("layerTheme", layerTheme);
		List<LayerTheme> lists = themeService.find("from LayerTheme t where t.type = 'r' ");
		model.addAttribute("lists", lists);
		model.addAttribute("layerThemeTypeId", layerThemeTypeId);
		return "/layerTheme/layerTheme_add";
	}
	
	/**
	 * 增加专题图
	 * @param layerTheme
	 * @param model
	 * @return
	 */
	@RequestMapping("addLayerTheme")
	@ResponseBody
	@RequiresPermissions(value = "sys-layerTheme-create")
	public Map<String,String> addLayerTheme(LayerTheme layerTheme, Model model,HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		String serviceIds [] = layerTheme.getServiceIds().split(",");
		User user = (User) request.getSession().getAttribute(Global.SESSION_USER);
		int count = 0;
		for(int i = 0; i < serviceIds.length; i++) {
			Service s = serviceService.get(Service.class, Integer.parseInt(serviceIds[i]));
			if(s != null) {
				LayerTheme lt = new LayerTheme();
				lt.setType("n");
				lt.setService(s);
				lt.setName(s.getShowName());
				lt.setCreateDate(new Date());
				lt.setCreator(user);
				lt.setParent(layerTheme.getParent());
				lt.setAddress(s.getServiceVisitAddress());
				lt.setPId(layerTheme.getParent().getId());
				
				List<LayerTheme> rtLists = themeService.find("from LayerTheme t where t.parent.id=" + lt.getParent().getId() +" and t.name='" + lt.getName()+"' and t.id <>" + lt.getId());
				if(rtLists != null && rtLists.size() > 0) {
					log.warn("存在相同的专题图");
					continue;
				}
				themeService.save(lt);
				count++;
			}
		}
		if(count == serviceIds.length) {
			map.put("msg", "新增成功！");
		}
		else {
			map.put("msg", "一共【" + (serviceIds.length) + "】个服务，成功新增【" + count +"】个");
		}
		map.put("flag", "1");
		return map;
	}
	
	@RequestMapping("toEditLayerTheme")
	@RequiresPermissions(value = "sys-layerTheme-edit")
	public String toEditLayerTheme(LayerTheme layerTheme, Model model) {
		layerTheme = themeService.get(LayerTheme.class, layerTheme.getId());
		model.addAttribute("layerTheme", layerTheme);
		List<LayerTheme> lists = themeService.find("from LayerTheme t where t.type = 'r'  ");
		model.addAttribute("layerThemeTypes", lists);
		return "/layerTheme/layerTheme_edit";
	}
	
	/**
	 * 更新专题图
	 * @param layer
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateLayerTheme")
	@RequiresPermissions(value = "sys-layerTheme-edit")
	public Map<String,String> updateLayerTheme(LayerTheme layerTheme, Model model,HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		LayerTheme dbLayerTheme = themeService.get(LayerTheme.class, layerTheme.getId());
		User user = (User) request.getSession().getAttribute(Global.SESSION_USER);
		try {
			BeanExtUtils.copyProperties(dbLayerTheme, layerTheme, true,true, null);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		dbLayerTheme.setUpdateDate(new Date());
		dbLayerTheme.setUpdator(user);
		themeService.update(dbLayerTheme);
		map.put("msg", "编辑成功！");
		map.put("flag", "1");
		return map;
	}
	
	@ResponseBody
	@RequestMapping("delete")
	@RequiresPermissions(value = "sys-layerTheme-delete")
	public Map<String, String> delete(String id) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if (StringUtils.isNotBlank(id)) {
			LayerTheme layerTheme = themeService.get(LayerTheme.class, Integer.parseInt(id));
			if (layerTheme != null) {
				// 判断该结点下是否有子结点
				List<LayerTheme> children = themeService.find("from LayerTheme t where t.parent.id = ?",new Object[] { layerTheme.getId() });
				if (children != null && children.size() > 0) {
					resultMap.put("msg", "该结点下有子结点，不能删除!");
					resultMap.put("flag", "2");
					return resultMap;
				}
				themeService.delete(layerTheme);
				resultMap.put("flag", "1");
				resultMap.put("msg", "删除成功！");
				return resultMap;
			}
		}
		else {
			resultMap.put("msg", "删除失败！");
			resultMap.put("flag", "2");
			return resultMap;
		}
		resultMap.put("flag", "2");
		resultMap.put("msg", "删除失败！");
		return resultMap;
	}
	
	@ResponseBody
	@RequestMapping("deletes")
	@RequiresPermissions(value = "sys-layerTheme-delete")
	public Map<String, String> deletes(String idsStr) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String ids[] = idsStr.split(",");
		int count = 0;
		if(ids != null && ids.length > 0) {
			for(String id:ids) {
				LayerTheme layerTheme = themeService.get(LayerTheme.class, Integer.parseInt(id));
				if (layerTheme != null) {
					themeService.delete(layerTheme);
					count++;
				}
			}
			if(ids.length != count) {
				resultMap.put("msg", "【"+(ids.length-count) + "】条记录没有删除");
			}
			else {
				resultMap.put("msg", "删除成功！");
			}
			resultMap.put("flag", "1");
			return resultMap;
		}
		else {
			resultMap.put("flag", "2");
			resultMap.put("msg", "删除失败！");
			return resultMap;
		}
	}
	
	/**
	 * 查看详情
	 * @param layerTheme
	 * @param model
	 * @return
	 */
	@RequestMapping("view")
	@RequiresPermissions(value = "sys-layer-list")
	public String view(LayerTheme layerTheme, Model model) {
		if (layerTheme.getId() != null) {
			layerTheme = themeService.get(LayerTheme.class, layerTheme.getId());
			model.addAttribute("layerTheme", layerTheme);
			model.addAttribute("serviceRegisterType",DataDictionary.getObject("service_register_type"));
		}
		return "/layerTheme/layerTheme_detail";
	}
}
