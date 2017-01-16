package com.ycsys.smartmap.sys.vo;

/**
 * 服务监控前台实体
 * Created by lixiaoxin on 2017/1/5.
 */
public class ServiceMonitorVo {

    private Integer id;
    private String serviceName;
    private String serviceShowName;
    private String status;
    private String monitorStatus;
    private String monitorUrl;
    private String monitorType;
    private String monitorRate;
    private String availableRate;
    private String averageRespTime;

    public ServiceMonitorVo(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceShowName() {
        return serviceShowName;
    }

    public void setServiceShowName(String serviceShowName) {
        this.serviceShowName = serviceShowName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMonitorStatus() {
        return monitorStatus;
    }

    public void setMonitorStatus(String monitorStatus) {
        this.monitorStatus = monitorStatus;
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }

    public String getMonitorType() {
        return monitorType;
    }

    public void setMonitorType(String monitorType) {
        this.monitorType = monitorType;
    }

    public String getMonitorRate() {
        return monitorRate;
    }

    public void setMonitorRate(String monitorRate) {
        this.monitorRate = monitorRate;
    }

    public String getAvailableRate() {
        return availableRate;
    }

    public void setAvailableRate(String availableRate) {
        this.availableRate = availableRate;
    }

    public String getAverageRespTime() {
        return averageRespTime;
    }

    public void setAverageRespTime(String averageRespTime) {
        this.averageRespTime = averageRespTime;
    }
}
