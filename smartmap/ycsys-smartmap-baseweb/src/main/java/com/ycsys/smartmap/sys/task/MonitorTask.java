package com.ycsys.smartmap.sys.task;

import com.ycsys.smartmap.monitor.entity.NativeDbMonitor;
import com.ycsys.smartmap.monitor.entity.NativeServerMonitor;
import com.ycsys.smartmap.monitor.entity.NativeTomcatMonitor;
import com.ycsys.smartmap.monitor.entity.ServiceMonitor;
import com.ycsys.smartmap.monitor.service.NativeDbMonitorService;
import com.ycsys.smartmap.monitor.service.NativeServerMonitorService;
import com.ycsys.smartmap.monitor.service.NativeTomcatMonitorService;
import com.ycsys.smartmap.monitor.service.ServiceMonitorService;
import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.sys.common.jmx.JavaInformations;
import com.ycsys.smartmap.sys.common.jmx.MemoryInformations;
import com.ycsys.smartmap.sys.common.jmx.TomcatInformations;
import com.ycsys.smartmap.sys.common.utils.DbMonitorUtil;
import com.ycsys.smartmap.sys.common.utils.HttpSocketUtil;
import com.ycsys.smartmap.sys.common.utils.JaxbMapper;
import com.ycsys.smartmap.sys.task.object.MonitorConstant;
import com.ycsys.smartmap.sys.common.snmp.*;
import com.ycsys.smartmap.sys.entity.ConfigServerMonitor;
import com.ycsys.smartmap.sys.service.ConfigServerMonitorService;
import com.ycsys.smartmap.sys.common.config.parseobject.tomcat.TomcatStatusObject;
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

import javax.servlet.ServletContext;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 监控任务
 * Created by lixiaoxin on 2016/12/14.
 */
public class MonitorTask {

    //收集服务器信息
    public void collectServiceInfo(ConfigServerMonitor config) {
            Map<String,Object> res = serviceMonitor(config.getId().toString(),config.getUrl(),config.getSnmpPort(),config.getCommunicate(),config.getMonitorRate());
            if(res != null && String.valueOf(res.get("code")).equals("1")){
                //SpringContextHolder.getBean("");
            }
    }

    public void saveServiceInfo() {
        ConfigServerMonitorService configServerMonitorService = SpringContextHolder.getBean("configServerMonitorService");
        MonitorConstant cons = MonitorConstant.getInstance();
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
        Map<String, List<NetAnalyzeInfo>> netMap = cons.getNetAnalyzeMap();
        Map<String, List<CpuInfo>> cpuMap = cons.getCpuInfoMap();
        cons.setCpuInfoMap(null);
        cons.setNetAnalyzeMap(null);
        Map<String, Object> ses = new HashMap<String, Object>();
        ses.put("netMap", netMap);
        ses.put("cpus", cpuMap);
        configServerMonitorService.saveServiceInfo(ses);
    }

