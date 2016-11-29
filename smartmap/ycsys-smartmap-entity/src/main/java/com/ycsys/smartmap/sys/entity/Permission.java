package com.ycsys.smartmap.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 权限 entity
 * @author lixiaoxin
 * @date 2016年11月1日
 */
@Entity
@Table(name = "sys_permission")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate @DynamicInsert
public class Permission implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	//权限名
	@Column(name="name",length = 50)
	private String name;

	//权限类型
	@Column(name="type",length = 20)
	private String type;

	//层级
	@Column(name="levels")
	private short level;

	@Column(name = "pid")
	private Integer pid;

	//链接
	@Column(name="url")
	private String url;

	//排序
	@Column(name="sort")
	private Integer sort;

	//权限代码
	@Column(name="code",length=3999)
	private String code;

	@Column(name = "system_code")
	private String systemCode;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "permission")
	private Set<RolePermission> rolePermissions = new HashSet<RolePermission>(0);

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "permission")
	private Set<OrganizationPermission> organizationPermissions = new HashSet<OrganizationPermission>(0);

	@Transient
	@JsonProperty("children")
	private List<Permission> childPermission;
	public Permission() {
	}

	public Permission(String name) {
		this.name = name;
	}
	
	public Permission(Integer id) {
		this.id=id;
	}
	
	public Permission (Integer id,String name){
		this.id=id;
		this.name=name;
	}
	
	public Permission (String name, String type,String url,String code){
		this.name=name;
		this.type=type;
		this.url=url;
		this.code=code;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	public List<Permission> getChildPermission() {
		return this.childPermission;
	}


	public void setChildPermission(List<Permission> childPermission) {
			this.childPermission = childPermission;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}



	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public Set<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(Set<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	/**
	 * 模块权限设置属性
	 * **/
	public void setAuthcModule(String name,Integer sort,short level,String systemCode){
		this.name = name;
		this.sort = sort;
		this.type = "module";
		this.level = level;
		this.systemCode = systemCode;

	}
	/**菜单权限设置属性**/
	public void setAuthcMenu(String name,Integer sort,short level,String systemCode){
		this.name = name;
		this.sort = sort;
		this.type = "menu";
		this.level = level;
		this.systemCode = systemCode;
	}
	/**链接权限设置属性**/
	public void setAuthcUrl(String name,Integer sort,short level,String href,String funcs,String systemCode){
		this.name = name;
		this.sort = sort;
		this.type = "url";
		this.level = level;
		this.url = href;
		this.code = funcs;
		this.systemCode = systemCode;
	}

	/**功能点设置属性**/
	public void setAuthcFunc(String name,Integer sort,short level,String code,String systemCode){
		this.name = name;
		this.sort = sort;
		this.type = "func";
		this.level = level;
		this.code = code;
		this.systemCode = systemCode;
	}

	public Set<OrganizationPermission> getOrganizationPermissions() {
		return organizationPermissions;
	}

	public void setOrganizationPermissions(Set<OrganizationPermission> organizationPermissions) {
		this.organizationPermissions = organizationPermissions;
	}
}