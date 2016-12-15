package com.ycsys.smartmap.sys.common.snmp;

/**
 * Created by lixiaoxin on 16-12-14.
 * 内存信息基本类  
 */  
public class MemoryInfo {  
    private String memorySize;               // 内存总大小    (单位MB)
    private String memoryFreeSize;           // 内存空闲量    (单位MB)
    private String memoryUsedSize;           // 内存使用量    (单位MB)
    private String memoryPercentage;        // 内存使用率
  
    public String getMemorySize() {
        return memorySize;  
    }  
    public void setMemorySize(String memorySize) {
        this.memorySize = memorySize;  
    }  
    public String getMemoryFreeSize() {
        return memoryFreeSize;  
    }  
    public void setMemoryFreeSize(String memoryFreeSize) {
        this.memoryFreeSize = memoryFreeSize;  
    }  
    public String getMemoryUsedSize() {
        return memoryUsedSize;  
    }  
    public void setMemoryUsedSize(String memoryUsedSize) {
        this.memoryUsedSize = memoryUsedSize;  
    }  
    public String getMemoryPercentage() {
        return memoryPercentage;  
    }  
    public void setMemoryPercentage(String memoryPercentage) {
        this.memoryPercentage = memoryPercentage;  
    }

    @Override
    public String toString() {
        return "MemoryInfo{" +
                "memorySize='" + memorySize + '\'' +
                ", memoryFreeSize='" + memoryFreeSize + '\'' +
                ", memoryUsedSize='" + memoryUsedSize + '\'' +
                ", memoryPercentage='" + memoryPercentage + '\'' +
                '}';
    }
}