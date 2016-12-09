package com.ycsys.smartmap.webgis.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ycsys.smartmap.webgis.dao.FlightPathDao;
import com.ycsys.smartmap.webgis.entity.FlightPath;
import com.ycsys.smartmap.webgis.service.FlightPathService;

/**
 * Created by chenlong on 2016/12/2.
 */
@Service("flightPathService")
public class FlightPathServiceImpl implements FlightPathService {

	@Resource
    private FlightPathDao flightPathDao;
	
	/***获取所有飞行路线***/
	@Override
	public List<FlightPath> getFlightPaths() {
        return flightPathDao.find("from FlightPath t order by t.createTime");
	}
	
	/***根据名称获取飞行路线***/
	@Override
	public FlightPath getFlightPathFromName(String pathName) {
		Object[] p = {pathName};
		List<FlightPath> paths = flightPathDao.find("from FlightPath t where t.pathName=? order by t.createTime",p);
		if(paths!=null&&paths.size()>=1)
			return paths.get(0);
		else
			return null;
	}
	
	/***根据名称模糊查找飞行路线***/
	@Override
	public List<FlightPath> getFlightPathsFromName(String pathName) {
		Object[] p = {pathName};
		return flightPathDao.find("from FlightPath t where t.pathName=? order by t.createTime",p);
	}
	
	/***添加飞行路线***/
	@Override
	public void addFlightPath(FlightPath flightPath) {
		// TODO Auto-generated method stub
		flightPathDao.save(flightPath);
	}

	/***删除飞行路线***/
	@Override
	public void deleteFlightPath(FlightPath flightPath) {
		// TODO Auto-generated method stub
		flightPathDao.delete(flightPath);
	}

	/***更新飞行路线***/
	@Override
	public void updateFlightPath(FlightPath flightPath) {
		// TODO Auto-generated method stub
		flightPathDao.update(flightPath);
	}
}
