package com.ycsys.smartmap.sys.common.snmp;

import java.util.ArrayList;

/**  
 * Created by lixiaoxin on 16-12-14.
 * CPU信息基本类  
 */  
public class CpuInfo {  
    private String cpuDesc;      //  cpu信息描述
    private int coreNum;        // cpu核数  
    private String userRate;      // cpu使用率
    private String sysRate;
    private String freeRate;      // cpu空闲率
    private ArrayList<CpuInfo> cpuDetailInfos;      // 每个核的信息
    private long time;
  
    public ArrayList<CpuInfo> getCpuDetailInfos() {
        return cpuDetailInfos;  
    }  
  
    public void setCpuDetailInfos(ArrayList<CpuInfo> cpuDetailInfos) {
        this.cpuDetailInfos = cpuDetailInfos;  
    }  
  
    public String getUserRate() {
        return userRate;  
    }  
  
    public void setUserRate(String userRate) {
        this.userRate = userRate;  
    }  
  
    public String getSysRate() {
        return sysRate;  
    }  
  
    public void setSysRate(String sysRate) {
        this.sysRate = sysRate;  
    }  
  
    public String getFreeRate() {
        return freeRate;  
    }  
  
    public void setFreeRate(String freeRate) {
        this.freeRate = freeRate;  
    }  
  
    public int getCoreNum() {  
        return coreNum;  
    }  
  
    public void setCoreNum(int coreNum) {  
        this.coreNum = coreNum;  
    }  
  
    public String getCpuDesc() {
        return cpuDesc;  
    }  
  
    public void setCpuDesc(String cpuDesc) {
        this.cpuDesc = cpuDesc;  
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CpuInfo{" +
                "cpuDesc='" + cpuDesc + '\'' +
                ", coreNum=" + coreNum +
                ", userRate='" + userRate + '\'' +
                ", sysRate='" + sysRate + '\'' +
                ", freeRate='" + freeRate + '\'' +
                ", cpuDetailInfos=" + cpuDetailInfos +
                '}';
    }
}