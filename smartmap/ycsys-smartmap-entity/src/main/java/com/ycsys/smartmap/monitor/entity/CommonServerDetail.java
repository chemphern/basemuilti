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

/**
 * 通用服务器的详细信息
 * 
 * @author liweixiong
 * @date 2016年11月3日
 */
@Entity
@Table(name = "m_common_server_detail")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class CommonServerDetail implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "cpu_user")
	private Float cpuUser; // cpu用户使用率

	@Column(name = "cpu_system")
	private Float cpuSystem; // cpu系统使用率

	@Column(name = "cpu_free")
	private Float cpuFree; // cpu当前空闲率

	@Column(name = "cpu_total")
	private Float cpuTotal; // cpu总使用率

	@Column(name = "receive_package")
	private Integer receivePackage; // 网络接收的总包裹数

	@Column(name = "send_package")
	private Integer sendPackage; // 网络发送的总包裹数
	
	@ManyToOne
	@JoinColumn(name = "common_server_id")
	private CommonServerMonitor commonServer; //服务器
	
	@Column(name = "monitor_date")
	private Date monitorDate;
	
	@Column(name = "create_date")
	private Date createDate; // 创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getCpuUser() {
		return cpuUser;
	}

	public void setCpuUser(Float cpuUser) {
		this.cpuUser = cpuUser;
	}

	public Float getCpuSystem() {
		return cpuSystem;
	}

	public void setCpuSystem(Float cpuSystem) {
		this.cpuSystem = cpuSystem;
	}

	public Float getCpuFree() {
		return cpuFree;
	}

	public void setCpuFree(Float cpuFree) {
		this.cpuFree = cpuFree;
	}

	public Float getCpuTotal() {
		return cpuTotal;
	}

	public void setCpuTotal(Float cpuTotal) {
		this.cpuTotal = cpuTotal;
	}

	public Integer getReceivePackage() {
		return receivePackage;
	}

	public void setReceivePackage(Integer receivePackage) {
		this.receivePackage = receivePackage;
	}

	public Integer getSendPackage() {
		return sendPackage;
	}

	public void setSendPackage(Integer sendPackage) {
		this.sendPackage = sendPackage;
	}

	public CommonServerMonitor getCommonServer() {
		return commonServer;
	}

	public void setCommonServer(CommonServerMonitor commonServer) {
		this.commonServer = commonServer;
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
