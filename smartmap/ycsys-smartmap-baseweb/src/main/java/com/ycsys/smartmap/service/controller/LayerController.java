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
import com.ycsys.smartmap.service.entity.Layer;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.LayerService;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.JsonMapper;
import com.ycsys.smartmap.sys.common.utils.StringUtils;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.util.DataDictionary;

/**
 * 图层管理 controller
 * 
 * @author lrr
 * @date 2016年12月5日
 */
@Controller
@RequestMapping("/layer")
public class LayerController {
	private static Logger log = Logger.getLogger(LayerController.class);
	
	@Autowired
	private LayerService layerService;
	@Autowired
	private ServiceService serviceService;
	
	//列表列出所有数据
	@RequestMapping("list")
	public String list(Model model){
		List<Layer> list = layerService.find("from Layer l where 1=1 ");
		/*if (list == null || list.size() < 1) {
			// 没有数据，在页面显示新增按钮
			//model.addAttribute("emptyTreeFlag", "1");
		}*/
		//model.addAttribute("emptyTreeFlag", "1");
		model.addAttribute("list", list);
		model.addAttribute("geometryType", DataDictionary.getObject("geometry_type"));
		return "layer/layer_list";
	}
		
	//查询出页面表格需要的数据
	@ResponseBody
	@RequestMapping("/listData")
	public Grid<Layer> listData(String pId, PageHelper page) {

		List<Layer> list = null;
		Layer ly = null;
		list = layerService.find("from Layer c where 1 = 1",null, page);
		
		if (StringUtils.isNotBlank(pId)) {
			Integer id = Integer.parseInt(pId);
			ly = layerService.get(Layer.class, id);
			//如果是根结点则查所有
			/*if(ly.getParent() == null) {
				list = layerService.find("from Layer r ",null, page);
			}else {
				list = layerService
						.find("from Layer r where r.parent.id = ? ",
								new Object[] { id }, page);
			}*/
			list = layerService
					.find("from Layer r where r.pId = ? ",
							new Object[] { id }, page);
		} 
		return new Grid<Layer>(list);
	}
	
	/**
	 * 把所有图层管理转成json字符串
	 */
	@ResponseBody
	@RequestMapping(value = "listAll", produces = "application/json;charset=UTF-8")
	public String listAll(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Layer> lists = layerService
				.find("from Layer l where 1 = 1");
		for (Layer e : lists) {
			Map<String, Object> map = Maps.newHashMap();
			//map.put("id", e.getId());
			//map.put("pid", e.getParent() != null ? e.getParent().getId() : 0);
			map.put("id", e.getId());
			map.put("pid", e.getPId());
			map.put("text", e.getName());
			mapList.add(map);
		}
		String jsonStr = JsonMapper.toJsonString(mapList);
		return jsonStr;
	}
	
	/*@RequestMapping("toEdit")
	public String toEdit(String flag, String actionNodeID, Model model) {
		log.debug("flag="+flag);
		//System.out.println("parent============");
		System.out.println("flag============"+flag);
		System.out.println("actionNodeID============"+actionNodeID);
		
		Layer layer = null;
		//新增
		if ("1".equals(flag)) {
			layer = new Layer();
			if(StringUtils.isNotBlank(actionNodeID)) {
				Layer parent = layerService.get(Layer.class, Integer.parseInt(actionNodeID));
				layer.setParent(parent);
				System.out.println("parent============"+parent.getPId());
			}
			System.out.println("layer============="+layer.toString());
			model.addAttribute("layer", layer);
		}
		//修改
		else {
			layer = layerService.get(Layer.class, Integer.parseInt(actionNodeID));
			model.addAttribute("layer", layer);
		}
		List<Service> serviceList = serviceService.find("from Service s where 1=1 ");
		model.addAttribute("serviceList", serviceList);
		return "/layer/layer_edit";
	}*/
	
