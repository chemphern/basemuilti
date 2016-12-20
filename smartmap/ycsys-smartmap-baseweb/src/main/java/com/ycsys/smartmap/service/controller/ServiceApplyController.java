package com.ycsys.smartmap.service.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycsys.smartmap.cluster.utils.ClusterUtils;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.entity.ServiceApply;
import com.ycsys.smartmap.service.service.ServiceApplyService;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.StringUtils;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.util.DataDictionary;

/**
 * 服务申请 controller
 * @author liweixiong
 * @date   2016年11月3日
 */
@Controller
@RequestMapping("/serviceApply")
public class ServiceApplyController {
	@Autowired
	private ServiceApplyService serviceApplyService;
	
	@Autowired
	private ServiceService serviceService;
	
	@RequestMapping("add")
	public String add(ServiceApply serviceApply,Model model,HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(
				Global.SESSION_USER);
		serviceApply = new ServiceApply();
		serviceApply.setTitle("test");
		serviceApply.setApplyDate(new Date());
		serviceApply.setApplyUser(user);
		serviceApply.setCreateDate(new Date());
		serviceApply.setCreator(user);
		Service s = serviceService.get(Service.class, 3875);
		serviceApply.setService(s);
		serviceApply.setValidDate("3个月");
		serviceApplyService.save(serviceApply);
		return "";
	}
	
	@ResponseBody
	@RequestMapping("/listData")
	public Grid<ServiceApply> listData(String showName,String registerName,String auditStatus,String title,String validDate,PageHelper page) {
		/*List<ServiceApply> list = serviceApplyService.find("from ServiceApply r order by r.createDate desc",
					null, page);*/
		
		List<ServiceApply> list = null;
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("from ServiceApply t where 1 = 1 ");
		
		if (StringUtils.isNotBlank(showName)) {
			hql.append("and t.showName like ? ");
			params.add('%' + showName + '%');
		}
		
		if (StringUtils.isNotBlank(registerName)) {
			hql.append("and t.registerName like ? ");
			params.add('%' + registerName + '%');
		}
		if (StringUtils.isNotBlank(auditStatus)) {
			hql.append("and t.auditStatus = ? ");
			params.add(auditStatus);
		} 
		
		if (StringUtils.isNotBlank(title)) {
			hql.append("and t.title like ? ");
			params.add('%' + title + '%');
		}
		
		if (StringUtils.isNotBlank(validDate)) {
			hql.append("and t.validDate = ? ");
			params.add(validDate);
		} 
		
		hql.append("order by t.createDate desc ");
		list = serviceApplyService.find(hql.toString(), params, page);
		long count= serviceApplyService.count(hql.toString(), params);
		Grid<ServiceApply> g = new Grid<ServiceApply>(count,list);
		return g;
	}
	
	
	@RequestMapping("toAudit")
	public String toAudit(ServiceApply serviceApply,Model model) {
		if(null != serviceApply.getId()) {
			serviceApply = serviceApplyService.get(ServiceApply.class, serviceApply.getId());
			model.addAttribute("validDate", DataDictionary.getObject("service_valid_date"));
			model.addAttribute("auditStatus", DataDictionary.getObject("audit_status"));
			model.addAttribute("serviceApply", serviceApply);
		}
		return "/service/service_audit_use";
	}
	
	@RequestMapping("audit")
	@ResponseBody
	public Map<String,String> audit(ServiceApply serviceApply, Model model,
			HttpServletRequest request) {
		Map<String,String> map = new HashMap<String,String>();
		try {
			ServiceApply dbObj = serviceApplyService.get(ServiceApply.class, serviceApply.getId());
			BeanExtUtils.copyProperties(dbObj, serviceApply, true, true,null);
			User user = (User) request.getSession().getAttribute(
					Global.SESSION_USER);
			dbObj.setAuditor(user);
			dbObj.setAuditDate(new Date());
			serviceApplyService.update(dbObj);
			map.put("flag", "0");
			map.put("msg", "审核成功！");
		} catch (Exception e) {
			map.put("flag", "1");
			map.put("msg", "审核失败！");
		} 
		
		return map;
	}
	
	@RequestMapping("delete")
	public String delete(ServiceApply serviceApply) {
		serviceApplyService.delete(serviceApply);
		return "";
	}
	
	@RequestMapping("update")
	public String update(ServiceApply serviceApply,Model model) {
		serviceApplyService.update(serviceApply);
		return "";
	}
	
	@RequestMapping("list")
	public String list(Model model) {
		model.addAttribute("validDate", DataDictionary.getObject("service_valid_date"));
		model.addAttribute("auditStatus", DataDictionary.getObject("audit_status"));
		return "/service/service_audit_use_list";
	}
	
	/**
	 * 支持删除多条记录
	 * 
	 * @param idsStr
	 * @return
	 */
	@RequestMapping("deletes")
	@ResponseBody
	public Map<String,String> deletes(String idsStr) {
		Map<String,String> map = new HashMap<String,String>();
		String ids[] = idsStr.split(",");
		if (ids != null && ids.length > 0) {
			try {
				for (String id : ids) {
					ServiceApply service = serviceApplyService.get(ServiceApply.class,
							Integer.parseInt(id));
					if (service != null) {
						serviceApplyService.delete(service);
					}
				}
				map.put("msg", "删除成功！");
				return map;
			} catch (Exception e) {
				map.put("msg", "删除失败！");
				return map;
			}
		} else {
			map.put("msg", "删除失败！");
		}
		return map;
	}
	
	/**
	 * 查看详情
	 * 
	 * @param serviceApply
	 * @param model
	 * @return
	 */
	@RequestMapping("view")
	public String view(ServiceApply serviceApply, Model model) {
		if (serviceApply.getId() != null) {
			serviceApply = serviceApplyService.get(ServiceApply.class, serviceApply.getId());
			model.addAttribute("serviceApply", serviceApply);
			model.addAttribute("validDate", DataDictionary.getObject("service_valid_date"));
			model.addAttribute("auditStatus", DataDictionary.getObject("audit_status"));
			model.addAttribute("clusterNames", ClusterUtils.lists());
		}
		return "/service/service_apply_view";
	}
}
