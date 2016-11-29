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

/**
 * 数据库监控
 * 
 * @author liweixiong
 * @date 2016年11月3日
 */
@Entity
@Table(name = "m_database")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class DatabaseMonitor implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "database_name", length = 20)
	private String databaseName; // 数据库名

	@Column(name = "version", length = 20)
	private String version; // 数据库版本

	@Column(name = "table_space_total_size")
	private Integer tableSpaceTotalSize; // 表空间总大小

	@Column(name = "table_space_remain_size")
	private Integer tableSpaceRemainSize;// 表空间剩余大小

	@Column(name = "table_space_max_continue")
	private Integer tableSpaceMaxContine; // 表空间最大连续空闲块

	@Column(name = "table_space_fragment")
	private Integer tableSpaceFragment; // 表空间碎片数

	@Column(name = "read_request")
	private Integer readRequest; // 写请求数

	@Column(name = "write_request")
	private Integer writeRequest; // 读请求数

	@Column(name = "table_space_read_request")
	private Integer tableSpaceReadRequest; // 表空间写请求数

	@Column(name = "table_space_write_request")
	private Integer tableSpaceWriteRequest; // 表空间读请求数

	@Column(name = "database_buffer_hit_ratio")
	private Float databaseBufferHitRatio; // 数据缓冲命中率

	@Column(name = "session_count")
	private Integer sessionCount; // 当前登录会话数

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

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getTableSpaceTotalSize() {
		return tableSpaceTotalSize;
	}

	public void setTableSpaceTotalSize(Integer tableSpaceTotalSize) {
		this.tableSpaceTotalSize = tableSpaceTotalSize;
	}

	public Integer getTableSpaceRemainSize() {
		return tableSpaceRemainSize;
	}

	public void setTableSpaceRemainSize(Integer tableSpaceRemainSize) {
		this.tableSpaceRemainSize = tableSpaceRemainSize;
	}

	public Integer getTableSpaceMaxContine() {
		return tableSpaceMaxContine;
	}

	public void setTableSpaceMaxContine(Integer tableSpaceMaxContine) {
		this.tableSpaceMaxContine = tableSpaceMaxContine;
	}

	public Integer getTableSpaceFragment() {
		return tableSpaceFragment;
	}

	public void setTableSpaceFragment(Integer tableSpaceFragment) {
		this.tableSpaceFragment = tableSpaceFragment;
	}

	public Integer getReadRequest() {
		return readRequest;
	}

	public void setReadRequest(Integer readRequest) {
		this.readRequest = readRequest;
	}

	public Integer getWriteRequest() {
		return writeRequest;
	}

	public void setWriteRequest(Integer writeRequest) {
		this.writeRequest = writeRequest;
	}

	public Integer getTableSpaceReadRequest() {
		return tableSpaceReadRequest;
	}

	public void setTableSpaceReadRequest(Integer tableSpaceReadRequest) {
		this.tableSpaceReadRequest = tableSpaceReadRequest;
	}

	public Integer getTableSpaceWriteRequest() {
		return tableSpaceWriteRequest;
	}

	public void setTableSpaceWriteRequest(Integer tableSpaceWriteRequest) {
		this.tableSpaceWriteRequest = tableSpaceWriteRequest;
	}

	public Float getDatabaseBufferHitRatio() {
		return databaseBufferHitRatio;
	}

	public void setDatabaseBufferHitRatio(Float databaseBufferHitRatio) {
		this.databaseBufferHitRatio = databaseBufferHitRatio;
	}

	public Integer getSessionCount() {
		return sessionCount;
	}

	public void setSessionCount(Integer sessionCount) {
		this.sessionCount = sessionCount;
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
