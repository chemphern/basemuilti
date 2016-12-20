package com.ycsys.smartmap.sys.common.snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**  
 * Created by lixiaoxin on 16-12-14.
 * SNMP公用信息采集类   基本都是OID为1.3.6.1.2.1.25下的信息  
 */  
public class SnmpBase extends SnmpUtil {  
    public SnmpBase(String ip,String port, String community) {
        super(ip, community,port);
    }  

    /**
     * 网卡流量信息采集
     * **/
    public NetInfo getNetInfo() throws Exception {
        String netIndexOID = props.getNetworkIndex();
        String networkTypeOID  = props.getNetworkType();
        String networkRecieveSPacketOID = props.getNetworkRecieveSPacket();
        String networkSendSPacketOID = props.getNetworkSendSPacket();
        String networkSendDiscardOID = props.getNetworkSendDiscard();
        String networkReceiveDiscardOID = props.getNetworkRecieveDiscard();
        String networkSendByteOID = props.getNetworkSendByte();
        String networkRecieveByteOID  = props.getNetworkRecieveByte();
        String networkSendPacketOID  = props.getNetworkSendPacket();
        String networkRecievePacketOID = props.getNetworkRecievePacket();
        String networkDescOID = props.getNetworkDesc();
        String networkMacOID = props.getNetworkPhysicalAddr();
        ArrayList<String> indexs = snmpWalk(netIndexOID);
        List<String> macList = new ArrayList<>();
        long sSendPack = 0;
        long mSendPack = 0;
        long sRecPack = 0;
        long mRecPack = 0;
        long sendSize = 0;
        long recSize = 0;
        long sendDiscard = 0;
        long recDiscard = 0;
        for(int x = 0 ;x <indexs.size();x++){
            //物理地址
            String networkMac = snmpGet(networkMacOID + "." + indexs.get(x));
            if(!networkMac.equals("") && macList.contains(networkMac)){
                continue;
            }
            macList.add(networkMac);
            String networkType = snmpGet(networkTypeOID + "." + indexs.get(x));
            //信息
            String networkDesc = snmpGet(networkDescOID + "." + indexs.get(x));
            //多播发送包裹
            String networkSendPacket = snmpGet(networkRecievePacketOID + "." + indexs.get(x));
            //单播发送包裹
            String networkSendSPacket = snmpGet(networkSendSPacketOID + "." + indexs.get(x));
            //多播接收包裹
            String networkRevievePacket = snmpGet(networkSendPacketOID + "." + indexs.get(x));
            //单播接收包裹
            String networkRevieceSPacket = snmpGet(networkRecieveSPacketOID + "." + indexs.get(x));
            //总接收字节
            String networkRecieveByte = snmpGet(networkRecieveByteOID + "." + indexs.get(x));
            //总发送字节
            String networkSendByte = snmpGet(networkSendByteOID + "." + indexs.get(x));
            //发送丢失数目
            String networkSendDiscard = snmpGet(networkSendDiscardOID + "." + indexs.get(x));
            //接收丢失数目
            String networkReceiveDiscard = snmpGet(networkReceiveDiscardOID + "." + indexs.get(x));
            sSendPack += Long.parseLong(networkSendSPacket);
            mSendPack += Long.parseLong(networkSendPacket);
            sRecPack += Long.parseLong(networkRevieceSPacket);
            mRecPack += Long.parseLong(networkRevievePacket);
            sendSize += Long.parseLong(networkSendByte);
            recSize += Long.parseLong(networkRecieveByte);
            sendDiscard += Long.parseLong(networkSendDiscard);
            recDiscard += Long.parseLong(networkReceiveDiscard);

//            System.out.println(getChinese(networkDesc)  + ":类型：" + networkType + ";接收字节：" + networkRecieveByte + "；发送字节：" + networkSendByte +
//                    ";多播接收包裹：" + networkRevievePacket +";单播接收包裹:" + networkRevieceSPacket  + ";多播发送包裹：" + networkSendPacket + ";单播发送包裹：" + networkSendSPacket +
//                    ";接收丢失数目:" + networkReceiveDiscard + ";发送丢失数目：" + networkSendDiscard + ";带宽：" + networkSpeed);
        }
        NetInfo netInfo= new NetInfo();
        netInfo.setmRecPack(mRecPack);
        netInfo.setmSendPack(mSendPack);
        netInfo.setTime((new Date()).getTime());
        netInfo.setRecDiscard(recDiscard);
        netInfo.setSendDiscard(sendDiscard);
        netInfo.setsSendPack(sSendPack);
        netInfo.setsRecPack(sRecPack);
        netInfo.setSendSize(sendSize);
        netInfo.setRecSize(recSize);
        netInfo.setSendPack(mSendPack + sSendPack);
        netInfo.setRecPack(mRecPack + sRecPack);
        return netInfo;
    }
  
