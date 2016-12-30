package com.ycsys.smartmap.service.entity;

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

import com.ycsys.smartmap.sys.entity.ConfigServerEngine;
import com.ycsys.smartmap.sys.entity.User;

/**
 * 服务 实体
 * 
 * @author liweixiong
 * @date 2016年11月3日
 */
@Entity
@Table(name = "s_service")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class Service implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "register_name", length = 30)
	private String registerName; // 注册名称(为英文)

	@Column(name = "show_name", nullable = false, length = 30)
	private String showName; // 服务显示名
	
	@Column(name = "arc_gis_service_name")
	private String arcGisServiceName; //在arcGisserver上面的服务名字，它可能跟注册到平台的名字不同

	@Column(name = "service_extend", length = 200)
	private String serviceExtend;// 服务扩展(KmlServer;FeatureServer;NAServer;WCSServer;WMSServer;MobileServer;JPIPServer)

	@Column(name = "register_type", length = 1)
	private String registerType; // 注册类型(0:gis服务注册;1:OneMap服务注册)

	@Column(name = "service_status", nullable = false, length = 1)
	private String serviceStatus;	//服务状态（0：停止；1：启动）
	
	@Column(name = "audit_status", length = 1)
	private String auditStatus; //服务审核状态（0：未审核；1：审核通过；2：审核不通过）
	
	@Column(name = "audit_date")
	private Date auditDate; // 审核日期

	@ManyToOne
	@JoinColumn(name = "auditor_id")
	private User auditor; // 审核人

	@Column(name = "audit_option")
	private String auditOption;// 审核意见

	@Column(name = "permission_status", length = 1)
	private String permissionStatus;// 0：自由服务；1：安全服务

	@Column(name = "max_version_num")
	private Integer maxVersionNum; // 最大版本号

	@Column(name = "version_remarks")
	private String versiomnRemarks; // 更新版本备注

	@Column(name = "type")
	private String type; //服务类型（0：文档服务；1：网络图集服务；2：第三方注册服务）
	
	@Column(name = "function_type")
	private String functionType; //功能类型（GlobeServer；MapServer；GeocodeServer；GPServer；GeometryServer；ImageServer；GeoDataServer；SearchServer）
	
	@Column(name = "folder_name")
	private String folderName; //服务的发布目录
	
	@Column(name = "cluster_name")
	private String clusterName; //服务的集群

	@Column(name = "cache_type", length = 1)
	private String cacheType;// 服务缓存类型(0:Dynamic;1:Tiled)

	@Column(name = "remote_services_type", length = 1)
	private String remoteServicesType; //远程服务类型（0：ArcGIS;1:OGC;）

	@Column(name = "service_visit_address", length = 200)
	private String serviceVisitAddress;// 服务访问地址
	
	@Column(name = "service_visit_address_open", length = 200)
	private String serviceVisitAddressOpen;// 服务对外访问地址(二、三维系统用)
	
	@Column(name = "manager_service_url", length = 200)
	private String managerServiceUrl;// 服务管理url(用于启动、停止、删除服务操作)
	
	@Column(name = "imagePath")
	private String imagePath; //服务缩略图 存放路径

	@Column(name = "metadata_visit_address", length = 100)
	private String metadataVisitAddress;// 元数据访问地址
	
	@Column(name = "more_property", length = 1)
	private String moreProperty;//(空:没有更多属性；1：有更多属性)

	@Column(name = "remarks", length = 200)
	private String remarks; // 备注

	@Column(name="monitor_status")
	private String monitorStatus;//监控状态

	@Column(name="monitor_rate")
	private String monitorRate;//监控频率

	@ManyToOne
	@JoinColumn(name = "server_engine_id")
	private ConfigServerEngine serverEngine;
	
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

	public String getRegisterName() {
		return registerName;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getServiceExtend() {
		return serviceExtend;
	}

	public void setServiceExtend(String serviceExtend) {
		this.serviceExtend = serviceExtend;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getServiceStatus() {
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}

	public String getPermissionStatus() {
		return permissionStatus;
	}

	public void setPermissionStatus(String permissionStatus) {
		this.permissionStatus = permissionStatus;
	}

	public Integer getMaxVersionNum() {
		return maxVersionNum;
	}

	public void setMaxVersionNum(Integer maxVersionNum) {
		this.maxVersionNum = maxVersionNum;
	}

	public String getVersiomnRemarks() {
		return versiomnRemarks;
	}

	public void setVersiomnRemarks(String versiomnRemarks) {
		this.versiomnRemarks = versiomnRemarks;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCacheType() {
		return cacheType;
	}

	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
	}

	public String getRemoteServicesType() {
		return remoteServicesType;
	}

	public void setRemoteServicesType(String remoteServicesType) {
		this.remoteServicesType = remoteServicesType;
	}

	public String getServiceVisitAddress() {
		return serviceVisitAddress;
	}

	public void setServiceVisitAddress(String serviceVisitAddress) {
		this.serviceVisitAddress = serviceVisitAddress;
	}

	public String getMetadataVisitAddress() {
		return metadataVisitAddress;
	}

	public void setMetadataVisitAddress(String metadataVisitAddress) {
		this.metadataVisitAddress = metadataVisitAddress;
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

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getFunctionType() {
		return functionType;
	}

	public void setFunctionType(String functionType) {
		this.functionType = functionType;
	}

	public ConfigServerEngine getServerEngine() {
		return serverEngine;
	}

	public void setServerEngine(ConfigServerEngine serverEngine) {
		this.serverEngine = serverEngine;
	}

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getManagerServiceUrl() {
		return managerServiceUrl;
	}

	public void setManagerServiceUrl(String managerServiceUrl) {
		this.managerServiceUrl = managerServiceUrl;
	}

	public String getArcGisServiceName() {
		return arcGisServiceName;
	}

	public void setArcGisServiceName(String arcGisServiceName) {
		this.arcGisServiceName = arcGisServiceName;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public User getAuditor() {
		return auditor;
	}

	public void setAuditor(User auditor) {
		this.auditor = auditor;
	}

	public String getAuditOption() {
		return auditOption;
	}

	public void setAuditOption(String auditOption) {
		this.auditOption = auditOption;
	}

	public String getMoreProperty() {
		return moreProperty;
	}

	public void setMoreProperty(String moreProperty) {
		this.moreProperty = moreProperty;
	}


	public String getMonitorStatus() {
		return monitorStatus;
	}

	public void setMonitorStatus(String monitorStatus) {
		this.monitorStatus = monitorStatus;
	}

	public String getMonitorRate() {
		return monitorRate;
	}

	public void setMonitorRate(String monitorRate) {
		this.monitorRate = monitorRate;
	}

	public String getServiceVisitAddressOpen() {
		return serviceVisitAddressOpen;
	}

	public void setServiceVisitAddressOpen(String serviceVisitAddressOpen) {
		this.serviceVisitAddressOpen = serviceVisitAddressOpen;
	}
	
}
