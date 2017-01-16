package com.ycsys.smartmap.sys.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycsys.smartmap.resource.entity.Resource;
import com.ycsys.smartmap.resource.entity.ResourceType;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.StringUtils;
import com.ycsys.smartmap.sys.entity.ConfigServiceExtendProperty;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.ConfigServiceExtendPropertyService;

/**
 * 服务扩展属性配置 controller
 * 
 * @author lrr
 * @date 2016年11月17日
 */
@Controller
@RequestMapping("/configServiceExtendProperty")
public class ConfigServiceExtendPropertyController {

	@Autowired
	private ConfigServiceExtendPropertyService configServiceExtendPropertyService;

	@RequestMapping("toEdit")
	@RequiresPermissions(value = "sys-serviceExtendProperty-edit")
	public String toEdit(String flag,
			ConfigServiceExtendProperty configServiceExtendProperty, Model model) {
		// 新增
		if (null == configServiceExtendProperty.getId()) {

		}
		// 修改
		else {
			configServiceExtendProperty = configServiceExtendPropertyService
					.get(ConfigServiceExtendProperty.class,
							configServiceExtendProperty.getId());
			model.addAttribute("configServiceExtendProperty",
					configServiceExtendProperty);
		}
		return "/configServiceExtendProperty/configServiceExtendProperty_add";
	}

	// 保存服务引擎配置方法
	@ResponseBody
	@RequestMapping("save")
	@RequiresPermissions(value = "sys-serviceExtendProperty-edit")
	public Map<String, String> save(
			ConfigServiceExtendProperty configServiceExtendProperty,
			Model model, HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(
				Global.SESSION_USER);
		Map<String, String> map = new HashMap<String, String>();
		map.put("msg", "操作失败！");
		// 新增
		if (configServiceExtendProperty.getId() == null) {
			configServiceExtendProperty.setCreateDate(new Date());
			configServiceExtendProperty.setCreator(user);
			configServiceExtendPropertyService
					.save(configServiceExtendProperty);
			map.put("msg", "新增成功！");
			map.put("flag", "1");
		}
		// 更新
		else {
			ConfigServiceExtendProperty dbConfigServerExtendProperty = configServiceExtendPropertyService
					.get(ConfigServiceExtendProperty.class,
							configServiceExtendProperty.getId());
			try {
				// 得到修改过的属性
				BeanExtUtils.copyProperties(dbConfigServerExtendProperty,
						configServiceExtendProperty, true, true, null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			configServiceExtendProperty.setUpdateDate(new Date());
			configServiceExtendProperty.setUpdator(user);
			configServiceExtendPropertyService
					.update(dbConfigServerExtendProperty);
			map.put("msg", "修改成功！");
			map.put("flag", "1");
		}

		return map;
	}

	@ResponseBody
	@RequestMapping("deletes")
	@RequiresPermissions(value = "sys-serviceExtendProperty-delete")
	public ResponseEx delete(String idsStr) {
		ResponseEx ex = new ResponseEx();
		String ids[] = idsStr.split(",");
		if (ids != null && ids.length > 0) {
			for (String id : ids) {
				ConfigServiceExtendProperty configServiceExtendProperty = configServiceExtendPropertyService
						.get(ConfigServiceExtendProperty.class,
								Integer.parseInt(id));
				if (configServiceExtendProperty != null) {
					configServiceExtendPropertyService
							.delete(configServiceExtendProperty);
				}
			}
			ex.setSuccess("删除成功");
		} else {
			ex.setFail("删除失败");
		}
		return ex;
	}

	/**
	 * 删除单条服务引擎扩展属性
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@RequiresPermissions(value = "sys-serviceExtendProperty-delete")
	public ResponseEx delete(
			ConfigServiceExtendProperty configServiceExtendProperty) {
		ResponseEx ex = new ResponseEx();
		try {
			configServiceExtendPropertyService
					.delete(configServiceExtendProperty);
			ex.setSuccess("删除成功");
		} catch (Exception e) {
			ex.setFail("删除失败");
		}
		return ex;
	}

	@RequestMapping("update")
	@RequiresPermissions(value = "sys-serviceExtendProperty-edit")
	public String update(
			ConfigServiceExtendProperty configServiceExtendProperty, Model model) {
		configServiceExtendPropertyService.update(configServiceExtendProperty);
		return "";
	}

	// 列表列出所有数据
	@RequestMapping("list")
	@RequiresPermissions(value = "sys-serviceExtendProperty-list")
	public String list(Model model) {
		List<ConfigServiceExtendProperty> list = configServiceExtendPropertyService
				.find("from ConfigServiceExtendProperty c where 1=1 ");
		model.addAttribute("list", list);
		return "configServiceExtendProperty/configServiceExtendProperty_list";
	}

	// 查询出填充数据表格的数据
	@ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions(value = "sys-serviceExtendProperty-list-data")
	public Grid<ConfigServiceExtendProperty> listData(PageHelper page) {
		List l=null;
		Grid g = new Grid(configServiceExtendPropertyService.find(
				"from ConfigServiceExtendProperty c where 1 = 1", l, page));
		return g;
	}

	private List<ConfigServiceExtendProperty> lits;

	public List<ConfigServiceExtendProperty> getLits() {
		return lits;
	}

	public void setLits(List<ConfigServiceExtendProperty> lits) {
		this.lits = lits;
	}

	/**
	 * 模糊查询
	 */
	@ResponseBody
	@RequiresPermissions(value = "sys-serviceExtendProperty-list")
	@RequestMapping("/findSelect")
	public Grid<ConfigServiceExtendProperty> findSelect(String name,
			String showName, String type, String need, PageHelper page) {

		/*
		 * lits = configServiceExtendPropertyService
		 * .findSelect(configServiceExtendProperty);
		 */

		List<ConfigServiceExtendProperty> list = null;
		//ResourceType rt = null;
		StringBuffer hql = new StringBuffer();
		hql.append("from ConfigServiceExtendProperty c where 1 = 1 ");
		List<Object> params = new ArrayList<Object>();
		if (StringUtils.isNotBlank(name)) {
			hql.append("and c.name like ? ");
			params.add('%' + name + '%');
		}
		if (StringUtils.isNotBlank(showName)) {
			hql.append("and c.showName like ? ");
			params.add('%' + showName + '%');
		}

		list = configServiceExtendPropertyService.find(hql.toString(), params,
				page);
		long count = configServiceExtendPropertyService.count(hql.toString(),
				params);
		Grid<ConfigServiceExtendProperty> g = new Grid<>(count, list);
		return g;

	}
}
