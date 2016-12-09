package com.ycsys.smartmap.sys;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.ycsys.smartmap.sys.common.config.parseobject.org.OrgRootXmlObject;
import com.ycsys.smartmap.sys.common.config.parseobject.permission.PermissionXmlObject;
import com.ycsys.smartmap.sys.common.config.parseobject.role.RoleRootXmlObject;
import com.ycsys.smartmap.sys.common.config.parseobject.system.SystemRootXmlObject;
import com.ycsys.smartmap.sys.common.config.parseobject.user.UserRootXmlObject;
import com.ycsys.smartmap.sys.service.*;
import com.ycsys.smartmap.sys.util.XmlParseObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import java.util.Properties;

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
		/*目前将全局变量放ehcached缓存
		// 全局变量缓存
		if (null != context){
			context.setAttribute(Global.CONTEXT_SYSTEM_CONFIG, loadSystemConfig());
		}*/
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
}
