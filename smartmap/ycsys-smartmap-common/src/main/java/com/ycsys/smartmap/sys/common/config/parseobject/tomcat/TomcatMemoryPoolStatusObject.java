package com.ycsys.smartmap.sys.common.config.parseobject.tomcat;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Administrator on 2016/12/20.
 */
public class TomcatMemoryPoolStatusObject {
    private String name;
    private String type;
    private String usageInit;
    private String usageCommitted;
    private String usageMax;
    private String usageUsed;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @XmlAttribute
    public String getUsageInit() {
        return usageInit;
    }

    public void setUsageInit(String usageInit) {
        this.usageInit = usageInit;
    }
    @XmlAttribute
    public String getUsageCommitted() {
        return usageCommitted;
    }

    public void setUsageCommitted(String usageCommitted) {
        this.usageCommitted = usageCommitted;
    }
    @XmlAttribute
    public String getUsageMax() {
        return usageMax;
    }

    public void setUsageMax(String usageMax) {
        this.usageMax = usageMax;
    }
    @XmlAttribute
    public String getUsageUsed() {
        return usageUsed;
    }

    public void setUsageUsed(String usageUsed) {
        this.usageUsed = usageUsed;
    }
}
