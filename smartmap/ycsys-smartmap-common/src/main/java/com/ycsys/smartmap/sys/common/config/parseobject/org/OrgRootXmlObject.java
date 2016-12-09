package com.ycsys.smartmap.sys.common.config.parseobject.org;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lixiaoxin on 2016/12/5.
 */
@XmlRootElement(name="orgs")
public class OrgRootXmlObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<OrgXmlObject> orgXmlObjectList;

    @XmlElement(name = "org")
    public List<OrgXmlObject> getOrgXmlObjectList() {
        return orgXmlObjectList;
    }

    public void setOrgXmlObjectList(List<OrgXmlObject> orgXmlObjectList) {
        this.orgXmlObjectList = orgXmlObjectList;
    }
}
