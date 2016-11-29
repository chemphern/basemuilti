package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.util.DataDictionary;
import com.ycsys.smartmap.sys.util.EhCacheUtils;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lixiaoxin on 2016/11/29.
 */
@RequestMapping("/cache")
@Controller
public class CacheController {

    @RequiresPermissions(value = "sys-cache-refreshDict")
    @RequestMapping("/refreshDict")
    @ResponseBody
    public ResponseEx refreshDict(){
        ResponseEx ex = new ResponseEx();
        try{
            CacheManager cacheManager = EhCacheUtils.getCacheManager();
            Cache cache = cacheManager.getCache(DataDictionary.CACHE_NAME);
            if(cache != null){
                cacheManager.removeCache(DataDictionary.CACHE_NAME);
            }
            DataDictionary.reload();
            ex.setSuccess("刷新成功！");
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }

    @RequiresPermissions(value="sys-cache-list")
    @RequestMapping("/list")
    public String list(){
        return "/cache/list";
    }

    @RequiresPermissions(value="sys-cache-listData")
    @RequestMapping("/listData")
    @ResponseBody
    public Grid<Map<String,Object>> listData(){
        Grid<Map<String,Object>> res = new Grid<>();
        Map<String,Object> dict = new HashMap<String,Object>();
        dict.put("name","数据字典");
        CacheManager cacheManager = EhCacheUtils.getCacheManager();
        Cache cache = cacheManager.getCache(DataDictionary.CACHE_NAME);
        int count = 0;
        if(cache != null) {
            List<String> keys = cache.getKeys();
            for (String key : keys) {
                if (!key.startsWith(DataDictionary.DICT_OBJECT))
                    count++;
            }
        }
        dict.put("count",count);
        dict.put("refresh","/cache/refreshDict");
        List<Map<String,Object>> list = new ArrayList<>();
        list.add(dict);
        res.setRows(list);
        return res;
    }
}
