package com.ycsys.smartmap.sys.entity;

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

/**
 * 服务扩展属性配置 实体
 * 
 * @author liweixiong
 * @date 2016年11月2日
 */
@Entity
@Table(name = "sys_config_service_property")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class ConfigServiceExtendProperty implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id; // 唯一标识

	@Column(name = "name", nullable = false, length = 30)
	private String name; // 属性字段名

	@Column(name = "show_name", nullable = false, length = 30)
	private String showName; // 扩展属性显示名

	@Column(name = "type", nullable = false, length = 1)
	private String type;// 类型（0：字符；1：日期；2：数字）

	@Column(name = "need",nullable = false,length = 1)
	private String need; // 是否必填(0:必填；1：非必填)

	@Column(name = "default_value",length = 30)
	private String defaultValue; // 默认值

	@Column(name = "reference_value",length = 30)
	private String referenceValue; // 参考值

	@Column(name = "remarks", length = 200)
	private String remarks; // 备注

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNeed() {
		return need;
	}

	public void setNeed(String need) {
		this.need = need;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getReferenceValue() {
		return referenceValue;
	}

	public void setReferenceValue(String referenceValue) {
		this.referenceValue = referenceValue;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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
