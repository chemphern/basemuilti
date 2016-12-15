package com.ycsys.smartmap.sys.common.snmp;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by lixiaoxin on 16-12-14.
 * SnmpOID配置文件读取类
 */
public class SnmpProperties {
    private static SnmpProperties snmpProperties = null;

    private String sysDesc;
    private String sysUptime;
    private String sysName;
    private String sysContact;
    private String sysLocation;
    private String cpuID;
    private String hrStorageFixedDisk;
    private String hrStorageRamDisk;
    private String hrStorageNetWorkDisk;
    private String ramID;
    private String exiRamID;
    private String windowUsedMemory;
    private String windowDiskIndex;
    private String windowDiskType;
    private String windowDiskDesc;
    private String windowDiskAmount;
    private String windowDiskUsed;
    private String windowDiskSize;
    private String linuxUserCPURate;
    private String linuxSysCPURate;
    private String linuxFreeCPURate;
    private String linuxDiskIndex;
    private String linuxDiskUsed;
    private String linuxDiskFree;
    private String linuxDiskSize;
    private String linxuDiskUsedRate;
    private String networkNumber;
    private String networkType;
    private String networkIndex;
    private String networkDesc;
    private String networkSpeed;
    private String networkPhysicalAddr;
    private String networkRecieveByte;
    private String networkSendByte;
    private String networkRecievePacket;
    private String networkRecieveSPacket;
    private String networkSendPacket;
    private String networkSendSPacket;
    private String networkSendDiscard;
    private String networkRecieveDiscard;
    private String memoryTotalSize;
    private String deviceIndex;
    private String deviceType;
    private String deviceInfo;
    private String cpuCurrentLoadIndex;

    public String getExiRamID() {
        return exiRamID;
    }

    public String getRamID() {
        return ramID;
    }

    public String getWindowDiskType() {
        return windowDiskType;
    }

    public String getWindowDiskDesc() {
        return windowDiskDesc;
    }

    public String getLinuxDiskIndex() {
        return linuxDiskIndex;
    }

    public String getLinuxDiskUsed() {
        return linuxDiskUsed;
    }

    public String getLinuxDiskFree() {
        return linuxDiskFree;
    }

    public String getLinuxDiskSize() {
        return linuxDiskSize;
    }

    public String getLinxuDiskUsedRate() {
        return linxuDiskUsedRate;
    }

    public String getSysDesc() {
        return sysDesc;
    }

    public String getSysUptime() {
        return sysUptime;
    }

    public String getSysName() {
        return sysName;
    }

    public String getSysContact() {
        return sysContact;
    }

    public String getSysLocation() {
        return sysLocation;
    }

    public String getCpuID() {
        return cpuID;
    }

    public String getWindowUsedMemory() {
        return windowUsedMemory;
    }

    public String getWindowDiskIndex() {
        return windowDiskIndex;
    }

    public String getWindowDiskAmount() {
        return windowDiskAmount;
    }

    public String getWindowDiskUsed() {
        return windowDiskUsed;
    }

    public String getWindowDiskSize() {
        return windowDiskSize;
    }

    public String getLinuxUserCPURate() {
        return linuxUserCPURate;
    }

    public String getLinuxSysCPURate() {
        return linuxSysCPURate;
    }

    public String getLinuxFreeCPURate() {
        return linuxFreeCPURate;
    }

    public String getNetworkNumber() {
        return networkNumber;
    }

    public String getNetworkType() {
        return networkType;
    }

    public String getNetworkIndex() {
        return networkIndex;
    }

    public String getNetworkDesc() {
        return networkDesc;
    }

    public String getNetworkSpeed() {
        return networkSpeed;
    }

    public String getNetworkPhysicalAddr() {
        return networkPhysicalAddr;
    }

    public String getNetworkRecieveByte() {
        return networkRecieveByte;
    }

    public String getNetworkSendByte() {
        return networkSendByte;
    }

    public String getNetworkRecievePacket() {
        return networkRecievePacket;
    }

    public String getNetworkSendPacket() {
        return networkSendPacket;
    }

    public String getMemoryTotalSize() {
        return memoryTotalSize;
    }

