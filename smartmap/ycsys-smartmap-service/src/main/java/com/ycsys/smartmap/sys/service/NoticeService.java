package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.entity.Notice;
import com.ycsys.smartmap.sys.entity.PageHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixiaoxin on 2017/1/13.
 */
public interface NoticeService {
    List<Notice> findByPage(PageHelper pageHelper);

    long countAll();

    void save(Notice notice);

    void createNotices(Map<String, Object> params);

    Notice getNotice(String id);

    void delete(String id);

    void update(Notice notice);
}
