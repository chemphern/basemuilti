package com.ycsys.smartmap.sys.common.config.parseobject.role;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lixiaoxin on 2016/12/5.
 */
@XmlRootElement(name="roles")
public class RoleRootXmlObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<RoleXmlObject> roleXmlObjectList;

    @XmlElement(name = "role")
    public List<RoleXmlObject> getRoleXmlObjectList() {
        return roleXmlObjectList;
    }

    public void setRoleXmlObjectList(List<RoleXmlObject> roleXmlObjectList) {
        this.roleXmlObjectList = roleXmlObjectList;
    }
}
