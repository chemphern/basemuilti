package com.ycsys.smartmap.sys.common.config.parseobject.tomcat;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by lixiaoxin on 2016/12/20.
 */
@XmlRootElement(name="status")
public class TomcatStatusObject {

    private TomcatJVMStatusObject jvm;

    private List<TomcatConnectorStatusObject> connector;

    @XmlElement(name = "jvm")
    public TomcatJVMStatusObject getJvm() {
        return jvm;
    }

    public void setJvm(TomcatJVMStatusObject jvm) {
        this.jvm = jvm;
    }

    @XmlElement(name = "connector")
    public List<TomcatConnectorStatusObject> getConnector() {
        return connector;
    }

    public void setConnector(List<TomcatConnectorStatusObject> connector) {
        this.connector = connector;
    }
}
