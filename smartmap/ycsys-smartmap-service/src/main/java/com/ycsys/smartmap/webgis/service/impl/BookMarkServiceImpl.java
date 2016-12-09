package com.ycsys.smartmap.webgis.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.webgis.dao.BookMarkDao;
import com.ycsys.smartmap.webgis.entity.BookMark;
import com.ycsys.smartmap.webgis.service.BookMarkService;

/**
 * Created by chenlong on 2016/12/7.
 */
@Service("bookMarkService")
public class BookMarkServiceImpl implements BookMarkService {

	@Resource
    private BookMarkDao bookMarkDao;


	@Override
	public Integer save(BookMark o) {
		return (Integer)bookMarkDao.save(o);
	}

	@Override
	public void delete(BookMark o) {
		bookMarkDao.delete(o);		
	}

	@Override
	public void update(BookMark o) {
		bookMarkDao.update(o);
	}

	@Override
	public void saveOrUpdate(BookMark o) {
		bookMarkDao.saveOrUpdate(o);
	}

	@Override
	public List<BookMark> find(String hql) {
		return bookMarkDao.find(hql);
	}

	@Override
	public List<BookMark> find(String hql, Object[] param) {
		return bookMarkDao.find(hql, param);
	}

	@Override
	public List<BookMark> find(String hql, List<Object> param) {
		return bookMarkDao.find(hql,param);
	}

	@Override
	public List<BookMark> find(String hql, Object[] param, PageHelper page) {
		return bookMarkDao.find(hql, param, page);
	}

	@Override
	public BookMark get(Class<BookMark> c, Serializable id) {
		return bookMarkDao.get(c, id);
	}

	@Override
	public BookMark get(String hql, Object[] param) {
		return bookMarkDao.get(hql, param);
	}

	@Override
	public BookMark get(String hql, List<Object> param) {
		return bookMarkDao.get(hql, param);
	}
}
