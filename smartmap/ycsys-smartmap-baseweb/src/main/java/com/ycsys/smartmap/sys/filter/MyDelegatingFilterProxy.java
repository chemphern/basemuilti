package com.ycsys.smartmap.sys.filter;

import com.ycsys.smartmap.sys.util.ActionContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 代理链
 * Created by lixiaoxin on 2017/1/4.
 */
public class MyDelegatingFilterProxy extends DelegatingFilterProxy {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Map<String,Object> map = new HashMap<>();
        map.put("request", request);
        map.put("response", response);
        ActionContext context = new ActionContext(map);
        ActionContext.setContext(context);
        super.doFilter(request, response, filterChain);
    }
}
