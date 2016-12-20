package com.ycsys.smartmap.sys.common.config.parseobject.tomcat;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Administrator on 2016/12/20.
 */
public class TomcatRequestInfoObject {
    private String maxTime;
    private String processingTime;
    private String requestCount;
    private String errorCount;
    private String bytesReceived;
    private String bytesSent;
    @XmlAttribute
    public String getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(String maxTime) {
        this.maxTime = maxTime;
    }
    @XmlAttribute
    public String getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(String processingTime) {
        this.processingTime = processingTime;
    }
    @XmlAttribute
    public String getRequestCount() {
        return requestCount;
    }

    public void setRequestCount(String requestCount) {
        this.requestCount = requestCount;
    }
    @XmlAttribute
    public String getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(String errorCount) {
        this.errorCount = errorCount;
    }
    @XmlAttribute
    public String getBytesReceived() {
        return bytesReceived;
    }

    public void setBytesReceived(String bytesReceived) {
        this.bytesReceived = bytesReceived;
    }
    @XmlAttribute
    public String getBytesSent() {
        return bytesSent;
    }

    public void setBytesSent(String bytesSent) {
        this.bytesSent = bytesSent;
    }
}
