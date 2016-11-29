package com.ycsys.smartmap.monitor.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
/**
 * 通用服务器监控
 * @author liweixiong
 * @date   2016年11月3日
 */
@Entity
@Table(name = "m_common_server")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class CommonServerMonitor implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识
	
	@Column(name = "server_name",length = 30)
	private String serverName; //服务器名
	
	@Column(name = "ip_address",length = 20)
	private String ipAddress; //ip地址
	
	@Column(name = "engine_name",length = 30)
	private String engineName; //机器名
	
	@Column(name = "system_version",length = 10)
	private String systemVersion;// 操作系统版本
	
	@Column(name = "system_type",length = 1)
	private String systemType;	//系统类型
	
	@Column(name = "system_memory")
	private Integer systemMemory;	//系统内存
	
	@Column(name = "create_date")
	private Date createDate; // 创建时间
}	