    /*  
     * 基础的CPU信息采集，只能采集到核数与每个CPU的  
     */  
    public CpuInfo getCpuInfo() throws Exception {
        String browseDeviceIndexOID = props.getDeviceIndex();
        String browseDeviceTypeOID = props.getDeviceType();
        String browseDeviceInfoOID = props.getDeviceInfo();
        String borwseDeviceLoadOID = props.getCpuCurrentLoadIndex();
        String cpuOID = props.getCpuID();
        ArrayList<String> rt = new ArrayList<String>();
        ArrayList<CpuInfo> cpuInfos = new ArrayList<CpuInfo>();
        int userRate = 0;  
        String cpuDesc = "";
        try {  
            ArrayList<String> deviceIndex = snmpWalk(browseDeviceIndexOID);
            // 因获取的CPU信息会有重覆，过滤掉一样的信息  
            boolean flag = true;  
            for (int i=0;i<deviceIndex.size();i++) {  
                String deviceType = snmpGet(browseDeviceTypeOID + "." + deviceIndex.get(i));
                if (deviceType.equals(cpuOID.trim())) {
                    String cpuInfo = snmpGet(browseDeviceInfoOID + "." + deviceIndex.get(i));
                    String loadCurrent = snmpGet(borwseDeviceLoadOID + "." + deviceIndex.get(i));
                    CpuInfo obj = new CpuInfo();  
                    obj.setCpuDesc(cpuInfo);  
                    obj.setUserRate(loadCurrent);  
                    if (flag) {  
                        int intelCpu = cpuInfo.indexOf("Intel");  
                        int amdCpu = cpuInfo.indexOf("AMD");  
  
                        if (intelCpu != -1) {  
                            cpuDesc = cpuInfo.substring(intelCpu);  
                        }else if (amdCpu != -1) {  
                            cpuDesc = cpuInfo.substring(amdCpu);  
                        }  
  
                        flag = false;  
                    }
                    userRate += Integer.parseInt(loadCurrent);
                    obj.setSysRate(loadCurrent);  
                    obj.setFreeRate(Integer.toString(100 - Integer.parseInt(loadCurrent)));
                    cpuInfos.add(obj);  
                }  
            }  
            // 重新组合成CpuInfo类  
//            for(String str:rt) {  
//                CpuInfo obj = new CpuInfo();  
//                obj.setCpuDesc(str);  
//                cpuInfos.add(obj);  
//            }
            int coreNum = this.getCpuCoreNum();
            userRate = userRate == 0 ?userRate:userRate/coreNum;
            CpuInfo result = new CpuInfo();
            result.setCpuDetailInfos(cpuInfos);
            result.setCpuDesc(cpuDesc);
            result.setSysRate(Integer.toString(userRate));
            result.setFreeRate(Integer.toString(100 - userRate));
            result.setCoreNum(coreNum);
            result.setTime((new Date()).getTime());
            return result;  
        } catch (Exception e) {
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    private int getCpuCoreNum() {  
        String cpuCoreOID = props.getCpuCurrentLoadIndex();
        ArrayList<String> result = snmpWalk(cpuCoreOID);
        return result.size();  
    }  
  
    public String getMemorySize(){
        String memorySizeOID = props.getMemoryTotalSize();
        try {
            return snmpGet(memorySizeOID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }  
  
    public MemoryInfo getMemoryInfo() throws Exception {
        MemoryInfo memoryInfo = new MemoryInfo();
        int index = this.getMemoryIndex();  

        double physicalSize = Double.parseDouble(snmpGet(props.getWindowDiskSize() + "." + index))
                * Double.parseDouble(snmpGet(props.getWindowDiskAmount() + "." + index)) /(1024*1024);
        double physicalUsedSize = Double.parseDouble(snmpGet(props.getWindowDiskUsed() + "." + index))
                * Double.parseDouble(snmpGet(props.getWindowDiskAmount() + "." + index)) /(1024*1024);
  
        memoryInfo.setMemorySize(String.valueOf(physicalSize));
        memoryInfo.setMemoryUsedSize(String.valueOf(physicalUsedSize));
        memoryInfo.setMemoryFreeSize(String.valueOf(physicalSize - physicalUsedSize));
        memoryInfo.setMemoryPercentage((physicalUsedSize/physicalSize * 100) + "");
        return memoryInfo;  
    }  
  
    private int getMemoryIndex() throws Exception {
        ArrayList<String> diskIndexTable = this.snmpWalk(props
                .getWindowDiskIndex());  
        String physicalMemoryID = props.getRamID();
        int index = 0;  
        int i = 1;  
        for (String str : diskIndexTable) {
            String diskType = this.snmpGet(props.getWindowDiskType() + "." + i);
            if (diskType.equals(physicalMemoryID.trim()))
                index = Integer.parseInt(str);
            i++;  
        }  
        return index;
    }
  
    public SystemInfo getSysInfo() throws Exception {
        SystemInfo sysInfo = new SystemInfo();  
        sysInfo.setSysDesc(snmpGet(props.getSysDesc()));  
        sysInfo.setSysContact(snmpGet(props.getSysContact()));  
        sysInfo.setSysName(snmpGet(props.getSysName()));  
        sysInfo.setSysUpTime(snmpGet(props.getSysUptime()));
        sysInfo.setSysLocation(snmpGet(props.getSysLocation()));  
        return sysInfo;  
    }  
  
    private ArrayList<String> getDiskIndex() throws Exception {
        String hrStorageFixedDisk = props.getHrStorageFixedDisk();
        ArrayList<String> diskIndexTable = this.snmpWalk(props
                .getWindowDiskIndex());  
        ArrayList<String> result = new ArrayList<String>();
        for (String str : diskIndexTable) {
            String diskType = this.snmpGet(props.getWindowDiskType() + "." + str);
            if (diskType.equals(hrStorageFixedDisk.trim()))
                result.add(str);  
        }  
        return result;
    }  
  
    /*  
     * 返回系统硬盘信息  
     * ArrayList<DiskInfo> 最后一个为硬盘的整个信息  
     * ArrayList value 硬盘信息 包括  
     */  
    public ArrayList<DiskInfo> getDiskInfo() throws Exception {
  
        ArrayList<String> index = this.getDiskIndex();
        ArrayList<DiskInfo> result = new ArrayList<DiskInfo>();
        for (int i=0;i<index.size();i++) {  
            DiskInfo obj = new DiskInfo();  
            obj.setDiskDesc(snmpGet(props.getWindowDiskDesc() + "." + index.get(i)));  
            double sSize = Double.parseDouble(snmpGet(props.getWindowDiskSize() + "." + index.get(i)))
                    * Double.parseDouble(snmpGet(props.getWindowDiskAmount() + "." + index.get(i)))
                    /(1024*1024 *1024);
            obj.setDiskSize(sSize+"");
            double usedSize = Double.parseDouble(snmpGet(props.getWindowDiskUsed() + "." + index.get(i)))
                    * Double.parseDouble(snmpGet(props.getWindowDiskAmount() + "." + index.get(i)))
                    /(1024*1024 * 1024);
            obj.setDiskUsedSize(usedSize + "");
            obj.setDiskFreeSize(String.valueOf(sSize - usedSize));
            obj.setPercentUsed((usedSize/sSize*100));
            result.add(obj);  
        }
        return result;  
    }

    public String getChinese(String octetString){
        try {
            String[] temps = octetString.split(":");
            byte[] bs = new byte[temps.length];
            for (int i = 0; i < temps.length; i++)
                bs[i] = (byte) Integer.parseInt(temps[i], 16);
            return new String(bs, "UTF-8");
        } catch (Exception e) {
            return octetString;
        }
    }

  
  
    public static void main(String[] args) {
        long prev = System.currentTimeMillis();
        SnmpBase snmp = new SnmpBase("172.16.10.52","161","public");
      //  SnmpBase snmp = new SnmpBase("172.16.10.50","public");
        try {
            snmp.snmpGet(".1.3.6.1.2.1.1.1.0");
        }catch (Exception e) {
            System.out.println("test");
            e.printStackTrace();  
        }finally {
            System.out.println(System.currentTimeMillis() - prev);
        }
    }


}  