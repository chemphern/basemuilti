package com.ycsys.smartmap.webgis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import com.ycsys.smartmap.webgis.entity.FlightPath;
import org.springframework.stereotype.Service;

import com.ycsys.smartmap.webgis.dao.FlightPathPointDao;
import com.ycsys.smartmap.webgis.entity.FlightPathPoint;
import com.ycsys.smartmap.webgis.service.FlightPathPointService;

/**
 * Created by chenlong on 2016/12/2.
 */
@Service("flightPathPointService")
public class FlightPathPointServiceImpl implements FlightPathPointService {

	@Resource
	private FlightPathPointDao flightPathPointDao;

	@Override
	public List<FlightPathPoint> getFlightPointsFromPathName(FlightPath flightPath) {
		Object[] p = { flightPath };
		return flightPathPointDao.find("from FlightPathPoint t where t.flightPath=? order by t.pointIndex",p);
	}

	@Override
	public FlightPathPoint getFlightPointFromPathName(FlightPath flightPath,Integer pointIndex) {
		Object[] p = { flightPath,pointIndex };
		List<FlightPathPoint> pathPints = flightPathPointDao.find("from FlightPathPoint t where t.flightPath=? and t.pointIndex=?",p);
		if(pathPints!=null&&pathPints.size()>=1)
			return pathPints.get(0);
		else
			return null;
	}

	@Override
	public void addFlightPointToPath(FlightPathPoint pathPoint) {
		flightPathPointDao.save(pathPoint);
	}

	@Override
	public void updateFlightPathToPath(FlightPathPoint pathPoint) {
		flightPathPointDao.update(pathPoint);
	}

	@Override
	public void deleteFlightPathToPath(FlightPathPoint pathPoint) {
		flightPathPointDao.delete(pathPoint);
	}
}
