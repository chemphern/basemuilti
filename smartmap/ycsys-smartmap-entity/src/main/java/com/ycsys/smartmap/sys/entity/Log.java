package com.ycsys.smartmap.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;


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

	//操作名称
	@Column(name = "operation_name",length = 50)
	private String operationName;

	//操作类型
	@Column(name="operation_type")
	private Integer operationType;

	//状态
	@Column
	private Integer status;

	//请求ip
	@Column(name="request_ip")
	private String requestIp;

	//请求参数
	@Lob
	@Column(name = "request_params")
	private String request_params;

	//响应参数
	@Lob
	@Column(name = "response_params")
	private String response_params;

	//创建时间
	@Column(name = "create_time")
	private Date createTime;

	//耗时
	@Column(name="used_time")
	private long usedTime;

	//备注
	@Column(name="remark",length=3999)
	private String remark;

	//用户名
	@Column
	private String username;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	public Log() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public Integer getOperationType() {
		return operationType;
	}

	public void setOperationType(Integer operationType) {
		this.operationType = operationType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRequestIp() {
		return requestIp;
	}

	public void setRequestIp(String requestIp) {
		this.requestIp = requestIp;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public long getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(long usedTime) {
		this.usedTime = usedTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}