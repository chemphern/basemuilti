package com.ycsys.smartmap.sys.common.config.parseobject.permission;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * 权限xml对应的java对象
 * Created by lixiaoxin on 2016/11/2.
 */
@XmlRootElement(name="permission")
public class PermissionXmlObject implements Serializable{

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
