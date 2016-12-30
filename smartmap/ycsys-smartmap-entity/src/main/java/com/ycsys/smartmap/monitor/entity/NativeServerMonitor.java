package com.ycsys.smartmap.monitor.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 本地服务器监控实体
 * Created by lixiaoxin on 2016/12/28.
 */
@Entity
@Table(name = "native_server_monitor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class NativeServerMonitor implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id; // 唯一标识

    @Column(name="ip")
    private String ip;//服务器ip

    @Column(name="used_rate")
    private double usedRate;//cpu使用率

    @Column(name="send_package")
    private long sendPackage;//发送包裹

    @Column(name="rec_package")
    private long recPackage;//接收包裹

    @Column(name="send_byte")
    private long sendByte;//发送流量

    @Column(name="rec_byte")
    private long recByte;//接收流量

    @Column(name="rate")
    private int rate;//间隔

    @Column(name="memory")
    private double memory;//总内存

    @Column(name="used_memory")
    private double usedMemory;//使用内存

    @Column
    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getUsedRate() {
        return usedRate;
    }

    public void setUsedRate(double usedRate) {
        this.usedRate = usedRate;
    }

    public long getSendPackage() {
        return sendPackage;
    }

    public void setSendPackage(long sendPackage) {
        this.sendPackage = sendPackage;
    }

    public long getRecPackage() {
        return recPackage;
    }

    public void setRecPackage(long recPackage) {
        this.recPackage = recPackage;
    }

    public long getSendByte() {
        return sendByte;
    }

    public void setSendByte(long sendByte) {
        this.sendByte = sendByte;
    }

    public long getRecByte() {
        return recByte;
    }

    public void setRecByte(long recByte) {
        this.recByte = recByte;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public double getMemory() {
        return memory;
    }

    public void setMemory(double memory) {
        this.memory = memory;
    }

    public double getUsedMemory() {
        return usedMemory;
    }

    public void setUsedMemory(double usedMemory) {
        this.usedMemory = usedMemory;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
