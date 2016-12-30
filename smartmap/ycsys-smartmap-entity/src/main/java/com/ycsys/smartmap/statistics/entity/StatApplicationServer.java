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
 * 平台应用服务器统计
 * 
 * @author liweixiong
 * @date 2016年11月3日
 */
@Entity
@Table(name = "stat_application_server")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class StatApplicationServer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "type", length = 1)
	private String type; // 类型

	@Column(name = "server_name", length = 50)
	private String serverName; // 应用服务器名

	@Column(name = "thread_max")
	private Integer threadMax; // 线程最大值

	@Column(name = "thread_min")
	private Integer threadMin; // 线程最小值

	@Column(name = "thread_average")
	private Float threadAverage; // 线程平均值

	@Column(name = "busy_thread_max")
	private Integer busyThreadMax; // 繁忙线程最大值

	@Column(name = "busy_thread_min")
	private Integer busyThreadMin; // 繁忙线程最小值

	@Column(name = "busy_thread_average")
	private Float busyThreadAverage; // 繁忙线程平均值

	@Column(name = "have_max_memory")
	private Long haveMaxMemory; // 已占用内存最大值

	@Column(name = "have_min_memory")
	private Long haveMinMemory; // 已占用内存最小值

	@Column(name = "have_average_memory")
	private Float haveAverageMemory; // 已占用内存平均值

	@Column(name = "free_max_memory")
	private Long freeMaxMemory; // 空闲内存最大值

	@Column(name = "free_min_memory")
	private Long freeMinMemory; // 空闲内存最小值

	@Column(name = "free_average_memory")
	private Float freeAverageMemory; // 空闲内存平均值

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public Integer getThreadMax() {
		return threadMax;
	}

	public void setThreadMax(Integer threadMax) {
		this.threadMax = threadMax;
	}

	public Integer getThreadMin() {
		return threadMin;
	}

	public void setThreadMin(Integer threadMin) {
		this.threadMin = threadMin;
	}

	public Float getThreadAverage() {
		return threadAverage;
	}

	public void setThreadAverage(Float threadAverage) {
		this.threadAverage = threadAverage;
	}

	public Integer getBusyThreadMax() {
		return busyThreadMax;
	}

	public void setBusyThreadMax(Integer busyThreadMax) {
		this.busyThreadMax = busyThreadMax;
	}

	public Integer getBusyThreadMin() {
		return busyThreadMin;
	}

	public void setBusyThreadMin(Integer busyThreadMin) {
		this.busyThreadMin = busyThreadMin;
	}

	public Float getBusyThreadAverage() {
		return busyThreadAverage;
	}

	public void setBusyThreadAverage(Float busyThreadAverage) {
		this.busyThreadAverage = busyThreadAverage;
	}

	public Long getHaveMaxMemory() {
		return haveMaxMemory;
	}

	public void setHaveMaxMemory(Long haveMaxMemory) {
		this.haveMaxMemory = haveMaxMemory;
	}

	public Long getHaveMinMemory() {
		return haveMinMemory;
	}

	public void setHaveMinMemory(Long haveMinMemory) {
		this.haveMinMemory = haveMinMemory;
	}

	public Float getHaveAverageMemory() {
		return haveAverageMemory;
	}

	public void setHaveAverageMemory(Float haveAverageMemory) {
		this.haveAverageMemory = haveAverageMemory;
	}

	public Long getFreeMaxMemory() {
		return freeMaxMemory;
	}

	public void setFreeMaxMemory(Long freeMaxMemory) {
		this.freeMaxMemory = freeMaxMemory;
	}

	public Long getFreeMinMemory() {
		return freeMinMemory;
	}

	public void setFreeMinMemory(Long freeMinMemory) {
		this.freeMinMemory = freeMinMemory;
	}

	public Float getFreeAverageMemory() {
		return freeAverageMemory;
	}

	public void setFreeAverageMemory(Float freeAverageMemory) {
		this.freeAverageMemory = freeAverageMemory;
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

}
