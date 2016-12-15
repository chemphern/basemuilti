package com.ycsys.smartmap.sys.task;

import com.ycsys.smartmap.sys.common.snmp.*;
import com.ycsys.smartmap.sys.common.utils.DateUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * snmp的任务定时器
 * Created by lixiaoxin on 2016/12/14.
 */
public class SnmpTask {
     private final long cpuAndNetTaskTime = 10000;
     private final long analyzeCpuAndNetTime = 30000;

    public void cpuAndNetTask(){
        System.out.println("==============收集系统信息开始===============");
        try {
            SnmpBase snmp = new SnmpBase("127.0.0.1", "public");
            CpuInfo cpuInfo = snmp.getCpuInfo();
            NetInfo netInfo = snmp.getNetInfo();
            SnmpConstant cons = SnmpConstant.getInstance();
            cons.getCpuInfoList().add(cpuInfo);
            NetInfo prev = cons.getNetInfos()[0];
            //不能得到分析的网络数据
            if(prev == null){
                cons.getNetInfos()[0] = netInfo;
            }else{
                NetAnalyzeInfo netAnalyzeInfo = new NetAnalyzeInfo();
                netAnalyzeInfo.setNetInfo(netInfo);
                //出网流量kbps
                long outkbps = (netAnalyzeInfo.getSendSize() - prev.getSendSize())/(cpuAndNetTaskTime/1000);
                //入网流量kbps
                long inkbps = (netAnalyzeInfo.getRecSize() - prev.getRecSize())/(cpuAndNetTaskTime/1000) ;
                //平均发送包裹数
                long outpack = (netAnalyzeInfo.getSendPack() - prev.getSendPack())/(cpuAndNetTaskTime/1000);
                //平均接收包裹数
                long inpack = (netAnalyzeInfo.getRecPack() - prev.getRecPack())/(cpuAndNetTaskTime/1000);
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

    public void analyzeCpuAndNet(){
        System.out.println("======================定时任务开始============================");
        SnmpConstant cons = SnmpConstant.getInstance();
        java.text.DecimalFormat   df   =new   java.text.DecimalFormat("#.##");
        System.out.println("netinfo的个数：" + cons.getNetAnalyzeList().size());
        for(NetAnalyzeInfo netinfo:cons.getNetAnalyzeList()){
            long time = netinfo.getTime();
            Date date = new Date(time);
            System.out.println("时间：" + DateUtils.dateFormat(date) + "   出网kbps：" + df.format((double)netinfo.getOutkbps()/1024) + "    入网kbps:" + df.format((double)netinfo.getInkbps()/1024 )+
                    "   总计发送字节：" + netinfo.getSendSize() + "    总计接收字节：" + netinfo.getRecSize() +
            "    发送包裹数/s：" + netinfo.getPersendpack() + "    接收包裹数/s:" + netinfo.getPerrecpack() +
            "    总计发送包裹数:" + netinfo.getSendPack() + "    总计接收包裹数：" + netinfo.getRecPack());
        }
        System.out.println("cpuInfo的个数：" + cons.getCpuInfoList().size());
        System.out.println("======================定时任务结束============================");
    }

}
