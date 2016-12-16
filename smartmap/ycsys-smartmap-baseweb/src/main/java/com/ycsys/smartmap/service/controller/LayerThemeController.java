package com.ycsys.smartmap.service.controller;

import java.lang.reflect.InvocationTargetException;
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

/**
 * 专题图管理 controller
 * 
 * @author lrr
 * @date 2016年12月5日
 */
@Controller
@RequestMapping("/layerTheme")
public class LayerThemeController {
	private static Logger log = Logger.getLogger(LayerThemeController.class);
	
	@Autowired
	private ThemeService themeService;
	
	@Autowired
	private ServiceService serviceService;
	
	//列表列出所有数据
	@RequestMapping("list")
	public String list(Model model){
		List<LayerTheme> list = themeService.find("from LayerTheme l where 1=1 ");
		model.addAttribute("list", list);
		return "layerTheme/layerTheme_list";
	}
		
	//查询出页面表格需要的数据
	/*@ResponseBody
	@RequestMapping("/listData")
	public Grid<LayerTheme> listData(String layerThemeId, PageHelper page) {

		List<LayerTheme> list = null;
		LayerTheme ly = null;
		list = themeService.find("from LayerTheme c where 1 = 1",null, page);
		
		if (StringUtils.isNotBlank(layerThemeId)) {
			Integer id = Integer.parseInt(layerThemeId);
			ly = themeService.get(LayerTheme.class, id);
			//如果是根结点则查所有
			if(ly.getParent() == null) {
				list = themeService.find("from LayerTheme r ",null, page);
			}else {
				list = themeService
						.find("from LayerTheme r where r.parent.id = ? ",
								new Object[] { id }, page);
			}
			list = themeService
					.find("from LayerTheme r where r.parent.id = ? ",
							new Object[] { id }, page);
		} 
		return new Grid<LayerTheme>(list);
	}*/
	
	@ResponseBody
	@RequestMapping("/listData")
	public Grid<LayerTheme> listData(String pId, PageHelper page) {

		List<LayerTheme> list = null;
		LayerTheme ly = null;
		list = themeService.find("from LayerTheme c where 1 = 1",null, page);
		
		if (StringUtils.isNotBlank(pId)) {
			Integer id = Integer.parseInt(pId);
			ly = themeService.get(LayerTheme.class, id);
			//如果是根结点则查所有
			/*if(ly.getParent() == null) {
				list = themeService.find("from LayerTheme r ",null, page);
			}else {
				list = themeService
						.find("from LayerTheme r where r.parent.id = ? ",
								new Object[] { id }, page);
			}*/
			list = themeService
					.find("from LayerTheme r where r.pId = ? ",
							new Object[] { id }, page);
		} 
		return new Grid<LayerTheme>(list);
	}
	
	/**
	 * 把所有专题图管理转成json字符串
	 */
	@ResponseBody
	@RequestMapping(value = "listAll", produces = "application/json;charset=UTF-8")
	public String listAll(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<LayerTheme> lists = themeService
				.find("from LayerTheme l where 1 = 1");
		for (LayerTheme e : lists) {
			Map<String, Object> map = Maps.newHashMap();
			//map.put("id", e.getId());
			//map.put("pid", e.getParent() != null ? e.getParent().getId() : 0);
			map.put("id", e.getId());
			map.put("pid", e.getpId());
			map.put("text", e.getName());
			mapList.add(map);
		}
		String jsonStr = JsonMapper.toJsonString(mapList);
		return jsonStr;
	}
	
