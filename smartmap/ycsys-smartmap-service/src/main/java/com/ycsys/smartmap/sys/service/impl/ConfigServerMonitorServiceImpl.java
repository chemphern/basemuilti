package com.ycsys.smartmap.sys.service.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ycsys.smartmap.sys.common.schedule.CronUtil;
import com.ycsys.smartmap.sys.common.schedule.JobTaskManager;
import com.ycsys.smartmap.sys.common.schedule.ScheduleJob;
import com.ycsys.smartmap.sys.common.snmp.CpuInfo;
import com.ycsys.smartmap.sys.common.snmp.NetAnalyzeInfo;
import com.ycsys.smartmap.sys.common.snmp.SnmpBase;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.HttpClientUtils;
import com.ycsys.smartmap.sys.entity.DictionaryItem;
import com.ycsys.smartmap.sys.util.DataDictionary;
import com.ycsys.smartmap.sys.util.SpringContextHolder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ycsys.smartmap.sys.dao.ConfigServerMonitorDao;
import com.ycsys.smartmap.sys.entity.ConfigServerEngine;
import com.ycsys.smartmap.sys.entity.ConfigServerMonitor;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.ConfigServerEngineService;
import com.ycsys.smartmap.sys.service.ConfigServerMonitorService;

/**
 * @author liweixiong
 * @date 2016年11月2日
 */
