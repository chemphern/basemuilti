package com.ycsys.smartmap.webgis.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 书签实体
 * Created by chenlong on 2016/12/7.
 */
@Entity
@Table(name = "map_bookmark")
@DynamicUpdate
@DynamicInsert
public class BookMark implements java.io.Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
	
	//书签名称
	@Column(name = "name", nullable = false, length=100)
	private String name;
	
	//书签描述
	@Column(name = "description", length=200)
	private String description;
	
	//书签范围最小X坐标
	@Column(name = "xmin", nullable = false)
	private Double xmin;
	
	//书签范围最小Y坐标
	@Column(name = "ymin", nullable = false)
	private Double ymin;
	
	//书签范围最大X坐标
	@Column(name = "xmax", nullable = false)
	private Double xmax;
	
	//书签范围最大Y坐标
	@Column(name = "ymax", nullable = false)
	private Double ymax;
	
	//书签范围最小Yaw坐标（三维）
	@Column(name = "yaw", nullable = false)
	private Double yaw;
	
	//书签范围最大Pitch坐标（三维）
	@Column(name = "pitch", nullable = false)
	private Double pitch;
	
	//书签范围最大Roll坐标（三维）
	@Column(name = "roll", nullable = false)
	private Double roll;
	
	//书签创建者
	@Column(name = "creator",length=100, nullable = false)
	private String creator;
	
	//书签创建时间
	@Column(name = "createtime", nullable = false)
	private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getXmin() {
		return xmin;
	}

	public void setXmin(Double xmin) {
		this.xmin = xmin;
	}

	public Double getYmin() {
		return ymin;
	}

	public void setYmin(Double ymin) {
		this.ymin = ymin;
	}

	public Double getXmax() {
		return xmax;
	}

	public void setXmax(Double xmax) {
		this.xmax = xmax;
	}

	public Double getYmax() {
		return ymax;
	}

	public void setYmax(Double ymax) {
		this.ymax = ymax;
	}

	public Double getYaw() {
		return yaw;
	}

	public void setYaw(Double yaw) {
		this.yaw = yaw;
	}

	public Double getPitch() {
		return pitch;
	}

	public void setPitch(Double pitch) {
		this.pitch = pitch;
	}

	public Double getRoll() {
		return roll;
	}

	public void setRoll(Double roll) {
		this.roll = roll;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
