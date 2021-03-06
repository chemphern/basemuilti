package com.ycsys.smartmap.sys.common.schedule;

import com.ycsys.smartmap.sys.util.SpringContextHolder;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TaskUtils {
	private static final org.slf4j.Logger log = LoggerFactory.getLogger(TaskUtils.class);

	/**
	 * 通过反射调用scheduleJob中定义的方法
	 * 
	 * @param scheduleJob
	 */
	public static void invokMethod(ScheduleJob scheduleJob) {
		Object object = null;
		Class clazz = null;
		if (StringUtils.isNotBlank(scheduleJob.getSpringId())) {
			object = SpringContextHolder.getBean(scheduleJob.getSpringId());
		} else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
			try {
				clazz = Class.forName(scheduleJob.getBeanClass());
				object = clazz.newInstance();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (object == null) {
			log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，请检查是否配置正确！！！");
			return;
		}
		clazz = object.getClass();
		Method method = null;
		try {
			//根据方法名称和方法参数找到该方法
			Object [] methodParameter = scheduleJob.getMethodParameter();
			if(methodParameter == null) {
				method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
			}else {
				Class [] paramClass = new Class[methodParameter.length];
				for(int x = 0 ;x<methodParameter.length;x++){
					Class c = (Class) methodParameter[x];
					paramClass[x] = c;
				}
				method = clazz.getDeclaredMethod(scheduleJob.getMethodName(),paramClass);
			}
		} catch (NoSuchMethodException e) {
			log.error("任务名称 = [" + scheduleJob.getJobName() + "]---------------未启动成功，方法名设置错误！！！");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (method != null) {
			try {
				method.invoke(object,scheduleJob.getArgs());
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
