package com.ycsys.smartmap.sys.entity;

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
 * 服务器监控配置 实体
 * 
 * @author liweixiong
 * @date 2016年11月2日
 */
@Entity
@Table(name = "sys_config_server_monitor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class ConfigServerMonitor implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "name", nullable = false, length = 30)
	private String name; // 名称

	@Column(name = "monitor_type", nullable = false, length = 1)
	private String monitorType; // 监控类型(0:应用服务器；1：数据库；2：GIS服务器)

	@Column(name = "software_type", nullable = false, length = 1)
	private String softwareType;// 应用软件类型（0：tomcat;1:oracle;2:gisServer）

	@Column(name = "url", length = 50)
	private String url; // 监控的URL

	@Column(name = "user_name", length = 30)
	private String userName; // 用户名

	@Column(name = "user_password", length = 30)
	private String userPassword; // 用户密码

	@Column(name = "database_driver", length = 30)
	private String databaseDriver; // 数据库驱动

	@ManyToOne
	@JoinColumn(name = "server_engine_id")
	private ConfigServerEngine serverEngineConfig; //服务引擎

	@ManyToOne
	@JoinColumn(name = "server_id")
	private Server server;  //服务器

	@Column(name = "remarks", length = 200)
	private String remarks; // 备注

	@Column(name = "create_date")
	private Date createDate; // 创建时间

	@ManyToOne
	@JoinColumn(name = "creator_id")
	private User creator; // 创建者

	@Column(name = "update_date")
	private Date updateDate; // 更新时间

	@ManyToOne
	@JoinColumn(name = "updator_id")
	private User updator; // 更新者

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

	public String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}

	public String getSoftwareType() {
		return softwareType;
	}

	public void setSoftwareType(String softwareType) {
		this.softwareType = softwareType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getDatabaseDriver() {
		return databaseDriver;
	}

	public void setDatabaseDriver(String databaseDriver) {
		this.databaseDriver = databaseDriver;
	}

	public ConfigServerEngine getServerEngineConfig() {
		return serverEngineConfig;
	}

	public void setServerEngineConfig(ConfigServerEngine serverEngineConfig) {
		this.serverEngineConfig = serverEngineConfig;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public User getUpdator() {
		return updator;
	}

	public void setUpdator(User updator) {
		this.updator = updator;
	}

}
