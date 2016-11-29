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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 异常报警配置 实体
 * 
 * @author liweixiong
 * @date 2016年11月2日
 */
@Entity
@Table(name = "sys_config_exception_alarm")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class ConfigExceptionAlarm implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "rule_type")
	private String ruleType;// 异常报警规则类型（0：服务异常；1：网络异常；2：数据库异常；3：应用服务器异常
							// ；4：GIS服务器异常）

	@ManyToOne
	@JoinColumn(name = "org_id")
	private Organization org; // 部门

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user; // 用户

	@Column(name = "user_grade")
	private String userGrade;// 用户等级（0：第一级；1：第二级；2：第三级）

	@Column(name = "interval_time")
	private Integer intervalTime; // 时间间隔（用户等级为第一级：0小时；第二级：10小时；第三级：20小时）

	@Column(name = "send_way", nullable = false, length = 1)
	private String sendWay; // 发送方式 (0:电子邮件；1：手机短信)

	@Column(name = "mail_address", length = 20)
	private String mailAddress;

	@Column(name = "phone", length = 20)
	private String phone;

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

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserGrade() {
		return userGrade;
	}

	public void setUserGrade(String userGrade) {
		this.userGrade = userGrade;
	}

	public Integer getIntervalTime() {
		return intervalTime;
	}

	public void setIntervalTime(Integer intervalTime) {
		this.intervalTime = intervalTime;
	}

	public String getSendWay() {
		return sendWay;
	}

	public void setSendWay(String sendWay) {
		this.sendWay = sendWay;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
