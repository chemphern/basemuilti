package com.ycsys.smartmap.monitor.entity;

import com.ycsys.smartmap.sys.entity.ConfigServerMonitor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * tomcat监控数据
 * Created by lixiaoxin on 2017/1/10.
 */
@Entity
@Table(name = "m_tomcat_monitor_data")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@DynamicInsert
@DynamicUpdate
public class TomcatMonitorData implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id; // 唯一标识

    @Column
    private String thread;//线程数

    @Column(name = "thread_busy")
    private String threadBusy;//线程繁忙数

    @Column(name = "max_memory")
    private String maxMemory;//最大使用内存

    @Column(name = "free_memory")
    private String freeMemory;//空闲内存

    @Column
    private String memory;//总内存

    @ManyToOne
    @JoinColumn(name = "server_id")
    private ConfigServerMonitor configServerMonitor; //服务器

    @Column(name = "monitor_time")
    private Date monitorTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getThread() {
        return thread;
    }

    public void setThread(String thread) {
        this.thread = thread;
    }

    public String getThreadBusy() {
        return threadBusy;
    }

    public void setThreadBusy(String threadBusy) {
        this.threadBusy = threadBusy;
    }

    public String getMaxMemory() {
        return maxMemory;
    }

    public void setMaxMemory(String maxMemory) {
        this.maxMemory = maxMemory;
    }

    public String getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(String freeMemory) {
        this.freeMemory = freeMemory;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public ConfigServerMonitor getConfigServerMonitor() {
        return configServerMonitor;
    }

    public void setConfigServerMonitor(ConfigServerMonitor configServerMonitor) {
        this.configServerMonitor = configServerMonitor;
    }

    public Date getMonitorTime() {
        return monitorTime;
    }

    public void setMonitorTime(Date monitorTime) {
        this.monitorTime = monitorTime;
    }
}
