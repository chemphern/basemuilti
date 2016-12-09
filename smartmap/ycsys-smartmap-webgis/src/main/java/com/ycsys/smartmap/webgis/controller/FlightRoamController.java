package com.ycsys.smartmap.webgis.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycsys.smartmap.webgis.entity.FlightPath;
import com.ycsys.smartmap.webgis.entity.FlightPathPoint;
import com.ycsys.smartmap.webgis.service.FlightPathPointService;
import com.ycsys.smartmap.webgis.service.FlightPathService;

@Controller
@RequestMapping("/flightRoam")
public class FlightRoamController {
	
	@Resource
    private FlightPathService flightPathService;
	
	@ResponseBody
	@RequestMapping("/roamPathList")
	public List<FlightPath> findRoamPathList(){
		return flightPathService.getFlightPaths();
	}
	
	@ResponseBody
	@RequestMapping("/roamPath")
	public FlightPath findRoamPath(String pathName){
		return flightPathService.getFlightPathFromName(pathName);
	}
	
	@ResponseBody
	@RequestMapping("/roamPaths")
	public List<FlightPath> findRoamPaths(String pathName){
		return flightPathService.getFlightPathsFromName(pathName);
	}
	
	@ResponseBody
	@RequestMapping("/addRoamPath")
	public void addRoamPath(String pathName,String creator){
		Date currentTime = new Date();
		FlightPath path = new FlightPath(pathName,creator,currentTime);
		flightPathService.addFlightPath(path);
	}
	
	@ResponseBody
	@RequestMapping("/deleteRoamPath")
	public void deleteRoamPath(String pathName){
		FlightPath path = this.findRoamPath(pathName);
		flightPathService.deleteFlightPath(path);
	}
	
	@ResponseBody
	@RequestMapping("/updateRoamPath")
	public void updateRoamPath(String pathName){
		FlightPath path = this.findRoamPath(pathName);
		flightPathService.updateFlightPath(path);
	}
	
	////////////////////////////////////////////////////////飞行路径点//////////////////////////////////////////////////////////////////////

	@Resource
	private FlightPathPointService flightPathPointService;
	
	@ResponseBody
	@RequestMapping("/getRoamPathPoints")
	public List<FlightPathPoint> getRoamPathPoints(String pathName){
		FlightPath path = this.findRoamPath(pathName);
		List<FlightPathPoint> points = flightPathPointService.getFlightPointsFromPathName(path);
		return points;
	}
	
	@ResponseBody
	@RequestMapping("/getRoamPathPoint")
	public FlightPathPoint getRoamPathPoint(String pathName,Integer pointIndex){
		FlightPath path = this.findRoamPath(pathName);
		return flightPathPointService.getFlightPointFromPathName(path,pointIndex);
	}
	
	@ResponseBody
	@RequestMapping("/addRoamPathPoint")
	public void addRoamPathPoint(String pathName,Integer pointIndex){
		FlightPath path = this.findRoamPath(pathName);
		FlightPathPoint pathPoint = new FlightPathPoint();
		pathPoint.setFlightPath(path);
		pathPoint.setPointIndex(pointIndex);
		flightPathPointService.getFlightPointFromPathName(path,pointIndex);
	}
	
	@ResponseBody
	@RequestMapping("/deleteRoamPathPoint")
	public void deleteRoamPathPoint(String pathName,Integer pointIndex){
		FlightPath path = this.findRoamPath(pathName);
		flightPathPointService.getFlightPointFromPathName(path,pointIndex);
	}
	
	@ResponseBody
	@RequestMapping("/updateRoamPathPoint")
	public void updateRoamPathPoint(String pathName,Integer pointIndex){
		FlightPath path = this.findRoamPath(pathName);
		flightPathPointService.getFlightPointFromPathName(path,pointIndex);
	}
}
