package com.ycsys.smartmap.webgis.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.ycsys.smartmap.sys.common.result.ResponseEx;
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
	public ResponseEx addRoamPath(String pathName){
		ResponseEx ex = new ResponseEx();
		try {
			Date currentTime = new Date();
			String creator = "admin";
			FlightPath path = new FlightPath(pathName, creator, currentTime);
			flightPathService.addFlightPath(path);
			ex.setSuccess("增加飞行路径成功！");
		}catch (Exception e){
			ex.setFail("增加飞行路径失败！");
		}
		return ex;
	}
	
	@ResponseBody
	@RequestMapping("/deleteRoamPath")
	public ResponseEx deleteRoamPath(String pathName){
		ResponseEx ex = new ResponseEx();
		try {
			FlightPath path = this.findRoamPath(pathName);
			List<FlightPathPoint> pathPoints = this.getRoamPathPoints(path.getPathName());
			for (int i = 0; i < pathPoints.size(); i++) {
				flightPathPointService.deleteFlightPointToPath(pathPoints.get(i));
			}
			flightPathService.deleteFlightPath(path);
			ex.setSuccess("删除飞行路径成功！");
		}catch (Exception e){
			ex.setFail("删除飞行路径失败！");
		}
		return ex;
	}
	
	@ResponseBody
	@RequestMapping("/updateRoamPath")
	public ResponseEx updateRoamPath(String oldPathName,String newPathName){
		ResponseEx ex = new ResponseEx();
		try {
			FlightPath path = flightPathService.getFlightPathFromName(oldPathName);
			if(path!=null){
				path.setPathName(newPathName);
				flightPathService.updateFlightPath(path);
			}
			ex.setSuccess("更新飞行路径成功！");
		}catch (Exception e){
			ex.setFail("更新飞行路径失败！");
		}
		return ex;
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
	public ResponseEx addRoamPathPoint(String pathName,String pointName,Double x,Double y,Double z,Double yaw,Double pitch,Double roll,Float stopTime){
		ResponseEx ex = new ResponseEx();
		try {
			FlightPath path = this.findRoamPath(pathName);
			if(path==null){
				this.addRoamPath(pathName);
				path = this.findRoamPath(pathName);
			}
			Integer pointIndex = flightPathPointService.getFlightPointsFromPathName(path).size() + 1;
			FlightPathPoint newPathPoint = new FlightPathPoint(path,pointName,pointIndex,x,y,z,yaw,pitch,roll,stopTime);
			flightPathPointService.addFlightPointToPath(newPathPoint);
			ex.setSuccess("增加飞行路径点成功！");
		}catch (Exception e){
			ex.setFail("增加飞行路径点失败！");
		}
		return ex;
	}
	
	@ResponseBody
	@RequestMapping("/deleteRoamPathPoint")
	public ResponseEx deleteRoamPathPoint(String pathName,Integer pointIndex){
		ResponseEx ex = new ResponseEx();
		try {
			List<FlightPathPoint> pathPoints = this.getRoamPathPoints(pathName);
			if(pathPoints!=null&&pathPoints.size()>0){
				for(int i=0;i<pathPoints.size();i++){
					FlightPathPoint point = pathPoints.get(i);
					if(point.getPointIndex()==pointIndex){
						flightPathPointService.deleteFlightPointToPath(point);
					}
					if(i>pointIndex-1){
						point.setPointIndex(i);
						flightPathPointService.updateFlightPointToPath(point);
					}
				}
			}
			ex.setSuccess("删除飞行路径点成功！");
		}catch (Exception e){
			ex.setFail("删除飞行路径点失败！");
		}
		return ex;
	}
	
	@ResponseBody
	@RequestMapping("/updateRoamPathPoint")
	public ResponseEx updateRoamPathPoint(String pathName,String pointName,Integer pointIndex,Double x,Double y,Double z,Double yaw,Double pitch,Double roll,Float stopTime){
		ResponseEx ex = new ResponseEx();
		try {
			FlightPath path = this.findRoamPath(pathName);
			FlightPathPoint point = flightPathPointService.getFlightPointFromPathName(path,pointIndex);
			if(point!=null){
				point.setPointName(pointName);
				point.setPointX(x);
				point.setPointY(y);
				point.setPointZ(z);
				point.setPointYaw(yaw);
				point.setPointPitch(pitch);
				point.setPointRoll(roll);
				point.setStopTime(stopTime);
				flightPathPointService.updateFlightPointToPath(point);
				ex.setSuccess("更新飞行路径点成功！");
			}
			ex.setFail("没有找到匹配的飞行路径点！");
		}catch (Exception e){
			ex.setFail("更新飞行路径点失败！");
		}
		return ex;
	}
}
