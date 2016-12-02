package com.ycsys.smartmap.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "map_layer_manager")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Layer implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "pid")
	private Integer pId;//父节点
	
	@Column(name = "name", length = 50)
	private String name;
	
	@Column(name = "address", length = 300)
	private String address;
	
	/**
	 * 图层几何类型，结合实际，发布服务所对应的几何类型统一约定三种类型，不考虑path,ring,sphere,envelop...等几何图形
	 * 栅格图层：null
	 * 点图层：esriGeometryPoint
	 * 线图层：esriGeometryPolyline
	 * 面图层：esriGeometryPolygon
	 */
	@Column(name = "geometry_type")
	private String geometryType;//几何类型：栅格(图片):[null];[esriGeometryPoint ];[esriGeometryPolyline];[esriGeometryPolygon ]

	@Transient
	private Boolean open=true;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPId() {
		return pId;
	}

	public void setPId(Integer pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGeometryType() {
		return geometryType;
	}

	public void setGeometryType(String geometryType) {
		this.geometryType = geometryType;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	
	
}
