package com.ycsys.smartmap.webgis.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 飞行路径实体
 * Created by chenlong on 2016/12/1.
 */
@Entity
@Table(name = "map_roam_flightpath")
@DynamicUpdate
@DynamicInsert
public class FlightPath {
	
	@Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;
	
	//路径名称
	@Column(name = "path_name", unique = true, nullable = false, length=100)
	private String pathName;
	
	//飞行路径创建者
	@Column(name = "creator",length=100, nullable = false)
	private String creator;
	
	//飞行路径创建时间
	@Column(name = "create_time")
	private Date createTime;
	
	
	//id
	public Integer getId() {
        return this.id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    
    //路径名称
    public String getPathName(){
    	return pathName;
    }
    public void setPathName(String pathName){
    	this.pathName = pathName;
    }
    
    //飞行路径创建者
    public String getCreator(){
    	return this.creator;
    }
    public void setCreator(String creator){
    	this.creator = creator;
    }
    
    //飞行路径创建时间
    public Date getCreateTime(){
    	return this.createTime;
    }
    public void setCreateTime(Date createTime){
    	this.createTime = createTime;
    }
}
