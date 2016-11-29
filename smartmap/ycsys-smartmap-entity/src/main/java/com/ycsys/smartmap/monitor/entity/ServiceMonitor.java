package com.ycsys.smartmap.monitor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ycsys.smartmap.service.entity.Service;

/**
 * 监控服务
 * 
 * @author liweixiong
 * @date 2016年11月3日
 */
@Entity
@Table(name = "m_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class ServiceMonitor implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@ManyToOne
	@JoinColumn(name = "service_id")
	private Service service; // 服务

	@Column(name = "status", length = 1)
	private String status; // 状态

	@Column(name = "monitor_status", length = 1)
	private String monitorStatus; // 监控状态

	@Column(name = "monitor_address", length = 50)
	private String monitorAddress; // 监控地址

	@Column(name = "type", length = 1)
	private String type; // 监控类型(0:http)

	@Column(name = "frequency")
	private Float frequency; // 监控频率

	@Column(name = "available_rate")
	private Float avalibleRate; // 可用率

	@Column(name = "Average_response_time")
	private Float averageResponseTime; // 平均响应时间

	@Column(name = "monitor_date")
	private Date monitorDate; // 监控时间
	
	@Column(name = "create_date")
	private Date createDate; // 创建时间

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMonitorStatus() {
		return monitorStatus;
	}

	public void setMonitorStatus(String monitorStatus) {
		this.monitorStatus = monitorStatus;
	}

	public String getMonitorAddress() {
		return monitorAddress;
	}

	public void setMonitorAddress(String monitorAddress) {
		this.monitorAddress = monitorAddress;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Float getFrequency() {
		return frequency;
	}

	public void setFrequency(Float frequency) {
		this.frequency = frequency;
	}

	public Float getAvalibleRate() {
		return avalibleRate;
	}

	public void setAvalibleRate(Float avalibleRate) {
		this.avalibleRate = avalibleRate;
	}

	public Float getAverageResponseTime() {
		return averageResponseTime;
	}

	public void setAverageResponseTime(Float averageResponseTime) {
		this.averageResponseTime = averageResponseTime;
	}

	public Date getMonitorDate() {
		return monitorDate;
	}

	public void setMonitorDate(Date monitorDate) {
		this.monitorDate = monitorDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
