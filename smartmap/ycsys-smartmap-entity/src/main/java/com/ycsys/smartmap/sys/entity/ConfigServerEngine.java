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
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 服务引擎配置 实体
 * 
 * @author liweixiong
 * @date 2016年11月1日
 */
@Entity
@Table(name = "sys_config_server_engine")
@DynamicUpdate
@DynamicInsert
public class ConfigServerEngine implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	// 配置名称
	@Column(name = "config_name", nullable = false, length = 50)
	private String configName;

	// 引擎类型 （0：ArcGISServer服务引擎；1：TOKEN服务引擎；2：用户自定义服务引擎；3：服务数据服务引擎）
	@Column(name = "engineType_type", length = 1)
	private String engineType;

	// 集成模式 (0：单机；1:集群)
	@Column(name = "integration_model", nullable = false, length = 1)
	private String integrationModel = "0";

	//机器名
	@Column(name = "machine_name", nullable = false, length = 50)
	private String machineName;

	//内网ip
	@Column(name = "intranet_ip", nullable = false, length = 20)
	private String intranetIp;

	//内网端口
	@Column(name = "intranet_port", nullable = false)
	private Integer intranetPort;

	//运行状态 （0：启用；1：禁用）
	@Column(name = "running_status", nullable = false)
	private String runningStatus;

	//数据上传服务地址
	@Column(name = "data_upload_path", length = 100)
	private String dataUploadPath;
	
	//数据上传绝对路径
	@Column(name = "data_upload_real_path", length = 20)
	private String dataUploadRealPath;
	
	//引擎管理员
	@Column(name = "engine_manager", length = 20)
	private String engineManager;

	@Transient
	private String oldPassword;
	
	//管理密码
	@Column(name = "manager_password", length = 30)
	private String managerPassword;

	//创建时间
	@Column(name = "create_date")
	private Date createDate;

	//创建者
	@ManyToOne
	@JoinColumn(name = "creator_id")
	private User creator;

	//更新时间
	@Column(name = "update_date")
	private Date updateDate;

	//更新者
	@ManyToOne
	@JoinColumn(name = "updator_id")
	private User updator;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getIntegrationModel() {
		return integrationModel;
	}

	public void setIntegrationModel(String integrationModel) {
		this.integrationModel = integrationModel;
	}

	public String getMachineName() {
		return machineName;
	}

	public void setMachineName(String machineName) {
		this.machineName = machineName;
	}

	public String getIntranetIp() {
		return intranetIp;
	}

	public void setIntranetIp(String intranetIp) {
		this.intranetIp = intranetIp;
	}

	public Integer getIntranetPort() {
		return intranetPort;
	}

	public void setIntranetPort(Integer intranetPort) {
		this.intranetPort = intranetPort;
	}

	public String getRunningStatus() {
		return runningStatus;
	}

	public void setRunningStatus(String runningStatus) {
		this.runningStatus = runningStatus;
	}

	public String getDataUploadPath() {
		return dataUploadPath;
	}

	public void setDataUploadPath(String dataUploadPath) {
		this.dataUploadPath = dataUploadPath;
	}

	public String getDataUploadRealPath() {
		return dataUploadRealPath;
	}

	public void setDataUploadRealPath(String dataUploadRealPath) {
		this.dataUploadRealPath = dataUploadRealPath;
	}

	public String getEngineManager() {
		return engineManager;
	}

	public void setEngineManager(String engineManager) {
		this.engineManager = engineManager;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getManagerPassword() {
		return managerPassword;
	}

	public void setManagerPassword(String managerPassword) {
		this.managerPassword = managerPassword;
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
