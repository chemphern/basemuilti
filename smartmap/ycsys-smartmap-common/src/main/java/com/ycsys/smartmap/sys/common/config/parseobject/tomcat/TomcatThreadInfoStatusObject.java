package com.ycsys.smartmap.sys.common.config.parseobject.tomcat;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Administrator on 2016/12/20.
 */
public class TomcatThreadInfoStatusObject {
    private String maxThreads;
    private String currentThreadCount;
    private String currentThreadsBusy;
    @XmlAttribute
    public String getMaxThreads() {
        return maxThreads;
    }

    public void setMaxThreads(String maxThreads) {
        this.maxThreads = maxThreads;
    }
    @XmlAttribute
    public String getCurrentThreadCount() {
        return currentThreadCount;
    }

    public void setCurrentThreadCount(String currentThreadCount) {
        this.currentThreadCount = currentThreadCount;
    }
    @XmlAttribute
    public String getCurrentThreadsBusy() {
        return currentThreadsBusy;
    }

    public void setCurrentThreadsBusy(String currentThreadsBusy) {
        this.currentThreadsBusy = currentThreadsBusy;
    }
}
