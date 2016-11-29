package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.BaseDao;
import com.ycsys.smartmap.sys.dao.DictItemDao;
import com.ycsys.smartmap.sys.entity.Dictionary;
import com.ycsys.smartmap.sys.entity.DictionaryItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lixiaoxin on 2016/11/11.
 */
@Repository("dictItemDao")
public class DictItemDaoImpl extends BaseDaoImpl<DictionaryItem,Integer> implements DictItemDao{
    @Override
    public List<DictionaryItem> findAllByDictId(String dictId) {
        Object[] o = {Integer.parseInt(dictId)};
        return find("from DictionaryItem where dictionary.id = ? order by sort",o);
    }
}
