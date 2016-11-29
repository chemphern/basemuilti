package com.ycsys.smartmap.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * 系统表
 * Created by lixiaoxin on 2016/11/8.
 */
@Entity
@Table(name = "sys_system")
@DynamicUpdate
@DynamicInsert
public class System implements Serializable{

    private static final long serialVersionUID = 1L;

    //id
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    //系统名称
    @Column(name = "name",length=100)
    private String name;

    //系统编号
    @Column(name = "code",length=50)
    private String code;

    //创建人
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //创建时间
    @Column(name="create_time")
    private Timestamp createTime;

    //访问地址
    @Column(name="url")
    private String url;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "system")
    private Set<SystemUser> systemUser = new HashSet<SystemUser>(0);

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<SystemUser> getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(Set<SystemUser> systemUser) {
        this.systemUser = systemUser;
    }
}
