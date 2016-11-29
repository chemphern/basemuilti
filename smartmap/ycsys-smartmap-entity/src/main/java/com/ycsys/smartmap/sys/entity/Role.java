package com.ycsys.smartmap.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色entity
 * @author lixiaoxin
 * @date 2016年11月1日
 */
@Entity
@Table(name = "sys_role")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate @DynamicInsert
public class Role implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	//角色名
	@Column(name = "name",length = 50)
	private String name;

	//角色编码
	@Column(name = "code",length=50)
	private String code;

	//备注描述
	@Column(name = "remark",length = 3999)
	private String remark;

	//创建者
	@Column(name = "creator")
	private Integer creator;

	//创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "create_time")
	private Timestamp createTime;

	//更新时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "update_time")
	private Timestamp updateTime;

	//更新者
	@Column(name = "updater")
	private Integer updater;

	//排序
	@Column(name = "sort")
	private Integer sort;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<RolePermission> rolePermissions = new HashSet<RolePermission>(0);

	public Role() {
	}
	
	public Role(Integer id) {
		this.id=id;
	}

	public Role(String name, String code) {
		this.name = name;
		this.code = code;
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUpdater() {
		return updater;
	}

	public void setUpdater(Integer updater) {
		this.updater = updater;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(Set<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

}