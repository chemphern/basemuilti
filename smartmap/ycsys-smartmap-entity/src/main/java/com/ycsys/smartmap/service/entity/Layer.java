package com.ycsys.smartmap.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private Integer pId ;//父节点
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private Layer parent; // 父亲结点
	
	@ManyToOne
	@JoinColumn(name = "service_id")
	private Service service;
	
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
	
	/**
	 * 树的节点类型
	 */
	@Column(name="type")
	private String type;
	
	/**
	 * 名字字段，标识名称的字段
	 */
	@Column(name="name_field")
	private String nameField;
	
	/**
	 * 配置用于简略显示的主显示字段，逗号分隔
	 */
	@Column(name="summary_fields")
	private String summaryFields;
	
	/**
	 * 属性表中要用于地图上显示的字段，多字段逗号分隔
	 */
	@Column(name="display_fields")
	private String displayFields;
	
	public Layer(){}
	
	public Layer(Integer id,String name,String address,String geometryType,Integer pId,String type,String nameField,String summaryFields,String displayFields){
		this.id=id;
		this.name=name;
		this.address=address;
		this.geometryType=geometryType;
		this.pId=pId;
		this.type=type;
		this.open=true;
		this.nameField=nameField;
		this.summaryFields=summaryFields;
		this.displayFields=displayFields;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Layer getParent() {
		return parent;
	}

	public void setParent(Layer parent) {
		this.parent = parent;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	public String getSummaryFields() {
		return summaryFields;
	}

	public void setSummaryFields(String summaryFields) {
		this.summaryFields = summaryFields;
	}

	public String getDisplayFields() {
		return displayFields;
	}

	public void setDisplayFields(String displayFields) {
		this.displayFields = displayFields;
	}

	
}
