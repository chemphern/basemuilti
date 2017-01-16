package com.ycsys.smartmap.sys.common.exception;

import com.ycsys.smartmap.sys.common.enums.ExceptionLevel;

/**
 * 业务逻辑层异常
 * Created by lixiaoxin on 2016/12/23.
 */
public class ServiceException extends RuntimeException implements PlatException{
    private String code;
    private String title;
    private String contents;
    private String remark;
    private int level;

    public ServiceException(String title,String contents){
        this.title = title;
        this.contents = contents;
        this.level = ExceptionLevel.PRIMARY.getValue();
    }

    public ServiceException(String title,Exception e){
        this.title = title;
        this.contents = e.getMessage();
        this.level = ExceptionLevel.PRIMARY.getValue();
    }
    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
