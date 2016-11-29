package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.UserDao;
import com.ycsys.smartmap.sys.entity.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


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

    public void saveAdminUser(User user, Role role, UserRole userRole) {
        this.getCurrentSession().save(user);
        this.getCurrentSession().save(role);
        this.getCurrentSession().save(userRole);
    }
}
