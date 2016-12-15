package com.ycsys.smartmap.sys.common.snmp;

import java.util.ArrayList;

/**  
 * Created by lixiaoxin on 16-12-14.
 * 系统信息基础类（包括CPU，内存，硬盘信息)  
 */  
public class SystemInfo {  
    private String sysDesc; // 系统描述
    private String sysUpTime; // 系统运行时间(单位：秒)
    private String sysContact; // 系统联系人
    private String sysName; // 计算机名
    private String sysLocation; // 计算机位置
    private MemoryInfo memoryInfo; // 计算机内存信息  
    private DiskInfo diskInfos; // 计算机硬盘信息
    private CpuInfo cpuInfo;    // cpu信息  
  
    public CpuInfo getCpuInfo() {  
        return cpuInfo;  
    }  
  
    public void setCpuInfo(CpuInfo cpuInfo) {  
        this.cpuInfo = cpuInfo;  
    }  
  
    public String getSysDesc() {
        return sysDesc;  
    }  
  
    public void setSysDesc(String sysDesc) {
        this.sysDesc = sysDesc;  
    }  
  
    public String getSysUpTime() {
        return sysUpTime;  
    }  
  
    public void setSysUpTime(String sysUpTime) {
        this.sysUpTime = sysUpTime;  
    }  
  
    public String getSysContact() {
        return sysContact;  
    }  
  
    public void setSysContact(String sysContact) {
        this.sysContact = sysContact;  
    }  
  
    public String getSysName() {
        return sysName;  
    }  
  
    public void setSysName(String sysName) {
        this.sysName = sysName;  
    }  
  
    public String getSysLocation() {
        return sysLocation;  
    }  
  
    public void setSysLocation(String sysLocation) {
        this.sysLocation = sysLocation;  
    }  
  
    public MemoryInfo getMemoryInfo() {  
        return memoryInfo;  
    }  
  
    public void setMemoryInfo(MemoryInfo memoryInfo) {  
        this.memoryInfo = memoryInfo;  
    }  
  
    public DiskInfo getDiskInfos() {
        return diskInfos;  
    }  
  
    public void setDiskInfos(ArrayList<DiskInfo> diskInfos) {
        DiskInfo disk = new DiskInfo();
        double free = 0;
        double size = 0;
        double used = 0;
        for(DiskInfo diskInfo:diskInfos){
            String freeSize = diskInfo.getDiskFreeSize();
            String diskSize = diskInfo.getDiskSize();
            String diskUsedSize = diskInfo.getDiskUsedSize();
            free += Double.parseDouble(freeSize);
            size += Double.parseDouble(diskSize);
            used += Double.parseDouble(diskUsedSize);
        }
        disk.setDiskFreeSize(free + "");
        disk.setDiskSize(size + "");
        disk.setDiskUsedSize(used + "");
        disk.setPercentUsed(used/size * 100);
        this.diskInfos = disk;
    }  
  
    public String toString() {
        StringBuffer info = new StringBuffer();
        info.append("The System Base Info: \n SysDesc: " + this.getSysDesc()  
                + "\n SysName: " + this.getSysName() + "\n SysUptime: "  
                + this.getSysUpTime() + "\n SysContact: "  
                + this.getSysContact() + "\n SysLocation: "  
                + this.getSysLocation() + "\n");  
        info.append("The Memory Info: \n Memory Size: " + this.getMemoryInfo().getMemorySize()  
                + "\n Memory Free Size: " + this.getMemoryInfo().getMemoryFreeSize()  
                + "\n Memory Used Size: " + this.getMemoryInfo().getMemoryUsedSize()  
                + "\n Memory Used Percentage: " + this.getMemoryInfo().getMemoryPercentage() + "\n");  
  
        info.append("The Disk Info: \n");  
        info.append(this.diskInfotoString());  
        info.append("The CPU Info: \n");  
        info.append(this.cpuInfotoString());  
        return info.toString();  
    }  
  
    private String cpuInfotoString() {
        StringBuffer cpuInfoStr = new StringBuffer();
        CpuInfo cpuInfo = this.getCpuInfo();  
        ArrayList<CpuInfo> cpuInfos = cpuInfo.getCpuDetailInfos();
        for (int i=0;i<cpuInfos.size();i++) {  
            CpuInfo obj = cpuInfos.get(i);  
            cpuInfoStr.append("Cpu Desc: " + obj.getCpuDesc() + "\n");  
        }  
        cpuInfoStr.append("The Number of core CPU : " + cpuInfo.getCoreNum()  
                + "\nSystem Rate of CPU: " + cpuInfo.getSysRate()  
                + "\nUser Rate of CPU: " + cpuInfo.getUserRate()  
                + "\nFree Rate of CPU: " + cpuInfo.getFreeRate());  
        return cpuInfoStr.toString();  
    }  
  
    private String diskInfotoString() {
        return this.diskInfos.toString();
    }
}