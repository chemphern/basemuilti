package com.ycsys.smartmap.service.service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.service.dao.LayerServiceDao;
import com.ycsys.smartmap.service.entity.Layer;
import com.ycsys.smartmap.service.service.LayerService;
import com.ycsys.smartmap.sys.entity.PageHelper;

@Transactional
@Service("layerService")
public class LayerServiceImpl implements LayerService {

	@Autowired
	private LayerServiceDao layerServiceDao;
	
	@Override
	public Integer save(Layer o) {
		return (Integer)layerServiceDao.save(o);
	}

	@Override
	public void delete(Layer o) {
		layerServiceDao.delete(o);
	}

	@Override
	public void update(Layer o) {
		layerServiceDao.update(o);
	}

	@Override
	public void saveOrUpdate(Layer o) {
		layerServiceDao.saveOrUpdate(o);
	}

	@Override
	public List<Layer> find(String hql, Object[] param) {
		return layerServiceDao.find(hql, param);
	}

	@Override
	public List<Layer> find(String hql, List<Object> param) {
		return layerServiceDao.find(hql, param);
	}

	@Override
	public List<Layer> find(String hql, Object[] param, PageHelper page) {
		return layerServiceDao.find(hql, param,page);
	}

	@Override
	public Layer get(Class<Layer> c, Serializable id) {
		return layerServiceDao.get(c, id);
	}

	@Override
	public Layer get(String hql, Object[] param) {
		return layerServiceDao.get(hql, param);
	}

	@Override
	public Layer get(String hql, List<Object> param) {
		return layerServiceDao.get(hql, param);
	}

	@Override
	public List<Layer> find(String hql) {
		return layerServiceDao.find(hql);
	}

}
