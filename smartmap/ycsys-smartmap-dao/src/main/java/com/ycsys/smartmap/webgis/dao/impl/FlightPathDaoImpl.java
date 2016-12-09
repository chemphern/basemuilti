package com.ycsys.smartmap.webgis.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
import com.ycsys.smartmap.webgis.entity.FlightPath;
import com.ycsys.smartmap.webgis.dao.FlightPathDao;

/**
 * Created by chenlong on 2016/12/2.
 */
@Repository("flightPathDao")
public class FlightPathDaoImpl extends BaseDaoImpl<FlightPath,Integer> implements FlightPathDao {
}
