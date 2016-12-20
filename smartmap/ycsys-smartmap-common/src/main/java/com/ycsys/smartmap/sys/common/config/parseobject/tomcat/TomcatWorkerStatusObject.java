package com.ycsys.smartmap.sys.common.config.parseobject.tomcat;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Administrator on 2016/12/20.
 */
public class TomcatWorkerStatusObject {
    private String stage;
    private String requestProcessingTime;
    private String requestBytesSent;
    private String requestBytesReceived;
    private String remoteAddr;
    private String virtualHost;
    private String method;
    private String currentUri;
    private String currentQueryString;
    private String protocol;

    @XmlAttribute
    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    @XmlAttribute
    public String getRequestProcessingTime() {
        return requestProcessingTime;
    }

    public void setRequestProcessingTime(String requestProcessingTime) {
        this.requestProcessingTime = requestProcessingTime;
    }

    @XmlAttribute
    public String getRequestBytesSent() {
        return requestBytesSent;
    }

    public void setRequestBytesSent(String requestBytesSent) {
        this.requestBytesSent = requestBytesSent;
    }
    @XmlAttribute
    public String getRequestBytesReceived() {
        return requestBytesReceived;
    }

    public void setRequestBytesReceived(String requestBytesReceived) {
        this.requestBytesReceived = requestBytesReceived;
    }
    @XmlAttribute
    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }
    @XmlAttribute
    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }
    @XmlAttribute
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
    @XmlAttribute
    public String getCurrentUri() {
        return currentUri;
    }

    public void setCurrentUri(String currentUri) {
        this.currentUri = currentUri;
    }
    @XmlAttribute
    public String getCurrentQueryString() {
        return currentQueryString;
    }

    public void setCurrentQueryString(String currentQueryString) {
        this.currentQueryString = currentQueryString;
    }
    @XmlAttribute
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
}
