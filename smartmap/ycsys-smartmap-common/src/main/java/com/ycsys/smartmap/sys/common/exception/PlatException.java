package com.ycsys.smartmap.sys.common.exception;

/**
 * Created by lixiaoxin on 2016/12/26.
 */
public interface PlatException{
    String getCode();
     String getContents();
    String getRemark();
    int getLevel();
    String getTitle();
}
