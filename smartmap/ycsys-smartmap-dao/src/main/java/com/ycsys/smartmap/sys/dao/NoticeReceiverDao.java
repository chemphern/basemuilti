package com.ycsys.smartmap.sys.dao;

import com.ycsys.smartmap.sys.entity.NoticeReceiver;
import com.ycsys.smartmap.sys.entity.PageHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixiaoxin on 2017/1/13.
 */
public interface NoticeReceiverDao extends BaseDao<NoticeReceiver,Integer>{
    List findList(String s, Object[] objects);

    List<Map<String,Object>> findList(String s, Object[] objects, PageHelper pageHelper);
}
