package com.ycsys.smartmap.sys.entity;

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
 * 邮件实体类
 * @author Administrator
 * @date 2016年11月28日
 */
@Entity
@Table(name = "sys_email_property")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class Email implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;//唯一标示
	
	@Column(name = "email_hostAddress",nullable = false, length = 50)
	private String emailHostAddress;//邮件服务器地址
	
	@Column(name = "email_name",nullable = false, length = 25)
	private String emailName;//邮箱登陆用户名
	
	@Column(name = "email_password",nullable = false, length = 25)
	private String emailPassword;//邮箱登陆密码
	
	@Column(name = "email_senderAddress",nullable = false, length = 25)
	private String senderAddress;//发送人邮件地址

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmailHostAddress() {
		return emailHostAddress;
	}

	public void setEmailHostAddress(String emailHostAddress) {
		this.emailHostAddress = emailHostAddress;
	}

	public String getEmailName() {
		return emailName;
	}

	public void setEmailName(String emailName) {
		this.emailName = emailName;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}
	
}
