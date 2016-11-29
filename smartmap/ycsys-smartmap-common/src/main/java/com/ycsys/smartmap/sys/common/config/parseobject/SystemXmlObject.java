package com.ycsys.smartmap.sys.common.config.parseobject;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * 系统xml对应实体
 * Created by lixiaoxin on 2016/11/23.
 */
public class SystemXmlObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String code;
    private String url;
    private List<ModuleXmlObject> moduleXmlObjectList;

    @XmlElement(name = "module")
    public List<ModuleXmlObject> getModuleXmlObjectList() {
        return moduleXmlObjectList;
    }

    public SystemXmlObject(){

    }
    public SystemXmlObject(String name,String code,String url){
        this.name = name;
        this.code = code;
        this.url = url;
    }
    public void setModuleXmlObjectList(List<ModuleXmlObject> moduleXmlObjectList) {
        this.moduleXmlObjectList = moduleXmlObjectList;
    }

    @XmlAttribute(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute(name="code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlAttribute(name="url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
