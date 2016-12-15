package com.ycsys.smartmap.webgis.service;

import java.util.List;

import com.ycsys.smartmap.webgis.entity.FlightPath;
import com.ycsys.smartmap.webgis.entity.FlightPathPoint;

/**
 * Created by chenlong on 2016/12/2.
 */
public interface FlightPathPointService {
	
	/**根据飞行路线名称获取飞行路径所有点**/
	List<FlightPathPoint> getFlightPointsFromPathName(FlightPath flightPath);
	
	/**根据飞行路线名称和点索引号获取飞行路径某个点**/
	FlightPathPoint getFlightPointFromPathName(FlightPath flightPath,Integer pointIndex);
	
	/**添加飞行路径点**/
	void addFlightPointToPath(FlightPathPoint pathPoint);
	
	/**更新飞行路径点**/
	void updateFlightPointToPath(FlightPathPoint pathPoint);
	
	/**删除飞行路径点**/
	void deleteFlightPointToPath(FlightPathPoint pathPoint);
}
