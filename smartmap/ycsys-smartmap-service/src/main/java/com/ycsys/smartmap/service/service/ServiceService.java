package com.ycsys.smartmap.service.service;

import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.BaseService;

import java.util.List;
import java.util.Map;
/**
 * 服务 service 接口
 * @author liweixiong
 * @date   2016年11月3日
 */
public interface ServiceService extends BaseService<Service, Integer> {
	List<Service> find(String hql, List<Object> param, PageHelper page);
	Long count(String hql, List<Object> param);
	
	Long count(String hql, Object[] param);
	
	//服务总数量
	Long countAll();

	/**更新监控信息，例如监控状态，监控频率**/
	void updateMonitor(Map<String, Object> saveParam);

    List<Service> findByPage(PageHelper pageHelper);

    /**修改监控配置**/
	void updateMonitorConfig(Service service);

	long count(String s);
	
	//更新服务状态
	void updateServiceStatus(Integer id,String status);
}
