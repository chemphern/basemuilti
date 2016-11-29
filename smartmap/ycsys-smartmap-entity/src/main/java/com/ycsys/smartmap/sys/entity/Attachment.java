package com.ycsys.smartmap.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 附件实体
 * Created by lixiaoxin on 2016/11/1.
 */
@Entity
@Table(name = "sys_attachment")
@DynamicUpdate
@DynamicInsert
public class Attachment implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name",length = 50)
    private String name;

    @Column(name = "file_size")
    private Long size;

    @Column(name = "type",length = 10)
    private String type;

    @Column(name = "fix",length = 10)
    private String fix;

    @Column(name = "status")
    private short status;

    @Column(name = "pid")
    private Integer pid;

    @Column(name = "content_type",length = 20)
    private String contentType;

    @Column(name = "remark",length = 3999)
    private String remark;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "create_time")
    private Timestamp createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "update_time")
    private Timestamp updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "download_time")
    private Timestamp downloadTime;

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

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFix() {
        return fix;
    }

    public void setFix(String fix) {
        this.fix = fix;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Timestamp getDownloadTime() {
        return downloadTime;
    }

    public void setDownloadTime(Timestamp downloadTime) {
        this.downloadTime = downloadTime;
    }
}
