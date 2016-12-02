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

/**
 * 飞行路径停靠点实体
 * Created by chenlong on 2016/12/2.
 */
@Entity
@Table(name = "map_roam_flightpathpoint")
@DynamicUpdate
@DynamicInsert
public class FlightPathPoint {
	
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
	private Float pointYaw = 0F;
	
	//路径点Pitch坐标
	@Column(name = "point_pitch", nullable = false)
	private Float pointPitch = 0F;
	
	//路径点Roll坐标
	@Column(name = "point_roll", nullable = false)
	private Float pointRoll = 0F;
	
	//路径点到下一个点的速度坐标(米/秒)
	@Column(name = "point_speed", nullable = false)
	private Float pointSpeed = 0F;
	
	//路径点到下一个点的速度坐标（秒）
	@Column(name = "stop_time", nullable = false)
	private Float stopTime = 0F;
	
	
	//id
	public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    //路径点名称
	public String getPointName() {
		return this.pointName;
	}
	public void setPointName(String pointName) {
		this.pointName = pointName;
	}
	
	//路径点序号
	public Integer getPointIndex() {
		return this.pointIndex;
	}
	public FlightPath getFlightPath() {
		return flightPath;
	}
	
	//关联飞行路径表ID字段
	public void setFlightPath(FlightPath flightPath) {
		this.flightPath = flightPath;
	}
	public void setPointIndex(Integer pointIndex) {
		this.pointIndex = pointIndex;
	}
	
	//路径点X坐标
	public Double getPointX() {
		return this.pointX;
	}
	public void setPointX(Double pointX) {
		this.pointX = pointX;
	}
	
	//路径点Y坐标
	public Double getPointY() {
		return this.pointY;
	}
	public void setPointY(Double pointY) {
		this.pointY = pointY;
	}
	
	//路径点Z坐标
	public Double getPointZ() {
		return this.pointZ;
	}
	public void setPointZ(Double pointZ) {
		this.pointZ = pointZ;
	}
	
	//路径点Yaw
	public Float getPointYaw() {
		return this.pointYaw;
	}
	public void setPointYaw(Float pointYaw) {
		this.pointYaw = pointYaw;
	}
	
	//路径点Pitch
	public Float getPointPitch() {
		return this.pointPitch;
	}
	public void setPointPitch(Float pointPitch) {
		this.pointPitch = pointPitch;
	}
	
	//路径点Roll
	public Float getPointRoll() {
		return this.pointRoll;
	}
	public void setPointRoll(Float pointRoll) {
		this.pointRoll = pointRoll;
	}
	
	//路径点到下一个点的速度坐标(米/秒)
	public Float getPointSpeed() {
		return this.pointSpeed;
	}
	public void setPointSpeed(Float pointSpeed) {
		this.pointSpeed = pointSpeed;
	}
	
	//路径点到下一个点的速度坐标（秒）
	public Float getStopTime() {
		return this.stopTime;
	}
	public void setStopTime(Float stopTime) {
		this.stopTime = stopTime;
	}
}
