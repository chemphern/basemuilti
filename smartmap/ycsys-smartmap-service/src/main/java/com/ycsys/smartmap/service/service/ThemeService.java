package com.ycsys.smartmap.service.service;

import java.util.List;

import com.ycsys.smartmap.service.entity.LayerTheme;
import com.ycsys.smartmap.sys.service.BaseService;
/**
 * 专题图层 接口
 * @author lzx
 * @date   2016年12月1日
 */
public interface ThemeService extends BaseService<LayerTheme, Integer> {
	public Long count(String hql, List<Object> param);
}
