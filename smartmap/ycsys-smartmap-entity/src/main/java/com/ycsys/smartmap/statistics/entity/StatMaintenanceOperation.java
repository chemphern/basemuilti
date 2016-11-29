package com.ycsys.smartmap.statistics.entity;

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

@Entity
@Table(name = "stat_maintenance_operation")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class StatMaintenanceOperation implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识
	
	@Column(name = "type",length = 1)
	private String type; //统计类型
	
	@Column(name = "normal_count")
	private Integer normalCount;	//正常次数
	
	@Column(name = "failure_count")
	private Integer failureCount;	//失败次数
	
	@ManyToOne
	@JoinColumn(name = "operate_user_id")
	private User operateUser;	//操作用户
	
	@Column(name = "operate_count")
	private Integer operateCount; // 操作次数
	
	@Column(name = "reg_user_modify_count")
	private Integer regUserModifyCount;//注册用户编辑次数
	
	@Column(name = "call_interface_count")
	private Integer callInterfaceCount;//调用系统接口次数
	
	@Column(name = "reg_user_per_count")
	private Integer regUserPerCount;//注册用户记授权次数
	
	@Column(name = "reg_user_group_per_count")
	private Integer regUserGroupPerCount;//注册用户记授权次数
	
	@Column(name = "statistics_date")
	private Date statisticsDate; // 统计时间

	@ManyToOne
	@JoinColumn(name = "statistics_user_id")
	private User statisticsUser; // 统计者

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getNormalCount() {
		return normalCount;
	}

	public void setNormalCount(Integer normalCount) {
		this.normalCount = normalCount;
	}

	public Integer getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}

	public User getOperateUser() {
		return operateUser;
	}

	public void setOperateUser(User operateUser) {
		this.operateUser = operateUser;
	}

	public Integer getOperateCount() {
		return operateCount;
	}

	public void setOperateCount(Integer operateCount) {
		this.operateCount = operateCount;
	}

	public Integer getRegUserModifyCount() {
		return regUserModifyCount;
	}

	public void setRegUserModifyCount(Integer regUserModifyCount) {
		this.regUserModifyCount = regUserModifyCount;
	}

	public Integer getCallInterfaceCount() {
		return callInterfaceCount;
	}

	public void setCallInterfaceCount(Integer callInterfaceCount) {
		this.callInterfaceCount = callInterfaceCount;
	}

	

	public Integer getRegUserPerCount() {
		return regUserPerCount;
	}

	public void setRegUserPerCount(Integer regUserPerCount) {
		this.regUserPerCount = regUserPerCount;
	}

	public Integer getRegUserGroupPerCount() {
		return regUserGroupPerCount;
	}

	public void setRegUserGroupPerCount(Integer regUserGroupPerCount) {
		this.regUserGroupPerCount = regUserGroupPerCount;
	}

	public Date getStatisticsDate() {
		return statisticsDate;
	}

	public void setStatisticsDate(Date statisticsDate) {
		this.statisticsDate = statisticsDate;
	}

	public User getStatisticsUser() {
		return statisticsUser;
	}

	public void setStatisticsUser(User statisticsUser) {
		this.statisticsUser = statisticsUser;
	}

}
