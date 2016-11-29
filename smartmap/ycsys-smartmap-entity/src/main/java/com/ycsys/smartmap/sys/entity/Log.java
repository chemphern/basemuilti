package com.ycsys.smartmap.sys.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * 日志entity
 * @author lixiaoxin
 * @date 2016年11月1日
 */
@Entity
@Table(name = "sys_log")
@DynamicUpdate @DynamicInsert
public class Log implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	//请求链接
	@Column(name = "request_url",length=3999)
	private String requestUrl;

	//请求地址
	@Column(name = "request_ip",length=50)
	private String requestIp;

	//服务器地址
	@Column(name = "server_ip",length=50)
	private String serverIp;

	//服务器端口
	private Integer serverPort;

	//服务名称
	@Column(name = "server_name",length=50)
	private String serverName;

	//服务类型
	@Column(name = "server_type",length = 50)
	private String serverType;

	//操作名称
	@Column(name = "operation_name",length = 50)
	private String operationName;

	//操作类型(操作编码)
	@Column(name = "operation_type",length = 50)
	private String operationType;

	//响应时间
	@Column(name = "response_time")
	private Long response_time;

	//响应状态
	@Column(name = "response_status",length=10)
	private String response_status;

	//请求参数
	@Column(name = "request_params",length = 3999)
	private String request_params;

	//响应参数
	@Column(name = "response_params",length=3999)
	private String response_params;

	//执行sql
	@Column(name = "exec_sql",length=3999)
	private String exec_sql;

	//开始时间
	@Column(name = "start_time")
	private Timestamp start_time;

	//结束时间
	@Column(name = "end_time")
	private Timestamp end_time;

	//token
	@Column(name = "token",length=64)
	private String token;

	//备注
	@Column(name="remark",length=3999)
	private String remark;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Log() {
	}

	public Log(String operationType, Timestamp end_time) {
		this.operationType = operationType;
		this.end_time = end_time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
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

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerType() {
		return serverType;
	}

	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public Long getResponse_time() {
		return response_time;
	}

	public void setResponse_time(Long response_time) {
		this.response_time = response_time;
	}

	public String getResponse_status() {
		return response_status;
	}

	public void setResponse_status(String response_status) {
		this.response_status = response_status;
	}

	public String getRequest_params() {
		return request_params;
	}

	public void setRequest_params(String request_params) {
		this.request_params = request_params;
	}

	public String getResponse_params() {
		return response_params;
	}

	public void setResponse_params(String response_params) {
		this.response_params = response_params;
	}

	public String getExec_sql() {
		return exec_sql;
	}

	public void setExec_sql(String exec_sql) {
		this.exec_sql = exec_sql;
	}

	public Timestamp getStart_time() {
		return start_time;
	}

	public void setStart_time(Timestamp start_time) {
		this.start_time = start_time;
	}

	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}