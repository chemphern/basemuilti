package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.sys.common.result.Grid;
import com.ycsys.smartmap.sys.common.result.ResponseEx;
import com.ycsys.smartmap.sys.entity.Area;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.AreaService;
import com.ycsys.smartmap.sys.util.DbUtils;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lixiaoxin on 2016/11/15.
 */
@RequestMapping("/area")
@Controller
public class AreaController extends BaseController{

    @Resource
    private AreaService areaService;

    @RequiresPermissions(value = "sys-area-list")
    @RequestMapping("/list")
    public String list(){
        return "/area/list";
    }

    @RequiresPermissions(value = "sys-area-listData")
    @RequestMapping("/listData")
    @ResponseBody
    public Grid<Area> listData(String code,PageHelper page){
        Grid<Area> g = new Grid<>();
        //不为空，加载市或者区
        List<Area> ls = null;
        if(code != null){
            code = code.trim();
            //加载市
            if(code.charAt(code.length()-2) == '0' && code.charAt(code.length()- 1) == '0'){
                ls = areaService.getCities(code.substring(0,2));
            }else{//加载区
                ls = areaService.getCounties(code);
            }
        }else{//加载省份
            ls = areaService.getProvinces(page);
        }
        if((ls !=null && ls.size() > 0 && ls.get(0).getCode().equals(code)) || ls== null){
            ls = new ArrayList<>();
        }
        g.setRows(ls);
        return g;
    }

    @RequiresPermissions(value = "sys-area-getAreas")
    @RequestMapping("/getAreas")
    @ResponseBody
    public ResponseEx getAreas(String code){
        ResponseEx ex = new ResponseEx();
        List<Area> ls = null;
        //不为空，加载市或者区
        if(code != null){
            code = code.trim();
            //加载市
            if(code.charAt(code.length()-2) == '0' && code.charAt(code.length()- 1) == '0'){
                ls = areaService.getCities(code.substring(0,2));
            }else{//加载区
                ls = areaService.getCounties(code);
            }
        }else{//加载省份
            ls = areaService.getProvinces();
        }
        ex.setRetData(ls);
        return ex;
    }

    @RequiresPermissions(value="sys-area-saveOrUpdate")
    @RequestMapping("/saveOrUpdate")
    @ResponseBody
    public ResponseEx saveOrUpdate(Area area){
        ResponseEx ex = new ResponseEx();
        try{
            areaService.saveOrUpdate(area);
            ex.setSuccess("保存成功！");
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }

    @RequiresPermissions(value = "sys-area-delete")
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseEx delete(String id){
        ResponseEx ex = new ResponseEx();
        try{
            areaService.delete(id);
            ex.setSuccess("删除成功！");
        }catch (Exception e){
            ex.setFail(e.getMessage());
        }
        return ex;
    }

    @RequiresPermissions(value = "sys-area-addAreav")
    @RequestMapping("/addAreav")
    public String addAreav(){
        return "/area/addArea";
    }

    @RequiresPermissions(value = "sys-area-updatev")
    @RequestMapping("/updateAreav")
    public String updateAreav(String id,Model model){
        Area area = areaService.getAreaById(id);
        model.addAttribute("area",area);
        return "/area/updateArea";
    }

    //数据导入
    public static void main(String [] args){
        try {
            //读取xls
            String file = "广东省行政区划及其行政代码.xls";
            InputStream is = AreaController.class.getResource("/" + file).openStream();
            BaseController b = new BaseController();
            List<ArrayList<String>> datas =  b.getFileDatas(is,file);
            System.out.println(datas);
            //读取配置文件
            Properties properties = new Properties();
            String profile = "db.properties";
            InputStream pis = AreaController.class.getResource("/" + profile).openStream();
            properties.load(pis);
            //创建数据库连接
            DbUtils db = new DbUtils();
            String driver = properties.getProperty("jdbc.driver");
            //如果是oracle，则sql需要特殊处理
            boolean isOracle = driver.indexOf("oracle") > -1;
            String insertSql = "";
            if(isOracle){
                String sequence = "HIBERNATE_SEQUENCE";
                insertSql = "insert into sys_area (id,name,code,type,all_name) values (" +sequence +  ".nextval,?,?,?,?)";
            }else {
                insertSql = "insert into sys_area (name,code,type,all_name) values (?,?,?,?)";
            }
            Class.forName(driver);
            Connection conn = DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
            conn.setAutoCommit(false);
            int p_n = 0;
            int c_n = 0;
            int ct_n = 0;
            try {
                //获取省数据
                ArrayList<String> province = datas.get(1);
                String province_name = province.get(1);
                String province_code = province.get(2);
                Object[] params = {province_code};
                //查询该省在数据中是否有数据
                Map<String, Object> res = db.query(conn, "select count(*) count from sys_area where code = ?", new MapHandler(), params);
                int num = Integer.parseInt(String.valueOf(res.get("count")));

                //无数据则导入
                if (num < 1) {
                    List<String> p = new ArrayList<>();
                    p.add(province_name);
                    p.add(province_code);
                    p.add("1");
                    p.add(province_name);
                    db.execute(conn, insertSql, p.toArray());
                    p_n++;
                    String temp_cityName = "";
                    //迭代市区
                    for (int x = 2; x < datas.size(); x++) {
                        ArrayList<String> data = datas.get(x);
                        String cityName = data.get(1);
                        String cityCode = data.get(2);
                        String countyName = data.get(3);
                        String countyCode = data.get(4);
                        //市
                        if (!cityName.trim().equals("") && !cityCode.trim().equals("")) {
                            List<String> ct = new ArrayList<>();
                            temp_cityName = cityName;
                            ct.add(cityName);
                            ct.add(cityCode);
                            ct.add("2");
                            ct.add(province_name + cityName);
                            db.execute(conn, insertSql, ct.toArray());
                            c_n ++;
                        }
                        //县
                        List<String> cp = new ArrayList<>();
                        cp.add(countyName);
                        cp.add(countyCode);
                        cp.add("3");
                        cp.add(province_name +temp_cityName +  countyName);
                        db.execute(conn, insertSql, cp.toArray());
                        ct_n++;
                    }
                    db.commit(conn);
                    System.out.println("=====================成功插入" + p_n + "条省级数据============================");
                    System.out.println("=====================成功插入" + c_n + "条市级数据============================");
                    System.out.println("=====================成功插入" + ct_n + "条区级数据============================");
                }
            }catch (Exception e){
                db.rollback(conn);
                throw new Exception(e);
            }
            db.close(conn);
            pis.close();
            is.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
