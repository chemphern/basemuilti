package com.ycsys.smartmap.sys.common.exception;

/**
 * @功能名 业务异常类
 * @类描述 通用业务异常
 * 
 * @author lixiaoxin
 * @date 2016-11-15
 */
public class SysException extends RuntimeException {

	private static final long serialVersionUID = -4788824259324634599L;

    private String code ;

    private String msg;

	public SysException(String message) {
		super(message);
	}

	public SysException(String message, Exception cause) {
		super(message, cause);
	}

    public SysException(String code , String message, Exception cause) {
        super(message, cause);
        this.code = code;
        this.msg = message;
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