	/*@RequestMapping("toEdit")
	public String toEdit(String flag, String actionNodeID, Model model) {
		log.debug("flag="+flag);
		LayerTheme layerTheme = null;
		//新增
		if ("1".equals(flag)) {
			layerTheme = new LayerTheme();
			if(StringUtils.isNotBlank(actionNodeID)) {
				LayerTheme parent = themeService.get(LayerTheme.class, Integer.parseInt(actionNodeID));
				layerTheme.setParent(parent);
			}
			model.addAttribute("layerTheme", layerTheme);
		}
		//修改
		else {
			layerTheme = themeService.get(
					LayerTheme.class, Integer.parseInt(actionNodeID));
			model.addAttribute("layerTheme", layerTheme);
		}
		List<Service> serviceList = serviceService.find("from Service s where 1=1 ");
		model.addAttribute("serviceList", serviceList);
		return "/layerTheme/layerTheme_edit";
	}*/
	@RequestMapping("toEdit")
	public String toEdit(String flag, String actionNodeID, Model model) {
		log.debug("flag="+flag);
		LayerTheme layerTheme = null;
		System.out.println("actionNodeID="+actionNodeID);
		//新增
		if ("1".equals(flag)) {
			layerTheme = new LayerTheme();
			if(StringUtils.isNotBlank(actionNodeID)) {
				layerTheme.setpId(Integer.parseInt(actionNodeID));
				System.out.println("actionNodeID="+actionNodeID);
				//model.addAttribute("pid",layerTheme.getPId());
				String name = themeService.get(LayerTheme.class, layerTheme.getpId()).getName();
				model.addAttribute("pid",name);
			}else{
				layerTheme.setpId(-1);
				//model.addAttribute("pid",-1);
				model.addAttribute("pid","");
				//layerTheme.setParentId(-1+"");
			}
			model.addAttribute("layerTheme", layerTheme);
		}
		//修改
		else {
			layerTheme = themeService.get(LayerTheme.class, Integer.parseInt(actionNodeID));
			if (layerTheme.getpId()==-1) {
				model.addAttribute("pid","");
				model.addAttribute("layerTheme", layerTheme);
			}else{
				model.addAttribute("layerTheme", layerTheme);
				//System.out.println(layerTheme.getName());
				Integer pId2 = layerTheme.getpId();
				//System.out.println(pId2);
				model.addAttribute("pid",pId2);
				String name = themeService.get(LayerTheme.class, pId2).getName();
				//System.out.println(name);
				model.addAttribute("pid",name);
			}
			//model.addAttribute("pid",layerTheme.getPId());
			//model.addAttribute("pid",layerTheme.getName());
		}
		return "/layerTheme/layerTheme_edit";
	}
	
	
	@RequestMapping("toEditLayerTheme")
	public String toEditLayerTheme(String flag, String actionNodeID, Model model,String serviceId, PageHelper page) {
		log.debug("flag="+flag);
		LayerTheme layerTheme = null;
		//新增
		if ("1".equals(flag)) {
			layerTheme = new LayerTheme();
			if(StringUtils.isNotBlank(actionNodeID)) {
				LayerTheme parent = themeService.get(LayerTheme.class, Integer.parseInt(actionNodeID));
				layerTheme.setParent(parent);
			}
			//List<Layer> find = layerService.find("from Layer s where s.serviceId = ?",new Object[]{serviceId},page);
			//List<Service> serviceList = serviceService.find("from Service s where 1=1 ");
			model.addAttribute("layerTheme", layerTheme);
			//model.addAttribute("serviceList", serviceList);
		}
		//修改
		else {
			layerTheme = themeService.get(LayerTheme.class, Integer.parseInt(actionNodeID));
			model.addAttribute("layerTheme", layerTheme);
		}
		List<Service> serviceList = serviceService.find("from Service s where 1=1 ");
		List<LayerTheme> lists = themeService.find("from LayerTheme l where l.pId=-1");
		model.addAttribute("serviceList", serviceList);
		model.addAttribute("lists", lists);
		return "/layerTheme/addLayerTheme_edit";
	}
	
