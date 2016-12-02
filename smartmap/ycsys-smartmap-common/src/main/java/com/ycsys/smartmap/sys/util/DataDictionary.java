package com.ycsys.smartmap.sys.util;

import com.ycsys.smartmap.sys.entity.Dictionary;
import com.ycsys.smartmap.sys.entity.DictionaryItem;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据字典操作类
 * Created by lixiaoxin on 2016/11/3.
 */
@SuppressWarnings("unchecked")
public class DataDictionary {
    public static final String DICT_OBJECT = "_OBJECT_";
    private static final Logger logger = LoggerFactory.getLogger(DataDictionary.class);
    public static final String CACHE_NAME = "DATA_SYS_DICT";
    private boolean initStatus = true;

    /**
     *根据数据编码code获取一个Map集合
     * 集合的值为子项的value : name
     * **/
    public static Map<String, Object> get(String dictCode)
    {
        Map<String,Object> h = (Map)EhCacheUtils.get(CACHE_NAME, dictCode);
        if (h == null) {
            SessionFactory sessionFactory = (SessionFactory)SpringContextHolder.getBean("sessionFactory");
            Session session = sessionFactory.openSession();
            String hql = "select i from DictionaryItem i inner join i.dictionary d where d.code = ? order by i.sort";
            Query q = session.createQuery(hql);
            q.setParameter(0,dictCode);
            List<DictionaryItem> items = q.list();
            session.close();
            if(items.size() > 0) {
                h = new LinkedHashMap();
                for (DictionaryItem m : items) {
                    h.put(m.getValue(), m.getName());
                }
                EhCacheUtils.put(CACHE_NAME, dictCode, h);
            }
        }
        return h;
    }

    /**
     *根据数据编码code获取一个Map集合
     * 集合的值为子项的value : Object
     * **/
    public static Map<String, Object> getObject(String dictCode)
    {
        Map<String,Object> h = (Map)EhCacheUtils.get(CACHE_NAME, DICT_OBJECT + dictCode);
        if (h == null) {
            SessionFactory sessionFactory = (SessionFactory)SpringContextHolder.getBean("sessionFactory");
            Session session = sessionFactory.openSession();
            String hql = "select i from DictionaryItem i inner join i.dictionary d where d.code = ? order by i.sort";
            Query q = session.createQuery(hql);
            q.setParameter(0,dictCode);
            List<DictionaryItem> items = q.list();
            session.close();
            if(items.size() > 0) {
                h = new LinkedHashMap();
                for (DictionaryItem m : items) {
                    h.put(m.getValue(), m);
                }

                EhCacheUtils.put(CACHE_NAME, DICT_OBJECT + dictCode, h);
            }
        }
        return h;
    }

    /**
     * 根据字典code和子项value获取memo
     * **/
    public static String getMemo(String dictCode, String key)
    {
        Map object = getObject(dictCode);
        if (object == null) return null;

        Map m = (Map)object.get(key);
        if (m != null) {
            return (String)m.get("memo");
        }
        return null;
    }

    public static String getMemo(Map<String, Object> object, String key)
    {
        Map m = (Map)object.get(key);
        if (m != null) {
            return (String)m.get("memo");
        }
        return null;
    }

    /**
     * 根据字典code和子项value获取子项name
     * **/
    public static String getName(String dictCode, String key)
    {
        Map map = get(dictCode);
        if (map != null) {
            return (String)map.get(key);
        }
        return null;
    }

    public static String getName(Map<String, Object> dict, String key)
    {
        if (dict != null) {
            return (String)dict.get(key);
        }
        return null;
    }

    public void init() {
        if (this.initStatus)
            load();
    }

    private static void load()
    {
        logger.info("正在加载数据字典");
        SessionFactory sessionFactory = (SessionFactory)SpringContextHolder.getBean("sessionFactory");
        Session session = sessionFactory.openSession();
        String hql = "from Dictionary";
        Query q = session.createQuery(hql);
        List<Dictionary> list = q.list();
        for (Dictionary map : list) {
            String key = map.getCode();
            Integer id = map.getId();
            Query qs = session.createQuery("select i from DictionaryItem i inner join i.dictionary d where d.id = ? order by i.sort");
            qs.setParameter(0,id);
            List<DictionaryItem> items = qs.list();
            Map<String,Object> h = new LinkedHashMap<>();
            Map<String,Object> ho = new LinkedHashMap<>();
            for (DictionaryItem m : items) {
                h.put(m.getValue(), m.getName());
                ho.put(m.getValue(), m);
            }

            EhCacheUtils.put(CACHE_NAME, key, h);
            EhCacheUtils.put(CACHE_NAME, DICT_OBJECT + key, ho);
        }
        logger.info("加载数据字典完成");
        session.close();
    }

    public static boolean reload()
    {
        load();
        return true;
    }

    /**刷新缓存**/
    public static void refresh(String dictCode, Integer id)
    {
        SessionFactory sessionFactory = (SessionFactory)SpringContextHolder.getBean("sessionFactory");
        Session session = sessionFactory.openSession();
        Query qs = sessionFactory.getCurrentSession().createQuery("select i from DictionaryItem i inner join i.dictionary d where d.id = ? order by i.sort");
        qs.setParameter(0,id);
        List<DictionaryItem> items = qs.list();
        session.close();
        Map<String,Object> h = new LinkedHashMap();
        Map<String,Object> ho = new LinkedHashMap();
        for (DictionaryItem m: items) {
            h.put(m.getValue(), m.getName());
            ho.put(m.getValue(), m);
        }
        EhCacheUtils.put(CACHE_NAME, dictCode, h);
        EhCacheUtils.put(CACHE_NAME, DICT_OBJECT + dictCode, ho);
    }

    public void setInitStatus(boolean initStatus) {
        this.initStatus = initStatus;
    }
}
