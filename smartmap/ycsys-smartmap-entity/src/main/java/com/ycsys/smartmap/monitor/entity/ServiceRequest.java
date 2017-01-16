package com.ycsys.smartmap.monitor.entity;

import com.ycsys.smartmap.sys.entity.User;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

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

	@Column(name = "request_ip",length = 255)
	private String requestIp; // 请求ip

	@Column(name = "server_ip",length = 255)
	private String serverIp; // 服务器ip
	
	@Column(name = "server_port",length = 10)
	private Integer serverPort;	//服务器端口

	@Column(name="service_name",length = 255)
	private String serviceName;//服务名称

	@Column(name="service_Type",length = 255)
	private String serviceType;//服务类型
	
	@Column(name = "service_method",length = 255)
	private String serviceMethod;	//服务方法
	
	@Column(name = "request_url",length = 3999)
	private String requestUrl; //请求url
	
	@Column(name ="visit_time")
	private long visitTime;	//访问时间
	
	@Column(name = "return_status",length = 10)
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

	public long getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(long visitTime) {
		this.visitTime = visitTime;
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

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
}
