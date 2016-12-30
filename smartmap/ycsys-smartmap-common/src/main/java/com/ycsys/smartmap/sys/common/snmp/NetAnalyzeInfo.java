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
    private long durationSendPack;//一段时间内发送包裹数
    private long durationRecPack;//一段时间内接受包裹数
    private long durationSendByte;//一段时间内发送字节数
    private long durationRecByte;//一段时间内接受字节数

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

    public long getDurationSendPack() {
        return durationSendPack;
    }

    public void setDurationSendPack(long durationSendPack) {
        this.durationSendPack = durationSendPack;
    }

    public long getDurationRecPack() {
        return durationRecPack;
    }

    public void setDurationRecPack(long durationRecPack) {
        this.durationRecPack = durationRecPack;
    }

    public long getDurationSendByte() {
        return durationSendByte;
    }

    public void setDurationSendByte(long durationSendByte) {
        this.durationSendByte = durationSendByte;
    }

    public long getDurationRecByte() {
        return durationRecByte;
    }

    public void setDurationRecByte(long durationRecByte) {
        this.durationRecByte = durationRecByte;
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
