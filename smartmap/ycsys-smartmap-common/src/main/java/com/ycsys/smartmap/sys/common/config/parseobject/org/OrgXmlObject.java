package com.ycsys.smartmap.sys.common.config.parseobject.org;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lixiaoxin on 2016/12/5.
 */
public class OrgXmlObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String areaName;
    private String code;
    private String type;
    private String remark;
    private String areaId;
    private List<OrgXmlObject> orgXmlObjectList;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @XmlAttribute
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlAttribute
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlAttribute
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlAttribute
    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    @XmlElement(name = "org")
    public List<OrgXmlObject> getOrgXmlObjectList() {
        return orgXmlObjectList;
    }

    public void setOrgXmlObjectList(List<OrgXmlObject> orgXmlObjectList) {
        this.orgXmlObjectList = orgXmlObjectList;
    }
}
