package com.ycsys.smartmap.sys.common.config.parseobject.permission;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * 链接节点对应java对象
 * Created by lixiaoxin on 2016/11/2.
 */
public class UrlXmlObject implements Serializable{
    private static final long serialVersionUID = 1L;

    private String name;
    private String sort;
    private String href;
    private String funcs;

    private List<FuncXmlObject> funcXmlObjects;

    public UrlXmlObject(){}
    public UrlXmlObject(String name,String sort,String href,String funcs){
        this.name = name;
        this.sort = sort;
        this.href = href;
        this.funcs = funcs;
    }
    @XmlAttribute(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name = "sort")
    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @XmlAttribute(name = "href")
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @XmlAttribute(name = "funcs")
    public String getFuncs() {
        return funcs;
    }

    public void setFuncs(String funcs) {
        this.funcs = funcs;
    }

    @XmlElement(name = "func")
    public List<FuncXmlObject> getFuncXmlObjects() {
        return funcXmlObjects;
    }

    public void setFuncXmlObjects(List<FuncXmlObject> funcXmlObjects) {
        this.funcXmlObjects = funcXmlObjects;
    }
}
