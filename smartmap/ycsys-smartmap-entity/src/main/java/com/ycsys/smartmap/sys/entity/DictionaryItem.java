package com.ycsys.smartmap.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lixiaoxin on 2016/11/1.
 */
@Entity
@Table(name = "sys_dictionary_item")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate
@DynamicInsert
public class DictionaryItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name",length=50)
    private String name;

    @Column(name = "value", length=3999)
    private String value;

    @Column(name = "memo",length=3999)
    private String memo;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "is_show")
    private short isShow = 1;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dictionary_id", nullable = false)
    private Dictionary dictionary;

    public DictionaryItem(){

    }
    public DictionaryItem(String name,String value,Integer sort,short isShow){
        this.name = name;
        this.value = value;
        this.sort =sort;
        this.isShow = isShow;
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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public short getIsShow() {
        return isShow;
    }

    public void setIsShow(short isShow) {
        this.isShow = isShow;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
    }
}
