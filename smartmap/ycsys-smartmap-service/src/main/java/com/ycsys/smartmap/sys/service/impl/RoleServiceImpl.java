package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.common.annotation.ToLog;
import com.ycsys.smartmap.sys.common.config.parseobject.role.RoleRootXmlObject;
import com.ycsys.smartmap.sys.common.config.parseobject.role.RoleXmlObject;
import com.ycsys.smartmap.sys.common.enums.LogType;
import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.common.utils.DateUtils;
import com.ycsys.smartmap.sys.dao.OrganizationDao;
import com.ycsys.smartmap.sys.dao.PermissionDao;
import com.ycsys.smartmap.sys.dao.RoleDao;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.Permission;
import com.ycsys.smartmap.sys.entity.Role;
import com.ycsys.smartmap.sys.service.RoleService;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixiaoxin on 2016/11/11.
 */
@Service("roleService")
public class  RoleServiceImpl implements RoleService{

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Resource
    private RoleDao roleDao;

    @Resource
    private OrganizationDao organizationDao;

    @Resource
    private PermissionDao permissionDao;

    @ToLog(name="查询所有角色",type= LogType.System)
    @Override
    public List<Role> findAll(PageHelper page) {
        return roleDao.find("from Role",page);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.find("from Role");
    }

    @Override
    public void initRole(RoleRootXmlObject roles) {
        long count = roleDao.count("select count(*) from Role");
        if(count < 1 ){
            logger.info("=============================初始化角色表开始=======================");
            for(RoleXmlObject rxo : roles.getRoleXmlObjectList()){
                long temp_count  = roleDao.count("select count(*) from Role where code = ?",new Object[]{rxo.getCode()});
                if(temp_count < 1) {
                    Role r = new Role();
                    r.setName(rxo.getName());
                    r.setRemark(rxo.getRemark());
                    r.setCode(rxo.getCode());
                    r.setCreateTime(DateUtils.getSysTimestamp());
                    boolean isSuper = false;
                    if(rxo.getIsSuper() != null) {
                        try {
                            isSuper = Boolean.parseBoolean(rxo.getIsSuper());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    r.setSuper(isSuper);
                    if (rxo.getSort() != null) {
                        r.setSort(Integer.parseInt(rxo.getSort()));
                    }
                    roleDao.save(r);
                }
            }
            logger.info("============================初始化角色表结束===========================");
        }
    }

    @Override
    public long countAll() {
        return roleDao.count("select count(*) from Role");
    }

    @Override
    public void delete(Role role) {
        Role r = roleDao.get(Role.class,role.getId());
        if(r.getUserRoles() == null || r.getUserRoles().size() <1){
            roleDao.delete(r);
            List<Object> params = new ArrayList<>();
            params.add(role.getId());
            roleDao.executeHql("delete RolePermission p where p.role.id = ?",params);
        }else{
            throw new ServiceException("该角色下有用户，不能删除！");
        }
    }

    @Override
    public void saveOrUpdate(Role role) {
        //角色代码不能重复
        long count = 0;
        //新增
        if(role.getId() == null){
            role.setCreateTime(DateUtils.getSysTimestamp());
            Object [] param = {role.getCode()};
            count = roleDao.count("select count(*) from Role where code = ?",param);
        }else{//修改
            Object [] param = {role.getId(),role.getCode()};
            count = roleDao.count("select count(*) from Role where id <> ? and code = ?",param);
            role.setUpdateTime(DateUtils.getSysTimestamp());
            Role r = roleDao.get(Role.class,role.getId());
            role.setSuper(r.isSuper());
            try {
                BeanExtUtils.copyProperties(r, role, true, true, null);
                role = r;
            }catch (Exception e){
                throw  new ServiceException("对象拷贝失败！:" + e.getMessage());
            }
        }
        if(count > 0){
            throw  new ServiceException("角色编码已存在！");
        }
        roleDao.saveOrUpdate(role);
    }

    @Override
    public Role getRole(String id) {
        return roleDao.get(Role.class,Integer.parseInt(id));
    }

    @Override
    public List<Permission> getPermissionByRoleId(String id) {
        Object [] param = {Integer.parseInt(id)};
        return permissionDao.find("select p from Permission p join p.rolePermissions rp where rp.role.id = ?",param);
    }


}
