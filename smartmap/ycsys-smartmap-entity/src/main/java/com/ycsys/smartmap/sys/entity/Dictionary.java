package com.ycsys.smartmap.sys.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * 字典 entity
 * @author lixiaoxin
 * @date 2016年11月1日
 */
@Entity
@Table(name = "sys_dictionary")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@DynamicUpdate @DynamicInsert
public class Dictionary implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "name",length=50)
	private String name;

	@Column(name = "code",length=50)
	private String code;

	@Column(name = "memo",length=3999)
	private String memo;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dictionary")
	private Set<DictionaryItem> dictionaryItems = new HashSet<DictionaryItem>(0);

	public Dictionary() {
	}
	public Dictionary(String name,String code,String memo){
		this.name = name;
		this.code = code;
		this.memo = memo;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Set<DictionaryItem> getDictionaryItems() {
		return dictionaryItems;
	}

	public void setDictionaryItems(Set<DictionaryItem> dictionaryItems) {
		this.dictionaryItems = dictionaryItems;
	}
}