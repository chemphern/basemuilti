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

import com.ycsys.smartmap.sys.entity.User;

/**
 * 服务申请 实体
 * 
 * @author liweixiong
 * @date 2016年11月3日
 */
@Entity
@Table(name = "s_service_apply")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class ServiceApply implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "title", length = 50)
	private String title; // 标题

	@ManyToOne
	@JoinColumn(name = "service_id")
	private Service service; // 申请的服务

	@ManyToOne
	@JoinColumn(name = "apply_user_id")
	private User applyUser; // 申请人

	@Column(name = "apply_date")
	private Date applyDate; // 申请日期

	@Column(name = "reason", length = 200)
	private String reason; // 申请理由

	@Column(name = "audit_status", length = 1)
	private String auditStastus; // 审核状态（0：不通过；1：通过）

	@Column(name = "audit_date")
	private Date auditDate; // 审核蜞

	@ManyToOne
	@JoinColumn(name = "auditor_id")
	private User auditor; // 审核人

	@Column(name = "audit_option")
	private String auditOption;// 审核意见

	@Column(name = "valid_date", length = 10)
	private String validDate; // 有效期

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public User getApplyUser() {
		return applyUser;
	}

	public void setApplyUser(User applyUser) {
		this.applyUser = applyUser;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getAuditStastus() {
		return auditStastus;
	}

	public void setAuditStastus(String auditStastus) {
		this.auditStastus = auditStastus;
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

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
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
