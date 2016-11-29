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

/**
 * 平台操作系统统计
 * @author liweixiong
 * @date   2016年11月3日
 */
@Entity
@Table(name = "stat_operating_system")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class StatOperatingSystem implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识
	
	@Column(name = "server_name")
	private String serverName; //服务名称
	
	@Column(name = "use_max")
	private Float useMax; //cpu总使用率最大值
	
	@Column(name = "use_min")
	private Float useMin; //cpu总使用率最小值
	
	@Column(name = "use_average")
	private Float useAverage; //cpu总使用率平均值
	
	@Column(name = "user_use_max")
	private Float userUseMax; //cpu用户使用率最大值
	
	@Column(name = "user_use_min")
	private Float userUseMin; //cpu用户使用率最小值
	
	@Column(name = "user_use_average")
	private Float userUseAverage; //cpu用户使用率平均值
	
	@Column(name = "sys_use_max")
	private Float sysUseMax; //cpu系统使用率最大值
	
	@Column(name = "sys_min")
	private Float sysUseMin; //cpu系统使用率最小值
	
	@Column(name = "sys_use_average")
	private Float sysUseAverage; //cpu系统使用率平均值
	
	@Column(name = "free_max")
	private Float freeMax;	//cpu当前空闲率最大值
	
	@Column(name = "free_min")
	private Float freeMin;	//cpu当前空闲率最小值
	
	@Column(name = "free_average")
	private Float freeAverage;	//cpu当前空闲率平均值
	
	@Column(name = "statistics_date")
	private Date statisticsDate; // 统计时间

	@ManyToOne
	@JoinColumn(name = "statistics_user_id")
	private User statisticsUser; // 统计者
	
}
