package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.entity.Dictionary;
import com.ycsys.smartmap.sys.entity.DictionaryItem;
import com.ycsys.smartmap.sys.entity.PageHelper;

import java.util.List;

/**
 * Created by lixiaoxin on 2016/10/24.
 */
public interface DictService {
    //分页查询所有字典
    List<Dictionary> findAll(PageHelper page);

    //更新或者创建字典
    void saveOrUpdate(Dictionary dictionary);

    //删除字典
    void delete(Dictionary dictionary);

    //删除字典子项
    void deleteItem(DictionaryItem dictionaryItem);

    //更新或者创建字典子项
    void saveOrUpdateItem(DictionaryItem item);

    //根据id获取一个字典
    Dictionary getById(String dict_id);

    //获取所有字典子项
    List<DictionaryItem> findItemByDictId(String dictId);

    //根据子项id获取子项
    DictionaryItem getItemById(String itemId);

    /**初始化数据字典**/
    void initDictionary ()throws Exception;

    /**获取所有字典的数目**/
    long countAll();
}
