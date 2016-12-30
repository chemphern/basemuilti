package com.ycsys.smartmap.sys.common.exception;

/**
 * Dao层异常
 * Created by lixiaoxin on 2016/12/23.
 */
public class DaoException extends RuntimeException{
    private String code;
    private String msg;

    public DaoException(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
    public DaoException(String msg){
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
