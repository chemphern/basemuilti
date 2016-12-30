package com.ycsys.smartmap.sys.task.object;

import com.ycsys.smartmap.sys.common.config.parseobject.tomcat.TomcatStatusObject;
import com.ycsys.smartmap.sys.common.snmp.CpuInfo;
import com.ycsys.smartmap.sys.common.snmp.NetAnalyzeInfo;
import com.ycsys.smartmap.sys.common.snmp.NetInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixiaoxin on 2016/12/14.
 */
public class MonitorConstant {
    private Map<String, List<CpuInfo>> cpuInfoMap;
    private Map<String, List<NetAnalyzeInfo>> netAnalyzeMap;
    private Map<String, NetInfo[]> netInfosMap;
    private Map<String, List<TomcatStatusObject>> tomcatStatusMap;
    private static MonitorConstant monitorConstant = new MonitorConstant();

    private MonitorConstant() {

    }

    public static MonitorConstant getInstance() {
        return monitorConstant;
    }

    public List<CpuInfo> getCpuInfoList(String key) {
        if (cpuInfoMap == null) {
            cpuInfoMap = new HashMap<>();
        }
        List<CpuInfo> cpuInfoList = cpuInfoMap.get(key);
        if (cpuInfoList == null) {
            cpuInfoList = new ArrayList<>();
            cpuInfoMap.put(key, cpuInfoList);
        }
        return cpuInfoList;
    }

    public void setCpuInfoList(String key, List<CpuInfo> cpuInfoList) {
        this.cpuInfoMap.put(key, cpuInfoList);
    }

    public List<NetAnalyzeInfo> getNetAnalyzeList(String key) {
        if (netAnalyzeMap == null) {
            netAnalyzeMap = new HashMap<>();
        }
        List<NetAnalyzeInfo> netAnalyzeInfoList = netAnalyzeMap.get(key);
        if (netAnalyzeInfoList == null) {
            netAnalyzeInfoList = new ArrayList<>();
            netAnalyzeMap.put(key, netAnalyzeInfoList);
        }
        return netAnalyzeInfoList;
    }

    public void setNetAnalyzeList(String key, List<NetAnalyzeInfo> netAnalyzeList) {
        this.netAnalyzeMap.put(key, netAnalyzeList);
    }

    public NetInfo[] getNetInfos(String key) {
        if (netInfosMap == null) {
            netInfosMap = new HashMap<>();
        }
        NetInfo[] netInfos = netInfosMap.get(key);
        if (netInfos == null) {
            netInfos = new NetInfo[1];
            netInfosMap.put(key, netInfos);
        }
        return netInfos;
    }

    public void setNetInfos(String key, NetInfo[] netInfos) {
        this.netInfosMap.put(key, netInfos);
    }

    public List<TomcatStatusObject> getTomcatStatusList(String key) {
        if (tomcatStatusMap == null) {
            tomcatStatusMap = new HashMap<>();
        }
        List<TomcatStatusObject> tomcatStatusList = tomcatStatusMap.get(key);
        if (tomcatStatusList == null) {
            tomcatStatusList = new ArrayList<>();
            tomcatStatusMap.put(key, tomcatStatusList);
        }
        return tomcatStatusList;
    }

    public void setTomcatStatusList(String key, List<TomcatStatusObject> tomcatStatusList) {
        this.tomcatStatusMap.put(key, tomcatStatusList);
    }

    public Map<String, List<CpuInfo>> getCpuInfoMap() {
        if (cpuInfoMap == null) {
            cpuInfoMap = new HashMap<>();
        }
        return cpuInfoMap;
    }

    public void setCpuInfoMap(Map<String, List<CpuInfo>> cpuInfoMap) {
        this.cpuInfoMap = cpuInfoMap;
    }

    public Map<String, List<NetAnalyzeInfo>> getNetAnalyzeMap() {
        if (netAnalyzeMap == null) {
            netAnalyzeMap = new HashMap<>();
        }
        return netAnalyzeMap;
    }

    public void setNetAnalyzeMap(Map<String, List<NetAnalyzeInfo>> netAnalyzeMap) {
        this.netAnalyzeMap = netAnalyzeMap;
    }

    public Map<String, NetInfo[]> getNetInfosMap() {
        if (netInfosMap == null) {
            netInfosMap = new HashMap<>();
        }
        return netInfosMap;
    }

    public void setNetInfosMap(Map<String, NetInfo[]> netInfosMap) {
        this.netInfosMap = netInfosMap;
    }

    public Map<String, List<TomcatStatusObject>> getTomcatStatusMap() {
        if (tomcatStatusMap == null) {
            tomcatStatusMap = new HashMap<>();
        }
        return tomcatStatusMap;
    }

    public void setTomcatStatusMap(Map<String, List<TomcatStatusObject>> tomcatStatusMap) {
        this.tomcatStatusMap = tomcatStatusMap;
    }
}
