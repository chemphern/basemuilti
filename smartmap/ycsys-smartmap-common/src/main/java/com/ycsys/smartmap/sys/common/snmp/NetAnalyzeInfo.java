package com.ycsys.smartmap.sys.common.snmp;

/**
 * 流量分析后的数据实体
 * Created by lixiaoxin on 2016/12/14.
 */
public class NetAnalyzeInfo extends NetInfo{
    private long outkbps;//出网kbps
    private long inkbps;//入网kbps
    private long persendpack;//每秒发送包裹数
    private long perrecpack;//每秒接收包裹数

    public long getOutkbps() {
        return outkbps;
    }

    public void setOutkbps(long outkbps) {
        this.outkbps = outkbps;
    }

    public long getInkbps() {
        return inkbps;
    }

    public void setInkbps(long inkbps) {
        this.inkbps = inkbps;
    }

    public long getPersendpack() {
        return persendpack;
    }

    public void setPersendpack(long persendpack) {
        this.persendpack = persendpack;
    }

    public long getPerrecpack() {
        return perrecpack;
    }

    public void setPerrecpack(long perrecpack) {
        this.perrecpack = perrecpack;
    }
    public void setNetInfo(NetInfo netInfo){
        this.setRecPack(netInfo.getRecPack());
        this.setSendSize(netInfo.getSendSize());
        this.setSendPack(netInfo.getSendPack());
        this.setRecSize( netInfo.getRecSize());
        this.setmRecPack(netInfo.getmRecPack());
        this.setmSendPack(netInfo.getmSendPack());
        this.setRecDiscard(netInfo.getRecDiscard());
        this.setSendDiscard(netInfo.getSendDiscard());
        this.setsRecPack(netInfo.getsRecPack());
        this.setTime(netInfo.getTime());
        this.setsSendPack( netInfo.getsSendPack());
    }
}
