package com.ycsys.smartmap.sys.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lixiaoxin on 2016/11/1.
 */
@Entity
@Table(name = "sys_system_config")
@DynamicUpdate
@DynamicInsert
public class SystemConfig implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name",length=50)
    private String key;

    @Column(name = "value",length = 3999)
    private String value;

    @Column(name = "type")
    private Integer type;

    @Column(name = "scope",length = 20)
    private String scope;

    @Column(name = "remark",length = 3999)
    private String remark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
