package com.ycsys.smartmap.sys.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.Map;

/**
 * Created by lixiaoxin on 2017/1/4.
 */
public class ActionContext implements Serializable{
    private static final long serialVersionUID = 1L;

    private static ThreadLocal<ActionContext> actionContext = new ThreadLocal();

    private Map<String, Object> context;

    public ActionContext(Map<String, Object> context)
    {
        this.context = context;
    }

    public static ActionContext getContext() {
        return (ActionContext)actionContext.get();
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest)this.context.get("request");
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse)this.context.get("response");
    }

    public static void setContext(ActionContext context) {
        actionContext.set(context);
    }

    public Map<String, Object> getContextMap() {
        return this.context;
    }

}