    public void collectTomcatInfo(ConfigServerMonitor config) {
        BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        AuthScope scope = new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM);
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(config.getUserName(), config.getUserPassword());
        credentialsProvider.setCredentials(scope, credentials);
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        String result = "";
        HttpGet httpGet = null;
        HttpResponse httpResponse = null;
        HttpEntity entity = null;
        httpGet = new HttpGet(config.getUrl());
        String key = config.getId().toString();
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
                        //System.out.println("验证失败");
                        return;
                    }
                    ;
                }
                result = EntityUtils.toString(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //System.out.println("连接失败！");
        }
        TomcatStatusObject tomcatStatusObject = JaxbMapper.fromXml(result, TomcatStatusObject.class);
        MonitorConstant cons = MonitorConstant.getInstance();
        cons.getTomcatStatusList(key).add(tomcatStatusObject);
    }

    public void collectServiceInfo(Service service) {
        String url = service.getServiceVisitAddress();
        Map<String,Object> res = HttpSocketUtil.sendRequest(url);
        String resTime = "";
        String resCode = "";
        if(String.valueOf(res.get("code")).equals("1")){
            resTime = (long)res.get("resTime") + "";
            resCode = String.valueOf(res.get("status"));
        }else{
            resTime = null;
            resCode = "600";
        }
        ServiceMonitor m = new ServiceMonitor();
        m.setStatus(resCode);
        m.setMonitorAddress(url);
        m.setRespTime(resTime);
        m.setMonitorDate(new Date());
        m.setService(service);
        ServiceMonitorService serviceMonitorService = SpringContextHolder.getBean("serviceMonitorService");
        serviceMonitorService.save(m);
    }

    public void collectNativeServer(String monitorId,String url,String port,String communicate,String rate){
        Map<String,Object> res = serviceMonitor( monitorId, url, port, communicate, rate);
        if(res != null && String.valueOf(res.get("code")).equals("1")){
            CpuInfo cpuInfo = (CpuInfo) res.get("cpuInfo");
            NetAnalyzeInfo netAnalyzeInfo = (NetAnalyzeInfo) res.get("netAnalyzeInfo");
            MemoryInfo memoryInfo = (MemoryInfo) res.get("memoryInfo");
            NativeServerMonitorService nativeServerMonitorService = SpringContextHolder.getBean("nativeServerMonitorService");
            NativeServerMonitor monitor = new NativeServerMonitor();
            monitor.setTime(new Date());
            monitor.setUsedMemory(Double.parseDouble(memoryInfo.getMemoryUsedSize()));
            monitor.setMemory(Double.parseDouble(memoryInfo.getMemorySize()));
            monitor.setRate(Integer.parseInt(rate));
            monitor.setRecByte(netAnalyzeInfo.getDurationRecByte());
            monitor.setSendByte(netAnalyzeInfo.getDurationSendByte());
            monitor.setRecPackage(netAnalyzeInfo.getDurationRecPack());
            monitor.setSendPackage(netAnalyzeInfo.getDurationSendPack());
            monitor.setUsedRate(Double.parseDouble(cpuInfo.getSysRate()));
            monitor.setIp(url);
            nativeServerMonitorService.save(monitor);
        }
    }
    public void collectNativeDb(String driver,String url,String username,String password){
        Map<String,Object> res = DbMonitorUtil.getDbMsg(driver,url,username,password);
        if(String.valueOf(res.get("code")).equals("1")) {
            String connect = (String) res.get("connect");
            String type = (String) res.get("type");
            NativeDbMonitor db = new NativeDbMonitor();
            db.setTime(new Date());
            db.setType(type);
            db.setConnect(Integer.parseInt(connect));
            NativeDbMonitorService nativeDbMonitorService = SpringContextHolder.getBean("nativeDbMonitorService");
            nativeDbMonitorService.save(db);
        }
    }

    public void collectNativeTomcat(ServletContext servletContext){
        JavaInformations javaInformations = new JavaInformations(servletContext,true);
        List<TomcatInformations> tomcats = javaInformations.getTomcatInformationsList();
        int threadCount = 0;
        int threadBusy = 0;
        for(TomcatInformations tomcat:tomcats){
            threadCount += tomcat.getCurrentThreadCount();
            threadBusy += tomcat.getCurrentThreadsBusy();
        }
        MemoryInformations memoryInformations = javaInformations.getMemoryInformations();
        long maxMemory = memoryInformations.getMaxMemory();
        long usedMemory = memoryInformations.getUsedMemory();
        long freeMemory = maxMemory - usedMemory;
        NativeTomcatMonitor t = new NativeTomcatMonitor();
        t.setTime(new Date());
        t.setFreeMemory(freeMemory);
        t.setUsedMemory(usedMemory );
        t.setThread(threadCount);
        t.setThreadBusy(threadBusy);
        NativeTomcatMonitorService nativeTomcatMonitorService = SpringContextHolder.getBean("nativeTomcatMonitorService");
        nativeTomcatMonitorService.save(t);
        javaInformations = null;
    }

    /**
     * monitorId用于区别内存块
     * **/
    public Map<String,Object> serviceMonitor(String monitorId,String url,String port,String communicate,String rate){
        Map<String,Object> res = new HashMap<String,Object>();
        try {
            SnmpBase snmp = new SnmpBase(url,port, communicate);
            NetInfo netInfo = snmp.getNetInfo();
            String key = monitorId;
            MonitorConstant cons = MonitorConstant.getInstance();
            //cons.getCpuInfoList(key).add(cpuInfo);
            NetInfo prev = cons.getNetInfos(key)[0];
            long time = Long.parseLong(rate) * 1000;
            //不能得到分析的网络数据
            if (prev == null) {
                cons.getNetInfos(key)[0] = netInfo;
                res = null;
            } else {
                NetAnalyzeInfo netAnalyzeInfo = new NetAnalyzeInfo();
                netAnalyzeInfo.setNetInfo(netInfo);
                long doutByte = netAnalyzeInfo.getSendSize() - prev.getSendSize();
                long dinByte = netAnalyzeInfo.getRecSize() - prev.getRecSize();
                long doutpack = netAnalyzeInfo.getSendPack() - prev.getSendPack();
                long dinpack = netAnalyzeInfo.getRecPack() - prev.getRecPack();
                //出网流量kbps
                long outkbps = (netAnalyzeInfo.getSendSize() - prev.getSendSize()) / (time / 1000);
                //入网流量kbps
                long inkbps = (netAnalyzeInfo.getRecSize() - prev.getRecSize()) / (time / 1000);
                //平均发送包裹数
                long outpack = (netAnalyzeInfo.getSendPack() - prev.getSendPack()) / (time / 1000);
                //平均接收包裹数
                long inpack = (netAnalyzeInfo.getRecPack() - prev.getRecPack()) / (time / 1000);
                netAnalyzeInfo.setOutkbps(outkbps);
                netAnalyzeInfo.setInkbps(inkbps);
                netAnalyzeInfo.setPersendpack(outpack);
                netAnalyzeInfo.setPerrecpack(inpack);
                netAnalyzeInfo.setDurationRecByte(dinByte);
                netAnalyzeInfo.setDurationRecPack(dinpack);
                netAnalyzeInfo.setDurationSendByte(doutByte);
                netAnalyzeInfo.setDurationSendPack(doutpack);
                //cons.getNetAnalyzeList(key).add(netAnalyzeInfo);
                cons.getNetInfos(key)[0] = netInfo;
                CpuInfo cpuInfo = snmp.getCpuInfo();
                MemoryInfo memoryInfo = snmp.getMemoryInfo();
                res.put("cpuInfo",cpuInfo);
                res.put("netAnalyzeInfo",netAnalyzeInfo);
                res.put("memoryInfo",memoryInfo);
                res.put("code","1");
            }
        } catch (Exception e) {
            res.put("code","0");
            res.put("msg",e.getMessage());
            return res;
        }
        return res;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
//        Class clazz = Class.forName("com.ycsys.smartmap.sys.task.MonitorTask");
//        Object object = clazz.newInstance();
//        clazz = object.getClass();
//        Class [] cs = new Class[1];
//        Object [] obj = new Object[]{ConfigServerMonitor.class};
//        for(int x = 0 ;x<obj.length;x++){
//            Class c = (Class) obj[x];
//            cs[x] = c;
//        }
//        Method method = clazz.getDeclaredMethod("collectServiceInfo", cs);
//        ConfigServerMonitor config = new ConfigServerMonitor();
//        config.setUrl("127.0.0.1");
//        config.setSnmpPort("161");
//        config.setCommunicate("public");
//        config.setMonitorRate("4");
//        Object o = config;
//        method.invoke(object,new Object[]{config});
//        Service service = new Service();
//        String url1 = "http://map.geoq.cn/ArcGIS/rest/services/ChinaOnlineCommunity_Mobile/MapServer";
//        String url2= "http://172.16.10.52:6080/arcgis/rest/services/GuangdongL08/ImageServer";
//        String url3 = "http://172.16.10.50:8080/ycsys-smartmap-baseweb";
//        String url4 = "http://127.0.0.1:80";
//        service.setServiceVisitAddress(url1);
//        MonitorTask m = new MonitorTask();
//        m.collectServiceInfo(service);
//        for(int x = 0;x<10;x++) {
//            m.collectServiceInfo(service);
//        }
    }

}
