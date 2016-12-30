package com.ycsys.smartmap.sys.common.enums;

/**
 * 异常级别枚举
 * Created by lixiaoxin on 2016/12/26.
 */
public enum ExceptionLevel {
    /**普通**/
    PRIMARY(1),
    /**严重**/
    SERIOUS(2);

    private final int value;

    private ExceptionLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static ExceptionLevel findByValue(int value){
        switch(value){
            case 1:
                return PRIMARY;
            case 2:
                return SERIOUS;
            default:
                return null;
        }
    }
}
