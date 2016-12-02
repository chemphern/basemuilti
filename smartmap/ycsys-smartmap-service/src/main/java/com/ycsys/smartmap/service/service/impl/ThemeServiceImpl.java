package com.ycsys.smartmap.service.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.service.dao.ThemeServiceDao;
import com.ycsys.smartmap.service.entity.Layer;
import com.ycsys.smartmap.service.entity.LayerTheme;
import com.ycsys.smartmap.service.service.LayerService;
import com.ycsys.smartmap.service.service.ThemeService;
import com.ycsys.smartmap.sys.entity.PageHelper;

@Transactional
@Service("themeService")
public class ThemeServiceImpl implements ThemeService {

	@Autowired
	private ThemeServiceDao themeServiceDao;

	@Override
	public Integer save(LayerTheme o) {
		return (Integer)themeServiceDao.save(o);
	}

	@Override
	public void delete(LayerTheme o) {
		themeServiceDao.delete(o);
	}

	@Override
	public void update(LayerTheme o) {
		themeServiceDao.update(o);
	}

	@Override
	public void saveOrUpdate(LayerTheme o) {
		themeServiceDao.saveOrUpdate(o);
	}

	@Override
	public List<LayerTheme> find(String hql) {
		return themeServiceDao.find(hql);
	}

	@Override
	public List<LayerTheme> find(String hql, Object[] param) {
		return themeServiceDao.find(hql, param);
	}

	@Override
	public List<LayerTheme> find(String hql, List<Object> param) {
		return themeServiceDao.find(hql, param);
	}

	@Override
	public List<LayerTheme> find(String hql, Object[] param, PageHelper page) {
		return themeServiceDao.find(hql, param, page);
	}

	@Override
	public LayerTheme get(Class<LayerTheme> c, Serializable id) {
		return themeServiceDao.get(c, id);
	}

	@Override
	public LayerTheme get(String hql, Object[] param) {
		return themeServiceDao.get(hql, param);
	}

	@Override
	public LayerTheme get(String hql, List<Object> param) {
		return themeServiceDao.get(hql, param);
	}

}
