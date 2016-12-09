package com.ycsys.smartmap.sys.common.config.parseobject.system;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * Created by lixiaoxin on 2016/12/6.
 */
public class SystemXmlObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;

    private String code;

    private String url;

    private String userId;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlAttribute
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @XmlAttribute
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
