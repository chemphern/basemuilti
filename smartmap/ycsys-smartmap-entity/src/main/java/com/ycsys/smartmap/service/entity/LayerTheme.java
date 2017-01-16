package com.ycsys.smartmap.service.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.ycsys.smartmap.sys.entity.User;

@Entity
@Table(name = "map_layer_theme")
@DynamicInsert
@DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class LayerTheme implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "pId")
	private Integer pId;//父节点
	
	@Column(name = "name")
	private String name;
	
	@Transient
	private String serviceIds;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "show_address", length = 500)
	private String showAddress; //展示服务地址
	
	@Column(name = "query_address", length = 500)
	private String queryAddress; //查询服务地址
	
	@Column(name = "real_address", length = 500)
	private String realAddress; //展示服务的真实地址
	
	@Column(name = "real_address2", length = 500)
	private String realAddress2; //查询服务的真实地址
	
	@ManyToOne
	@JoinColumn(name = "parent_id")
	private LayerTheme parent; // 父亲结点
	
	@ManyToOne
	@JoinColumn(name = "show_service_id")
	private Service showService; //展示服务
	
	@ManyToOne
	@JoinColumn(name = "query_service_id")
	private Service queryService; //查询服务
	
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
	
	public LayerTheme getParent() {
		return parent;
	}

	public void setParent(LayerTheme parent) {
		this.parent = parent;
	}

	public Integer getPId() {
		return pId;
	}

	public void setPId(Integer pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getServiceIds() {
		return serviceIds;
	}

	public void setServiceIds(String serviceIds) {
		this.serviceIds = serviceIds;
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
	
	public String getRealAddress() {
		return realAddress;
	}

	public void setRealAddress(String realAddress) {
		this.realAddress = realAddress;
	}

	public Service getQueryService() {
		return queryService;
	}

	public void setQueryService(Service queryService) {
		this.queryService = queryService;
	}

	public String getShowAddress() {
		return showAddress;
	}

	public void setShowAddress(String showAddress) {
		this.showAddress = showAddress;
	}

	public String getQueryAddress() {
		return queryAddress;
	}

	public void setQueryAddress(String queryAddress) {
		this.queryAddress = queryAddress;
	}

	public String getRealAddress2() {
		return realAddress2;
	}

	public void setRealAddress2(String realAddress2) {
		this.realAddress2 = realAddress2;
	}

	public Service getShowService() {
		return showService;
	}

	public void setShowService(Service showService) {
		this.showService = showService;
	}
	
}