	//保存专题图
	/*@RequestMapping("save")
	@ResponseBody
	public Map<String,String> save(LayerTheme layerTheme, Model model,
			HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		User user = (User) request.getSession().getAttribute(
				Global.SESSION_USER);
		//判断是否存在相同名字的资源分类
		List<LayerTheme> rtLists = themeService.find("from LayerTheme t where t.parent.id="+layerTheme.getParent().getId() +" and t.name='"+layerTheme.getName()+"' and t.id <>" +layerTheme.getId());
		//List<Layer> rtLists2 = themeService.find("from Layer t where t.parent.id=? and t.name = ? and t.id <> ?",new Object[]{layer.getParent().getId(),layer.getName(),layer.getId()});
		if(rtLists != null && rtLists.size() > 0) {
			log.warn("存在相同的专题图");
			map.put("msg", "存在相同的专题图！");
			map.put("flag", "2");
			return map;
		}
		// 新增
		if(null == layerTheme.getId()) {
			Integer parentId = layerTheme.getParent().getId();
			//根结点没有父亲结点，所以为空
			if(parentId == null) {
				layerTheme.setParent(null);
				themeService.save(layerTheme);
				map.put("msg", "新增成功！");
				map.put("flag", "1");
			}else{
				LayerTheme parent = layerTheme.getParent();
				LayerTheme pp = themeService.get(LayerTheme.class, parent.getId());
				if (pp!=null) {
					if (pp.getParent()==null) {
						themeService.save(layerTheme);
						map.put("msg", "新增成功！");
						map.put("flag", "1");
					}else{
						map.put("msg", "专题图节点不能再新增节点！");
						map.put("flag", "1");
					}
				}else{
					themeService.save(layerTheme);
					map.put("msg", "新增成功！");
					map.put("flag", "1");
				}
			}
		}
		// 修改
		else {
			LayerTheme dBLayer = themeService.get(
					LayerTheme.class, layerTheme.getId());
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
			map.put("flag", "3");
		}
		return map;
	}*/
	@RequestMapping("save")
	@ResponseBody
	public Map<String,String> save(LayerTheme layerTheme, Model model,
			HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		//判断是否存在相同名字的资源分类
		List<LayerTheme> rtLists = themeService.find("from LayerTheme t where t.pId="+layerTheme.getpId() +" and t.name='"+layerTheme.getName()+"' and t.id <>" +layerTheme.getId());
		//List<Layer> rtLists2 = themeService.find("from Layer t where t.parent.id=? and t.name = ? and t.id <> ?",new Object[]{layer.getParent().getId(),layer.getName(),layer.getId()});
		if(rtLists != null && rtLists.size() > 0) {
			log.warn("存在相同的专题图");
			map.put("msg", "存在相同的专题图！");
			map.put("flag", "2");
			return map;
		}
		// 新增
		if(null == layerTheme.getId()) {
			Integer pId = layerTheme.getpId();
				if(pId != null ) {
					if (pId==-1) {
						themeService.save(layerTheme);
						map.put("msg", "新增成功！");
						map.put("flag", "1");
					}else{
						LayerTheme pp = themeService.get(LayerTheme.class, pId);
						//System.out.println(pp.getName());
						Integer pId2 = pp.getpId();
						LayerTheme p2 = themeService.get(LayerTheme.class, pId2);
						
						if (p2!=null) {
							Integer pId3 = p2.getpId();
							//System.out.println("================"+pId3);
							if (pId3==-1) {
								map.put("msg", "图层节点不能再新增节点！");
								map.put("flag", "1");
							}else{
								themeService.save(layerTheme);
								map.put("msg", "新增成功！");
								map.put("flag", "1");
							}
						}else{
							themeService.save(layerTheme);
							map.put("msg", "新增成功！");
							map.put("flag", "1");
						}
					}
					
				}else{
					themeService.save(layerTheme);
					map.put("msg", "新增成功！");
					map.put("flag", "1");
				}
			}
		// 修改
		else {
			LayerTheme dBLayer = themeService.get(
					LayerTheme.class, layerTheme.getId());
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
			map.put("flag", "3");
		}
		return map;
	}
	
