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
import com.ycsys.smartmap.service.entity.Layer;
import com.ycsys.smartmap.service.service.LayerService;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.service.utils.ServiceUtils;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.JsonMapper;
import com.ycsys.smartmap.sys.common.utils.StringUtils;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.util.DataDictionary;

/**
 * 图层 controller
 * @author liweixiong
 * @date   2016年12月19日
 */
@Controller
@RequestMapping("/layer")
public class LayerController {
	private static Logger log = LoggerFactory.getLogger(LayerController.class);
	
	@Autowired
	private LayerService layerService;
	@Autowired
	private ServiceService serviceService;
	
	/**
	 * 列表列出所有数据
	 * @param model
	 * @return
	 */
	@RequestMapping("list")
	@RequiresPermissions(value = "sys-layer-list")
	public String list(Model model){
		model.addAttribute("geometryType", DataDictionary.getObject("geometry_type"));
		model.addAttribute("serviceRegisterType",DataDictionary.getObject("service_register_type"));
		return "layer/layer_list";
	}
	
	/**
	 * 把所有图层目录转成json字符串
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys-layer-list-data")
	@RequestMapping(value = "listLayerType", produces = "application/json;charset=UTF-8")
	public String listLayerType(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Layer> lists = layerService.find("from Layer l where 1 = 1");
		for (Layer e : lists) {
			Map<String, Object> map = Maps.newHashMap();
			//map.put("pid", e.getParent() != null ? e.getParent().getId() : 0);
			map.put("id", e.getId());
			map.put("pid", e.getParent() != null ? e.getParent().getId() : -1);
			//map.put("pid", e.getPId());
			map.put("text", e.getName());
			mapList.add(map);
		}
		String jsonStr = JsonMapper.toJsonString(mapList);
		return jsonStr;
	}
	
	/**
	 * 查询出页面表格需要的数据
	 * @param pId
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions(value = "sys-layer-list-data")
	public Grid<Layer> listData(String pId, PageHelper page) {
		List<Layer> list = null;
		list = layerService.find("from Layer c where 1 = 1 and c.type = 'n' ",null, page);
		if (StringUtils.isNotBlank(pId)) {
			Integer id = Integer.parseInt(pId);
			list = layerService.find("from Layer r where 1 = 1 and r.type = ? and  r.pId = ? ",new Object[] {"n",id }, page);
		} 
		return new Grid<Layer>(list);
	}
	
	/**
	 * 准备编辑图层树
	 * @param layer
	 * @param actionNodeID(父亲结点 id)
	 * @param model
	 * @return
	 */
	@RequestMapping("toEditLayerType")
	@RequiresPermissions(value = "sys-layer-edit")
	public String toEditLayerType(Layer layer, String actionNodeID, Model model) {
		//新增
		if(layer.getId() == null) {
			if(StringUtils.isNotBlank(actionNodeID)) {
				Layer parent = layerService.get(Layer.class, Integer.parseInt(actionNodeID));
				layer.setParent(parent);
			}
		}
		//修改
		else {
			layer = layerService.get(Layer.class, layer.getId());
		}
		model.addAttribute("layer", layer);
		
		return "/layer/layer_type_edit";
	}
	
	/**
	 * 保存图层树
	 */
	@ResponseBody
	@RequestMapping("saveLayerType")
	@RequiresPermissions(value = "sys-layer-edit")
	public Map<String,String> saveLayerType(Layer layer, Model model,
			HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		User user = (User) request.getSession().getAttribute(Global.SESSION_USER);
		//判断是否存在相同名字的图层目录
		List<Layer> rtLists = layerService.find("from Layer t where t.parent.id = " + layer.getParent().getId() + " and t.name='"+layer.getName()+"' and t.id <>" +layer.getId());
		if(rtLists != null && rtLists.size() > 0) {
			log.warn("存在相同的图层目录");
			map.put("msg", "存在相同的图层目录！");
			map.put("flag", "2");
			return map;
		}
		// 新增
		if(null == layer.getId()) {
			Integer pId = layer.getParent().getId();
			if(pId != null ) {
				//判断是否可以增加
				Layer parent = layerService.get(Layer.class, pId);
				if(parent != null && "n".equals(parent.getType())) {
					map.put("msg", "图层结点不能增加图层目录！");
					return map;
				}
				layer.setPId(pId);
			}else{
				layer.setParent(null);
				layer.setPId(-1);
			}
			layer.setType("r");
			layer.setCreateDate(new Date());
			layer.setCreator(user);
			layerService.save(layer);
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
			layer.setUpdateDate(new Date());
			layer.setUpdator(user);
			layerService.update(dBLayer);
			map.put("msg", "修改成功！");
			map.put("flag", "1");
		}
		return map;
	}
	
	/**
	 * 准备增加图层
	 * @param actionNodeID
	 * @param layer
	 * @param model
	 * @return
	 */
	@RequestMapping("toAddLayer")
	@RequiresPermissions(value = "sys-layer-create")
	public String toAddLayer(String layerTypeId,Layer layer, Model model) {
		model.addAttribute("layer", layer);
		//List<Service> serviceList = serviceService.find("from Service t where 1=1 and t.auditStatus = '1' ");
		//List<Service> serviceList = serviceService.find("from Service t ");
		List<Layer> lists = layerService.find("from Layer t where t.type = 'r' ");
		model.addAttribute("layerTypeId", layerTypeId);
		//model.addAttribute("serviceList", serviceList);
		model.addAttribute("geometryType", DataDictionary.getObject("geometry_type"));
		model.addAttribute("lists", lists);
		return "/layer/layer_add";
	}
	
