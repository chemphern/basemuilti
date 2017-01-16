package com.ycsys.smartmap.sys;

import com.ycsys.smartmap.service.entity.Service;
import com.ycsys.smartmap.service.service.ServiceService;
import com.ycsys.smartmap.sys.common.config.parseobject.org.OrgRootXmlObject;
import com.ycsys.smartmap.sys.common.config.parseobject.permission.PermissionXmlObject;
import com.ycsys.smartmap.sys.common.config.parseobject.role.RoleRootXmlObject;
import com.ycsys.smartmap.sys.common.config.parseobject.system.SystemRootXmlObject;
import com.ycsys.smartmap.sys.common.config.parseobject.user.UserRootXmlObject;
import com.ycsys.smartmap.sys.common.schedule.CronUtil;
import com.ycsys.smartmap.sys.common.schedule.JobTaskManager;
import com.ycsys.smartmap.sys.common.schedule.ScheduleJob;
import com.ycsys.smartmap.sys.common.utils.DbMonitorUtil;
import com.ycsys.smartmap.sys.common.utils.HttpSocketUtil;
import com.ycsys.smartmap.sys.common.utils.MonitorUtil;
import com.ycsys.smartmap.sys.service.*;
import com.ycsys.smartmap.sys.util.XmlParseObjectUtil;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.InputStream;
import java.util.*;

/**
 * 
 * 类名称：Initialization <br />
 * 类描述：initDatabase 初始化系统参数 <br />
 * 
 * @version
 */
@Component
public class Initialization implements ServletContextAware {
	
	ServletContext context;
	@Autowired
	UserService userService;

	@Autowired
	PermissionService permissionService;

	@Autowired
	RoleService roleService;

	@Autowired
	SystemService systemService;

	@Autowired
	AreaService areaService;

	@Autowired
	DictService dictService;

	@Autowired
	OrganizationService organizationService;

	@Autowired
	private ServiceService serviceService;

	@Autowired
	private JobTaskManager jobTaskManager;

	@Resource(name="config")
	private Properties config;

	private static final Logger logger = LoggerFactory.getLogger(Initialization.class);

	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	@PostConstruct
	public void init() {
		logger.info("========================初始化数据库开始==================================");
		initDatabase();
		logger.info("========================初始化数据库完成==================================");
		logger.info("========================启动定时任务开始========================");
		initJob();
		logger.info("========================启动定时任务结束========================");
	}

	/**
	 * 初始化数据库
	 */
	public void initDatabase() {
		if (null == context) {
			// 单元测试时
			System.out.println("Initialization not context.");
			return;
		}
		try {
			//初始化地区
			try {
				areaService.initArea();
			} catch (Exception e) {
				e.printStackTrace();
				logger.info("==================导入地区出现异常=====================");
			}
		String ab_path = Initialization.class.getClassLoader().getResource("").getPath();
		String permPath = ab_path + "/data/init/permission.xml";
		String sysPath = ab_path + "/data/init/system.xml";
		String userPath = ab_path + "/data/init/user.xml";
		String rolePath = ab_path + "/data/init/role.xml";
		String orgPath = ab_path + "/data/init/org.xml";
		String encoding = "UTF-8";
		SystemRootXmlObject systems = XmlParseObjectUtil.getXmlObjectByFile(sysPath,encoding,SystemRootXmlObject.class);
		RoleRootXmlObject  roles = XmlParseObjectUtil.getXmlObjectByFile(rolePath,encoding,RoleRootXmlObject.class);
		OrgRootXmlObject orgs = XmlParseObjectUtil.getXmlObjectByFile(orgPath,encoding,OrgRootXmlObject.class);
		UserRootXmlObject users = XmlParseObjectUtil.getXmlObjectByFile(userPath,encoding,UserRootXmlObject.class);
		PermissionXmlObject perms = XmlParseObjectUtil.getXmlObjectByFile(permPath,encoding,PermissionXmlObject.class);
		//初始化系统配置
		systemService.initSystem(systems);
		//初始化角色
		roleService.initRole(roles);
		//初始化机构
		organizationService.initOrg(orgs);
		//初始管理员
		userService.initAdminuser(users);
		//初始化权限配置（只有当权限表为空时才会进行）
		permissionService.initPermission(perms);
		//初始化数据字典
			dictService.initDictionary();
		}catch (Exception e){
			e.printStackTrace();
			logger.info("==================初始化数据库出现异常=====================");
		}
	}

	/**初始化定时任务**/
	public void initJob(){
		/**启动服务监控定时器**/
		startServiceJob();
		/**启动本地服务器监控定时器**/
		startNativeServerJob();

		/**启动本地tomcat监控定时器**/
		startTomcatJob();

		/**启动本地数据库监控定时器**/
		startDbJob();
	}

