package com.ycsys.smartmap.sys.task.object;

import com.ycsys.smartmap.sys.common.snmp.CpuInfo;
import com.ycsys.smartmap.sys.common.snmp.NetAnalyzeInfo;
import com.ycsys.smartmap.sys.common.snmp.NetInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoxin on 2016/12/14.
 */
public class MonitorConstant {
    private List<CpuInfo> cpuInfoList;
    private List<NetAnalyzeInfo> netAnalyzeList;
    private NetInfo[] netInfos;
    private static MonitorConstant monitorConstant = new MonitorConstant();
    private MonitorConstant(){

    }
    public static MonitorConstant getInstance(){
        return monitorConstant;
    }

    public List<CpuInfo> getCpuInfoList() {
        if(cpuInfoList == null){
            cpuInfoList = new ArrayList<>();
        }
        return cpuInfoList;
    }

    public void setCpuInfoList(List<CpuInfo> cpuInfoList) {
        this.cpuInfoList = cpuInfoList;
    }

    public List<NetAnalyzeInfo> getNetAnalyzeList() {
        if(netAnalyzeList == null){
            netAnalyzeList =  new ArrayList<>();
        }
        return netAnalyzeList;
    }

    public void setNetAnalyzeList(List<NetAnalyzeInfo> netAnalyzeList) {
        this.netAnalyzeList = netAnalyzeList;
    }

    public NetInfo[] getNetInfos() {
        if(netInfos == null){
            netInfos = new NetInfo[1];
        }
        return netInfos;
    }

    public void setNetInfos(NetInfo[] netInfos) {
        this.netInfos = netInfos;
    }
}
