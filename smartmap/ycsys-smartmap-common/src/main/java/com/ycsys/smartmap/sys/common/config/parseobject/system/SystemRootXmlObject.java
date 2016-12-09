package com.ycsys.smartmap.sys.common.config.parseobject.system;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lixiaoxin on 2016/12/6.
 */
@XmlRootElement(name="systems")
public class SystemRootXmlObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<SystemXmlObject> systemXmlObjectList;

    @XmlElement(name = "system")
    public List<SystemXmlObject> getSystemXmlObjectList() {
        return systemXmlObjectList;
    }

    public void setSystemXmlObjectList(List<SystemXmlObject> systemXmlObjectList) {
        this.systemXmlObjectList = systemXmlObjectList;
    }
}
