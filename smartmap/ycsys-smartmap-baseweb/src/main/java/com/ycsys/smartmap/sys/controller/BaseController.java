package com.ycsys.smartmap.sys.controller;

import com.ycsys.smartmap.sys.common.utils.POIExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 基础controller
 * 自带一些公共方法
 * Created by lixiaoxin on 2016/11/15.
 */

public class BaseController {
    //文件转换成MultipartFile
    public MultipartFile getMultipartFile(MultipartHttpServletRequest multipartRequest) throws Exception {
        InputStream inputStream = null;
        for (Iterator it = multipartRequest.getFileNames(); it.hasNext(); ) {
            String key = (String) it.next();
            MultipartFile file = multipartRequest.getFile(key);
            if (file.getOriginalFilename().length() > 0) {
                return file;
            }
        }
        return null;
    }
    //数据格式转换——>List<ArrayList<String>>
    public List<ArrayList<String>> getFileDatas(MultipartFile file, String fileName) throws Exception {
        InputStream is = file.getInputStream();
        //excel文件格式的验证
        Workbook wb = new POIExcelUtil().validateExcel(fileName, is,1000);
        POIExcelUtil poiExcelUtil = new POIExcelUtil();
        List<ArrayList<String>> datas = poiExcelUtil.read(wb);
        is.close();
        return datas;
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
