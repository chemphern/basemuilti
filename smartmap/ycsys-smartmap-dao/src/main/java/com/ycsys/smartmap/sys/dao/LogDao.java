package com.ycsys.smartmap.sys.dao;

import com.ycsys.smartmap.sys.entity.Log;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */
public interface LogDao extends BaseDao<Log,Integer>{
    public void deleteBatch(List<Integer> idList);
}
