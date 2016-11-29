package com.ycsys.smartmap.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户entity
 * @author lixiaoxin
 * @date 2016年11月1日
 */
@Entity
@Table(name = "sys_user")
@DynamicUpdate @DynamicInsert
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	//id
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	//姓名
	@Column(name="name" ,nullable = false, length = 20)
	private String name;

	//登录名
	@Column(name="login_name",nullable = false, length = 20)
	private String loginName;

	//密码
	@Column(name="password" ,nullable = false, length = 100)
	private String password;

	@Transient
	@JsonIgnore
	private String plainPassword;

	//类型
	@Column(name="type")
	private short type;

	//email
	@Column(name="email",length = 50)
	private String email;

	//备注
	@Column(name="remark",length=3999)
	private String remark;

	//最后登录ip
	@Column(name="last_login_ip",length = 30)
	private String lastLoginIp;

	//最后登录时间
	@Column(name="last_login_time")
	private Timestamp lastLoginTime;

	//状态
	@Column(name="status")
	private short status;

	//创建时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name="create_time")
	private Timestamp createTime;

	//注册时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name="regist_time")
	private Timestamp registTime;

	//创建端
	@Column(name="create_source")
	private Integer createSource;

	//盐值
	@Column(name="salt")
	private String salt;

	//生日
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
	@Column(name="birthday")
	private Timestamp birthday;

	//性别
	@Column(name="sex")
	private Short sex;

	//手机号
	@Column(name="phone",length=15)
	private String phone;

	//头像
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="icon",columnDefinition="BLOB")
	private byte[] icon;

	//登陆次数
	@Column(name="login_count")
	private Integer loginCount;

	//上次登录
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name="previous_visit")
	private Timestamp previousVisit;

	//最后一次登录
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	@Column(name = "last_visit", length = 19)
	private Timestamp lastVisit;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<UserRole> userRoles = new HashSet<UserRole>(0);

	@ManyToOne(targetEntity=Organization.class )
	@JoinColumn(name = "organization_id", nullable = true)
	private Organization organization;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	private Set<SystemUser> systemUsers = new HashSet<SystemUser>(0);

	@Transient
	private boolean isSuper = false;

	public User() {
	}
	
	public User(Integer id) {
		this.id=id;
	}

	public User(String loginName, String name, String password) {
		this.loginName = loginName;
		this.name = name;
		this.password = password;
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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Timestamp registTime) {
		this.registTime = registTime;
	}

	public Integer getCreateSource() {
		return createSource;
	}

	public void setCreateSource(Integer createSource) {
		this.createSource = createSource;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Timestamp getBirthday() {
		return birthday;
	}

	public void setBirthday(Timestamp birthday) {
		this.birthday = birthday;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public byte[] getIcon() {
		return icon;
	}

	public void setIcon(byte[] icon) {
		this.icon = icon;
	}

	public Integer getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	public Timestamp getPreviousVisit() {
		return previousVisit;
	}

	public void setPreviousVisit(Timestamp previousVisit) {
		this.previousVisit = previousVisit;
	}

	public Timestamp getLastVisit() {
		return lastVisit;
	}

	public void setLastVisit(Timestamp lastVisit) {
		this.lastVisit = lastVisit;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<SystemUser> getSystemUsers() {
		return systemUsers;
	}

	public void setSystemUsers(Set<SystemUser> systemUsers) {
		this.systemUsers = systemUsers;
	}

	public boolean isSuper() {
		return isSuper;
	}

	public void setSuper(boolean aSuper) {
		isSuper = aSuper;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
}