	@RequestMapping("toEdit")
	public String toEdit(String flag, String actionNodeID, Model model) {
		log.debug("flag="+flag);
		Layer layer = null;
		System.out.println("actionNodeID="+actionNodeID);
		//新增
		if ("1".equals(flag)) {
			layer = new Layer();
			if(StringUtils.isNotBlank(actionNodeID)) {
				//Layer parent = layerService.get(Layer.class, Integer.parseInt(actionNodeID));
				//Integer pId = parent.getPId();
				layer.setPId(Integer.parseInt(actionNodeID));
				//layer.setId(Integer.parseInt(actionNodeID));
				//layer.setParentId(actionNodeID);
				System.out.println("actionNodeID="+actionNodeID);
				//model.addAttribute("pid",layer.getPId());
				String name = layerService.get(Layer.class, layer.getPId()).getName();
				model.addAttribute("pid",name);
			}else{
				layer.setPId(-1);
				//model.addAttribute("pid",-1);
				model.addAttribute("pid","");
				//layer.setParentId(-1+"");
			}
			model.addAttribute("layer", layer);
			//model.addAttribute("pid",-1);
		}
		//修改
		else {
			layer = layerService.get(Layer.class, Integer.parseInt(actionNodeID));
			if (layer.getPId()==-1) {
				model.addAttribute("pid","");
				model.addAttribute("layer", layer);
			}else{
				model.addAttribute("layer", layer);
				//System.out.println(layer.getName());
				Integer pId2 = layer.getPId();
				//System.out.println(pId2);
				model.addAttribute("pid",pId2);
				String name = layerService.get(Layer.class, pId2).getName();
				//System.out.println(name);
				model.addAttribute("pid",name);
			}
			//model.addAttribute("pid",layer.getPId());
			//model.addAttribute("pid",layer.getName());
		}
		return "/layer/layer_edit";
	}
	
	@RequestMapping("toEditLayer")
	public String toEditLayer(String flag, String actionNodeID, Model model,String serviceId, PageHelper page) {
		log.debug("flag="+flag);
		Layer layer = null;
		//新增
		if ("1".equals(flag)) {
			layer = new Layer();
			if(StringUtils.isNotBlank(actionNodeID)) {
				Layer parent = layerService.get(Layer.class, Integer.parseInt(actionNodeID));
				layer.setParent(parent);
			}
			//List<Layer> find = layerService.find("from Layer s where s.serviceId = ?",new Object[]{serviceId},page);
			//List<Service> serviceList = serviceService.find("from Service s where 1=1 ");
			model.addAttribute("layer", layer);
			//model.addAttribute("serviceList", serviceList);
		}
		//修改
		else {
			layer = layerService.get(Layer.class, Integer.parseInt(actionNodeID));
			model.addAttribute("layer", layer);
		}
		List<Service> serviceList = serviceService.find("from Service s where 1=1 ");
		List<Layer> lists = layerService.find("from Layer l where l.pId=-1");
		model.addAttribute("serviceList", serviceList);
		model.addAttribute("geometryType", DataDictionary.getObject("geometry_type"));
		model.addAttribute("lists", lists);
		return "/layer/addLayer_edit";
	}
	
