package com.ycsys.smartmap.monitor.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 本地数据库监控实体
 * Created by lixiaoxin on 2016/12/28.
 */
@Entity
@Table(name = "native_db_monitor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class NativeDbMonitor implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id; // 唯一标识

    @Column
    private String type;//数据库类型

    @Column(name="connect_num")
    private int connect;//连接数

    @Column(name="mac_addr")
    private String macAddr;//本机max地址

    @Column(name="native_ip")
    private String nativeIp;//本机ip

    @Column
    private Date time;//时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getConnect() {
        return connect;
    }

    public void setConnect(int connect) {
        this.connect = connect;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public String getNativeIp() {
        return nativeIp;
    }

    public void setNativeIp(String nativeIp) {
        this.nativeIp = nativeIp;
    }
}
