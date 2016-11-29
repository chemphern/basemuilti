package com.ycsys.smartmap.sys.dao;

import com.ycsys.smartmap.sys.dao.impl.DictItemDaoImpl;
import com.ycsys.smartmap.sys.entity.Dictionary;
import com.ycsys.smartmap.sys.entity.DictionaryItem;

import java.util.List;

/**
 * Created by lixiaoxin on 2016/11/11.
 */
public interface DictItemDao extends BaseDao<DictionaryItem,Integer>{

    /**根据字典id获取所有字典子项**/
    List<DictionaryItem> findAllByDictId(String dictId);

}
