package com.ycsys.smartmap.resource.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ycsys.smartmap.sys.entity.User;

/**
 * 资源分类 实体
 * 
 * @author liweixiong
 * @date 2016年10月27日
 */
@Entity
@Table(name = "r_resource_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class ResourceType implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "name", nullable = false, length = 30)
	private String name; // 名称

	@Column(name = "backups_flag", nullable = false, length = 1)
	private String backupsFlag = "0"; // 0:未备份；1：已备份
	
	@Column(name = "backup_date")
	private Date backupDate; // 备份时间

	@ManyToOne
	@JoinColumn(name = "parent_id")
	private ResourceType parent; // 父亲结点
	
	/*
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
	private Set<ResourceType> children = new HashSet<ResourceType>(); // 孩子结点
	*/
	@Column(name = "del_flag", nullable = false, length = 1)
	private String delFlag = "0"; // 0:正常；1删除

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

	public String getBackupsFlag() {
		return backupsFlag;
	}

	public void setBackupsFlag(String backupsFlag) {
		this.backupsFlag = backupsFlag;
	}

	
	public Date getBackupDate() {
		return backupDate;
	}

	public void setBackupDate(Date backupDate) {
		this.backupDate = backupDate;
	}

	public ResourceType getParent() {
		return parent;
	}

	public void setParent(ResourceType parent) {
		this.parent = parent;
	}

	/*
	public Set<ResourceType> getChildren() {
		return children;
	}

	public void setChildren(Set<ResourceType> children) {
		this.children = children;
	}
*/
	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
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