	public void startServiceJob(){
		//服务监控定时任务
		List<Service> services = serviceService.find("from Service");
		for(Service service :services){
			String url = service.getServiceVisitAddress();
			Map<String, Object> saveParam = new HashMap<>();
			saveParam.put("id", service.getId());
			if(url != null && !url.equals("")) {
				Map<String, Object> res = HttpSocketUtil.sendRequest(url);
				if (String.valueOf(res.get("code")).equals("1")) {
					ScheduleJob job = new ScheduleJob();
					job.setJobName("serviceMonitor_" + service.getId());
					job.setJobGroup("service");
					job.setJobStatus(ScheduleJob.STATUS_RUNNING);
					String rate = service.getMonitorRate();
					//默认30秒
					if(rate == null || rate.equals("")){
						rate = "30";
					}
					job.setCronExpression(CronUtil.getCronByMillions(Long.parseLong(rate)));
					job.setDescription("服务监控的定时器");
					job.setBeanClass("com.ycsys.smartmap.sys.task.MonitorTask");
					job.setMethodName("collectServiceInfo");
					job.setMethodParameter(new Object[]{Service.class});
					job.setArgs(new Object[]{service});
					job.setIsConcurrent(ScheduleJob.CONCURRENT_NOT);
					job.setCreateTime(new Date());
					saveParam.put("monitorStatus", 1);
					try {
						jobTaskManager.addJob(job);
					} catch (SchedulerException e) {
						saveParam.put("monitorStatus", 0);
						serviceService.updateMonitor(saveParam);
						e.printStackTrace();
					}
					serviceService.updateMonitor(saveParam);
				} else {
					saveParam.put("monitorStatus", 0);
					serviceService.updateMonitor(saveParam);
				}
			}else{
				saveParam.put("monitorStatus", 0);
				serviceService.updateMonitor(saveParam);
			}
		}
	}

	public void startNativeServerJob(){
		try{
			String file = "config.properties";
			InputStream is = Initialization.class.getResource("/" + file).openStream();
			Properties properties = new Properties();
			properties.load(is);
			String url = properties.getProperty("snmp.url");
			String port = properties.getProperty("snmp.port");
			String communicate = properties.getProperty("snmp.communicate");
			/**测试地址是否能连通**/
			if(MonitorUtil.testSnmpStatus(url,port,communicate)) {
				String rate = properties.getProperty("snmp.rate");
				if (rate == null || rate.equals("")) {
					rate = "30";
				}
				String monitorkey = "nativeServerJob";
				ScheduleJob job = new ScheduleJob();
				job.setJobName("nativeServerJob");
				job.setJobGroup("nativeInfoMonitor");
				job.setJobStatus(ScheduleJob.STATUS_RUNNING);
				job.setCronExpression(CronUtil.getCronByMillions(Long.parseLong(rate)));
				job.setDescription("本地服务器监控的定时器");
				job.setBeanClass("com.ycsys.smartmap.sys.task.MonitorTask");
				job.setMethodName("collectNativeServer");
				job.setMethodParameter(new Object[]{String.class, String.class, String.class, String.class, String.class});
				job.setArgs(new Object[]{monitorkey, url, port, communicate, rate});
				job.setIsConcurrent(ScheduleJob.CONCURRENT_NOT);
				job.setCreateTime(new Date());
				jobTaskManager.addJob(job);
				is.close();
			}
		}catch (Exception e){
			logger.error("本地服务器监控启动失败！");
		}
	}
	public void startTomcatJob(){
		try {
			String rate = "30";
			ScheduleJob job = new ScheduleJob();
			job.setJobName("nativeTomcatJob");
			job.setJobGroup("nativeInfoMonitor");
			job.setJobStatus(ScheduleJob.STATUS_RUNNING);
			job.setCronExpression(CronUtil.getCronByMillions(Long.parseLong(rate)));
			job.setDescription("本地Tomcat监控的定时器");
			job.setBeanClass("com.ycsys.smartmap.sys.task.MonitorTask");
			job.setMethodName("collectNativeTomcat");
			job.setMethodParameter(new Object[]{ServletContext.class});
			job.setArgs(new Object[]{context});
			job.setIsConcurrent(ScheduleJob.CONCURRENT_NOT);
			job.setCreateTime(new Date());
			jobTaskManager.addJob(job);
		}catch (Exception e){
			logger.error("本地tomcat监控启动失败！");
		}
	}
	public void startDbJob(){
		try {
			String file = "db.properties";
			InputStream is = Initialization.class.getResource("/" + file).openStream();
			Properties properties = new Properties();
			properties.load(is);
			String driver = properties.getProperty("jdbc.driver");
			String url = properties.getProperty("jdbc.url");
			String username = properties.getProperty("jdbc.username");
			String password = properties.getProperty("jdbc.password");
			Map<String,Object> res = DbMonitorUtil.getDbMsg(driver,url,username,password);
			if(String.valueOf(res.get("code")).equals("1")) {
				String rate = "30";
				ScheduleJob job = new ScheduleJob();
				job.setJobName("nativeDbJob");
				job.setJobGroup("nativeInfoMonitor");
				job.setJobStatus(ScheduleJob.STATUS_RUNNING);
				job.setCronExpression(CronUtil.getCronByMillions(Long.parseLong(rate)));
				job.setDescription("本地数据库监控的定时器");
				job.setBeanClass("com.ycsys.smartmap.sys.task.MonitorTask");
				job.setMethodName("collectNativeDb");
				job.setMethodParameter(new Object[]{String.class, String.class, String.class, String.class});
				job.setArgs(new Object[]{driver, url, username, password});
				job.setIsConcurrent(ScheduleJob.CONCURRENT_NOT);
				job.setCreateTime(new Date());
				jobTaskManager.addJob(job);
			}else{
				logger.error("本地数据库监控定时器启动失败！");
			}
			is.close();
		}catch (Exception e){
			logger.error("本地数据库监控定时器启动失败！");
			e.printStackTrace();
		}
	}
}
