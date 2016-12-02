package com.ycsys.smartmap.service.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.service.dao.LayerServiceDao;
import com.ycsys.smartmap.service.dao.ThemeServiceDao;
import com.ycsys.smartmap.service.entity.Layer;
import com.ycsys.smartmap.service.entity.LayerTheme;
import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
/**
 * themeServiceDao
 * @author zsx
 * @date   2016年12月1日
 */
@Repository("themeServiceDao")
public class ThemeServiceDaoImpl extends BaseDaoImpl<LayerTheme, Integer> implements ThemeServiceDao{

}
