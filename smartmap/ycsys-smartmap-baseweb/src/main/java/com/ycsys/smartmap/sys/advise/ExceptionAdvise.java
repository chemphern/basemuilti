package com.ycsys.smartmap.sys.advise;

import com.ycsys.smartmap.monitor.entity.Alarm;
import com.ycsys.smartmap.sys.common.enums.ExceptionClass;
import com.ycsys.smartmap.sys.common.enums.ExceptionLevel;
import com.ycsys.smartmap.sys.common.exception.*;
import com.ycsys.smartmap.sys.service.AlarmService;
import com.ycsys.smartmap.sys.util.SpringContextHolder;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 全局异常捕获切面
 * Created by lixiaoxin on 2016/12/26.
 */
public class ExceptionAdvise {
    private Logger logger = LoggerFactory.getLogger(ExceptionAdvise.class);

    /**
     * 业务层方法出现异常，记录日志
     */
    public void error(JoinPoint joinPoint, Throwable ex) {
        logger.info("出现异常的方法名：【" + joinPoint.getSignature().getName() + "】");
        String err = ex.getClass().getName();
        //Gis异常
        PlatException platException = null;
        int type;
        if (err.equals(ExceptionClass.GisServerException.getValue())) {
            platException = (GisServerException) ex;
            type = ExceptionClass.GisServerException.getType();
            //应用服务器异常
        } else if (err.equals(ExceptionClass.ASException.getValue())) {
            platException= (ASException) ex;
            type = ExceptionClass.ASException.getType();
            //数据库异常
        } else if (err.equals(ExceptionClass.DbException.getValue())) {
            platException = (DbException) ex;
            type = ExceptionClass.DbException.getType();
            //网络异常
        } else if (err.equals(ExceptionClass.NetworkException.getValue())) {
            platException = (NetworkException) ex;
            type = ExceptionClass.NetworkException.getType();
            //服务异常
        } else if (err.equals(ExceptionClass.ServerException.getValue())) {
            platException = (ServerException) ex;
            type = ExceptionClass.ServerException.getType();
            //业务逻辑层异常
        } else if (err.equals(ExceptionClass.ServiceException.getValue())) {
            platException = (ServiceException) ex;
            type = ExceptionClass.ServiceException.getType();
            //系统异常
        } else if (err.equals(ExceptionClass.SysException.getValue())) {
            SysException sysEx = (SysException) ex;
            platException = new ServerException(sysEx.getMsg());
            type = ExceptionClass.SysException.getType();
            //hibernate异常
        } else if (err.equals(ExceptionClass.HServiceException.getValue())) {
            org.hibernate.service.spi.ServiceException hex = (org.hibernate.service.spi.ServiceException) ex;
            platException = new ServerException(hex.getMessage());
            type = ExceptionClass.HServiceException.getType();
            //其他异常
        } else {
            Exception eex = (Exception) ex;
            platException = new ServerException("程序发生异常",eex.getMessage(), ExceptionLevel.PRIMARY.getValue(),"");
            type = ExceptionClass.Other.getType();
        }
        if(platException != null){
            saveException(platException,type);
        }
    }

    private void saveException(PlatException ex,int type){
        AlarmService alarmService = SpringContextHolder.getBean("alarmService");
        Alarm alarm = new Alarm();
        alarm.setContent(ex.getContents());
        alarm.setGrade(ex.getLevel() + "");
        Date now = new Date();
        alarm.setCreateDate(now);
        alarm.setTitle(ex.getTitle());
        alarm.setHappenDate(now);
        alarm.setStatus("0");
        alarm.setRemarks(ex.getRemark());
        alarm.setType(type + "");
        alarmService.save(alarm);
    }

}
