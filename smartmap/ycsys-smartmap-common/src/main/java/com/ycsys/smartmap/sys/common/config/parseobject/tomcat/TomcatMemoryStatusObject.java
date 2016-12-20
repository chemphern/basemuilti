package com.ycsys.smartmap.sys.common.config.parseobject.tomcat;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Created by Administrator on 2016/12/20.
 */
public class TomcatMemoryStatusObject {
    private String free;
    private String total;
    private String max;

    @XmlAttribute
    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    @XmlAttribute
    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @XmlAttribute
    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }
}
