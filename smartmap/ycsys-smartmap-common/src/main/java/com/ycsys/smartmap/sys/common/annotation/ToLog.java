package com.ycsys.smartmap.sys.common.annotation;

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
    public abstract String remark() default "";

    /**日志类型**/
    public abstract String type();

}
