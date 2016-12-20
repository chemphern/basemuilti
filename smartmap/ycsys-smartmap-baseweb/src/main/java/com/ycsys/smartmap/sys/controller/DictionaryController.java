package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.sys.common.exception.SysException;
import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.entity.Dictionary;
import com.ycsys.smartmap.sys.entity.DictionaryItem;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.DictService;
import com.ycsys.smartmap.sys.util.DataDictionary;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import java.util.*;

/**
 * 字典controller
 * Created by lixiaoxin on 2016/11/11.
 */
@Controller
@RequestMapping("/dictionary")
public class DictionaryController extends BaseController{
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
    /**
     * 分页查询所有字典
     * **/
    @RequiresPermissions(value = "sys-dict-listData")
    @ResponseBody
    @RequestMapping("/listData")
    public Grid<Dictionary> listData(PageHelper page){
        Grid<Dictionary> g = new Grid<>();
        g.setRows(dictService.findAll(page));
        g.setTotal(dictService.countAll());
        return g;
    }

    /**
     * 不分页查询字典项
     * **/
    @RequiresPermissions(value = "sys-dict-listItemData")
    @RequestMapping("listItemData")
    @ResponseBody
    public Grid<DictionaryItem> listItemData(String dictId){
        Grid<DictionaryItem> g = new Grid<>();
        g.setRows(dictService.findItemByDictId(dictId));
        return g;
    }

    /**
     * 创建字典
     * **/
    @RequiresPermissions(value = "sys-dict-create")
    @ResponseBody
    @RequestMapping(value="/create",method = RequestMethod.POST)
    public ResponseEx create(String name,String code,String memo,
                             String [] showOrder,String [] itemName,String [] itemValue,String [] isShow){
        ResponseEx ex = new ResponseEx();
        try{
            Dictionary dictionary = new Dictionary(name,code,memo);
            Set<DictionaryItem> items = new LinkedHashSet<>();
            if(itemName.length == itemValue.length){
                for(int x = 0 ;x < itemName.length;x++){
                    DictionaryItem i = new DictionaryItem(itemName[x],itemValue[x],Integer.parseInt(showOrder[x]),(short)Integer.parseInt(isShow[x]));
                    i.setDictionary(dictionary);
                    items.add(i);
                }
            }
            dictionary.setDictionaryItems(items);
            dictService.saveOrUpdate(dictionary);
            ex.setSuccess("保存成功");
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }

    /**
     * 修改字典
     * **/
    @RequiresPermissions(value = "sys-dict-update")
    @ResponseBody
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    public ResponseEx update(Dictionary dictionary){
        ResponseEx ex = new ResponseEx();
        try{
            dictService.saveOrUpdate(dictionary);
            ex.setSuccess("修改成功");
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }

    /**
     * 新增字典子项
     * **/
    @RequiresPermissions(value = "sys-dict-createItem")
    @ResponseBody
    @RequestMapping(value = "/createItem",method = RequestMethod.POST)
    public ResponseEx createItem(String dict_id,DictionaryItem item){
        ResponseEx ex = new ResponseEx();
        try{
            Dictionary d = dictService.getById(dict_id);
            if(d == null){
                ex.setFail("修改失败，字典不存在");
            }else {
                item.setDictionary(d);
                dictService.saveOrUpdateItem(item);
                ex.setSuccess("修改成功");
            }
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }

    /**
     * 修改字典子项
     * **/
    @RequiresPermissions(value = "sys-dict-updateItem")
    @ResponseBody
    @RequestMapping(value = "/updateItem",method = RequestMethod.POST)
    public ResponseEx updateItem(DictionaryItem item){
        ResponseEx ex = new ResponseEx();
        try{
            dictService.saveOrUpdateItem(item);
            ex.setSuccess("修改成功");
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }
    /**
     * 删除字典
     * **/
    @RequiresPermissions(value = "sys-dict-delete")
    @ResponseBody
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    public ResponseEx delete(Dictionary dictionary){
        ResponseEx ex = new ResponseEx();
        try{
            dictService.delete(dictionary);
            ex.setSuccess("删除成功");
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }

    /**
     * 删除字典子项
     * **/
    @RequiresPermissions(value = "sys-dict-deleteItem")
    @ResponseBody
    @RequestMapping(value = "/deleteItem",method = RequestMethod.POST)
    public ResponseEx deleteItem(DictionaryItem dictionaryItem){
        ResponseEx ex = new ResponseEx();
        try{
            dictService.deleteItem(dictionaryItem);
            ex.setSuccess("删除子项成功");
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }

    @RequiresPermissions(value = "sys-dict-importDictionary")
    @RequestMapping(value="/importDictionary",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEx importDictionary(MultipartHttpServletRequest multipartRequest){
        ResponseEx ex = new ResponseEx();
        try {
            MultipartFile file = getMultipartFile(multipartRequest);
            String fileName = file.getOriginalFilename();
            List<ArrayList<String>> datas = getFileDatas(file, fileName);
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }


    @RequiresPermissions(value = "sys-dict-list")
    @RequestMapping("/list")
    public String list(){
        return "/dictionary/list";
    }

    @RequiresPermissions(value = "sys-dict-addDictv")
    @RequestMapping("/addDictv")
    public String addDictv(){
        return "/dictionary/addDictionary";
    }

    @RequiresPermissions(value = "sys-dict-updateDictv")
    @RequestMapping("/updateDictv")
    public String updateDictv(String id, Model model){
        Dictionary dictionary = dictService.getById(id);
        model.addAttribute("dictionary",dictionary);
        return "/dictionary/updateDictionary";
    }

    @RequiresPermissions(value = "sys-dict-addItemv")
    @RequestMapping("/addItemv")
    public String addItemv(String dictId,Model model){
        model.addAttribute("dictId",dictId);
        return "/dictionary/addDictionaryItem";
    }

    @RequiresPermissions(value = "sys-dict-updateDictItemv")
    @RequestMapping("/updateDictItemv")
    public String updateDictItemv(String itemId,Model model){
        DictionaryItem item = dictService.getItemById(itemId);
        model.addAttribute("item",item);
        return "/dictionary/updateDictionaryItem";
    }
}
