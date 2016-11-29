package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.dao.OrganizationDao;
import com.ycsys.smartmap.sys.entity.Organization;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.OrganizationService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */
@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService{

    @Resource
    private OrganizationDao organizationDao;

    /**分页查找所有机构**/
    public List<Organization> findAll(PageHelper page) {
        return organizationDao.find("from Organization",page);
    }

    /***保存或者创建*/
    @Override
    public void saveOrUpdate(Organization o) {
        //名称和code唯一
        long count = 0;
        //添加
        if(o.getId() == null){
            Object [] param = {o.getName(),o.getCode()};
            count = organizationDao.count("select count(*) from Organization where name=? or code = ?",param);
            //修改
        }else{
            Object [] param = {o.getId(),o.getName(),o.getCode()};
            count = organizationDao.count("select count(*) from Organization where id <> ? and (name=? or code = ?)",param);
        }
        if(count > 0){
            throw new ServiceException("名称或者编码已存在！");
        }
        if(o.getPid() == null || o.getPid() == -1){
            o.setLevel((short)1);
            o.setPid(null);
        }else{
            Organization po = organizationDao.get(Organization.class,o.getPid());
            o.setLevel((short)(po.getLevel() + 1));
        }
        organizationDao.saveOrUpdate(o);
    }

    /**删除**/
    @Override
    public void delete(Organization o) {
        List<Object> param = new ArrayList<>();
        param.add(o.getId());
        List<Organization> os = organizationDao.find("from Organization o where pid = ?",param);
        if(os == null || os.size() < 1) {
            organizationDao.delete(o);
        }else{
            throw new ServiceException("该机构下有子目录，不能删除");
        }
    }

    /**分页查找所有父机构**/
    @Override
    public List<Organization> findOrgNotChild(String pid,PageHelper page) {
        if(pid == null){
            Object[] o = {(short) 1};
            return organizationDao.find("from Organization where level = ?", o, page);
        }else {
            Object[] o = {Integer.parseInt(pid)};
            return organizationDao.find("from Organization where pid = ?", o, page);
        }
    }

    /**根据id获取一个机构**/
    @Override
    public Organization getOrg(String id) {
        return organizationDao.get(Organization.class,Integer.parseInt(id));
    }

    @Override
    public List<Organization> findAll() {
        return organizationDao.find("from Organization");
    }
}
