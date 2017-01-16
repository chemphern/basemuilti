package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.dao.NoticeReceiverDao;
import com.ycsys.smartmap.sys.entity.NoticeReceiver;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.NoticeReceiverService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by lixiaoxin on 2017/1/13.
 */
@Service("/noticeReceiverService")
public class NoticeReceiverServiceImpl implements NoticeReceiverService {
    @Resource
    private NoticeReceiverDao noticeReceiverDao;

    @Override
    public long countNotice(Integer id) {
        return noticeReceiverDao.count("select count(*) from NoticeReceiver where notice.id = ?",new Object[]{id});
    }

    @Override
    public List<Map<String, Object>> findNoticesByUserId(Integer id, PageHelper pageHelper) {
        return noticeReceiverDao.findList("select new map(nr.id as id,notice.id as noticeId,nr.sendTime as sendTime, nr.receiveTime as receiveTime,nr.status as status,notice.title as title,notice.content as content,notice.type as type) from NoticeReceiver nr where user.id = ?",new Object[]{id},pageHelper);
    }

    @Override
    public long countByUserId(Integer id) {
        return noticeReceiverDao.count("select count(*) from NoticeReceiver where user.id = ?",new Object[]{id});
    }

    @Override
    public void delete(String id) {
        NoticeReceiver n = new NoticeReceiver();
        n.setId(Integer.parseInt(id));
        noticeReceiverDao.delete(n);
    }

    @Override
    public List<Map<String, Object>> findNoticesByNoticeId(String noticeId,PageHelper pageHelper) {
        return noticeReceiverDao.findList("select new map(nr.user.name as name,nr.user.loginName as loginName,nr.status as status) from NoticeReceiver nr where notice.id = ?",new Object[]{Integer.parseInt(noticeId)},pageHelper);
    }

    @Override
    public long countByNoticeId(String noticeId) {
        return noticeReceiverDao.count("select count(*) from NoticeReceiver where notice.id = ?",new Object[]{Integer.parseInt(noticeId)});
    }
}
