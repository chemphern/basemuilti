package com.ycsys.smartmap.sys.common.exception;

/**
 * 服务异常类
 * Created by lixiaoxin on 2016/12/23.
 */
public class ServerException extends RuntimeException implements PlatException{
    private String code;
    private String title;
    private String contents;
    private String remark;
    private int level;

    public ServerException(String title,String contents,int level,String remark){
        this.title = title;
        this.contents = contents;
        this.remark = remark;
        this.level = level;
    }
    public ServerException(String title,int level){
        this.level = level;
        this.title = title;
    }
    public ServerException(String title){
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
