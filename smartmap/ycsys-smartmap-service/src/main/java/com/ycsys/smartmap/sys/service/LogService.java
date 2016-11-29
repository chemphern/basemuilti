package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.entity.Log;
import com.ycsys.smartmap.sys.entity.PageHelper;

import java.util.List;

/**
 * Created by Administrator on 2016/10/24.
 */
public interface LogService {
    public void deleteLog(List<Integer> idList);

    List<Log> findAll(PageHelper page);
}
