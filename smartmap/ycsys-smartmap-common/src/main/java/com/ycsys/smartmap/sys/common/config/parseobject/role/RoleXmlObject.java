package com.ycsys.smartmap.sys.common.config.parseobject.role;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * Created by lixiaoxin on 2016/12/5.
 */
public class RoleXmlObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private String isSuper;
    private String remark;
    private String sort;

    @XmlAttribute
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getIsSuper() {
        return isSuper;
    }

    public void setIsSuper(String isSuper) {
        this.isSuper = isSuper;
    }

    @XmlAttribute
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlAttribute
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
