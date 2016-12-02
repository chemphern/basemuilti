package com.ycsys.smartmap.service.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.service.dao.LayerServiceDao;
import com.ycsys.smartmap.service.entity.Layer;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
/**
 * 
 * @author zsx
 * @date   2016年12月1日
 */
@Repository("layerServiceDao")
public class LayerServiceDaoImpl extends BaseDaoImpl<Layer, Integer> implements LayerServiceDao{

}
