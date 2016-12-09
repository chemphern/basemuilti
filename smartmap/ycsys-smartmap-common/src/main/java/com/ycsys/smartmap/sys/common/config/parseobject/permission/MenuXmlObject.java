package com.ycsys.smartmap.sys.common.config.parseobject.permission;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * 菜单节点对应java对象
 * Created by lixiaoxin on 2016/11/2.
 */
public class MenuXmlObject implements Serializable{

    private static final long serialVersionUID = 1L;


    private String name;

    private String sort;

    public MenuXmlObject(){
    }
    public MenuXmlObject(String name,String sort){
        this.name = name;
        this.sort = sort;
    }

    private List<UrlXmlObject> urlXmlObjects;

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

    @XmlElement(name = "url")
    public List<UrlXmlObject> getUrlXmlObjects() {
        return urlXmlObjects;
    }

    public void setUrlXmlObjects(List<UrlXmlObject> urlXmlObjects) {
        this.urlXmlObjects = urlXmlObjects;
    }
}
