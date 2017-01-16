package com.ycsys.smartmap.statistics.entity;

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

import com.ycsys.smartmap.sys.entity.User;

/**
 * 平台操作系统统计
 * @author liweixiong
 * @date   2016年11月3日
 */
@Entity
@Table(name = "stat_operating_system")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class StatOperatingSystem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识
	
	@Column(name = "server_name")
	private String serverName; //服务名称
	
	@Column(name = "use_max")
	private Float useMax; //cpu总使用率最大值
	
	@Column(name = "use_min")
	private Float useMin; //cpu总使用率最小值
	
	@Column(name = "use_average")
	private Float useAverage; //cpu总使用率平均值
	
	@Column(name = "user_use_max")
	private Float userUseMax; //cpu用户使用率最大值
	
	@Column(name = "user_use_min")
	private Float userUseMin; //cpu用户使用率最小值
	
	@Column(name = "user_use_average")
	private Float userUseAverage; //cpu用户使用率平均值
	
	@Column(name = "sys_use_max")
	private Float sysUseMax; //cpu系统使用率最大值
	
	@Column(name = "sys_min")
	private Float sysUseMin; //cpu系统使用率最小值
	
	@Column(name = "sys_use_average")
	private Float sysUseAverage; //cpu系统使用率平均值
	
	@Column(name = "free_max")
	private Float freeMax;	//cpu当前空闲率最大值
	
	@Column(name = "free_min")
	private Float freeMin;	//cpu当前空闲率最小值
	
	@Column(name = "free_average")
	private Float freeAverage;	//cpu当前空闲率平均值
	
	private long sendPackageMax; //发送包裹最大
	
	private long sendPackageMin; //发送包裹最小
	
	private Float sendPackageAverage; //发送包裹平均
	
	private long recPackageMax; //接收包裹最大
	
	private long recPackageMin; //接收包裹最小
	
	private Float recPackageAverage; //接收包裹平均
	
	private long sendByteMax; //发送流量最大
	
	private long sendByteMin; //发送流量最小
	
	private Float sendByteAverage; //发送流量平均
	
	private long recByteMax; //接收流量最大
	
	private long recByteMin; //接收流量最小
	
	private Float recByteAverage; //接收流量平均
	
	private double usedMemoryMax; //使用内存最大
	
	private double usedMemoryMin; //使用内存最小
	
	private double usedMemoryAverage; //使用内存平均
	
	
	@Column(name = "statistics_date")
	private Date statisticsDate; // 统计时间

	@ManyToOne
	@JoinColumn(name = "statistics_user_id")
	private User statisticsUser; // 统计者

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Float getUseMax() {
		return useMax;
	}

	public void setUseMax(Float useMax) {
		this.useMax = useMax;
	}

	public Float getUseMin() {
		return useMin;
	}

	public void setUseMin(Float useMin) {
		this.useMin = useMin;
	}

	public Float getUseAverage() {
		return useAverage;
	}

	public void setUseAverage(Float useAverage) {
		this.useAverage = useAverage;
	}

	public Float getUserUseMax() {
		return userUseMax;
	}

	public void setUserUseMax(Float userUseMax) {
		this.userUseMax = userUseMax;
	}

	public Float getUserUseMin() {
		return userUseMin;
	}

	public void setUserUseMin(Float userUseMin) {
		this.userUseMin = userUseMin;
	}

	public Float getUserUseAverage() {
		return userUseAverage;
	}

	public void setUserUseAverage(Float userUseAverage) {
		this.userUseAverage = userUseAverage;
	}

	public Float getSysUseMax() {
		return sysUseMax;
	}

	public void setSysUseMax(Float sysUseMax) {
		this.sysUseMax = sysUseMax;
	}

	public Float getSysUseMin() {
		return sysUseMin;
	}

	public void setSysUseMin(Float sysUseMin) {
		this.sysUseMin = sysUseMin;
	}

	public Float getSysUseAverage() {
		return sysUseAverage;
	}

	public void setSysUseAverage(Float sysUseAverage) {
		this.sysUseAverage = sysUseAverage;
	}

	public Float getFreeMax() {
		return freeMax;
	}

	public void setFreeMax(Float freeMax) {
		this.freeMax = freeMax;
	}

	public Float getFreeMin() {
		return freeMin;
	}

	public void setFreeMin(Float freeMin) {
		this.freeMin = freeMin;
	}

	public Float getFreeAverage() {
		return freeAverage;
	}

	public void setFreeAverage(Float freeAverage) {
		this.freeAverage = freeAverage;
	}

	public Date getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(Date statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

	public User getStatisticsUser() {
		return statisticsUser;
	}

	public void setStatisticsUser(User statisticsUser) {
		this.statisticsUser = statisticsUser;
	}

	public long getSendPackageMax() {
		return sendPackageMax;
	}

	public void setSendPackageMax(long sendPackageMax) {
		this.sendPackageMax = sendPackageMax;
	}

	public long getSendPackageMin() {
		return sendPackageMin;
	}

	public void setSendPackageMin(long sendPackageMin) {
		this.sendPackageMin = sendPackageMin;
	}

	public Float getSendPackageAverage() {
		return sendPackageAverage;
	}

	public void setSendPackageAverage(Float sendPackageAverage) {
		this.sendPackageAverage = sendPackageAverage;
	}

	public long getRecPackageMax() {
		return recPackageMax;
	}

	public void setRecPackageMax(long recPackageMax) {
		this.recPackageMax = recPackageMax;
	}

	public long getRecPackageMin() {
		return recPackageMin;
	}

	public void setRecPackageMin(long recPackageMin) {
		this.recPackageMin = recPackageMin;
	}

	public Float getRecPackageAverage() {
		return recPackageAverage;
	}

	public void setRecPackageAverage(Float recPackageAverage) {
		this.recPackageAverage = recPackageAverage;
	}

	public long getSendByteMax() {
		return sendByteMax;
	}

	public void setSendByteMax(long sendByteMax) {
		this.sendByteMax = sendByteMax;
	}

	public long getSendByteMin() {
		return sendByteMin;
	}

	public void setSendByteMin(long sendByteMin) {
		this.sendByteMin = sendByteMin;
	}

	public Float getSendByteAverage() {
		return sendByteAverage;
	}

	public void setSendByteAverage(Float sendByteAverage) {
		this.sendByteAverage = sendByteAverage;
	}

	public double getUsedMemoryMax() {
		return usedMemoryMax;
	}

	public void setUsedMemoryMax(double usedMemoryMax) {
		this.usedMemoryMax = usedMemoryMax;
	}

	public double getUsedMemoryMin() {
		return usedMemoryMin;
	}

	public void setUsedMemoryMin(double usedMemoryMin) {
		this.usedMemoryMin = usedMemoryMin;
	}

	public double getUsedMemoryAverage() {
		return usedMemoryAverage;
	}

	public void setUsedMemoryAverage(double usedMemoryAverage) {
		this.usedMemoryAverage = usedMemoryAverage;
	}

	public long getRecByteMax() {
		return recByteMax;
	}

	public void setRecByteMax(long recByteMax) {
		this.recByteMax = recByteMax;
	}

	public long getRecByteMin() {
		return recByteMin;
	}

	public void setRecByteMin(long recByteMin) {
		this.recByteMin = recByteMin;
	}

	public Float getRecByteAverage() {
		return recByteAverage;
	}

	public void setRecByteAverage(Float recByteAverage) {
		this.recByteAverage = recByteAverage;
	}
	
}
