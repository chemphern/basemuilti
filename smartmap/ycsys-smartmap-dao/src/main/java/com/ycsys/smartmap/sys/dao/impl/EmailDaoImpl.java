package com.ycsys.smartmap.sys.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.sys.dao.EmailDao;
import com.ycsys.smartmap.sys.entity.Email;

/**
 * 邮件服务器参数配置实现类
 * @date 2016年11月28日
 * @author lrr
 */
@Repository("emailDao")
public class EmailDaoImpl extends BaseDaoImpl<Email, Integer> implements
EmailDao {

}