	//保存图层
	/*@RequestMapping("save")
	@ResponseBody
	public Map<String,String> save(Layer layer, Model model,
			HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		//User user = (User) request.getSession().getAttribute(Global.SESSION_USER);
		//判断是否存在相同名字的图层管理
		List<Layer> rtLists = layerService.find("from Layer t where t.parent.id="+layer.getParent().getId() +" and t.name='"+layer.getName()+"' and t.id <>" +layer.getId());
		
		if(rtLists != null && rtLists.size() > 0) {
			log.warn("存在相同的图层管理");
			map.put("msg", "存在相同的图层管理！");
			map.put("flag", "2");
			return map;
		}
		// 新增
		if(null == layer.getId()) {
			Integer parentId = layer.getParent().getId();
			//System.out.println("parentId="+parentId);
			//根结点没有父亲结点，所以为空
			//判断新增节点的父节点的父节点是否存在，存在即不能继续新增节点
			if(parentId == null ) {
				layer.setParent(null);
				layerService.save(layer);
				map.put("msg", "新增成功！");
				map.put("flag", "1");
			}else{
				Layer parent = layer.getParent();
				Layer pp = layerService.get(Layer.class, parent.getId());
				if(pp!=null){
					if(pp.getParent()==null){
						layerService.save(layer);
						map.put("msg", "新增成功！");
						map.put("flag", "1");
					}else{
						map.put("msg", "图层节点不能再新增节点！");
						map.put("flag", "1");
					}
				}else {
					layerService.save(layer);
					map.put("msg", "新增成功！");
					map.put("flag", "1");
				}
			}
			//layerService.save(layer);
			//map.put("msg", "新增成功！");
			//map.put("flag", "1");
		}
		// 修改
		else {
			Layer dBLayer = layerService.get(
					Layer.class, layer.getId());
			Layer parent = dBLayer.getParent();
			try {
				// 得到修改过的属性
				BeanExtUtils.copyProperties(dBLayer, layer, true,
						true, null);
			} catch (IllegalAccessException | InvocationTargetException e) {
				map.put("msg", "修改失败！");
				return map;
			}
			//根结点没有父亲结点，所以为空
			if(parent == null) {
				dBLayer.setParent(null);
			}
			layerService.update(dBLayer);
			map.put("msg", "修改成功！");
			map.put("flag", "3");
		}
		return map;
	}*/
	
	//保存图层
		@RequestMapping("save")
		@ResponseBody
		public Map<String,String> save(Layer layer, Model model,
				HttpServletRequest request) {
			Map<String,String> map = new HashMap<String,String>();
			//判断是否存在相同名字的图层管理
			//List<Layer> rtLists = layerService.find("from Layer t where t.parent.id="+layer.getParent().getId() +" and t.name='"+layer.getName()+"' and t.id <>" +layer.getId());
			List<Layer> rtLists = layerService.find("from Layer t where t.pId="+layer.getPId() +" and t.name='"+layer.getName()+"' and t.id <>" +layer.getId());
			if(rtLists != null && rtLists.size() > 0) {
				log.warn("存在相同的图层管理");
				map.put("msg", "存在相同的图层管理！");
				map.put("flag", "2");
				return map;
			}
			// 新增
			if(null == layer.getId()) {
			Integer pId = layer.getPId();
				//System.out.println("保存时的pId="+pId);
				if(pId != null ) {
					if (pId==-1) {
						layerService.save(layer);
						map.put("msg", "新增成功！");
						map.put("flag", "1");
					}else{
						Layer pp = layerService.get(Layer.class, pId);
						//System.out.println(pp.getName());
						Integer pId2 = pp.getPId();
						Layer p2 = layerService.get(Layer.class, pId2);
						
						if (p2!=null) {
							Integer pId3 = p2.getPId();
							//System.out.println("================"+pId3);
							if (pId3==-1) {
								map.put("msg", "图层节点不能再新增节点！");
								map.put("flag", "1");
							}else{
								layerService.save(layer);
								map.put("msg", "新增成功！");
								map.put("flag", "1");
							}
						}else{
							layerService.save(layer);
							map.put("msg", "新增成功！");
							map.put("flag", "1");
						}
					}
					
				}else{
					layerService.save(layer);
					map.put("msg", "新增成功！");
					map.put("flag", "1");
				}
			}
			// 修改
			else {
				Layer dBLayer = layerService.get(
						Layer.class, layer.getId());
				Layer parent = dBLayer.getParent();
				try {
					// 得到修改过的属性
					BeanExtUtils.copyProperties(dBLayer, layer, true,
							true, null);
				} catch (IllegalAccessException | InvocationTargetException e) {
					map.put("msg", "修改失败！");
					return map;
				}
				//根结点没有父亲结点，所以为空
				if(parent == null) {
					dBLayer.setParent(null);
				}
				layerService.update(dBLayer);
				map.put("msg", "修改成功！");
				map.put("flag", "3");
			}
			return map;
		}
	
