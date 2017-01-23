package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.BaseDao;
import com.ycsys.smartmap.sys.entity.PageHelper;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Repository("baseDao")
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T,PK> extends HibernateDaoSupport implements BaseDao<T,PK> {

	@Resource
	private SessionFactory sessionFactory;

	protected Class<T> entityClass;
	// 把sessionFactory注入到HibernateDaoSupport中，这样就可以使用HibernateTemplate
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		}
		return entityClass;
	}
	
	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	public Serializable save(T o) {
		return this.getCurrentSession().save(o);
	}

	public void delete(T o) {
		this.getCurrentSession().delete(o);
	}

	public void update(T o) {
		this.getCurrentSession().update(o);
	}

	public void saveOrUpdate(T o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	public List<T> find(String hql) {
		return this.getCurrentSession().createQuery(hql).list();
	}
	
	public List findSQL(String hql, Class T) {
		return this.getCurrentSession().createSQLQuery(hql).addEntity(T).list();
	}
	
	public List findSQL(String hql) {
		return this.getCurrentSession().createSQLQuery(hql).list();
	}

	public List<T> find(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	public List<T> find(String s, PageHelper page) {
		Query q = this.getCurrentSession().createQuery(s);
		q.setFirstResult(page.getFirstResult());
		q.setMaxResults(page.getMaxResults());
		return q.list();
	}

	public List<T> find(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.list();
	}
	
	public List<T> find(String hql, Object[] param, Integer page, Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public List<T> find(String hql, List<Object> param, Integer page, Integer rows) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (rows == null || rows < 1) {
			rows = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	public T get(Class<T> c, Serializable id) {
		return (T) this.getCurrentSession().get(c, id);
	}

	public T get(String hql, Object[] param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	public T get(String hql, List<Object> param) {
		List<T> l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		} else {
			return null;
		}
	}

	public Long count(String hql) {
		return (Long) this.getCurrentSession().createQuery(hql).uniqueResult();
	}

	public Long count(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		//return (long) q.list().size();
		return (Long) q.uniqueResult();
	}

	public Long count(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		//return (long) q.list().size();
		return (Long) q.uniqueResult();
	}
	
	public Long countGroupBy(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return (long) q.list().size();
	}

	public Long countGroupBy(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return (long) q.list().size();
	}
	

	public Integer executeHql(String hql) {
		return this.getCurrentSession().createQuery(hql).executeUpdate();
	}

	public Integer executeHql(String hql, Object[] param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.executeUpdate();
	}

	public Integer executeHql(String hql, List<Object> param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.executeUpdate();
	}

	public Query createQuery(String hql){
		return this.getCurrentSession().createQuery(hql);
	}

	/**
	 *
	 * 与find()函数可进行更加灵活的操作.
	 * @param queryString 查询
	 * @param values
	 */
	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * @param queryString
	 * @param values 数量可变的参数,按顺序绑定.
	 * @return Query
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = this.getCurrentSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(String.valueOf(i), values[i]);
			}
		}
		return query;
	}
	/**
	 * 按属性查找唯一对象, 匹配方式为相等
	 * @param propertyName 属性name
	 * @param value 属性值
	 * @return 结果对象
	 */
	public T findUniqueBy(final String propertyName, final Object value) {
		Criterion criterion = Restrictions.eq(propertyName, value);
		return (T) createCriteria(criterion).uniqueResult();
	}
	/**
	 * 根据Criterion条件创建Criteria.
	 * 与find()函数可进行更加灵活的操作.
	 * @param criterions 数量可变的Criterion.
	 * @return Criteria
	 */
	private Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = this.getCurrentSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		return criteria;
	}

	public Criteria createCriteria(Boolean isCache,final Criterion... criterions) {
		Criteria criteria = this.getCurrentSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			criteria.add(c);
		}
		criteria.setCacheable(isCache);
		return criteria;
	}

	public List<T> find(String hql, Object[] param, PageHelper page) {
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

	@Override
	public List<T> find(String hql, List<Object> param, PageHelper page) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		q.setFirstResult(page.getFirstResult());
		q.setMaxResults(page.getMaxResults());
		return q.list();
	}

	@Override
	public Object[] findArrValue(String hql, List<Object> params) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				q.setParameter(i, params.get(i));
			}
		}
		List<Object> list = q.list();
		if(list != null && list.size() > 0) {
			return list.toArray();
		}
		return null;
	}

	@Override
	public Object[] findArrValue(String hql, List<Object> params, Integer page,
			Integer pageSize) {
		if (page == null || page < 1) {
			page = 1;
		}
		if (pageSize == null || pageSize < 1) {
			pageSize = 10;
		}
		Query q = this.getCurrentSession().createQuery(hql);
		if (params != null && params.size() > 0) {
			for (int i = 0; i < params.size(); i++) {
				q.setParameter(i, params.get(i));
			}
		}
		q.setFirstResult((page - 1) * pageSize);
		q.setMaxResults(pageSize);
		List<Object> list = q.list();
		if(list != null && list.size() > 0) {
			return list.toArray();
		}
		return null;
	}

}