	//删除单个专题图
	/*@RequestMapping("delete")
	@ResponseBody
	public Map<String, String> delete(String id) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", "0");
		if (StringUtils.isNotBlank(id)) {
			LayerTheme layerTheme = themeService.get(
					LayerTheme.class, Integer.parseInt(id));
			if (layerTheme != null) {
				// 判断该结点下是否有子结点 或 已上传资源
				List<LayerTheme> children = themeService.find(
						"from LayerTheme t where t.parent = ?",
						new Object[] { layerTheme });
				if (children != null && children.size() > 0) {
					resultMap.put("result", "1");
					return resultMap;
				}
				themeService.delete(layerTheme);
				List<LayerTheme> lists = themeService
						.find("from LayerTheme t");
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
	}*/
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, String> delete(String id) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", "0");
		if (StringUtils.isNotBlank(id)) {
			LayerTheme layerTheme = themeService.get(
					LayerTheme.class, Integer.parseInt(id));
			if (layerTheme != null) {
				// 判断该结点下是否有子结点 或 已上传资源
				List<LayerTheme> children = themeService.find(
						"from LayerTheme t where t.pId = ?",
						new Object[] { layerTheme.getId() });
				if (children != null && children.size() > 0) {
					resultMap.put("result", "1");
					return resultMap;
				}
				themeService.delete(layerTheme);
				resultMap.put("result", "3");
				return resultMap;
			}
		}
		else {
			resultMap.put("result", "2");
			return resultMap;
		}
		return resultMap;
	}
	
	/*
	 * 删除多个专题图管理
	 */
	/*@RequestMapping("deletes")
	@ResponseBody
	public Map<String, String> deletes(String idsStr) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String ids[] = idsStr.split(",");
		if(ids != null && ids.length > 0) {
			for(String id:ids) {
				LayerTheme layerTheme = themeService.get(LayerTheme.class, Integer.parseInt(id));
				if (layerTheme != null) {
					// 判断该结点下是否有子结点
					List<LayerTheme> children = themeService.find(
							"from LayerTheme t where t.parent = ?",
							new Object[] { layerTheme });
					if (children != null && children.size() > 0) {
						resultMap.put("result", "1");
						return resultMap;
					}
					themeService.delete(layerTheme);
					List<LayerTheme> lists = themeService
							.find("from LayerTheme t");
					if(lists == null || lists.size() < 1) {
						resultMap.put("showBtnFlag", "1"); //把所有结点都删除了，这时需要把新增按钮显示
					}
				}
			}
		}
		else {
			resultMap.put("result", "2");
			return resultMap;
		}
		return resultMap;
	}*/
	@RequestMapping("deletes")
	@ResponseBody
	public Map<String, String> deletes(String idsStr) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String ids[] = idsStr.split(",");
		if(ids != null && ids.length > 0) {
			for(String id:ids) {
				LayerTheme layerTheme = themeService.get(LayerTheme.class, Integer.parseInt(id));
				if (layerTheme != null) {
					// 判断该结点下是否有子结点
					List<LayerTheme> children = themeService.find(
							"from LayerTheme t where t.pId = ?",
							new Object[] { layerTheme.getId() });
					if (children != null && children.size() > 0) {
						resultMap.put("result", "1");
						return resultMap;
					}
					themeService.delete(layerTheme);
					resultMap.put("result", "3");
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
	 * 查看详情
	 * @return
	 */
	@RequestMapping("detail")
	public String view(LayerTheme layerTheme, Model model) {
		if (layerTheme.getId() != null) {
			layerTheme = themeService.get(LayerTheme.class, layerTheme.getId());
			model.addAttribute("layerTheme", layerTheme);
			List<Service> serviceList = serviceService.find("from Service s where 1=1 ");
			List<LayerTheme> lists = themeService.find("from Layer l where l.pId=-1");
			model.addAttribute("lists", lists);
			model.addAttribute("serviceList", serviceList);
		}
		return "/layerTheme/layerTheme_detail";
	}
}
