package com.ycsys.smartmap.sys.entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 地区实体
 * Created by lixiaoxin on 2016/11/15.
 */
@Entity
@Table(name = "sys_area")
@DynamicUpdate
@DynamicInsert
public class Area implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer id;

    //地区名称
    @Column(name = "name",length=50)
    private String name;

    //行政编码
    @Column(name = "code",length=20)
    private String code;

    //地区类型
    @Column(name = "type")
    private short type;

    //全称
    @Column(name = "all_name",length=100)
    private String allName;

    //邮政编码
    @Column(name = "postcode",length=20)
    private String postcode;

    //首字母
    @Column(name = "lyx",length=10)
    private String lyx;

    public Area(){

    }
    public Area(String name,String code,short type,String allName){
        this.name = name;
        this.code = code;
        this.type = type;
        this.allName = allName;
    }
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

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getAllName() {
        return allName;
    }

    public void setAllName(String allName) {
        this.allName = allName;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getLyx() {
        return lyx;
    }

    public void setLyx(String lyx) {
        this.lyx = lyx;
    }

}
