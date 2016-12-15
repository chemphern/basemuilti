package com.ycsys.smartmap.webgis.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.security.PublicKey;

/**
 * 飞行路径停靠点实体
 * Created by chenlong on 2016/12/2.
 */
@Entity
@Table(name = "map_roam_flightpathpoint")
@DynamicUpdate
@DynamicInsert
public class FlightPathPoint implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
	
	//路径点名称
	@Column(name = "point_name", length=100)
	private String pointName;
	
	//关联飞行路径表ID字段
	@ManyToOne
	@JoinColumn(name="flight_path_id")
	private FlightPath flightPath;
	
	//路径点序号
	@Column(name = "point_index", nullable = false)
    private Integer pointIndex;
	
	//路径点X坐标
	@Column(name = "point_x", nullable = false)
	private Double pointX;
	
	//路径点Y坐标
	@Column(name = "point_y", nullable = false)
	private Double pointY;
	
	//路径点Z坐标
	@Column(name = "point_z", nullable = false)
	private Double pointZ;
	
	//路径点Yaw坐标
	@Column(name = "point_yaw", nullable = false)
	private Double pointYaw = 0d;
	
	//路径点Pitch坐标
	@Column(name = "point_pitch", nullable = false)
	private Double pointPitch = 0d;
	
	//路径点Roll坐标
	@Column(name = "point_roll", nullable = false)
	private Double pointRoll = 0d;
	
	//路径点到下一个点的速度坐标(米/秒)
	@Column(name = "point_speed", nullable = false)
	private Float pointSpeed = 0F;
	
	//路径点到下一个点的速度坐标（秒）
	@Column(name = "stop_time", nullable = false)
	private Float stopTime = 0F;

	public  FlightPathPoint(){}

	public 	FlightPathPoint(FlightPath flightPath,String pointName,Integer pointIndex,Double pointX,Double pointY,Double pointZ,Double pointYaw,Double pointPitch,Double pointRoll,Float stopTime){
		this.flightPath = flightPath;
		this.pointName = pointName;
		this.pointIndex = pointIndex;
		this.pointX = pointX;
		this.pointY = pointY;
		this.pointZ = pointZ;
		this.pointYaw = pointYaw;
		this.pointPitch = pointPitch;
		this.pointRoll = pointRoll;
		this.stopTime = stopTime;
	}
	
	public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
	public String getPointName() {
		return this.pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	
	public Integer getPointIndex() {
		return this.pointIndex;
	}
	public FlightPath getFlightPath() {
		return flightPath;
	}
	
	public void setFlightPath(FlightPath flightPath) {
		this.flightPath = flightPath;
	}
	public void setPointIndex(Integer pointIndex) {
		this.pointIndex = pointIndex;
	}
	
	public Double getPointX() {
		return this.pointX;
	}
	public void setPointX(Double pointX) {
		this.pointX = pointX;
	}
	
	public Double getPointY() {
		return this.pointY;
	}
	public void setPointY(Double pointY) {
		this.pointY = pointY;
	}
	
	public Double getPointYaw() {
		return pointYaw;
	}
	public void setPointYaw(Double pointYaw) {
		this.pointYaw = pointYaw;
	}
	public Double getPointPitch() {
		return pointPitch;
	}
	public void setPointPitch(Double pointPitch) {
		this.pointPitch = pointPitch;
	}
	public Double getPointRoll() {
		return pointRoll;
	}
	public void setPointRoll(Double pointRoll) {
		this.pointRoll = pointRoll;
	}

	public Double getPointZ() {
		return this.pointZ;
	}
	public void setPointZ(Double pointZ) {
		this.pointZ = pointZ;
	}
	
	public Float getPointSpeed() {
		return this.pointSpeed;
	}
	public void setPointSpeed(Float pointSpeed) {
		this.pointSpeed = pointSpeed;
	}

	public Float getStopTime() {
		return this.stopTime;
	}
	public void setStopTime(Float stopTime) {
		this.stopTime = stopTime;
	}
}
