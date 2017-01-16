package com.ycsys.smartmap.monitor.service.impl;

import com.ycsys.smartmap.monitor.dao.ServiceRequestDao;
import com.ycsys.smartmap.monitor.entity.ServiceRequest;
import com.ycsys.smartmap.monitor.service.ServiceRequestService;
import com.ycsys.smartmap.sys.entity.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lixiaoxin on 2017/1/3.
 */
@Service("serviceRequestService")
@Transactional
public class ServiceRequestServiceImpl implements ServiceRequestService{

    @Resource
    private ServiceRequestDao serviceRequestDao;

    @Override
    public void save(ServiceRequest sq) {
        serviceRequestDao.save(sq);
    }

    @Override
    public List<ServiceRequest> findCurrentRequestDuringTime(long min) {
        Date now = new Date();
        Date currentDate = new Date(now.getTime() - 30*60*1000);
        return serviceRequestDao.find("from ServiceRequest where requestDate >= ?",new Object[]{currentDate});
    }

    @Override
    public long getCurrentRequestDuringTimeCount(long min) {
        Date now = new Date();
        Date currentDate = new Date(now.getTime() - 30*60*1000);
        return serviceRequestDao.count("select count(*) from ServiceRequest where requestDate >= ?",new Object[]{currentDate});
    }

    @Override
	public Object[] findArrValue(String hql, List<Object> params, Integer page,
			Integer pageSize) {
		// TODO Auto-generated method stub
		return serviceRequestDao.findArrValue(hql, params, page, pageSize);
	}

    @Override
    public List<ServiceRequest> findByPage(PageHelper page) {
        return serviceRequestDao.find("from ServiceRequest",page);
    }

    @Override
    public long countAll() {
        return serviceRequestDao.count("select count(*) from ServiceRequest");
    }

    @Override
    public List<ServiceRequest> findBySolution(Date date, Date date1,int num) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.setPage(1);
        if(num == -1){
            num = 500;
        }else if(num > 10000){
            num = 10000;
        }
        pageHelper.setPagesize(num);
        pageHelper.setPagesize(num);
        List<Object> pl = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("from ServiceRequest where 1=1 ");
        if(date!= null){
            pl.add(date);
            pl.add(date1);
            sb.append(" and createDate >= ? and createDate <= ?");
        }
        sb.append(" order by createDate");
        return serviceRequestDao.find( sb.toString(),pl.toArray(),pageHelper);
    }
}
