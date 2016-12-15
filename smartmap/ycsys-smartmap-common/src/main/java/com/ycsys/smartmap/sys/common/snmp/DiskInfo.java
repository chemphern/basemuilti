package com.ycsys.smartmap.sys.common.snmp;

/**
     * Created by lixiaoxin on 16-12-14.
     * 硬盘信息基础类  
     */
    public class DiskInfo {
        private String diskName;         // 盘符
        private String diskLabel;        // 卷标名
        private String diskSN;           // 序列号
        private String diskSize;         // 硬盘容量
        private String diskFreeSize;     // 硬盘空闲容量
        private String diskUsedSize;     // 硬盘已用容量
        private double percentUsed;         // 硬盘已用百分比  
        private String diskDesc;         // 硬盘描述
      
        public String getDiskDesc() {
            return diskDesc;  
        }  
        public void setDiskDesc(String diskDesc) {
            this.diskDesc = diskDesc;  
        }  
        public String getDiskName() {
            return diskName;  
        }  
        public void setDiskName(String diskName) {
            this.diskName = diskName;  
        }  
        public String getDiskLabel() {
            return diskLabel;  
        }  
        public void setDiskLabel(String diskLabel) {
            this.diskLabel = diskLabel;  
        }  
        public String getDiskSN() {
            return diskSN;  
        }  
        public void setDiskSN(String diskSN) {
            this.diskSN = diskSN;  
        }  
        public String getDiskSize() {
            return diskSize;  
        }  
        public void setDiskSize(String diskSize) {
            this.diskSize = diskSize;  
        }  
        public String getDiskFreeSize() {
            return diskFreeSize;  
        }  
        public void setDiskFreeSize(String diskFreeSize) {
            this.diskFreeSize = diskFreeSize;  
        }  
        public String getDiskUsedSize() {
            return diskUsedSize;  
        }  
        public void setDiskUsedSize(String diskUsedSize) {
            this.diskUsedSize = diskUsedSize;  
        }  
        public double getPercentUsed() {  
            return percentUsed;  
        }  
        public void setPercentUsed(double percentUsed) {  
            this.percentUsed = percentUsed;  
        }

    @Override
    public String toString() {
        return "DiskInfo{" +
                "diskName='" + diskName + '\'' +
                ", diskLabel='" + diskLabel + '\'' +
                ", diskSN='" + diskSN + '\'' +
                ", diskSize='" + diskSize + '\'' +
                ", diskFreeSize='" + diskFreeSize + '\'' +
                ", diskUsedSize='" + diskUsedSize + '\'' +
                ", percentUsed=" + percentUsed +
                ", diskDesc='" + diskDesc + '\'' +
                '}';
    }
}