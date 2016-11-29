package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.entity.Area;
import com.ycsys.smartmap.sys.entity.PageHelper;

import java.util.List;

/**
 * Created by lixiaoxin on 2016/11/15.
 */
public interface AreaService {
    /**省份**/
    List<Area> getProvinces();

    /**省份分页**/
    List<Area> getProvinces(PageHelper page);

    /**城市**/
    List<Area> getCities(String substring);

    /**区**/
    List<Area> getCounties(String code);

    /**初始化地区**/
    void initArea() throws Exception;


    /**删除**/
    void delete(String id);

    /**保存或者修改**/
    void saveOrUpdate(Area area);

    /**根据id获取区域**/
    Area getAreaById(String id);
}
