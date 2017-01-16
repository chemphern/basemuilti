package com.ycsys.smartmap.monitor.service;

import com.ycsys.smartmap.monitor.entity.ServiceRequest;
import com.ycsys.smartmap.sys.entity.PageHelper;

import java.util.Date;
import java.util.List;

/**
 * Created by lixiaoxin on 2017/1/3.
 */
public interface ServiceRequestService {
    void save(ServiceRequest sq);

    //根据during时间获取当前请求
    List<ServiceRequest> findCurrentRequestDuringTime(long min);

    //总数
    long getCurrentRequestDuringTimeCount(long min);

	Object[] findArrValue(String hql, List<Object> params, Integer page,
			Integer pageSize);

    List<ServiceRequest> findByPage(PageHelper page);

    long countAll();

    List<ServiceRequest> findBySolution(Date date, Date date1,int num);
}
