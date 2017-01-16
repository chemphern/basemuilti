package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.common.utils.BeanExtUtils;
import com.ycsys.smartmap.sys.dao.NoticeDao;
import com.ycsys.smartmap.sys.dao.NoticeReceiverDao;
import com.ycsys.smartmap.sys.entity.Notice;
import com.ycsys.smartmap.sys.entity.NoticeReceiver;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.entity.User;
import com.ycsys.smartmap.sys.service.NoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * Created by lixiaoxin on 2017/1/13.
 */
@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{

    @Resource
    private NoticeDao noticeDao;

    @Resource
    private NoticeReceiverDao noticeReceiverDao;

    @Override
    public List<Notice> findByPage(PageHelper pageHelper) {
        return noticeDao.find("from Notice",pageHelper);
    }

    @Override
    public long countAll() {
        return noticeDao.count("select count(*) from Notice");
    }

    @Override
    public void save(Notice notice) {
        noticeDao.save(notice);
    }

    @Override
    public void createNotices(Map<String, Object> params) {
        Notice notice = new Notice();
        if(params.containsKey("message_id")) {
            String i = String.valueOf(params.get("message_id"));
            if( i != null && !i.equals("")){
                notice.setId(Integer.parseInt(i));
            }

        }
        Timestamp now = new Timestamp(System.currentTimeMillis());
        notice.setCreateTime(now);
        notice.setContent(String.valueOf(params.get("content")));
        List<String> userList = (List<String>) params.get("useridList");
        short status = 1;
        if(userList.size() > 0){
            status = 2;
        }
        notice.setStatus(status);
        notice.setType(Short.parseShort(String.valueOf(params.get("type"))));
        notice.setTitle(String.valueOf(params.get("title")));
        noticeDao.saveOrUpdate(notice);
        for(String user_id :userList){
            if(user_id != null && !("".equals(user_id))){
                int id = Integer.parseInt(user_id);
                User u = new User();
                u.setId(id);
                NoticeReceiver nr = new NoticeReceiver();
                nr.setNotice(notice);
                nr.setSendTime(now);
                nr.setUser(u);
                nr.setStatus((short)1);
                noticeReceiverDao.save(nr);
            }
        }
    }

    @Override
    public Notice getNotice(String id) {
        return noticeDao.get(Notice.class,Integer.parseInt(id));
    }

    @Override
    public void delete(String id) {
        Notice notice = new Notice();
        notice.setId(Integer.parseInt(id));
        noticeDao.delete(notice);
    }

    @Override
    public void update(Notice notice) {
        Notice prev = noticeDao.get(Notice.class,notice.getId());
        try {
            BeanExtUtils.copyProperties(prev, notice, true, true,
                    null);
            notice = prev;
            noticeDao.update(notice);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
