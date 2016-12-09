package com.ycsys.smartmap.sys.common.config.parseobject.user;

import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * Created by lixiaoxin on 2016/12/5.
 */
public class UserXmlObject implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String loginName;
    private String password;
    private String email;
    private String sex;
    private String phone;
    private String remark;
    private String roles;
    private String org;

    @XmlAttribute
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlAttribute
    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @XmlAttribute
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @XmlAttribute
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlAttribute
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @XmlAttribute
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlAttribute
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @XmlAttribute
    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    @XmlAttribute
    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }
}
