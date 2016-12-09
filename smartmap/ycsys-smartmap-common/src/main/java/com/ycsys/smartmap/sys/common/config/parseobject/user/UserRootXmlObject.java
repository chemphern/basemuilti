package com.ycsys.smartmap.sys.common.config.parseobject.user;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lixiaoxin on 2016/12/5.
 */
@XmlRootElement(name="users")
public class UserRootXmlObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<UserXmlObject> userXmlObjectList;

    @XmlElement(name = "user")
    public List<UserXmlObject> getUserXmlObjectList() {
        return userXmlObjectList;
    }

    public void setUserXmlObjectList(List<UserXmlObject> userXmlObjectList) {
        this.userXmlObjectList = userXmlObjectList;
    }
}
