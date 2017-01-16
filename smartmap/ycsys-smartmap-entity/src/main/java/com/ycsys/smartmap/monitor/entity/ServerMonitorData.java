package com.ycsys.smartmap.monitor.entity;

import com.ycsys.smartmap.sys.entity.ConfigServerMonitor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 通用服务器的监控数据
 * 
 * @author lixiaoxin
 * @date 2016年1月10日
 */
@Entity
@Table(name = "m_server_monitor_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class ServerMonitorData implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "cpu_used_rate")
	private String cpuUsedRate; // cpu用户使用率

	@Column(name = "receive_package")
	private long receivePackage; // 网络接收的总包裹数

	@Column(name = "send_package")
	private long sendPackage; // 网络发送的总包裹数

	@Column(name="send_byte")
	private long sendByte;

	@Column(name="rec_byte")
	private long recByte;

	@Column(name="rate")
	private String rate;
	
	@ManyToOne
	@JoinColumn(name = "server_id")
	private ConfigServerMonitor configServerMonitor; //服务器
	
	@Column(name = "monitor_time")
	private Date monitorTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpuUsedRate() {
		return cpuUsedRate;
	}

	public void setCpuUsedRate(String cpuUsedRate) {
		this.cpuUsedRate = cpuUsedRate;
	}

	public long getReceivePackage() {
		return receivePackage;
	}

	public void setReceivePackage(long receivePackage) {
		this.receivePackage = receivePackage;
	}

	public long getSendPackage() {
		return sendPackage;
	}

	public void setSendPackage(long sendPackage) {
		this.sendPackage = sendPackage;
	}

	public long getSendByte() {
		return sendByte;
	}

	public void setSendByte(long sendByte) {
		this.sendByte = sendByte;
	}

	public long getRecByte() {
		return recByte;
	}

	public void setRecByte(long recByte) {
		this.recByte = recByte;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public ConfigServerMonitor getConfigServerMonitor() {
		return configServerMonitor;
	}

	public void setConfigServerMonitor(ConfigServerMonitor configServerMonitor) {
		this.configServerMonitor = configServerMonitor;
	}

	public Date getMonitorTime() {
		return monitorTime;
	}

	public void setMonitorTime(Date monitorTime) {
		this.monitorTime = monitorTime;
	}
}
