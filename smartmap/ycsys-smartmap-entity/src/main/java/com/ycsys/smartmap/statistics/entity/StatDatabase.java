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
import org.springframework.cglib.core.Local;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycsys.smartmap.sys.entity.User;

/**
 * 数据库统计
 * 
 * @author liweixiong
 * @date 2016年11月3日
 */
@Entity
@Table(name = "stat_database")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class StatDatabase implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "database_name", length = 30)
	private String databaseName;

	@Column(name = "session_count")
	private Integer sessionCount; // 会话个数

	@Column(name = "session_max_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date sessionMaxDate; // 当前会话数最大值发生时间

	@Column(name = "session_max_count")
	private Integer sessionMaxCount; // 当前会话数最大值

	@Column(name = "session_min_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date sessionMinDate; // 当前会话数最小值发生时间

	@Column(name = "session_min_count")
	private Integer sessionMinCount; // 当前会话数最小值

	@Column(name = "session_average")
	private Float sessionAverage; // 当前会话平均值

	@Column(name = "statistics_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
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

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public Integer getSessionCount() {
		return sessionCount;
	}

	public void setSessionCount(Integer sessionCount) {
		this.sessionCount = sessionCount;
	}

	public Date getSessionMaxDate() {
		return sessionMaxDate;
	}

	public void setSessionMaxDate(Date sessionMaxDate) {
		this.sessionMaxDate = sessionMaxDate;
	}

	public Integer getSessionMaxCount() {
		return sessionMaxCount;
	}

	public void setSessionMaxCount(Integer sessionMaxCount) {
		this.sessionMaxCount = sessionMaxCount;
	}

	public Date getSessionMinDate() {
		return sessionMinDate;
	}

	public void setSessionMinDate(Date sessionMinDate) {
		this.sessionMinDate = sessionMinDate;
	}

	public Integer getSessionMinCount() {
		return sessionMinCount;
	}

	public void setSessionMinCount(Integer sessionMinCount) {
		this.sessionMinCount = sessionMinCount;
	}

	public Float getSessionAverage() {
		return sessionAverage;
	}

	public void setSessionAverage(Float sessionAverage) {
		this.sessionAverage = sessionAverage;
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
