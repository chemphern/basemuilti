package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.common.config.parseobject.*;
import com.ycsys.smartmap.sys.common.utils.DateUtils;
import com.ycsys.smartmap.sys.dao.PermissionDao;
import com.ycsys.smartmap.sys.dao.SystemDao;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.Permission;
import com.ycsys.smartmap.sys.service.PermissionService;
import com.ycsys.smartmap.sys.util.PermissionUtil;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * 权限service
 * @author lixiaoxin
 * @date 2016年11月2日
 */
@Service("permissionService")
public class PermissionServiceImpl implements PermissionService {
	
	@Autowired
	private PermissionDao permissionDao;

	@Autowired
	private SystemDao systemDao;

	private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

	/**
	 * 获取角色拥有的菜单
	 * @param userId
	 * @return 菜单集合
	 */
	public List<Permission> getMenus(Integer userId,String system_code){
		return permissionDao.findMenus(userId,system_code);
	}
	
	/**
	 * 获取所有菜单
	 * @return 菜单集合
	 */
	public List<Permission> getMenus(){
		return permissionDao.findMenus();
	}

	@Override
	public List<Permission> getMenus(String sys_code) {
		return permissionDao.findMenus(sys_code);
	}

	@Override
	public List<Permission> getAllPermissions(String sys_code) {
		return permissionDao.getAllPermissions(sys_code);
	}

	/**导入权限**/
	@Override
	public void importPermission(InputStream is, String encoding){
		try {
			PermissionXmlObject obj = PermissionUtil.getXmlObjectByStream(is, encoding);
			//删除系统表
			permissionDao.executeHql("delete from System");
			//删除角色权限表
			permissionDao.executeHql("delete from RolePermission");
			//删除所有该系统权限
			permissionDao.executeHql("delete from Permission");
			updateSystemFromXmlObject(obj);
		}catch (Exception e){
			throw  new ServiceException(e.getMessage());
		}
	}

	/**
	 *初始化权限表（当权限表没有数据的时候，会从permission.xml中导入基础数据）
	 * **/
	public void initPermission(String system_code) {
		long i = permissionDao.count("select count(*) from Permission p where p.systemCode = ?",new Object[]{system_code});
		//当本系统没有数据时，方才执行初始化导入
		if(i == 0){
			String ab_path = PermissionServiceImpl.class.getClassLoader().getResource("").getPath();
			try {
				PermissionXmlObject obj = PermissionUtil.getXmlObjectByFile(ab_path + "/permission/permission.xml","UTF-8");
				updateSystemFromXmlObject(obj);
				logger.info("==================导入权限========================");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**分页获取所有权限**/
	@Override
	public List<Permission> findAll(PageHelper page) {
		return permissionDao.find("from Permission",page);
	}



	/***
	 * 保存链接权限信息
	 * */
	private void saveUrlPermission(Permission p,List<UrlXmlObject> urls,int level,String system_code){
		if(urls != null){
			for(UrlXmlObject url :urls){
				String url_name = url.getName();
				String url_sort = url.getSort();
				String url_func = url.getFuncs();
				String url_href = url.getHref();
				Permission menu_url_permission = new Permission();
				menu_url_permission.setAuthcUrl(url_name,Integer.parseInt(url_sort),(short)level,url_href,url_func,system_code);
				menu_url_permission.setPid(p.getId());
				permissionDao.save(menu_url_permission);
				int menu_url_permission_id = menu_url_permission.getId();
				//获取功能点
				List<FuncXmlObject> url_funcs = url.getFuncXmlObjects();
				if(url_funcs != null){
					for(FuncXmlObject func : url_funcs){
						String func_name = func.getName();
						String func_sort = func.getSort();
						String func_code = func.getCode();
						if(func_code != null){
							func_code = func_code.trim().replaceAll("\n","").replaceAll(" ","");
						}
						Permission url_func_permission = new Permission();
						url_func_permission.setAuthcFunc(func_name,Integer.parseInt(func_sort),(short)(level+1),func_code,system_code);
						url_func_permission.setPid(menu_url_permission.getId());
						permissionDao.save(url_func_permission);
					}
				}
			}
		}
	}

	public void updateSystemFromXmlObject(PermissionXmlObject obj){
		List<SystemXmlObject> systemList = obj.getSystemXmlObjectList();
		//读取系统
		for(SystemXmlObject system :systemList) {
			//处理系统
			String system_code = system.getCode();
			if (system_code != null) {
				system_code = system_code.trim().replaceAll("\n", "").replaceAll(" ", "");
			}else{
				throw new ServiceException("xml文件格式不正确！");
			}
			//如果该系统编码不存在，则创建系统
			com.ycsys.smartmap.sys.entity.System sys = systemDao.get("from System where code = ?",new Object[]{system_code});
			if(sys == null){
				sys = new com.ycsys.smartmap.sys.entity.System();
				sys.setUrl(system.getUrl());
				sys.setCreateTime(DateUtils.getSysTimestamp());
				sys.setCode(system_code);
				sys.setName(system.getName());
				systemDao.save(sys);
			}else{
				sys.setName(system.getName());
				sys.setUrl(system.getUrl());
				systemDao.update(sys);
			}
			//删除角色权限表
			permissionDao.executeHql("delete from RolePermission where permission.id in (select id from Permission where systemCode = ? )",new Object[]{system_code});
			//删除所有该系统权限
			permissionDao.executeHql("delete from Permission where systemCode = ?", new Object[]{system_code});
			//读取模块
			List<ModuleXmlObject> modules = system.getModuleXmlObjectList();
			for (ModuleXmlObject module : modules) {
				String module_name = module.getName();
				String module_sort = module.getSort();
				Permission module_permission = new Permission();
				module_permission.setAuthcModule(module_name, Integer.parseInt(module_sort), (short) 0, system_code);
				//模块保存
				permissionDao.save(module_permission);
				int module_permission_id = module_permission.getId();
				//获取菜单
				List<MenuXmlObject> menus = module.getMenuXmlObject();
				if (menus != null) {
					for (MenuXmlObject menu : menus) {
						String menu_name = menu.getName();
						String menu_sort = menu.getSort();
						Permission menu_permission = new Permission();
						menu_permission.setAuthcMenu(menu_name, Integer.parseInt(menu_sort), (short) 1, system_code);
						menu_permission.setPid(module_permission.getId());
						permissionDao.save(menu_permission);
						int menu_permission_id = menu_permission.getId();
						this.saveUrlPermission(menu_permission, menu.getUrlXmlObjects(), 2, system_code);
					}
				}
				//获取链接
				this.saveUrlPermission(module_permission, module.getUrlXmlObject(), 1, system_code);
			}
		}
	}

}
