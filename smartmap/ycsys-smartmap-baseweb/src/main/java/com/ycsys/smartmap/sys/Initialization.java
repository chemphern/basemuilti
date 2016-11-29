package com.ycsys.smartmap.sys;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import com.ycsys.smartmap.sys.service.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import java.util.Map;

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
	SystemService systemService;

	@Autowired
	AreaService areaService;

	@Autowired
	DictService dictService;

	private static final Logger logger = Logger.getLogger(Initialization.class);

	public void setServletContext(ServletContext context) {
		this.context = context;
	}

	@PostConstruct
	public void init() {
		logger.info("========================初始化数据库开始==================================");
		initDatabase();
		logger.info("========================初始化数据库完成==================================");
		/*目前将全局变量放ehcached缓存
		// 全局变量缓存
		if (null != context){
			context.setAttribute(Global.CONTEXT_SYSTEM_CONFIG, loadSystemConfig());
		}*/
	}

	public Map<String,Object> loadSystemConfig(){
		return null;
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
		//初始化系统配置
		systemService.initSystem();
		//初始管理员
		userService.initAdminuser();
		//初始化权限配置（只有当权限表为空时才会进行）
		permissionService.initPermission();
		//初始化数据字典
		try {
			dictService.initDictionary();
		}catch (Exception e){
			e.printStackTrace();
			logger.info("==================导入数据字典出现异常=====================");
		}
			//初始化地区
		try {
			areaService.initArea();
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("==================导入地区出现异常=====================");
		}
	}
}
