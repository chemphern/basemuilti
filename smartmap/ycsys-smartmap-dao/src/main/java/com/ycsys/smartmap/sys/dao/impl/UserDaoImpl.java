package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.UserDao;
import com.ycsys.smartmap.sys.entity.User;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 用户DAO
 * @author ty
 * @date 2015年1月13日
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User,Integer>  implements UserDao  {

    public User findByAttr(String loginName, String loginName1) {
        Criteria criteria = this.getCurrentSession().createCriteria(User.class);
        Criterion criterion = Restrictions.eq(loginName, loginName1);
        criteria.add(criterion);
        return (User)criteria.uniqueResult();
    }

    @Override
    public List<Integer> findUsersByRoleIds(List<Integer> ids) {
        String hql = "select u.id from User u join u.userRoles ur join ur.role r where r.id in (:alist)";
        Query query=this.getCurrentSession().createQuery(hql);
        query.setParameterList("alist", ids);
        return query.list();
    }

    @Override
    public List<Integer> findUsersByOrgIds(List<Integer> ids) {
        String hql = "select id from User where organization.id in (:olist)";
        Query query=this.getCurrentSession().createQuery(hql);
        query.setParameterList("olist", ids);
        return query.list();
    }


}
