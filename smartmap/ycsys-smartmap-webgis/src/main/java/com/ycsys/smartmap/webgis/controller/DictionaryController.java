package com.ycsys.smartmap.webgis.controller;

import com.ycsys.smartmap.sys.common.exception.SysException;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.service.DictService;
import com.ycsys.smartmap.sys.util.DataDictionary;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 字典controller
 * Created by lixiaoxin on 2016/11/11.
 */
@Controller
@RequestMapping("/dictionary")
public class DictionaryController{
    @Resource
    private DictService dictService;

    @RequiresPermissions(value = "sys-dict-getDictItemByCode")
    @RequestMapping("getDictItemByCode")
    @ResponseBody
    public ResponseEx getDictItemByCode(String code){
        ResponseEx ex = new ResponseEx();
        try{
            Map<String,Object> map = DataDictionary.get(code);
            if(map == null || map.size() < 0){
                throw new SysException("该字典不存在！");
            }
            ex.setRetData(map);
            ex.setSuccess("加载字典成功");
        }   catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }

}
