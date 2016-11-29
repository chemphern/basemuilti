package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.LogDao;
import com.ycsys.smartmap.sys.entity.Log;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 日志DAO
 * @author ty
 * @date 2015年1月13日
 */
@Repository
public class LogDaoImpl extends BaseDaoImpl<Log, Integer> implements LogDao {

	/**
	 * 批量删除日志
	 * @param idList 日志id列表
	 */
	public void deleteBatch(List<Integer> idList){
		String hql="delete from Log log where log.id in (:idList)";
		Query query=createQuery(hql);
		query.setParameterList("idList", idList);
		query.executeUpdate();
	}
}
