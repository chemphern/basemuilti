package com.ycsys.smartmap.webgis.service;

import java.util.List;

import com.ycsys.smartmap.webgis.entity.FlightPath;

/**
 * Created by chenlong on 2016/12/2.
 */
public interface FlightPathService {
	
	/**获取所有飞行路线**/
    List<FlightPath> getFlightPaths();
    
    /**从名称获取飞行路线**/
    FlightPath getFlightPathFromName(String pathName);
    
    /**从名称模糊获取飞行路线**/
    List<FlightPath> getFlightPathsFromName(String pathName);
    
    /**添加飞行路线**/
    void addFlightPath(FlightPath flightPath);
    
    /**删除飞行路线**/
    void deleteFlightPath(FlightPath flightPath);
    
    /**更新飞行路线**/
    void updateFlightPath(FlightPath flightPath);
}
