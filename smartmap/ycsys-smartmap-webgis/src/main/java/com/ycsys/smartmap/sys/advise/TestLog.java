package com.ycsys.smartmap.sys.advise;

import com.alibaba.fastjson.JSON;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2016/11/7.
 */
public class TestLog implements MethodInterceptor {
    protected Logger logger = Logger.getLogger(MethodInterceptor.class);

    public Object invoke(MethodInvocation invocation) throws Throwable {
        System.out.println("==================================测试=======================================");
        System.out.println("拦截service名称：" + invocation.getMethod().getName());
        //logger.info("Before: interceptor name: {}", ));
        Object [] os = invocation.getArguments();
        for(int x = 0 ;x<os.length;x++) {
            System.out.println(JSON.toJSONString(os[x]));
        }
        System.out.println("==============result==================");

        //logger.info("Arguments: {}", );

        Object result = invocation.proceed();
        System.out.println(result);
        System.out.println(JSON.toJSONString(result));
        //logger.info("After: result: {}", result);
        System.out.println("=====================================");
        return result;
    }
}
