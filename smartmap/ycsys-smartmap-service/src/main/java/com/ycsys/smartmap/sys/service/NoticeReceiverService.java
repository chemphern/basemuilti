package com.ycsys.smartmap.sys.service;

import com.ycsys.smartmap.sys.entity.PageHelper;

import java.util.List;
import java.util.Map;

/**
 * Created by lixiaoxin on 2017/1/13.
 */
public interface NoticeReceiverService {
    long countNotice(Integer id);

    List<Map<String,Object>> findNoticesByUserId(Integer id, PageHelper pageHelper);

    long countByUserId(Integer id);

    void delete(String id);

    List<Map<String,Object>> findNoticesByNoticeId(String noticeId, PageHelper pageHelper);

    long countByNoticeId(String noticeId);
}
