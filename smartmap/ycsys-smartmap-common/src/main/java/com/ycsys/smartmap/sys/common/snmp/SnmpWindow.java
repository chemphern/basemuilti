package com.ycsys.smartmap.sys.common.snmp;

/**
 * Created by lixiaoxin on 16-12-14.
 */  
public class SnmpWindow extends SnmpBase {  
    public SnmpWindow(String ip, String port,String community) {
        super(ip, port,community);
    }  
  
    public SystemInfo getSysInfo() throws Exception {
        SystemInfo sysInfo = super.getSysInfo();
        sysInfo.setMemoryInfo(getMemoryInfo());  
        sysInfo.setDiskInfos(getDiskInfo());  
        sysInfo.setCpuInfo(getCpuInfo());  
        return sysInfo;  
    }  
}  