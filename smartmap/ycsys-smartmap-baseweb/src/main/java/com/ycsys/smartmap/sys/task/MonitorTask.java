package com.ycsys.smartmap.sys.task;

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

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 监控任务
 * Created by lixiaoxin on 2016/12/14.
 */
public class MonitorTask {

    //收集服务器信息
    public void collectServiceInfo(ConfigServerMonitor config){
        System.out.println("===================收集服务器信息开始====================");
        try {
            SnmpBase snmp = new SnmpBase(config.getUrl(),config.getSnmpPort(),config.getCommunicate());
            CpuInfo cpuInfo = snmp.getCpuInfo();
            NetInfo netInfo = snmp.getNetInfo();
            MonitorConstant cons = MonitorConstant.getInstance();
            cons.getCpuInfoList().add(cpuInfo);
            NetInfo prev = cons.getNetInfos()[0];
            long time = Long.parseLong(config.getMonitorRate()) * 1000;
            //不能得到分析的网络数据
            if(prev == null){
                cons.getNetInfos()[0] = netInfo;
            }else{
                NetAnalyzeInfo netAnalyzeInfo = new NetAnalyzeInfo();
                netAnalyzeInfo.setNetInfo(netInfo);
                //出网流量kbps
                long outkbps = (netAnalyzeInfo.getSendSize() - prev.getSendSize())/(time/1000);
                //入网流量kbps
                long inkbps = (netAnalyzeInfo.getRecSize() - prev.getRecSize())/(time/1000) ;
                //平均发送包裹数
                long outpack = (netAnalyzeInfo.getSendPack() - prev.getSendPack())/(time/1000);
                //平均接收包裹数
                long inpack = (netAnalyzeInfo.getRecPack() - prev.getRecPack())/(time/1000);
                netAnalyzeInfo.setOutkbps(outkbps);
                netAnalyzeInfo.setInkbps(inkbps);
                netAnalyzeInfo.setPersendpack(outpack);
                netAnalyzeInfo.setPerrecpack(inpack);
                cons.getNetAnalyzeList().add(netAnalyzeInfo);
                cons.getNetInfos()[0] = netInfo;
            }
        }catch (Exception e){

        }
        System.out.println("==============收集系统信息结束===============");
    }

    public void saveServiceInfo(){
        ConfigServerMonitorService configServerMonitorService = SpringContextHolder.getBean("configServerMonitorService");
        MonitorConstant cons = MonitorConstant.getInstance();
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.##");
       List<NetAnalyzeInfo> nets = cons.getNetAnalyzeList();
       List<CpuInfo> cpus = cons.getCpuInfoList();
       cons.setCpuInfoList(null);
       cons.setNetAnalyzeList(null);
       Map<String,Object> ses = new HashMap<String,Object>();
       ses.put("nets",nets);
       ses.put("cpus",cpus);
       configServerMonitorService.saveServiceInfo(ses);
    }

    public void collectTomcatInfo(ConfigServerMonitor config){
        System.out.println("===================收集Tomcat信息开始====================");
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
        try {
            httpResponse = httpClient.execute(httpGet);
            entity = httpResponse.getEntity();
            if( entity != null ){
                //判断格式是否正确
                String content_type = entity.getContentType().getValue();
                String [] contents = content_type.split(";");
                String type = contents[0];
                if(type != null){
                    if(type.trim().equals("text/html")){
                        //System.out.println("验证失败");
                        return;
                    };
                }
                result = EntityUtils.toString(entity);
            }
        } catch (Exception e){
            e.printStackTrace();
            //System.out.println("连接失败！");
        }
        TomcatStatusObject tomcatStatusObject = JaxbMapper.fromXml(result, TomcatStatusObject.class);
        System.out.println("===================收集Tomcat信息结束====================");
    }

    public static void main(String [] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
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
    }

}
