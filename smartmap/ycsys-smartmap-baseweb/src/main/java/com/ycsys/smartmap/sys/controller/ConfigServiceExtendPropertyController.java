package com.ycsys.smartmap.sys.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.entity.ConfigServiceExtendProperty;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.ConfigServiceExtendPropertyService;
/**
 * 服务扩展属性配置 controller
 * @author linrongren
 * @date   2016年11月17日
 */
@Controller
@RequestMapping("/configServiceExtendProperty")
public class ConfigServiceExtendPropertyController {
	
	@Autowired
	private ConfigServiceExtendPropertyService configServiceExtendPropertyService;


	@RequestMapping("toEdit")
	public String toEdit(String flag, ConfigServiceExtendProperty configServiceExtendProperty, Model model) {
		// 新增
		if (null == configServiceExtendProperty.getId()) {

		}
		// 修改
		else {
			configServiceExtendProperty = configServiceExtendPropertyService.get(ConfigServiceExtendProperty.class, configServiceExtendProperty.getId());
			model.addAttribute("configServiceExtendProperty", configServiceExtendProperty);
		}
		return "/configServiceExtendProperty/configServiceExtendProperty_add";
	}
	
	//保存服务引擎配置方法
	@RequestMapping("save")
	@ResponseBody
	public String save(ConfigServiceExtendProperty configServiceExtendProperty,Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Global.SESSION_USER);
		//新增
		if(configServiceExtendProperty.getId()==null){
			configServiceExtendProperty.setCreateDate(new Date());
			configServiceExtendProperty.setCreator(user);
			configServiceExtendPropertyService.save(configServiceExtendProperty);
		}
		//更新
		else{
			ConfigServiceExtendProperty dbConfigServerExtendProperty = configServiceExtendPropertyService.get(ConfigServiceExtendProperty.class,
					configServiceExtendProperty.getId());
			try {
				// 得到修改过的属性
				BeanExtUtils.copyProperties(dbConfigServerExtendProperty, configServiceExtendProperty, true, true,
						null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			configServiceExtendProperty.setUpdateDate(new Date());
			configServiceExtendProperty.setUpdator(user);
			configServiceExtendPropertyService.update(dbConfigServerExtendProperty);
		}
		
		return "success";
	}
		
	//删除多条服务引擎扩展属性
	/*@RequestMapping("deletes")
	@ResponseBody
	public String delete(String idsStr) {
		String ids[] = idsStr.split(",");
		if(ids != null && ids.length > 0) {
			for(String id:ids) {
				ConfigServiceExtendProperty configServiceExtendProperty = configServiceExtendPropertyService.get(ConfigServiceExtendProperty.class, Integer.parseInt(id));
				if(configServiceExtendProperty != null) {
					configServiceExtendPropertyService.delete(configServiceExtendProperty);
				}
			}
			return "success";
		}
		else {
			return "failure";
		}
	}*/
	
	@RequestMapping("deletes")
	@ResponseBody
	public ResponseEx delete(String idsStr) {
		ResponseEx ex = new ResponseEx();
		String ids[] = idsStr.split(",");
		if(ids != null && ids.length > 0) {
			for(String id:ids) {
				ConfigServiceExtendProperty configServiceExtendProperty = configServiceExtendPropertyService.get(ConfigServiceExtendProperty.class, Integer.parseInt(id));
				if(configServiceExtendProperty != null) {
					configServiceExtendPropertyService.delete(configServiceExtendProperty);
				}
			}
			ex.setSuccess("删除成功");
		}
		else {
			ex.setFail("删除失败");
		}
		return ex;
	}
	/**
	 * 删除单条服务引擎扩展属性
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public ResponseEx delete(ConfigServiceExtendProperty configServiceExtendProperty){
        ResponseEx ex = new ResponseEx();
        try{
        	configServiceExtendPropertyService.delete(configServiceExtendProperty);
            ex.setSuccess("删除成功");
        }catch (Exception e){
            ex.setFail("删除失败");
        }
        return ex;
    }
	
	@RequestMapping("update")
	public String update(ConfigServiceExtendProperty configServiceExtendProperty,Model model) {
		configServiceExtendPropertyService.update(configServiceExtendProperty);
		return "";
	}
	
	//列表列出所有数据
	@RequestMapping("list")
	public String list(Model model){
		List<ConfigServiceExtendProperty> list = configServiceExtendPropertyService.find("from ConfigServiceExtendProperty c where 1=1 ");
		model.addAttribute("list", list);
		return "configServiceExtendProperty/configServiceExtendProperty_list";
	}
	
	//查询出填充数据表格的数据
	@ResponseBody
	@RequestMapping("/listData")
	public Grid<ConfigServiceExtendProperty> listData(PageHelper page) {
		Grid g = new Grid(configServiceExtendPropertyService.find("from ConfigServiceExtendProperty c where 1 = 1",
				null, page));
		return g;
	}
}
