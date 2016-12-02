package com.ycsys.smartmap.sys.advise;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 * Created by lixiaoxin on 2016/11/4.
 */
//@Component
//@Aspect
public class LogAdvise {
    private Logger logger = LoggerFactory.getLogger(LogAdvise.class);
    /** 开始的毫秒数 */
    private long beginTimeMillis;
    /** 结束的毫秒数 */
    private long endTimeMillis;
    /** 在调用业务层方法之前  */
    //@Before("execution(* com.ycsys.smartmap.sys.service.impl.UserServiceImpl.*(..))")
    public void invokeBefore(JoinPoint joinPoint){
        logger.info("开始调用的业务层方法名：" + joinPoint.getSignature().getName());
        System.out.println("before");
        beginTimeMillis =  System.currentTimeMillis();
        logger.info("开始调用的时间毫秒数：" + beginTimeMillis);
    }

    /** 在调用业务层方法之后  */
    //@AfterReturning(pointcut="execution(* com.ycsys.smartmap.sys.service.impl.UserServiceImpl.*(..))", returning="returnObj")
    public void invokeAfter(JoinPoint joinPoint, Object returnObj){
        logger.info("结束调用的业务层方法名：" + joinPoint.getSignature().getName());
        logger.info("调用【" + joinPoint.getSignature().getName() + "】方法，返回值：" + returnObj);
        System.out.println("after");
        endTimeMillis = System.currentTimeMillis();
        logger.info("调用【" +joinPoint.getSignature().getName() +"】方法，一共花费了" + (endTimeMillis - beginTimeMillis) + "毫秒!" );
    }

    /** 业务层方法出现异常，记录日志 */
    //@AfterThrowing(pointcut="@annotation(com.ycsys.smartmap.sys.common.annotation.ToLog) and execution(* com.ycsys.smartmap..service.impl.*.*(..))", throwing="ex")
    public void error(JoinPoint joinPoint, Throwable ex){
        logger.info("出现异常的方法名：【" + joinPoint.getSignature().getName() + "】");
        logger.error("调用该方法【" + joinPoint.getSignature().getName() + "】的异常信息", ex);
    }

}