	/**
	 * 增加图层
	 * @param layer
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addLayer")
	@RequiresPermissions(value = "sys-layer-create")
	public Map<String,String> addLayer(Layer layer, Model model,HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		String names [] = layer.getNames().split(",");
		String ids [] = layer.getIds().split(",");
		User user = (User) request.getSession().getAttribute(Global.SESSION_USER);
		layer.setType("n");
		
		Layer parent = layerService.get(Layer.class, layer.getParent().getId());
		if(parent != null && StringUtils.isBlank(parent.getAddress())) {
			parent.setAddress(layer.getService().getServiceVisitAddress());
		}
		long count = 0;
		for(int i = 0; i < names.length; i++) {
			Layer newLayer = new Layer();
			try {
				BeanExtUtils.copyProperties(newLayer, layer, true,true, null);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			newLayer.setPId(layer.getParent().getId());
			newLayer.setName(names[i]);
			String address = layer.getService().getServiceVisitAddress() + "/" + ids[i];
			newLayer.setAddress(address);
			List<Layer> rtLists = layerService.find("from Layer t where t.parent.id="+newLayer.getParent().getId() +" and t.name='"+newLayer.getName()+"' and t.id <>" +newLayer.getId());
			if(rtLists != null && rtLists.size() > 0) {
				log.warn("存在相同的图层");
				continue;
			}
			
			newLayer.setUpdateDate(new Date());
			newLayer.setUpdator(user);
			layerService.save(newLayer);
			count++;
		}
		if(count == names.length) {
			map.put("msg", "新增成功！");
		}
		else {
			map.put("msg", "一共【" + (names.length) + "】个图层，成功新增【" + count +"】个");
		}
		map.put("flag", "1");
		return map;
	}
	
	/**
	 * 修改图层
	 * @param layer
	 * @param model
	 * @param page
	 * @return
	 */
	@RequestMapping("toEditLayer")
	@RequiresPermissions(value = "sys-layer-edit")
	public String toEditLayer(Layer layer, Model model) {
		layer = layerService.get(Layer.class, layer.getId());
		List<Layer> layerTypes = layerService.find("from Layer t where t.type = 'r' ");
		model.addAttribute("layerTypes", layerTypes);
		model.addAttribute("layer", layer);
		return "/layer/layer_edit";
	}
	
	/**
	 * 选择图层的域
	 * @param layer
	 * @param flag 选择不同的域(1:nameField；2：summaryFields:3：displayFields)
	 * @param model
	 * @return
	 */
	@RequestMapping("toSelectFields")
	@RequiresPermissions(value = "sys-select-fields")
	public String toSelectFields(Layer layer,String flag, Model model) {
		model.addAttribute("flag",flag);
		model.addAttribute("address",layer.getAddress());
		return "/layer/layer_select_fields";
	}
	
	/**
	 * 选择图层的域
	 * @param layer
	 * @param serviceVisitAddress
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/listFields")
	@RequiresPermissions(value = "sys-select-fields")
	public Grid<Layer> listFields(Layer layer,String serviceVisitAddress, PageHelper page) {
		List<Layer> fields = ServiceUtils.getFields(layer.getAddress());
		Grid<Layer> g = new Grid<Layer>(fields);
		return g;
	}
	
	/**
	 * 更新图层
	 * @param layer
	 * @param model
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("updateLayer")
	@RequiresPermissions(value = "sys-layer-edit")
	public Map<String,String> updateLayer(Layer layer, Model model,HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		Layer dbLayer = layerService.get(Layer.class, layer.getId());
		User user = (User) request.getSession().getAttribute(Global.SESSION_USER);
		try {
			BeanExtUtils.copyProperties(dbLayer, layer, true,true, null);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		dbLayer.setUpdateDate(new Date());
		dbLayer.setUpdator(user);
		layerService.update(dbLayer);
		
		map.put("msg", "编辑成功！");
		map.put("flag", "1");
		return map;
	}
	
	@ResponseBody
	@RequestMapping("delete")
	@RequiresPermissions(value = "sys-layer-delete")
	public Map<String, String> delete(String id) {
		Map<String, String> resultMap = new HashMap<String, String>();
		if (StringUtils.isNotBlank(id)) {
			Layer layer = layerService.get(Layer.class, Integer.parseInt(id));
			if (layer != null) {
				// 判断该结点下是否有子结点
				List<Layer> children = layerService.find("from Layer t where t.parent.id = ?",new Object[] { layer.getId() });
				if (children != null && children.size() > 0) {
					resultMap.put("msg", "该结点下有子结点，不能删除!");
					resultMap.put("flag", "2");
					return resultMap;
				}
				layerService.delete(layer);
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
	@RequiresPermissions(value = "sys-layer-delete")
	public Map<String, String> deletes(String idsStr) {
		Map<String, String> resultMap = new HashMap<String, String>();
		String ids[] = idsStr.split(",");
		int count = 0;
		if(ids != null && ids.length > 0) {
			for(String id:ids) {
				Layer layer = layerService.get(Layer.class, Integer.parseInt(id));
				if (layer != null) {
					layerService.delete(layer);
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
	 * @return
	 */
	@RequestMapping("view")
	@RequiresPermissions(value = "sys-layer-view")
	public String view(Layer layer, Model model) {
		if (layer.getId() != null) {
			layer = layerService.get(Layer.class, layer.getId());
			model.addAttribute("layer", layer);
			model.addAttribute("geometryType", DataDictionary.getObject("geometry_type"));
			model.addAttribute("serviceRegisterType",DataDictionary.getObject("service_register_type"));
		}
		return "/layer/layer_detail";
	}
	
}
