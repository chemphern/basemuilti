package com.ycsys.smartmap.sys.entity;

import javax.persistence.*;

/**
 * 用户角色entity
 * @author lixiaoxin
 * @date 2016年11月1日
 */
@Entity
@Table(name = "sys_user_role")
public class UserRole implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	public UserRole() {
	}

	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}