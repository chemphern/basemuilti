package com.ycsys.smartmap.monitor.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 本地tomcat监控实体
 * Created by lixiaoxin on 2016/12/28.
 */
@Entity
@Table(name = "native_tomcat_monitor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class NativeTomcatMonitor implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id; // 唯一标识

    @Column(name="thread")
    private int thread;//线程数

    @Column(name="thread_busy")
    private int threadBusy;//繁忙线程数

    @Column(name="used_memory")
    private long usedMemory;//jvm 使用内存

    @Column(name="free_memory")
    private long freeMemory;//jvm 空闲内存

    @Column(name="max_addr")
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

    public int getThread() {
        return thread;
    }

    public void setThread(int thread) {
        this.thread = thread;
    }

    public int getThreadBusy() {
        return threadBusy;
    }

    public void setThreadBusy(int threadBusy) {
        this.threadBusy = threadBusy;
    }

    public double getUsedMemory() {
        return usedMemory;
    }

    public double getFreeMemory() {
        return freeMemory;
    }

    public void setUsedMemory(long usedMemory) {
        this.usedMemory = usedMemory;
    }

    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
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
