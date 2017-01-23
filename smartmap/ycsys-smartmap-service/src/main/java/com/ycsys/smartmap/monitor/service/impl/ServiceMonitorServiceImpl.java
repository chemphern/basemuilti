package com.ycsys.smartmap.monitor.service.impl;

import com.ycsys.smartmap.monitor.dao.ServiceMonitorDao;
import com.ycsys.smartmap.monitor.entity.ServiceMonitor;
import com.ycsys.smartmap.monitor.service.ServiceMonitorService;
import com.ycsys.smartmap.sys.entity.PageHelper;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixiaoxin on 2016/12/28.
 */
@Service("serviceMonitorService")
@Transactional
public class ServiceMonitorServiceImpl implements ServiceMonitorService{

    @Resource
    private ServiceMonitorDao serviceMonitorDao;

    @Override
    public void save(ServiceMonitor m) {
        serviceMonitorDao.save(m);
    }

	@Override
	public List<ServiceMonitor> find(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serviceMonitorDao.find(hql, param);
	}

	@Override
	public Object[] findArrValue(String hql, List<Object> params) {
		// TODO Auto-generated method stub
		return serviceMonitorDao.findArrValue(hql, params);
	}

	@Override
	public Long count(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serviceMonitorDao.count(hql, param);
	}
	@Override
	public Map<String, Object> getMonitorInfo(Integer id) {
		Map<String,Object> infos = new HashMap<>();
    	ServiceMonitor monitor = serviceMonitorDao.getOne("from ServiceMonitor where service.id = ? order by monitorDate desc",new Object[]{id});
		String res = "异常";
		if(monitor.getStatus() != null && monitor.getStatus().equals("200")){
			res = "正常";
		}
		infos.put("status",res);
		Map<String,Object> cm = serviceMonitorDao.findOneToMap("select new map(count(*) as count) from ServiceMonitor where service.id = ?",new Object[]{id});
		long count = (long) cm.get("count");//serviceMonitorDao.count("select count(*) from ServiceMonitor where service.id = ?",new Object[]{id});
		double normal_rate = 0;
		double average_time = 0;
		if(count > 0) {
			Map<String,Object> countMap = serviceMonitorDao.findOneToMap("select new map(count(*) as count) from ServiceMonitor where service.id = ? and status = ?",new Object[]{id,"200"});
			long normal = (long) countMap.get("count");//serviceMonitorDao.count("select count(*) from ServiceMonitor where service.id = ? and status = ?", new Object[]{id, "200"});
			Map<String, Object> resMap = serviceMonitorDao.findOneToMap("select new Map(sum(respTime) as respTime) from ServiceMonitor where service.id = ?", new Object[]{id});
			String respTimes = String.valueOf(resMap.get("respTime"));
			average_time = Double.parseDouble(respTimes) / count;
			normal_rate = normal * 100 / count;
		}
		infos.put("availableRate",normal_rate);
		infos.put("averageRespTime",average_time);
    	return infos;
	}

	@Override
	public List<ServiceMonitor> findByPage(PageHelper page) {
		return serviceMonitorDao.find("from ServiceMonitor",page);
	}

	@Override
	public long countAll() {
		return serviceMonitorDao.count("select count(*) from ServiceMonitor");
	}

	@Override
	public List<ServiceMonitor> find(String hql, List<Object> param,
			Integer page, Integer rows) {
		// TODO Auto-generated method stub
		return serviceMonitorDao.find(hql, param, page, rows);
	}

	@Override
	public Long countGroupBy(String hql, Object[] param) {
		// TODO Auto-generated method stub
		return serviceMonitorDao.countGroupBy(hql, param);
	}

	@Override
	public Long countGroupBy(String hql, List<Object> param) {
		// TODO Auto-generated method stub
		return serviceMonitorDao.countGroupBy(hql, param);
	}

}
