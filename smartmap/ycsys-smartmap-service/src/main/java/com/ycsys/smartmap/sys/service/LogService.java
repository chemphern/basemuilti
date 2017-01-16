package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.common.enums.LogType;
import com.ycsys.smartmap.sys.entity.Log;
import com.ycsys.smartmap.sys.entity.PageHelper;

import java.util.Date;
import java.util.List;

/**
 * Created by lixiaoxin on 2016/10/24.
 */
public interface LogService {
    public void deleteLog(List<Integer> idList);

    List<Log> findAll(PageHelper page);

    void save(Log log);

    void saveLogInfo(String name, LogType type,String remark,int status,Object res,long usedTime);

    long countAll();
    
    Object[] findArrValue(String hql, List<Object> params, Integer page,Integer pageSize);

    /**根据条件获取日志**/
    List<Log> findBySolution(Date date, Date date1, int operationType, int status, int num);
}
