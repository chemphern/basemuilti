package com.ycsys.smartmap.sys.common.exception;

/**
 * 业务逻辑层异常
 * Created by lixiaoxin on 2016/12/23.
 */
public class ServiceException extends RuntimeException{
    private String code;
    private String msg;

    public ServiceException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public ServiceException(String msg){
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
