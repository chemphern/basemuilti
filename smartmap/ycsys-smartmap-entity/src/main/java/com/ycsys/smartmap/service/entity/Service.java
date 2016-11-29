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
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ycsys.smartmap.resource.entity.Resource;
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

	@ManyToOne
	@JoinColumn(name = "resource_id")
	private Resource resource; // 服务对应的资源

	@Column(name = "publish_directory", length = 200)
	private String publishDirectory; // 发布目录

	@Column(name = "service_extend", length = 200)
	private String serviceExtend;// 服务扩展

	@Column(name = "register_type", length = 1)
	private String registerType; // 注册类型(0:gis服务注册;1:OneMap服务注册)

	@Column(name = "service_status", nullable = false, length = 1)
	private String serviceStatus = "0";	//服务状态（0：停止；1：启动）

	@Column(name = "permission_status", length = 1)
	private String permissionStatus;// 0：自由服务；1：安全服务

	@Column(name = "max_version_num")
	private Integer maxVersionNum = 1; // 最大版本号

	@Column(name = "version_remarks")
	private String versiomnRemarks; // 更新版本备注

	/*@ManyToOne
	@JoinColumn(name = "service_type_id")
	private ServiceType serviceType; // 服务类型 (不用了)
*/	
	@Column(name = "type")
	private String type; //服务类型（0：文档服务；1：网络图集服务；2：第三方注册服务）
	
	@Column(name = "function_type")
	private String functionType; //功能类型（0：GlobeServer；1：MapServer；2：GeocodeServer；3：GPServer；4：GeometryServer；5：ImageServer；6：GeoDataServer；7：SearchServer）
	
	@Transient
	private String arcServiceType; //ArcGIS上面的服务类型  GeometryServer | ImageServer | MapServer | GeocodeServer | GeoDataServer | GPServer | GlobeServer | SearchServer
	@Transient
	private String folderName;
	
	@Column(name = "cluster_name")
	private String clusterName;

	@Column(name = "cache_type", length = 1)
	private String cacheType;// 服务缓存类型(0:Dynamic;1:Tiled)

	@Column(name = "remote_services_type", length = 1)
	private String remoteServicesType; //远程服务类型（0：ArcGIS;1:OGC;2:Other）

	@Column(name = "service_visit_address", length = 100)
	private String serviceVisitAddress;// 服务访问地址
	
	@Column(name = "imagePath")
	private String imagePath; //服务缩略图 存放路径

	@Column(name = "metadata_visit_address", length = 100)
	private String metadataVisitAddress;// 元数据访问地址
	
	@Column(name = "remarks", length = 200)
	private String remarks; // 备注
	
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

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getPublishDirectory() {
		return publishDirectory;
	}

	public void setPublishDirectory(String publishDirectory) {
		this.publishDirectory = publishDirectory;
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

	public String getArcServiceType() {
		return arcServiceType;
	}

	public void setArcServiceType(String arcServiceType) {
		this.arcServiceType = arcServiceType;
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

}
