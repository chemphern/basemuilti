package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.PermissionDao;
import com.ycsys.smartmap.sys.entity.Permission;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 权限DAO
 * @author lixiaoxin
 * @date 2016年11月2日
 */
@Repository("permissionDao")
public class PermissionDaoImpl extends BaseDaoImpl<Permission,Integer> implements PermissionDao{

	/**
	 * 查询用户拥有的权限
	 * @param userId 用户id
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Permission> findPermissions(Integer userId){
		StringBuffer sb=new StringBuffer();
		sb.append("select p from Permission p ");
		sb.append("inner join p.rolePermissions rp ");
		sb.append("inner join rp.role r ");
		sb.append("inner join r.userRoles ur ");
		sb.append("inner join ur.user u ");
		sb.append("where u.id= ? order by p.sort ");
		StringBuffer sp = new StringBuffer();
		sp.append("select p from User u ");
		sp.append("inner join u.organization o ");
		sp.append("inner join o.organizationPermissions op ");
		sp.append("inner join op.permission p ");
		sp.append("where u.id= ? order by p.sort");
		List<Permission> rp = find(sb.toString(),new Object[]{userId});
		List<Permission> op = find(sp.toString(),new Object[]{userId});
		return unionPermission(rp,op);
	}

	/**
	 * 将多个permision合并
	 * **/
	public List<Permission> unionPermission(List<Permission> ... permissions){
		List<Permission> resList = new ArrayList<>();
		for(int x = 0;x<permissions.length;x++){
			for(Permission permission:permissions[x]){
				boolean flag = true;
				for(Permission p:resList){
					if(permission.getId() == p.getId()){
						flag = false;
						break;
					}
				}
				if(flag){
					resList.add(permission);
				}
			}
		}
		return resList;
	}

	/**
	 * 查询所有菜单
	 * @return 菜单集合
	 */
	@SuppressWarnings("unchecked")
	public List<Permission> findMenus(){
		StringBuffer sb=new StringBuffer();
		sb.append("select p  from Permission p ");
		sb.append("where p.type = 'module' order by p.sort");
		List<Permission> ps = createQuery(sb.toString()).list();
		for(Permission permission:ps){
			setMenuChilds(permission);
		}
		return ps;
	}
	@Override
	public List<Permission> findMenus(String sys_code) {
		StringBuffer sb=new StringBuffer();
		sb.append("select p  from Permission p ");
		sb.append("where p.type = 'module' and p.systemCode = ? order by p.sort");
		Object [] param = {sys_code};
		List<Permission> ps = find(sb.toString(),param);
		for(Permission p:ps){
			setMenuChilds(p);
		}
		return ps;
	}

	@Override
	public List<Permission> getAllPermissions(String sys_code) {
		StringBuffer sb=new StringBuffer();
		sb.append("select p  from Permission p ");
		sb.append("where p.type = 'module' and p.systemCode = ? order by p.sort");
		List<Permission> ps = find(sb.toString(),new Object []{sys_code});
		for(Permission p:ps){
			setChilds(p);
		}
		return ps;
	}

	public void setChilds(Permission p){
		List<Permission> ps = find("from Permission p where pid = ?",new Object[]{p.getId()});
		if(ps != null && ps.size() > 0){
			p.setChildPermission(ps);
			for(Permission i : ps){
				setChilds(i);
			}
		}else {
			p.setChildPermission(null);
		}
	}

	public void setMenuChilds(Permission p){
		if(!p.getType().equals("url")) {
			List<Permission> ps = find("from Permission p where pid = ?",new Object[]{p.getId()});
			if(ps != null && ps.size() > 0){
				p.setChildPermission(ps);
				for(Permission i : ps){
					setMenuChilds(i);
				}
			}else {
				p.setChildPermission(null);
			}
		}
	}
	
	/**
	 * 查询用户拥有的菜单权限
	 * @param userId
	 * @return 结果集合
	 */
	@SuppressWarnings("unchecked")
	public List<Permission> findMenus(Integer userId,String system_code){
		StringBuffer sb=new StringBuffer();
		sb.append("select p  from Permission p ");
		sb.append("inner join p.rolePermissions rp ");
		sb.append("inner join rp.role r ");
		sb.append("inner join r.userRoles ur ");
		sb.append("inner join ur.user u ");
		sb.append("where u.id=? and p.systemCode = ? order by p.sort");
		Query query = createQuery(sb.toString());
		query.setParameter(0,userId);
		query.setParameter(1,system_code);
		List<Permission> ps = query.list();

		StringBuffer sp = new StringBuffer();
		sp.append("select p from User u ");
		sp.append("inner join u.organization o ");
		sp.append("inner join o.organizationPermissions op ");
		sp.append("inner join op.permission p ");
		sp.append("where u.id= ? and p.systemCode = ? order by p.sort");
		List<Permission> op = find(sp.toString(),new Object[]{userId,system_code});
		List<Permission> res = unionPermission(ps,op);
		return mapping(res);
	}

	/**
     * 映射方法，把平行结构的数据变成树结构的数据
     * 思路：迭代权限，获取所有有子权限的集合
     *       在添加权限集合的过程中删剩父权限集合
     *       再依次附上子权限列表
     * **/
    @SuppressWarnings("unchecked")
	public List<Permission> mapping(List<Permission>sources){
        Map<Integer,Object> mapping = new HashMap<>();
        Map<Integer,Object> temp = new HashMap<>();
        Map<Integer,Object> idList = new HashMap<>();
        List<Permission> targer = new ArrayList<>();
        for(Permission source :sources){
            //除了功能点外的权限
            if(!source.getType().equals("func")) {
                mapping.put(source.getId(), source);
                temp.put(source.getId(), source);
            }
        }
        for(Permission source:sources){
            //除了功能点外的权限
            if(!source.getType().equals("func")) {
                Integer id = source.getId();
                Integer pid = source.getPid();
                if (pid != null) {
                    Permission permission = (Permission) mapping.get(pid);
                    if (permission != null) {
                        List<Permission> list = (List<Permission>) idList.get(pid);
                        if (list == null) {
                            list = new ArrayList<>();
                            idList.put(pid, list);
                        }
                        list.add(source);
                        temp.remove(id);
                    }
                }
            }
        }
        for(Map.Entry<Integer,Object> m:temp.entrySet()){
            Permission p = (Permission)m.getValue();
            iteratorChild(p,idList);
            targer.add(p);
       }
        return targer;
    }

    /**迭代映射树**/
    @SuppressWarnings("unchecked")
    public void iteratorChild(Permission p,Map<Integer,Object> idList){
        if (idList.get(p.getId()) != null) {
            List<Permission> ps = (List<Permission>) idList.get(p.getId());
            if(ps != null){
                for(Permission perm:ps){
                    iteratorChild(perm,idList);
                }
                p.setChildPermission(ps);
            }

        }
    }

}
