package com.ycsys.smartmap.sys.vo;

/**
 * Created by Administrator on 2017/1/13.
 */
public class TreeVo {
    private String id;
    private String text;
    private String pid;
    private String type;

    public TreeVo(){}
    public TreeVo(String id,String text,String pid,String type){
        this.id = id;
        this.text = text;
        this.pid = pid;
        this.type = type;
    }
    public TreeVo(String id,String text,String pid){
        this.id = id;
        this.text = text;
        this.pid = pid;
    }
    public TreeVo(String id,String text){
        this.id = id;
        this.text = text;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPid() {
        if(pid == null){
            pid = "";
        }
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getType() {
        if(type == null){
            type = "";
        }
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
