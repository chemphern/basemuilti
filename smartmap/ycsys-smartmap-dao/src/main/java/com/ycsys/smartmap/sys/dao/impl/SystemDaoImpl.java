package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.SystemDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by lixiaoxin on 2016/11/8.
 */
@Repository("systemDao")
public class SystemDaoImpl extends BaseDaoImpl<com.ycsys.smartmap.sys.entity.System,Integer> implements SystemDao{

    public com.ycsys.smartmap.sys.entity.System getSystemByAttr(String code, String system_code) {
        Criteria criteria = this.getCurrentSession().createCriteria(com.ycsys.smartmap.sys.entity.System.class);
        Criterion criterion = Restrictions.eq(code, system_code);
        criteria.add(criterion);
        return (com.ycsys.smartmap.sys.entity.System)criteria.uniqueResult();
    }
}
