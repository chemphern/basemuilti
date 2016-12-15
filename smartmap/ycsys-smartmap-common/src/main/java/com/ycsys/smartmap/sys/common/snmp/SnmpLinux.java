package com.ycsys.smartmap.sys.common.snmp;

/**
 * Created by lixiaoxin on 16-12-14.
 */  
public class SnmpLinux extends SnmpBase {  
    public SnmpLinux(String ip, String community) {
        super(ip, community);  
    }  
  
    public CpuInfo getCpuInfo() throws Exception {
        CpuInfo cpuInfo = super.getCpuInfo();  
        cpuInfo.setSysRate(snmpGet(props.getLinuxSysCPURate()));  
        cpuInfo.setUserRate(snmpGet(props.getLinuxUserCPURate()));  
        cpuInfo.setFreeRate(snmpGet(props.getLinuxFreeCPURate()));  
        return cpuInfo;  
    }  
  
    public SystemInfo getSysInfo() throws Exception {
        SystemInfo sysInfo = super.getSysInfo();
        sysInfo.setMemoryInfo(getMemoryInfo());  
        sysInfo.setDiskInfos(getDiskInfo());  
        sysInfo.setCpuInfo(getCpuInfo());  
        return sysInfo;  
    }  
}  