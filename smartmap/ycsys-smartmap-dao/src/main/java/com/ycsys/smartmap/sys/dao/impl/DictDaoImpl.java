package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.DictDao;
import com.ycsys.smartmap.sys.entity.Dictionary;
import org.springframework.stereotype.Repository;

/**
 * 字典DAO
 * @author ty
 * @date 2015年1月13日
 */
@Repository("dictDao")
public class DictDaoImpl extends  BaseDaoImpl<Dictionary,Integer> implements DictDao{

}
