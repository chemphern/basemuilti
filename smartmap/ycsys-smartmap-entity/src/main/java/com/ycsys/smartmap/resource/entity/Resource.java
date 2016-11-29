package com.ycsys.smartmap.resource.entity;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ycsys.smartmap.sys.entity.Attachment;
import com.ycsys.smartmap.sys.entity.User;

/**
 * 资源 实体
 * 
 * @author liweixiong
 * @date 2016年11月2日
 */
@Entity
@Table(name = "r_resource")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class Resource implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false, length = 50)
	private String name; // 资源名称

	@Column(name = "full_name", nullable = false, length = 100)
	private String fullName; // 资源全称

	@Column(name = "type", nullable = false, length = 1)
	private String type; // 0:文档资料;1:专题地图;2:网络图集

	@Column(name = "file_type", length = 1)
	private String fileType; // 0：txt;1:zip;

	@Column(name = "status", length = 1)
	private String status = "0"; // 状态 0:待发布；1：发布

	@Column(name = "file_path", length = 200)
	private String filePath; // 上传文件路径

	@Column(name = "upload_status", length = 1)
	private String uploadStatus = "0"; // 1：成功；0：失败

	@ManyToOne
	@JoinColumn(name = "upload_person")
	private User uploadPerson; // 上传者

	@Column(name = "upload_date")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date uploadDate; // 上传时间

	@Column(name = "sort", nullable = false)
	private Integer sort; // 序号（用于排序）

	@ManyToOne
	@JoinColumn(name = "resource_type_id")
	private ResourceType resourceType; // 资源所属分类

	@Column(name = "del_flag", nullable = false, length = 2)
	private String delFlag = "0"; // 0:正常；1删除

	@ManyToOne
	@JoinColumn(name = "attachment_id")
	private Attachment attachment; // 附件
	
	@Column(name = "clusterName",length = 50)
	private String clusterName;

	@Column(name = "remarks", length = 200)
	private String remarks; // 备注

	@Column(name = "create_date", nullable = false)
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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getUploadStatus() {
		return uploadStatus;
	}

	public void setUploadStatus(String uploadStatus) {
		this.uploadStatus = uploadStatus;
	}

	public User getUploadPerson() {
		return uploadPerson;
	}

	public void setUploadPerson(User uploadPerson) {
		this.uploadPerson = uploadPerson;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceType resourceType) {
		this.resourceType = resourceType;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
	
	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
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
