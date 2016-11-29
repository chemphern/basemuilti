package com.ycsys.smartmap.sys.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 消息接收实体
 * Created by lixiaoxin on 2016/11/1.
 */
@Entity
@Table(name = "sys_notice_receiver")
@DynamicUpdate
@DynamicInsert
public class NoticeReceiver implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id", nullable = false)
    private Notice notice;

    @Column(name = "send_time")
    private Timestamp sendTime;

    @Column(name = "receive_time")
    private Timestamp receiveTime;

    @Column(name = "status")
    private short status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public Timestamp getSendTime() {
        return sendTime;
    }

    public void setSendTime(Timestamp sendTime) {
        this.sendTime = sendTime;
    }

    public Timestamp getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Timestamp receiveTime) {
        this.receiveTime = receiveTime;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }
}
