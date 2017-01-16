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
import com.ycsys.smartmap.sys.common.annotation.ToLog;
import com.ycsys.smartmap.sys.common.enums.LogType;
import com.ycsys.smartmap.sys.entity.PageHelper;

@Transactional
@Service("themeService")
public class ThemeServiceImpl implements ThemeService {

	@Autowired
	private ThemeServiceDao themeServiceDao;

	@Override
	@ToLog(name="新增专题图",type= LogType.Layer)
	public Integer save(LayerTheme o) {
		return (Integer)themeServiceDao.save(o);
	}

	@Override
	@ToLog(name="删除专题图",type= LogType.Layer)
	public void delete(LayerTheme o) {
		themeServiceDao.delete(o);
	}

	@Override
	@ToLog(name="修改专题图",type= LogType.Layer)
	public void update(LayerTheme o) {
		themeServiceDao.update(o);
	}

	@Override
	@ToLog(name="新增或修改专题图",type= LogType.Layer)
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
