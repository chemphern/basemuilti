package com.ycsys.smartmap.sys.vo;

import java.sql.Timestamp;

/**
 * 消息实体
 * Created by lixiaoxin on 2017/1/13.
 */
public class NoticeVo {
    private Integer id;
    private String title;
    private String content;
    private String types;
    private String statuss;
    private int type;
    private int status;
    private long sendNum;
    private Timestamp createTime;

    public NoticeVo(){

    }
    public NoticeVo(Integer id,String title,String content,int type,int status,Timestamp createTime){
        this.id = id;
        this.title = title;
        this.content = content;
        this.type = type;
        this.status = status;
        this.createTime = createTime;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    public String getStatuss() {
        return statuss;
    }

    public void setStatuss(String statuss) {
        this.statuss = statuss;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getSendNum() {
        return sendNum;
    }

    public void setSendNum(long sendNum) {
        this.sendNum = sendNum;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
