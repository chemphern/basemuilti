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

import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.sys.entity.User;

@Entity
@Table(name = "m_service_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class ServiceRequest implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@ManyToOne
	@JoinColumn(name = "request_user_id")
	private User requestUser; // 请求时间

	@Column(name = "request_date")
	private Date requestDate;

	@Column(name = "request_ip", length = 20)
	private String requestIp; // 请求ip

	@Column(name = "server_ip", length = 20)
	private String serverIp; // 服务器ip
	
	@Column(name = "server_port")
	private Integer serverPort;	//服务器端口
	
	@ManyToOne
	@JoinColumn(name = "service_id")
	private Service service; //服务
	
	@Column(name = "service_method",length = 30)
	private String serviceMethod;	//服务方法
	
	@Column(name = "request_url")
	private String requestUrl; //请求url
	
	@Column(name ="visit_date")
	private Date visitDate;	//访问时间
	
	@Column(name = "return_status",length = 1)
	private String returnStatus;	//返回状态
	
	@Column(name = "create_date")
	private Date createDate; // 创建时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getRequestUser() {
		return requestUser;
	}

	public void setRequestUser(User requestUser) {
		this.requestUser = requestUser;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
	}

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public Integer getServerPort() {
		return serverPort;
	}

	public void setServerPort(Integer serverPort) {
		this.serverPort = serverPort;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getServiceMethod() {
		return serviceMethod;
	}

	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public String getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
