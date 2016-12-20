package com.ycsys.smartmap.sys.common.config.parseobject.tomcat;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.List;

/**
 * Created by Administrator on 2016/12/20.
 */
public class TomcatConnectorStatusObject {
    private String name;
    private TomcatThreadInfoStatusObject threadInfo;
    private TomcatRequestInfoObject requestInfo;
    private List<TomcatWorkerStatusObject> workers;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name = "threadInfo")
    public TomcatThreadInfoStatusObject getThreadInfo() {
        return threadInfo;
    }

    public void setThreadInfo(TomcatThreadInfoStatusObject threadInfo) {
        this.threadInfo = threadInfo;
    }

    @XmlElement(name = "requestInfo")
    public TomcatRequestInfoObject getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(TomcatRequestInfoObject requestInfo) {
        this.requestInfo = requestInfo;
    }

    @XmlElementWrapper(name="workers")
    @XmlElement(name = "worker")
    public List<TomcatWorkerStatusObject> getWorkers() {
        return workers;
    }

    public void setWorkers(List<TomcatWorkerStatusObject> workers) {
        this.workers = workers;
    }
}
