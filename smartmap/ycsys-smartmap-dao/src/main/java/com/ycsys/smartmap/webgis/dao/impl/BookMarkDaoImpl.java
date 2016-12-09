package com.ycsys.smartmap.webgis.dao.impl;

import org.springframework.stereotype.Repository;

import com.ycsys.smartmap.sys.dao.impl.BaseDaoImpl;
import com.ycsys.smartmap.webgis.entity.BookMark;
import com.ycsys.smartmap.webgis.dao.BookMarkDao;

/**
 * Created by chenlong on 2016/12/7.
 */
@Repository("bookMarkDao")
public class BookMarkDaoImpl extends BaseDaoImpl<BookMark,Integer> implements BookMarkDao {
}
