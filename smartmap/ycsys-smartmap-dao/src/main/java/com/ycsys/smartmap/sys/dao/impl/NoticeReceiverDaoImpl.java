package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.NoticeReceiverDao;
import com.ycsys.smartmap.sys.entity.NoticeReceiver;
import com.ycsys.smartmap.sys.entity.PageHelper;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by lixiaoxins on 2017/1/13.
 */
@Repository("noticeReceiverDao")
public class NoticeReceiverDaoImpl extends BaseDaoImpl<NoticeReceiver,Integer> implements NoticeReceiverDao {
    @Override
    public List findList(String hql, Object[] param) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                q.setParameter(i, param[i]);
            }
        }
        return q.list();
    }

    @Override
    public List<Map<String, Object>> findList(String hql, Object[] param, PageHelper page) {
        Query q = this.getCurrentSession().createQuery(hql);
        if (param != null && param.length > 0) {
            for (int i = 0; i < param.length; i++) {
                q.setParameter(i, param[i]);
            }
        }
        q.setFirstResult(page.getFirstResult());
        q.setMaxResults(page.getMaxResults());
        return q.list();
    }
}
