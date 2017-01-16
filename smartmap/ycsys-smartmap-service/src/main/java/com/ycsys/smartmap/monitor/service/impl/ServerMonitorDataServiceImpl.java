package com.ycsys.smartmap.monitor.service.impl;

import com.ycsys.smartmap.monitor.dao.ServerMonitorDataDao;
import com.ycsys.smartmap.monitor.entity.ServerMonitorData;
import com.ycsys.smartmap.monitor.service.ServerMonitorDataService;
import com.ycsys.smartmap.sys.entity.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lixiaoxin on 2017/1/10.
 */
@Service("serverMonitorDataService")
public class ServerMonitorDataServiceImpl implements ServerMonitorDataService{

    @Resource
    private ServerMonitorDataDao serverMonitorDataDao;

    @Override
    public void save(ServerMonitorData serverMonitorData) {
        serverMonitorDataDao.save(serverMonitorData);
    }

    @Override
    public List<ServerMonitorData> findPrevData(String id,int i) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.setPage(1);
        pageHelper.setPagesize(i);
        return serverMonitorDataDao.find("from ServerMonitorData where configServerMonitor.id = ? order by monitorTime desc",new Object[]{Integer.parseInt(id)},pageHelper);
    }
}
