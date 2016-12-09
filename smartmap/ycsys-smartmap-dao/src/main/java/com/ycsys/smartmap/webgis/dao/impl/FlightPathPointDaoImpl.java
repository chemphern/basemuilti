package com.ycsys.smartmap.webgis.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
import com.ycsys.smartmap.webgis.dao.FlightPathPointDao;
import com.ycsys.smartmap.webgis.entity.FlightPathPoint;

/**
 * Created by chenlong on 2016/12/2.
 */
@Repository("flightPathPointDao")
public class FlightPathPointDaoImpl extends BaseDaoImpl<FlightPathPoint,Integer> implements FlightPathPointDao {
}
