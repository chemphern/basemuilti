package com.ycsys.smartmap.sys.common.config.parseobject.permission;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * 模块节点对应java对象
 * Created by lixiaoxin on 2016/11/2.
 */
public class ModuleXmlObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private String sort;

    private List<MenuXmlObject> menuXmlObject;

    private List<UrlXmlObject> urlXmlObject;

    public ModuleXmlObject(){

    }
    public ModuleXmlObject(String name,String sort){
        this.name = name;
        this.sort = sort;
    }
    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    @XmlAttribute(name="sort")
    public void setSort(String sort) {
        this.sort = sort;
    }


    @XmlElement(name = "menu")
    public List<MenuXmlObject> getMenuXmlObject() {
        return menuXmlObject;
    }

    public void setMenuXmlObject(List<MenuXmlObject> menuXmlObject) {
        this.menuXmlObject = menuXmlObject;
    }

    @XmlElement(name = "url")
    public List<UrlXmlObject> getUrlXmlObject() {
        return urlXmlObject;
    }

    public void setUrlXmlObject(List<UrlXmlObject> urlXmlObject) {
        this.urlXmlObject = urlXmlObject;
    }
}
