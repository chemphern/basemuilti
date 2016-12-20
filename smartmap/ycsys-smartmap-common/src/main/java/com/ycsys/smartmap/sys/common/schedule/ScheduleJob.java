package com.ycsys.smartmap.sys.common.schedule;

import java.util.Date;

/**
 *  
* 定时任务信息实体
 *
 * 定时任务主要是根据jobName和jobGroup进行区分的
 * 包装真实的定时任务实现时，可以使用springId去获取bean也可以通过指定beanClass 完整路径：包名+类名
 * 真实的定时任务通过反射调用，因此里面不能使用spring注入，只能通过spring的工具类获取需要的bean
 * Concurent有两个值，一个允许并发，一个不允许并发，区别是不允许并发必须等上个定时任务执行完才会执行下一个
 *
* @author lixiaoxin
* @date 2016年12月15日
 */  
public class ScheduleJob {

    public static final String STATUS_RUNNING = "1";
    public static final String STATUS_NOT_RUNNING = "0";

    public static final String CONCURRENT_IS = "1";  
    public static final String CONCURRENT_NOT = "0";
    private Date createTime;
  
    private Date updateTime;  
    /** 
     * 任务名称 
     */  
    private String jobName;  
    /** 
     * 任务分组 
     */  
    private String jobGroup;
    /**
     * 任务状态 是否启动任务
     */
    private String jobStatus = STATUS_NOT_RUNNING;
    /** 
     * cron表达式 
     */  
    private String cronExpression;  
    /** 
     * 描述 
     */  
    private String description;  
    /** 
     * 任务执行时调用哪个类的方法 包名+类名 
     */  
    private String beanClass;  
    /** 
     * 任务是否有状态 
     */  
    private String isConcurrent;  
    /** 
     * spring bean 
     */  
    private String springId;  
    /** 
     * 任务调用的方法名 
     */  
    private String methodName;

    /**
     * 调用的方法的参数(里面传Class<T> </>)
     * **/
    private Object[] methodParameter;

    /**
     * 调用方法的参数
     * **/
    private Object[] args;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public String getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getSpringId() {
        return springId;
    }

    public void setSpringId(String springId) {
        this.springId = springId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getMethodParameter() {
        return methodParameter;
    }

    public void setMethodParameter(Object[] methodParameter) {
        this.methodParameter = methodParameter;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}