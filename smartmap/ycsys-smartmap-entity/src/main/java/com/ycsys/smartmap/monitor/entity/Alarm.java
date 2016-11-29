package com.ycsys.smartmap.monitor.entity;

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
 * 异常报警
 * 
 * @author liweixiong
 * @date 2016年11月3日
 */
@Entity
@Table(name = "m_alarm")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class Alarm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "title", length = 30)
	private String title;	//标题

	@Column(name = "content", length = 200)
	private String content;	//报警内容
	
	@Column(name = "grade",length = 1)
	private String grade;	//报警等级
	
	@Column(name = "status",length = 1)
	private String status;	//状态
	
	@Column(name = "happen_date")
	private Date happenDate; //异常发生时间
	
	@ManyToOne
	@JoinColumn(name = "deal_user_id")
	private User dealUser;	//处理人
	
	@Column(name = "deal_opption",length = 200)
	private String dealOpption; //处理意见
	
	@Column(name = "deal_date")
	private Date dealDate;//处理时间
	
	@Column(name = "deal_flag",length = 1)
	private String dealFlag; //处理标志
	
	@Column(name = "create_date")
	private Date createDate; // 创建时间
	
	@Column(name = "remarks",length = 100)
	private String remarks; //备注

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getHappenDate() {
		return happenDate;
	}

	public void setHappenDate(Date happenDate) {
		this.happenDate = happenDate;
	}

	public User getDealUser() {
		return dealUser;
	}

	public void setDealUser(User dealUser) {
		this.dealUser = dealUser;
	}

	public String getDealOpption() {
		return dealOpption;
	}

	public void setDealOpption(String dealOpption) {
		this.dealOpption = dealOpption;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
