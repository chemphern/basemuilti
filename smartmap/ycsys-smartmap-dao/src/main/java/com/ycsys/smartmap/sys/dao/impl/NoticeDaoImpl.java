package com.ycsys.smartmap.sys.dao.impl;

import com.ycsys.smartmap.sys.dao.NoticeDao;
import com.ycsys.smartmap.sys.entity.Notice;
import org.springframework.stereotype.Repository;

/**
 * Created by lixiaoxin on 2017/1/13.
 */
@Repository("/noticeDao")
public class NoticeDaoImpl extends BaseDaoImpl<Notice,Integer> implements NoticeDao{
}
