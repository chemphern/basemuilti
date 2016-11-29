package com.ycsys.smartmap.monitor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "m_gis_server")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class GisServerMonitor implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "name", length = 30)
	private String name; // ArcGis服务名称

	@Column(name = "collect_date")
	private Date collectDate;

	@Column(name = "pass", length = 1)
	private String pass;// 是否连通
	
	@Column(name = "server_type",length = 20)
	private String serverType;	//服务类型
	
	@Column(name = "runnin_status",length = 1)
	private String runningStatus;	//运行状态
	
	@Column(name = "running_example")
	private Integer runningExample;	//运行中的实例
	
	@Column(name = "using_example")
	private Integer usingExample;	//使用中的实例
	
	@Column(name = "monitor_date")
	private Date monitorDate;	//监控时间
	
	@Column(name = "status_encoding",length = 30)
	private String statusEncoding; // 状态编码
	
	@Column(name = "create_date")
	private Date createDate; // 创建时间

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

	public Date getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(Date collectDate) {
		this.collectDate = collectDate;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getRunningStatus() {
		return runningStatus;
	}

	public void setRunningStatus(String runningStatus) {
		this.runningStatus = runningStatus;
	}

	public Integer getRunningExample() {
		return runningExample;
	}

	public void setRunningExample(Integer runningExample) {
		this.runningExample = runningExample;
	}

	public Integer getUsingExample() {
		return usingExample;
	}

	public void setUsingExample(Integer usingExample) {
		this.usingExample = usingExample;
	}

	public Date getMonitorDate() {
		return monitorDate;
	}

	public void setMonitorDate(Date monitorDate) {
		this.monitorDate = monitorDate;
	}

	public String getStatusEncoding() {
		return statusEncoding;
	}

	public void setStatusEncoding(String statusEncoding) {
		this.statusEncoding = statusEncoding;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
