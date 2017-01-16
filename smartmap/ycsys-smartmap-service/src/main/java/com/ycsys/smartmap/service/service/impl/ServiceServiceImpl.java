package com.ycsys.smartmap.service.service.impl;

import com.ycsys.smartmap.monitor.dao.ServiceMonitorDao;
import com.ycsys.smartmap.service.dao.ServiceDao;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.sys.common.annotation.ToLog;
import com.ycsys.smartmap.sys.common.enums.LogType;
import com.ycsys.smartmap.sys.common.exception.ServiceException;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.entity.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author liweixiong
 * @date   2016年11月3日
 */
@Transactional
@org.springframework.stereotype.Service("serviceService")
public class ServiceServiceImpl implements ServiceService {
	
	@Autowired
	private ServiceDao serviceDao;
	@Autowired
	private ServiceMonitorDao serviceMonitorDao;
	
	@ToLog(name="注册服务",type= LogType.Server)
	public Integer save(Service o) {
		// TODO Auto-generated method stub
		return (Integer) serviceDao.save(o);
	}

	@ToLog(name="删除服务",type= LogType.Server)
	public void delete(Service o) {
		//先删除服务监控数据
		serviceMonitorDao.executeHql("delete ServiceMonitor t where t.service.id = ?", new Object[]{o.getId()});
		serviceDao.delete(o);
	}
	
	@ToLog(name="修改服务",type= LogType.Server)
	public void update(Service o) {
		// TODO Auto-generated method stub
		serviceDao.update(o);
	}
	
	@ToLog(name="新增或修改服务",type= LogType.Server)
	public void saveOrUpdate(Service o) {
		// TODO Auto-generated method stub
		serviceDao.saveOrUpdate(o);
	}

	public List<Service> find(String hql) {
		// TODO Auto-generated method stub
		return serviceDao.find(hql);
	}

	public List<Service> find(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return serviceDao.find(hql, param);
	}

	public List<Service> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serviceDao.find(hql, param);
	}

	public Service get(Class<Service> c, Serializable id) {
		// TODO Auto-generated method stub
		return serviceDao.get(c, id);
	}

	public Service get(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return serviceDao.get(hql, param);
	}

	public Service get(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serviceDao.get(hql, param);
	}

	@Override
	public List<Service> find(String hql, Object[] param, PageHelper page) {
		// TODO Auto-generated method stub
		return serviceDao.find(hql, param, page);
	}

	@Override
	public List<Service> find(String hql, List<Object> param, PageHelper page) {
		// TODO Auto-generated method stub
		return serviceDao.find(hql, param, page);
	}

	@Override
	public Long count(String hql, List<Object> param) {
		return serviceDao.count(hql, param);
	}

	@Override
	public void updateMonitor(Map<String, Object> saveParam) {
		int id = (int) saveParam.get("id");
		if(saveParam.get("monitorStatus")!= null){
			String status = String.valueOf(saveParam.get("monitorStatus"));
			serviceDao.executeHql("update Service set monitorStatus = ? where id = ?",new Object[]{status,id});
		}
	}

	@Override
	public List<Service> findByPage(PageHelper pageHelper) {
		return serviceDao.find("from Service",pageHelper);
	}

	@Override
	public void updateMonitorConfig(Service service) {
		try {
			Service source = get(Service.class, service.getId());
			//监控配置修改判断
			boolean changeMonitor = false;
			boolean changeMonitorRate = false;
			if(source.getMonitorStatus() != null){
				if(!source.getMonitorStatus().equals(service.getMonitorStatus())){
					changeMonitor  = true;
				}
			}else{
				changeMonitor = true;
			}
			if(source.getMonitorRate() != null){
				if(!source.getMonitorRate().equals(service.getMonitorRate())){
					changeMonitorRate = true;
				}
			}else{
				changeMonitorRate = true;
			}
			//改变了时间且监控已经启动
			if(changeMonitorRate && service.getMonitorStatus().equals("1")){

			}
			BeanExtUtils.copyProperties(source, service, true, true,
					null);
			service = source;
			serviceDao.save(service);
		}catch (Exception e){
			throw new ServiceException("修改服务监控配置发生异常",e);
		}
	}

	@Override
	public long count(String s) {
		return serviceDao.count(s);
	}

}
