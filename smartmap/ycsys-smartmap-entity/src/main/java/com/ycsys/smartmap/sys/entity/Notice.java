package com.ycsys.smartmap.sys.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * 消息通知类
 * Created by lixiaoxin on 2016/11/1.
 */
@Entity
@Table(name = "sys_notice")
@DynamicUpdate
@DynamicInsert
public class Notice implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    //标题
    @Column(name = "title",length=100)
    private String title;

    //内容
    @Column(name = "content",length=3999)
    private String content;
    //类型
    @Column(name = "type")
    private short type;
    //状态
    @Column(name = "status")
    private short status;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "create_time")
    private Timestamp createTime;

    //更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "update_time")
    private Timestamp updateTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "notice")
    private Set<NoticeReceiver> noticeReceivers = new HashSet<NoticeReceiver>(0);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
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

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Set<NoticeReceiver> getNoticeReceivers() {
        return noticeReceivers;
    }

    public void setNoticeReceivers(Set<NoticeReceiver> noticeReceivers) {
        this.noticeReceivers = noticeReceivers;
    }
}
