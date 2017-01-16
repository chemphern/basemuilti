package com.ycsys.smartmap.monitor.service.impl;

import com.ycsys.smartmap.monitor.dao.TomcatMonitorDataDao;
import com.ycsys.smartmap.monitor.entity.TomcatMonitorData;
import com.ycsys.smartmap.monitor.service.TomcatMonitorDataService;
import com.ycsys.smartmap.sys.entity.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lixiaoxin on 2017/1/10.
 */
@Service("tomcatMonitorDataService")
public class TomcatMonitorDataServiceImpl implements TomcatMonitorDataService{

    @Resource
    private TomcatMonitorDataDao tomcatMonitorDataDao;

    @Override
    public void save(TomcatMonitorData tomcatMonitorData) {
        tomcatMonitorDataDao.save(tomcatMonitorData);
    }

    @Override
    public List<TomcatMonitorData> findPrevData(String id, int i) {
        PageHelper pageHelper = new PageHelper();
        pageHelper.setPage(1);
        pageHelper.setPagesize(i);
        return tomcatMonitorDataDao.find("from TomcatMonitorData where configServerMonitor.id = ? order by monitorTime desc",new Object[]{Integer.parseInt(id)},pageHelper);
    }
}
