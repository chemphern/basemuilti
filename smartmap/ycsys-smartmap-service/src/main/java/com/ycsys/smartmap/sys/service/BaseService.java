package com.ycsys.smartmap.sys.service;

import java.io.Serializable;
import java.util.List;

import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;

/**
 * 基本服务 接口
 * 
 * @author liweixiong
 * @date 2016年11月2日
 * @param <T>
 * @param <PK>
 */
public interface BaseService<T, PK extends Serializable> {
	public PK save(T o);

	public void delete(T o);

	public void update(T o);

	public void saveOrUpdate(T o);

	public List<T> find(String hql);

	public List<T> find(String hql, Object[] param);

	public List<T> find(String hql, List<Object> param);

	public List<T> find(String hql, Object[] param, PageHelper page);

	public T get(Class<T> c, Serializable id);

	public T get(String hql, Object[] param);

	public T get(String hql, List<Object> param);

}
