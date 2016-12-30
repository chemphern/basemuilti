package com.ycsys.smartmap.sys.advise;

import com.ycsys.smartmap.sys.common.enums.ExceptionClass;
import com.ycsys.smartmap.sys.common.exception.*;
import com.ycsys.smartmap.sys.common.exception.ServiceException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.service.spi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 日志切面
 * Created by lixiaoxin on 2016/11/4.
 */
public class LogAdvise {
    private Logger logger = LoggerFactory.getLogger(LogAdvise.class);
    /**
     * 开始的毫秒数
     */
    private long beginTimeMillis;
    /**
     * 结束的毫秒数
     */
    private long endTimeMillis;

    /**
     * 在调用业务层方法之前
     */
    public void invokeBefore(JoinPoint joinPoint) {
        logger.info("开始调用的业务层方法名：" + joinPoint.getSignature().getName());
        System.out.println("before");
        beginTimeMillis = System.currentTimeMillis();
        logger.info("开始调用的时间毫秒数：" + beginTimeMillis);
    }

    /**
     * 在调用业务层方法之后
     */
    public void invokeAfter(JoinPoint joinPoint, Object returnObj) {
        logger.info("结束调用的业务层方法名：" + joinPoint.getSignature().getName());
        logger.info("调用【" + joinPoint.getSignature().getName() + "】方法，返回值：" + returnObj);
        System.out.println("after");
        endTimeMillis = System.currentTimeMillis();
        logger.info("调用【" + joinPoint.getSignature().getName() + "】方法，一共花费了" + (endTimeMillis - beginTimeMillis) + "毫秒!");
    }

    /**
     * 业务层方法出现异常，记录日志
     */
    public void error(JoinPoint joinPoint, Throwable ex) {
        logger.info("出现异常的方法名：【" + joinPoint.getSignature().getName() + "】");
        String err = ex.getClass().getName();
//        //Gis异常
//        if (err.equals(ExceptionClass.GisServerException.getValue())) {
//            GisServerException gex = (GisServerException) ex;
//            //应用服务器异常
//        } else if (err.equals(ExceptionClass.ASException.getValue())) {
//            ASException asex = (ASException) ex;
//            //数据库异常
//        } else if (err.equals(ExceptionClass.DbException.getValue())) {
//            DbException dbex = (DbException) ex;
//            //网络异常
//        } else if (err.equals(ExceptionClass.NetworkException.getValue())) {
//            NetworkException nwex = (NetworkException) ex;
//            //服务异常
//        } else if (err.equals(ExceptionClass.ServerException.getValue())) {
//            ServerException sex = (ServerException) ex;
//            //业务逻辑层异常
//        } else if (err.equals(ExceptionClass.ServiceException.getValue())) {
//            ServiceException serviceEx = (ServiceException) ex;
//            //系统异常
//        } else if (err.equals(ExceptionClass.SysException.getValue())) {
//            SysException sysEx = (SysException) ex;
//            //hibernate异常
//        } else if (err.equals(ExceptionClass.HServiceException.getValue())) {
//            org.hibernate.service.spi.ServiceException hex = (org.hibernate.service.spi.ServiceException) ex;
//            //其他异常
//        } else {
//            Exception eex = (Exception) ex;
//        }
        //ex.get
    }

}
