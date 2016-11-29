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

import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.sys.entity.User;

/**
 * 服务统计	实体
 * 
 * @author liweixiong
 * @date 2016年11月3日
 */
@Entity
@Table(name = "stat_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class statService implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@ManyToOne
	@JoinColumn(name = "service_id")
	private Service service;// 服务

	@Column(name = "service_visit_count")
	private Integer serviceVisitCount; // 服务访问次数

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "user_visit_count")
	private Integer userVisitCount; // 用户访问服务次数

	@Column(name = "ip_address", length = 20)
	private String ipAddress; // IP地址

	@Column(name = "ip_visit_count")
	private Integer ipVisitCount; // IP地址访问服务次数

	@Column(name = "visit_date")
	private Date visitDate; // 服务访问时间

	@Column(name = "waiting_time")
	private Float waitingTime; // 服务等待时间

	@Column(name = "average_response_time")
	private Float averageResponseTime;// 服务平均响应时间

	@Column(name = "visit_success_rate")
	private Float visitSuccessRate;// 服务访问成功率

	@Column(name = "visit_average_success_rate")
	private Float visitAverageSuccessRate; // 服务访问平均成功率

	@Column(name = "start_date")
	private Date startDate; // 服务启动时间

	@Column(name = "exception_date")
	private Date exceptionDate;// 服务发生异常时间

	@Column(name = "recovery_date")
	private Date recoveryDate;// 异常恢复时间

	@Column(name = "exception_time")
	private Integer exceptonTime; // 异常持续时间

	@Column(name = "status_info", length = 100)
	private String statusInfo;

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

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public Integer getServiceVisitCount() {
		return serviceVisitCount;
	}

	public void setServiceVisitCount(Integer serviceVisitCount) {
		this.serviceVisitCount = serviceVisitCount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getUserVisitCount() {
		return userVisitCount;
	}

	public void setUserVisitCount(Integer userVisitCount) {
		this.userVisitCount = userVisitCount;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Integer getIpVisitCount() {
		return ipVisitCount;
	}

	public void setIpVisitCount(Integer ipVisitCount) {
		this.ipVisitCount = ipVisitCount;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public Float getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(Float waitingTime) {
		this.waitingTime = waitingTime;
	}

	public Float getAverageResponseTime() {
		return averageResponseTime;
	}

	public void setAverageResponseTime(Float averageResponseTime) {
		this.averageResponseTime = averageResponseTime;
	}

	public Float getVisitSuccessRate() {
		return visitSuccessRate;
	}

	public void setVisitSuccessRate(Float visitSuccessRate) {
		this.visitSuccessRate = visitSuccessRate;
	}

	public Float getVisitAverageSuccessRate() {
		return visitAverageSuccessRate;
	}

	public void setVisitAverageSuccessRate(Float visitAverageSuccessRate) {
		this.visitAverageSuccessRate = visitAverageSuccessRate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getExceptionDate() {
		return exceptionDate;
	}

	public void setExceptionDate(Date exceptionDate) {
		this.exceptionDate = exceptionDate;
	}

	public Date getRecoveryDate() {
		return recoveryDate;
	}

	public void setRecoveryDate(Date recoveryDate) {
		this.recoveryDate = recoveryDate;
	}

	public Integer getExceptonTime() {
		return exceptonTime;
	}

	public void setExceptonTime(Integer exceptonTime) {
		this.exceptonTime = exceptonTime;
	}

	public String getStatusInfo() {
		return statusInfo;
	}

	public void setStatusInfo(String statusInfo) {
		this.statusInfo = statusInfo;
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