@Service("configServerMonitorService")
public class ConfigServerMonitorServiceImpl implements
        ConfigServerMonitorService {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(ConfigServerMonitorServiceImpl.class);

    @Autowired
    private ConfigServerMonitorDao configServerMonitorDao;

    public Integer save(ConfigServerMonitor o) {
        // TODO Auto-generated method stub
        return (Integer) configServerMonitorDao.save(o);
    }

    public void delete(ConfigServerMonitor o) {
        // TODO Auto-generated method stub
        configServerMonitorDao.delete(o);
    }

    public void update(ConfigServerMonitor o) {
        // TODO Auto-generated method stub
        configServerMonitorDao.update(o);
    }

    public void saveOrUpdate(ConfigServerMonitor o) {
        //修改
        if (o.getId() != null) {
            ConfigServerMonitor prev = configServerMonitorDao.get(ConfigServerMonitor.class, o.getId());
            try {
                BeanExtUtils.copyProperties(prev, o, true, true,
                        null);
                o = prev;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        configServerMonitorDao.saveOrUpdate(o);
    }

    @Override
    public void delete(String id) {
        ConfigServerMonitor o = configServerMonitorDao.get(ConfigServerMonitor.class, Integer.parseInt(id));
        if (o.getStatus() != null && o.getStatus().equals("1")) {
            throw new ServiceException("该监控处于开启状态，请先停止监控！");
        }
        configServerMonitorDao.delete(o);
    }

    @Override
    public ConfigServerMonitor getById(String id) {
        return configServerMonitorDao.get(ConfigServerMonitor.class, Integer.parseInt(id));
    }

    @Transactional
    @Override
    public void startMonitor(String id) {
        ConfigServerMonitor config = configServerMonitorDao.get(ConfigServerMonitor.class, Integer.parseInt(id));
        //监控处于停止状态才可启动监控
        if (config.getStatus() != null && config.getStatus().equals("0")) {
            JobTaskManager jobTaskManager = SpringContextHolder.getBean("jobTaskManager");
            if (config.getMonitorType() != null) {
                switch (config.getMonitorType()) {
                    case "1": {//启动服务器监控
                        try {
                            SnmpBase base = new SnmpBase(config.getUrl(), config.getSnmpPort(), config.getCommunicate());
                            try {
                                base.snmpGet(".1.3.6.1.2.1.1.1.0");
                            } catch (Exception e) {
                                throw new ServiceException("启动失败！ip或者端口或者社区标识不正确，网络不通！");
                            }
                            //收集服务器的定时器
                            ScheduleJob job = new ScheduleJob();
                            job.setJobName("collectServiceInfo_" + id);
                            job.setJobGroup("snmp");
                            job.setJobStatus(ScheduleJob.STATUS_RUNNING);
                            //设置频率
                            job.setCronExpression(CronUtil.getCronByMillions(Long.parseLong(config.getMonitorRate())));
                            job.setDescription("收集服务器信息的定时器");
                            job.setBeanClass("com.ycsys.smartmap.sys.task.MonitorTask");
                            job.setMethodName("collectServiceInfo");
                            job.setMethodParameter(new Object[]{ConfigServerMonitor.class});
                            job.setArgs(new Object[]{config});
                            job.setIsConcurrent(ScheduleJob.CONCURRENT_NOT);
                            job.setCreateTime(new Date());
                            log.info("==============启动收集服务器信息的定时器：" + "collectServiceInfo_" + id + "================");
                            log.info("==============表达式：" + CronUtil.getCronByMillions(Long.parseLong(config.getMonitorRate())));
                            jobTaskManager.addJob(job);
                            //保存服务器信息的定时器
                            ScheduleJob saveJob = new ScheduleJob();
                            saveJob.setJobName("saveServiceInfo_" + id);
                            saveJob.setJobGroup("snmp");
                            saveJob.setJobStatus(ScheduleJob.STATUS_RUNNING);
                            //设置入库的定时器频率（从数据字典中拿）
                            Map<String, Object> savetimes = DataDictionary.getObject("monitor_times");
                            DictionaryItem dt = (DictionaryItem) savetimes.get(config.getMonitorType());
                            String times = dt.getMemo().trim();
                            saveJob.setCronExpression(CronUtil.getCronByMillions(Long.parseLong(times)));
                            saveJob.setDescription("服务器信息入库的定时器");
                            saveJob.setBeanClass("com.ycsys.smartmap.sys.task.MonitorTask");
                            saveJob.setMethodName("saveServiceInfo");
                            saveJob.setIsConcurrent(ScheduleJob.CONCURRENT_NOT);
                            saveJob.setCreateTime(new Date());
                            jobTaskManager.addJob(saveJob);
                            log.info("==============启动保存服务器信息的定时器：" + "saveServiceInfo_" + id + "================");
                            log.info("==============表达式：" + CronUtil.getCronByMillions(Long.parseLong(times)));

                        } catch (Exception e) {
                            throw new ServiceException("启动失败！ip或者端口或者社区标识不正确，网络不通！");
                        }
                        break;
                    }
                    case "2": {//启动tomcat监控
                        try {
                            if (!testTomcatConfig(config)) {
                                throw new ServiceException("启动失败！网络不连通或者账号密码配置错误！");
                            }
                            ;
                            //收集Tomcat的定时器
                            ScheduleJob job = new ScheduleJob();
                            job.setJobName("collectTomcatInfo_" + id);
                            job.setJobGroup("tomcat");
                            job.setJobStatus(ScheduleJob.STATUS_RUNNING);
                            //设置频率
                            job.setCronExpression(CronUtil.getCronByMillions(Long.parseLong(config.getMonitorRate())));
                            job.setDescription("收集Tomcat信息的定时器");
                            job.setBeanClass("com.ycsys.smartmap.sys.task.MonitorTask");
                            job.setMethodName("collectTomcatInfo");
                            job.setMethodParameter(new Object[]{ConfigServerMonitor.class});
                            job.setArgs(new Object[]{config});
                            job.setIsConcurrent(ScheduleJob.CONCURRENT_NOT);
                            job.setCreateTime(new Date());
                            jobTaskManager.addJob(job);
                            log.info("==============启动收集Tomcat信息的定时器：" + "collectTomcatInfo_" + id + "================");
                            log.info("==============表达式：" + CronUtil.getCronByMillions(Long.parseLong(config.getMonitorRate())));
                        } catch (Exception e) {
                            throw new ServiceException("启动失败！网络不连通或者账号密码配置错误！");
                        }
                        break;
                    }
                    case "3": {//启动oracle监控
                        break;
                    }
                    case "4": {//启动arcgis监控
                        break;
                    }
                    default: {
                        break;
                    }
                }
                configServerMonitorDao.executeHql("update ConfigServerMonitor set status = ? where id = ? ", new Object[]{"1", config.getId()});
            }
        } else {
            throw new ServiceException("监控已开启！");
        }
    }

    @Transactional
    @Override
    public void stopMonitor(String id) {
        ConfigServerMonitor config = configServerMonitorDao.get(ConfigServerMonitor.class, Integer.parseInt(id));
        //监控处于启动状态才可启动监控
        if (config.getStatus() != null && config.getStatus().equals("1")) {
            JobTaskManager jobTaskManager = SpringContextHolder.getBean("jobTaskManager");
            if (config.getMonitorType() != null) {
                try {
                    switch (config.getMonitorType()) {
                        case "1": {
                            ScheduleJob job = new ScheduleJob();
                            job.setJobName("collectServiceInfo_" + id);
                            job.setJobGroup("snmp");
                            jobTaskManager.pauseJob(job);
                            ScheduleJob saveJob = new ScheduleJob();
                            saveJob.setJobName("saveServiceInfo_" + id);
                            saveJob.setJobGroup("snmp");
                            jobTaskManager.pauseJob(saveJob);
                            break;
                        }
                        case "2": {
                            ScheduleJob job = new ScheduleJob();
                            job.setJobName("collectTomcatInfo_" + id);
                            job.setJobGroup("tomcat");
                            jobTaskManager.pauseJob(job);
                            break;
                        }
                        case "3": {
                            break;
                        }
                        case "4": {
                            break;
                        }
                        default: {
                            break;
                        }
                    }
                } catch (Exception e) {
                    throw new ServiceException("停止失败！");
                }
            }
            configServerMonitorDao.executeHql("update ConfigServerMonitor set status = ? where id = ? ", new Object[]{"0", config.getId()});
        } else {
            throw new ServiceException("监控已停止！");
        }
    }

    @Override
    public List<ConfigServerMonitor> findAll() {
        return configServerMonitorDao.find("from ConfigServerMonitor");
    }

    @Override
    public Boolean testTomcatConfig(ConfigServerMonitor config) {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(config.getUserName(), config.getUserPassword());
        credentialsProvider.setCredentials(scope, credentials);
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        HttpGet httpGet = null;
        HttpResponse httpResponse = null;
        HttpEntity entity = null;
        httpGet = new HttpGet(config.getUrl());
        try {
            httpResponse = httpClient.execute(httpGet);
            entity = httpResponse.getEntity();
            if (entity != null) {
                //判断格式是否正确
                String content_type = entity.getContentType().getValue();
                String[] contents = content_type.split(";");
                String type = contents[0];
                if (type != null) {
                    if (type.trim().equals("text/html")) {
                        return false;
                    }
                    ;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void saveServiceInfo(Map<String, Object> ses) {
        Map<String,List<NetAnalyzeInfo>> netAnalyzeInfos = (Map<String, List<NetAnalyzeInfo>>) ses.get("nets");
        Map<String,List<CpuInfo>> cpuInfos = (Map<String, List<CpuInfo>>) ses.get("cpus");
        System.out.println("入库！");
    }


    public List<ConfigServerMonitor> find(String hql) {
        // TODO Auto-generated method stub
        return configServerMonitorDao.find(hql);
    }

    public List<ConfigServerMonitor> find(String hql, Object[] param) {
        // TODO Auto-generated method stub
        return configServerMonitorDao.find(hql, param);
    }

    public List<ConfigServerMonitor> find(String hql, List<Object> param) {
        // TODO Auto-generated method stub
        return configServerMonitorDao.find(hql, param);
    }

    public ConfigServerMonitor get(Class<ConfigServerMonitor> c, Serializable id) {
        // TODO Auto-generated method stub
        return configServerMonitorDao.get(c, id);
    }

    public ConfigServerMonitor get(String hql, Object[] param) {
        // TODO Auto-generated method stub
        return configServerMonitorDao.get(hql, param);
    }

    public ConfigServerMonitor get(String hql, List<Object> param) {
        // TODO Auto-generated method stub
        return configServerMonitorDao.get(hql, param);
    }


    @Override
    public List<ConfigServerMonitor> findByType(String type, PageHelper pageHelper) {
        return configServerMonitorDao.find("from ConfigServerMonitor where monitorType = ?", new Object[]{type}, pageHelper);
    }

    @Override
    public long countByType(String type) {
        return configServerMonitorDao.count("select count(*) from ConfigServerMonitor where monitorType = ?", new Object[]{type});
    }
}
