package com.ycsys.smartmap.sys.entity;

import javax.persistence.*;

/**
 * 角色权限entity
 * @author lixiaoxin
 * @date 2016年11月1日
 */
@Entity
@Table(name = "sys_role_permission")
public class RolePermission implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "permission_id")
	private Permission permission;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;

	public RolePermission() {
	}

	public RolePermission(Permission permission, Role role) {
		this.permission = permission;
		this.role = role;
	}


	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Permission getPermission() {
		return this.permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}