package com.ycsys.smartmap.sys.common.snmp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/14.
 */
public class SnmpConstant {
    private List<CpuInfo> cpuInfoList;
    private List<NetAnalyzeInfo> netAnalyzeList;
    private NetInfo [] netInfos;
    private static SnmpConstant snmpConstant = new SnmpConstant();
    private SnmpConstant(){

    }
    public static SnmpConstant getInstance(){
        return snmpConstant;
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
