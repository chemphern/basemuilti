package com.ycsys.smartmap.sys.common.config.parseobject.permission;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * 功能点节点对应java对象
 * Created by lixiaoxin on 2016/11/2.
 */
public class FuncXmlObject implements Serializable{
    private static final long serialVersionUID = 1L;
    private String name;
    private String sort;
    private String code;

    public FuncXmlObject(){}

    public FuncXmlObject(String name,String sort,String code){
        this.name = name;
        this.sort = sort;
        this.code = code;
    }

    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name="sort")
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @XmlElement(name="code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
