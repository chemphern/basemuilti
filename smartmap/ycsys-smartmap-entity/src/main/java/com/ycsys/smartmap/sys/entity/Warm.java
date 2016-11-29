package com.ycsys.smartmap.sys.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 异常报警实体
 * Created by lixiaoxin on 2016/11/1.
 */
@Entity
@Table(name = "sys_warm")
@DynamicUpdate
@DynamicInsert
public class Warm implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name",length=50)
    private String name;

    @Column(name = "content",length=3999)
    private String content;

    @Column(name = "levels",length=50)
    private String level;

    @Column(name = "status")
    private short status;

    @Column(name = "create_time")
    private Timestamp createTime;

    @Column(name = "remark")
    private String remark;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