    public String getDeviceIndex() {
        return deviceIndex;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public String getCpuCurrentLoadIndex() {
        return cpuCurrentLoadIndex;
    }

    public String getHrStorageFixedDisk() {
        return hrStorageFixedDisk;
    }

    public String getNetworkRecieveSPacket() {
        return networkRecieveSPacket;
    }

    public String getNetworkSendSPacket() {
        return networkSendSPacket;
    }

    public String getNetworkSendDiscard() {
        return networkSendDiscard;
    }

    public String getNetworkRecieveDiscard() {
        return networkRecieveDiscard;
    }

    public String getHrStorageRamDisk() {
        return hrStorageRamDisk;
    }

    public String getHrStorageNetWorkDisk() {
        return hrStorageNetWorkDisk;
    }

    public static SnmpProperties loadProperties() {
        if (snmpProperties != null)
            return snmpProperties;
        else {
            String strPath = SnmpProperties.class.getResource("").toString();

            //int preIndex = ("jar:file:/").length();
            //int endIndex = strPath.indexOf("BASE-1.0-SNAPSHOT.jar!/com/emc/snmp/comm/");
            //strPath = strPath.substring(preIndex,endIndex);

            HashMap<String,String> hashMap = new HashMap<String,String>();
            try {
                //JarFile jarFile = new JarFile(strPath + "BASE-1.0-SNAPSHOT.jar");
                //JarEntry entry = jarFile.getJarEntry("snmp_conf.properties");
                InputStream is = SnmpProperties.class.getResource("/" + "snmp_conf.properties").openStream();
//                InputStream input = jarFile.getInputStream(entry);

                Properties dbProps = new Properties();
                //dbProps.load(input);
                dbProps.load(is);
                //---------------BASE SYSTEM INFORMATION-----------
                hashMap.put("SYSDESC", dbProps.getProperty("get.system.description"));
                hashMap.put("SYSUPTIME", dbProps.getProperty("get.system.uptime"));
                hashMap.put("SYSCONTACT", dbProps.getProperty("get.system.contact"));
                hashMap.put("SYSNAME", dbProps.getProperty("get.system.name"));
                hashMap.put("SYSLOCATION", dbProps.getProperty("get.system.location"));
                hashMap.put("CPUID",dbProps.getProperty("DEVICE_CPU_ID"));
                hashMap.put("HR_STORAGE_FIXED_DISK",dbProps.getProperty("DEVICE_DISK_ID"));
                hashMap.put("HR_STORAGE_RAM_DISK",dbProps.getProperty("DEVICE_RAM_DISK_ID"));
                hashMap.put("HR_STORAGE_NETWORK_DISK",dbProps.getProperty("DEVICE_NETWORK_DISK_ID"));
                hashMap.put("HR_STORAGE_RAM_ID",dbProps.getProperty("DEVICE_STORAGE_RAM_ID"));
                hashMap.put("HR_STORIAGE_RAM_EXI_ID",dbProps.getProperty("DEVICE_STORAGE_RAM_ESXi_ID"));

                //---------------Memory Information------------------------------------
                hashMap.put("MEMORY_SIZE",dbProps.getProperty("get.memory.total.physical"));

                //---------------------------NetWork Information-------------------------------------
                hashMap.put("NETWORK_NUMBER",dbProps.getProperty("get.network.interface.number"));
                hashMap.put("NETWORK_INDEX",dbProps.getProperty("walk.network.interface.index"));
                hashMap.put("NETWORK_DESC",dbProps.getProperty("walk.network.interface.desc"));
                hashMap.put("NETWORK_TYPE",dbProps.getProperty("walk.network.interface.type"));
                hashMap.put("NETWORK_SPEED",dbProps.getProperty("walk.network.interface.speed"));
                hashMap.put("NETWORK_PHYSICAL_ADDR",dbProps.getProperty("walk.network.interface.physicalAddress"));
                hashMap.put("NETWORK_REC_BYTE",dbProps.getProperty("walk.network.interface.recieve.byte"));
                hashMap.put("NETWORK_SEND_BYTE",dbProps.getProperty("walk.network.interface.send.byte"));
                hashMap.put("NETWORK_REC_PACKET",dbProps.getProperty("walk.network.interface.recieve.packet"));
                hashMap.put("NETWORK_SEND_PACKET",dbProps.getProperty("walk.network.interface.send.packet"));
                hashMap.put("NETWORK_REC_SPACKET",dbProps.getProperty("walk.network.interface.recieve.spacket"));
                hashMap.put("NETWORK_SEND_SPACKET",dbProps.getProperty("walk.network.interface.send.spacket"));
                hashMap.put("NETWORK_REC_DISCARD",dbProps.getProperty("walk.network.interface.recieve.discard"));
                hashMap.put("NETWORK_SEND_DISCARD",dbProps.getProperty("walk.network.interface.send.discard"));

                //----------------------------CPU Information----------------------------------------
                hashMap.put("DEVICE_INDEX",dbProps.getProperty("walk.device.index"));
                hashMap.put("DEVICE_TYPE",dbProps.getProperty("walk.device.type"));
                hashMap.put("DEVICE_INFO",dbProps.getProperty("walk.device.info"));
                hashMap.put("CPU_LOAD",dbProps.getProperty("walk.cpu.current.load.index"));




                //-----------------PRIVATE WINDOWS OID---------------------------------------------
                ////-----------------DISK INFORMATION------------------------------------
                hashMap.put("WINDOW_DISK_INDEX",dbProps.getProperty("walk.window.disk.index"));
                hashMap.put("WINDOW_DISK_AMOUNT",dbProps.getProperty("walk.window.disk.amount"));
                hashMap.put("WINDOW_DISK_SIZE",dbProps.getProperty("walk.window.disk.size"));
                hashMap.put("WINDOW_DISK_USED",dbProps.getProperty("walk.window.disk.used"));
                hashMap.put("WINDOW_DISK_DESC",dbProps.getProperty("walk.window.disk.desc"));
                hashMap.put("WINDOW_DISK_TYPE", dbProps.getProperty("walk.window.disk.type"));

                ////---------------------Memory Information------------------------------------------
                hashMap.put("WINDOW_MEMORY_USED",dbProps.getProperty("walk.window.memory.physical.used"));


                //-----------------------PRIVATE LINUX OID------------------------------------------
                hashMap.put("LINUX_USER",dbProps.getProperty("get.linux.user.cpu.rate"));
                hashMap.put("LINUX_SYS",dbProps.getProperty("get.linux.system.cpu.rate"));
                hashMap.put("LINUX_FREE",dbProps.getProperty("get.linux.free.cpu.rate"));
                hashMap.put("LINUX_DISK_INDEX",dbProps.getProperty("walk.linux.disk.index"));
                hashMap.put("LINUX_DISK_SIZE",dbProps.getProperty("walk.linux.disk.size"));
                hashMap.put("LINUX_DISK_FREE",dbProps.getProperty("walk.linux.disk.free.size"));
                hashMap.put("LINUX_DISK_USED",dbProps.getProperty("walk.linux.disk.used.size"));
                hashMap.put("LINUX_DISK_USED_RATE",dbProps.getProperty("walk.linux.disk.used.rate"));
            }catch(Exception e) {
                e.printStackTrace();
            }
            return new SnmpProperties(hashMap);
        }
    }

    private SnmpProperties(HashMap<String,String> hashMap) {
        this.sysDesc = hashMap.get("SYSDESC");
        this.sysUptime = hashMap.get("SYSUPTIME");
        this.sysContact = hashMap.get("SYSCONTACT");
        this.sysLocation = hashMap.get("SYSLOCATION");
        this.sysName = hashMap.get("SYSNAME");
        this.cpuID = hashMap.get("CPUID");
        this.hrStorageFixedDisk = hashMap.get("HR_STORAGE_FIXED_DISK");
        this.hrStorageRamDisk = hashMap.get("HR_STORAGE_RAM_DISK");
        this.hrStorageNetWorkDisk = hashMap.get("HR_STORAGE_NETWORK_DISK");
        this.ramID = hashMap.get("HR_STORAGE_RAM_ID");
        this.exiRamID = hashMap.get("HR_STORIAGE_RAM_EXI_ID");
        this.memoryTotalSize = hashMap.get("MEMORY_SIZE");
        this.cpuCurrentLoadIndex = hashMap.get("CPU_LOAD");
        this.deviceIndex = hashMap.get("DEVICE_INDEX");
        this.deviceInfo = hashMap.get("DEVICE_INFO");
        this.deviceType = hashMap.get("DEVICE_TYPE");
        this.linuxFreeCPURate = hashMap.get("LINUX_FREE");
        this.linuxSysCPURate = hashMap.get("LINUX_SYS");
        this.linuxUserCPURate = hashMap.get("LINUX_USER");
        this.windowDiskAmount = hashMap.get("WINDOW_DISK_AMOUNT");
        this.windowDiskIndex = hashMap.get("WINDOW_DISK_INDEX");
        this.windowDiskType = hashMap.get("WINDOW_DISK_TYPE");
        this.windowDiskSize = hashMap.get("WINDOW_DISK_SIZE");
        this.windowDiskUsed = hashMap.get("WINDOW_DISK_USED");
        this.windowDiskDesc = hashMap.get("WINDOW_DISK_DESC");
        this.windowUsedMemory = hashMap.get("WINDOW_MEMORY_USED");
        this.networkDesc = hashMap.get("NETWORK_DESC");
        this.networkIndex = hashMap.get("NETWORK_INDEX");
        this.networkNumber = hashMap.get("NETWORK_NUMBER");
        this.networkPhysicalAddr = hashMap.get("NETWORK_PHYSICAL_ADDR");
        this.networkRecieveByte = hashMap.get("NETWORK_REC_BYTE");
        this.networkRecievePacket = hashMap.get("NETWORK_REC_PACKET");
        this.networkSendByte = hashMap.get("NETWORK_SEND_BYTE");
        this.networkSendPacket = hashMap.get("NETWORK_SEND_PACKET");
        this.networkSpeed = hashMap.get("NETWORK_SPEED");
        this.networkType = hashMap.get("NETWORK_TYPE");
        this.networkRecieveSPacket = hashMap.get("NETWORK_REC_SPACKET");
        this.networkSendSPacket = hashMap.get("NETWORK_SEND_SPACKET");
        this.networkRecieveDiscard = hashMap.get("NETWORK_REC_DISCARD");
        this.networkSendDiscard = hashMap.get("NETWORK_SEND_DISCARD");
        this.linuxDiskFree = hashMap.get("LINUX_DISK_FREE");
        this.linuxDiskIndex = hashMap.get("LINUX_DISK_INDEX");
        this.linuxDiskSize = hashMap.get("LINUX_DISK_SIZE");
        this.linuxDiskUsed = hashMap.get("LINUX_DISK_USED");
        this.linxuDiskUsedRate = hashMap.get("LINUX_DISK_USED_RATE");
    }
}