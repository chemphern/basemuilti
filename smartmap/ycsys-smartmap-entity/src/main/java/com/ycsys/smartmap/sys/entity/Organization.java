package com.ycsys.smartmap.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 机构实体
 * Created by lixiaoxin on 2016/11/1.
 */
@Entity
@Table(name = "sys_organization")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class Organization implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    //父id
    @Column(name = "pid")
    private Integer pid;

    //机构名称
    @Column(name = "name")
    private String name;

    //层级
    @Column(name = "levels")
    private short level;

    //备注描述
    @Column(name = "remark",length = 3999)
    private String remark;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name="type")
    private short type;

    @Column(name="area_id")
    private Integer areaId;

    @Column(name="area_name")
    private String areaName;

    @Column(name="code")
    private String code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
