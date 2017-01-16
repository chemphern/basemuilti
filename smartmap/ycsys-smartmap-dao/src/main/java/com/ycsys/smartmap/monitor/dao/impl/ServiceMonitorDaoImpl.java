package com.ycsys.smartmap.monitor.dao.impl;

import com.ycsys.smartmap.monitor.dao.ServiceMonitorDao;
import com.ycsys.smartmap.monitor.entity.ServiceMonitor;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by lixiaoxin on 2016/12/28.
 */
@Repository("serviceMonitorDao'")
public class ServiceMonitorDaoImpl extends BaseDaoImpl<ServiceMonitor,Integer> implements ServiceMonitorDao {
    @Override
    public ServiceMonitor getOne(String s,Object[] param) {
        Query q = this.getCurrentSession().createQuery(s);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                q.setParameter(i, param[i]);
            }
        }
        q.setMaxResults(1);
        return (ServiceMonitor) q.uniqueResult();
    }

    @Override
    public Map<String, Object> findOneToMap(String hql, Object[] param) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                q.setParameter(i, param[i]);
            }
        }
//        List<Object> list = q.list();
//        for(Object o:list){
//            Map map = (Map) o;
//        }
        return (Map<String, Object>) q.uniqueResult();
    }
}
