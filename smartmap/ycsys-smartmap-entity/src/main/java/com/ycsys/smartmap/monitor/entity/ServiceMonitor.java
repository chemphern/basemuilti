package com.ycsys.smartmap.monitor.entity;

import com.ycsys.smartmap.service.entity.Service;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

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

	@Column(name = "status",length = 10)
	private String status; // 响应状态

	@Column(name = "monitor_address", length = 3999)
	private String monitorAddress; // 监控地址

	@Column(name = "respTime",length = 10)
	private float respTime; // 响应时间

	@Column(name = "monitor_date")
	private Date monitorDate; // 监控时间

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

	public String getMonitorAddress() {
		return monitorAddress;
	}

	public void setMonitorAddress(String monitorAddress) {
		this.monitorAddress = monitorAddress;
	}

	public float getRespTime() {
		return respTime;
	}

	public void setRespTime(float respTime) {
		this.respTime = respTime;
	}

	public Date getMonitorDate() {
		return monitorDate;
	}

	public void setMonitorDate(Date monitorDate) {
		this.monitorDate = monitorDate;
	}
}
