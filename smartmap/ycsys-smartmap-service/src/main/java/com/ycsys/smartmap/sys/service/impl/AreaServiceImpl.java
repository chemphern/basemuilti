package com.ycsys.smartmap.sys.service.impl;

import com.ycsys.smartmap.sys.common.utils.POIExcelUtil;
import com.ycsys.smartmap.sys.dao.AreaDao;
import com.ycsys.smartmap.sys.entity.Area;
import com.ycsys.smartmap.sys.entity.PageHelper;
import com.ycsys.smartmap.sys.service.AreaService;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by lixiaoxin on 2016/11/15.
 */
@Service("/areaService")
public class AreaServiceImpl implements AreaService{

    private static final Logger logger = Logger.getLogger(AreaServiceImpl.class);

    @Resource
    private AreaDao areaDao;

    /***省份*/
    @Override
    public List<Area> getProvinces() {
        Object[] p = {(short)1};
        return areaDao.find("from Area where type = ? order by code",p);
    }

    @Override
    public List<Area> getProvinces(PageHelper page) {
        return areaDao.find("from Area where type = ? order by code",new Object[]{(short)1},page);
    }

    /**城市**/
    @Override
    public List<Area> getCities(String substring) {
        Object[] ps = {(short)2,substring + "%"};
        return areaDao.find("from Area where type = ? and code like ? order by code",ps);
    }

    @Override
    public List<Area> getCounties(String code) {
        Object[] ps = {(short)3,code + "%"};
        return areaDao.find("from Area where type= ? and code like ? order by code",ps);
    }

    /***初始化地区*/
    @Override
    public void initArea() throws Exception {
        long count = areaDao.count("select count(*) from Area");
        if(count < 1) {
            //读取xls
            String file = "广东省行政区划及其行政代码.xls";
            InputStream is = AreaServiceImpl.class.getResource("/template/" + file).openStream();
            List<ArrayList<String>> datas = getFileDatas(is, file);
            //读取配置文件
            Properties properties = new Properties();
            String profile = "db.properties";
            InputStream pis = AreaServiceImpl.class.getResource("/" + profile).openStream();
            properties.load(pis);
            int p_n = 0;
            int c_n = 0;
            int ct_n = 0;
            //获取省数据
            ArrayList<String> province = datas.get(1);
            String province_name = province.get(1);
            String province_code = province.get(2);
            Object[] params = {province_code};
            //查询该省在数据中是否有数据
            long num = areaDao.count("select count(*) from Area where code = ?", new Object[]{province_code});
            //无数据则导入
            if (num < 1) {
                Area pro = new Area(province_name, province_code, Short.parseShort("1"), province_name);
                areaDao.save(pro);
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
                        temp_cityName = cityName;
                        Area cit = new Area(cityName, cityCode, Short.parseShort("2"), province_name + cityName);
                        areaDao.save(cit);
                        c_n++;
                    }
                    //县
                    Area cpt = new Area(countyName, countyCode, Short.parseShort("3"), province_name + temp_cityName + countyName);
                    areaDao.save(cpt);
                    ct_n++;
                }
                logger.info("=====================成功插入" + p_n + "条省级数据============================");
                logger.info("=====================成功插入" + c_n + "条市级数据============================");
                logger.info("=====================成功插入" + ct_n + "条区级数据============================");
            }
            pis.close();
            is.close();
        }
    }

    @Override
    public void delete(String id) {
        Area a = areaDao.get(Area.class,Integer.parseInt(id));
        String code = a.getCode();
        if(code.length() == 4){
            code = code.substring(0,3);
        }
        long count = areaDao.count("select count(*) from Area where code like ? and code <> ?",new Object[]{code + "%",a.getCode()});
        if(count > 0){
            throw new ServiceException("区域下有子区域，不能删除！");
        }
        areaDao.delete(a);
    }

    @Override
    public void saveOrUpdate(Area area) {
        long count = 0;
        if(area.getId() == null){
            count = areaDao.count("select count(*) from Area where code = ? or name = ?",new Object[]{area.getCode(),area.getName()});
        }else{
            count = areaDao.count("select count(*) from Area where id <> ? and ( code = ? or name = ? )",new Object[]{area.getId(),area.getCode(),area.getName()});
        }
        if(count > 0){
            throw new ServiceException("名称或者编码已存在！");
        }
        areaDao.saveOrUpdate(area);
    }

    @Override
    public Area getAreaById(String id) {
        return areaDao.get(Area.class,Integer.parseInt(id));
    }

    //数据格式转换——>List<ArrayList<String>>
    public List<ArrayList<String>> getFileDatas(InputStream is, String fileName) throws Exception {
        //excel文件格式的验证
        Workbook wb = new POIExcelUtil().validateExcel(fileName, is,1000);
        POIExcelUtil poiExcelUtil = new POIExcelUtil();
        List<ArrayList<String>> datas = poiExcelUtil.read(wb);
        is.close();
        return datas;
    }
}
