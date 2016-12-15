package com.ycsys.smartmap.sys.common.snmp;

/**
 * Created by Administrator on 2016/12/13.
 */
public class NetInfo {
    private long mSendPack;//多播发送包裹
    private long sSendPack;//单播发送包裹
    private long mRecPack;//多播接收包裹
    private long sRecPack;//单播接收包裹
    private long sendSize;//总计发送大小
    private long recSize;//总计接收大小
    private long sendDiscard;//发送丢失数目
    private long recDiscard;//接收丢失数目
    private long sendPack;//总共发送包裹
    private long recPack;//总共接收包裹
    private long time;//时间

    public long getmSendPack() {
        return mSendPack;
    }

    public void setmSendPack(long mSendPack) {
        this.mSendPack = mSendPack;
    }

    public long getsSendPack() {
        return sSendPack;
    }

    public void setsSendPack(long sSendPack) {
        this.sSendPack = sSendPack;
    }

    public long getmRecPack() {
        return mRecPack;
    }

    public void setmRecPack(long mRecPack) {
        this.mRecPack = mRecPack;
    }

    public long getsRecPack() {
        return sRecPack;
    }

    public void setsRecPack(long sRecPack) {
        this.sRecPack = sRecPack;
    }

    public long getSendSize() {
        return sendSize;
    }

    public void setSendSize(long sendSize) {
        this.sendSize = sendSize;
    }

    public long getRecSize() {
        return recSize;
    }

    public void setRecSize(long recSize) {
        this.recSize = recSize;
    }

    public long getSendDiscard() {
        return sendDiscard;
    }

    public void setSendDiscard(long sendDiscard) {
        this.sendDiscard = sendDiscard;
    }

    public long getRecDiscard() {
        return recDiscard;
    }

    public void setRecDiscard(long recDiscard) {
        this.recDiscard = recDiscard;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public long getSendPack() {
        return sendPack;
    }

    public void setSendPack(long sendPack) {
        this.sendPack = sendPack;
    }

    public long getRecPack() {
        return recPack;
    }

    public void setRecPack(long recPack) {
        this.recPack = recPack;
    }

    @Override
    public String toString() {
        return "NetInfo{" +
                "mSendPack=" + mSendPack +
                ", sSendPack=" + sSendPack +
                ", mRecPack=" + mRecPack +
                ", sRecPack=" + sRecPack +
                ", sendSize=" + sendSize +
                ", recSize=" + recSize +
                ", sendDiscard=" + sendDiscard +
                ", recDiscard=" + recDiscard +
                ", sendPack=" + sendPack +
                ", recPack=" + recPack +
                ", time=" + time +
                '}';
    }
}