	//保存图层
		/*@RequestMapping("saveLayer")
		@ResponseBody
		public Map<String,String> saveLayer(Layer layer, Model model,
				HttpServletRequest request) {
			Map<String,String> map = new HashMap<String,String>();
			// 新增
			if(null == layer.getId()) {
				System.out.println("11111111");
				System.out.println(layer.getService());
				Layer parent = layer.getParent();
				System.out.println(parent);
				layerService.save(layer);
				System.out.println("222222222");
				map.put("msg", "新增成功！");
				map.put("flag", "1");
			}
			// 修改
			else {
				Layer dBLayer = layerService.get(Layer.class, layer.getId());
				Layer parent = dBLayer.getParent();
				try {
					// 得到修改过的属性
					BeanExtUtils.copyProperties(dBLayer, layer, true,
							true, null);
				} catch (IllegalAccessException | InvocationTargetException e) {
					map.put("msg", "修改失败！");
					return map;
				}
				//根结点没有父亲结点，所以为空
				if(parent == null) {
					dBLayer.setParent(null);
				}
				layerService.update(dBLayer);
				map.put("msg", "修改成功！");
				map.put("flag", "3");
			}
			return map;
		}*/
	
	//删除单个图层管理
	/*@RequestMapping("delete")
	@ResponseBody
	public Map<String, String> delete(String id) {
		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("result", "0");
		if (StringUtils.isNotBlank(id)) {
			Layer layer = layerService.get(
					Layer.class, Integer.parseInt(id));
			if (layer != null) {
				// 判断该结点下是否有子结点
				List<Layer> children = layerService.find(
						"from Layer t where t.parent = ?",
						new Object[] { layer });
				if (children != null && children.size() > 0) {
					resultMap.put("result", "1");
					return resultMap;
				}
				layerService.delete(layer);
				List<Layer> lists = layerService
						.find("from Layer t");
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
				Layer layer = layerService.get(
						Layer.class, Integer.parseInt(id));
				if (layer != null) {
				// 判断该结点下是否有子结点
				List<Layer> children = layerService.find(
						"from Layer t where t.pId = ?",
						new Object[] { layer.getId() });
				if (children != null && children.size() > 0) {
					resultMap.put("result", "1");
					return resultMap;
				}
					layerService.delete(layer);
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
	 * 删除多个图层管理
	 */
	/*@RequestMapping("deletes")
	@ResponseBody
	public Map<String, String> deletes(String idsStr) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String ids[] = idsStr.split(",");
		if(ids != null && ids.length > 0) {
			for(String id:ids) {
				Layer layer = layerService.get(Layer.class, Integer.parseInt(id));
				if (layer != null) {
					// 判断该结点下是否有子结点
					List<Layer> children = layerService.find(
							"from Layer t where t.parent = ?",
							new Object[] { layer });
					if (children != null && children.size() > 0) {
						resultMap.put("result", "1");
						return resultMap;
					}
					layerService.delete(layer);
					List<Layer> lists = layerService
							.find("from Layer t");
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
					Layer layer = layerService.get(Layer.class, Integer.parseInt(id));
					if (layer != null) {
						// 判断该结点下是否有子结点
						List<Layer> children = layerService.find(
								"from Layer t where t.pId = ?",
								new Object[] { layer.getId() });
						if (children != null && children.size() > 0) {
							resultMap.put("result", "1");
							return resultMap;
						}
							layerService.delete(layer);
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
	public String view(Layer layer, Model model) {
		if (layer.getId() != null) {
			layer = layerService.get(Layer.class, layer.getId());
			model.addAttribute("layer", layer);
			List<Service> serviceList = serviceService.find("from Service s where 1=1 ");
			List<Layer> lists = layerService.find("from Layer l where l.pId=-1");
			model.addAttribute("lists", lists);
			model.addAttribute("serviceList", serviceList);
			model.addAttribute("geometryType", DataDictionary.getObject("geometry_type"));
		}
		return "/layer/layer_detail";
	}
}
