package com.ycsys.smartmap.sys.advise;

import com.alibaba.fastjson.JSONObject;
import com.ycsys.smartmap.sys.common.annotation.ToLog;
import com.ycsys.smartmap.sys.common.config.Global;
import com.ycsys.smartmap.sys.entity.Log;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.LogService;
import com.ycsys.smartmap.sys.util.ActionContext;
import com.ycsys.smartmap.sys.util.NetWorkUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 日志切面
 * Created by lixiaoxin on 2016/11/4.
 */
public class LogAdvise {

    private Logger logger = LoggerFactory.getLogger(LogAdvise.class);

    @Resource
    private LogService logService;

    public Object invoke(ProceedingJoinPoint point) throws Throwable {
        long prev = System.currentTimeMillis();
        Object[] args = point.getArgs();
        ToLog toLog = null;
        Object ret = null;
        try {
            try {
                MethodSignature s = (MethodSignature) point.getSignature();
                Class[] types = s.getMethod().getParameterTypes();
                Class clazz = point.getTarget().getClass();
                Method method = clazz.getMethod(point.getSignature().getName(), types);
                toLog = method.getAnnotation(ToLog.class);
            } catch (Exception e) {
                logger.error("初始化日志对象失败", e);
            }
            ret = point.proceed(args);
        } catch (Throwable e) {
            throw e;
        }
            long end = System.currentTimeMillis();
            long usedTime = end - prev;
            try {
                doLog(toLog.name(), toLog.type().getType(), 1, toLog.remark(), usedTime, ret);
            }catch (Exception e){
                logger.error("记录日志失败！",e);
            }
            return ret;
    }

    /**
     * 日志记录
     **/
    private void doLog(String name, int type, int status, String remark, long usedTime, Object ret) {
        ActionContext context = ActionContext.getContext();
        HttpServletRequest request = context.getRequest();
        User user = (User) request.getSession().getAttribute(Global.SESSION_USER);
        String ip = NetWorkUtil.getIpAddress(request);
        Log log = new Log();
        log.setRequestIp(ip);
        log.setCreateTime(new Date());
        log.setOperationName(name);
        log.setOperationType(type);
        log.setRemark(remark);
        String request_params = JSONObject.toJSONString(request.getParameterMap());
        log.setRequest_params(request_params);
        log.setResponse_params(JSONObject.toJSONString(ret));
        log.setStatus(status);
        log.setUsername(user.getName());
        log.setUser(user);
        log.setUsedTime(usedTime);
        //LogService logService = SpringContextHolder.getBean("logService");
        logService.save(log);
    }


}
