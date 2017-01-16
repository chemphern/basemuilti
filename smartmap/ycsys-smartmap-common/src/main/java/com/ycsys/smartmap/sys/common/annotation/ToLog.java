package com.ycsys.smartmap.sys.common.annotation;

import com.ycsys.smartmap.sys.common.enums.LogType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志注解
 * Created by lixiaoxin on 2016/11/3.
 */
@Target({java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ToLog {
    /**操作名称**/
    public abstract String name();

    /***备注*/
    public abstract String remark() default "正常";

    /**日志类型**/
    public abstract LogType type();

}
