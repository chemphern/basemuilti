package com.ycsys.smartmap.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.ycsys.smartmap.sys.common.exception.SysException;
import com.ycsys.smartmap.sys.common.utils.POIExcelUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

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
        Workbook wb = new POIExcelUtil().validateExcel(fileName, is, 1000);
        POIExcelUtil poiExcelUtil = new POIExcelUtil();
        List<ArrayList<String>> datas = poiExcelUtil.read(wb);
        is.close();
        return datas;
    }

    //数据格式转换——>List<ArrayList<String>>
    public List<ArrayList<String>> getFileDatas(InputStream is, String fileName) throws Exception {
        //excel文件格式的验证
        Workbook wb = new POIExcelUtil().validateExcel(fileName, is, 1000);
        POIExcelUtil poiExcelUtil = new POIExcelUtil();
        List<ArrayList<String>> datas = poiExcelUtil.read(wb);
        is.close();
        return datas;
    }

    /**
     * 导出文件
     *
     * @param file
     * @param fileName(包括文件的后缀，如:xxx.doc)
     * @param contentType(application/msword|zip，text/xml)
     * @param request
     * @param response
     */
    public void doExport(File file, String fileName, String contentType, HttpServletRequest request, HttpServletResponse response) {
        InputStream in = null;
        DataInputStream din = null;
        OutputStream out = null;
        DataOutputStream dout = null;
        try {
            //防止文件名乱码
            if (request.getHeader("USER-AGENT").toLowerCase().indexOf("msie") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
                fileName = fileName.replace(".", "%2e");
            } else if (request.getHeader("USER-AGENT").toLowerCase()
                    .indexOf("firefox") > 0) {
                fileName = "=?UTF-8?B?"
                        + (new String(Base64.encodeBase64(fileName
                        .getBytes("UTF-8")))) + "?=";
            } else {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            }
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            in = new FileInputStream(file);
            din = new DataInputStream(new BufferedInputStream(in));
            out = response.getOutputStream();
            dout = new DataOutputStream(new BufferedOutputStream(out));
            int n;
            byte buf[] = new byte[2048];
            while ((n = din.read(buf)) != -1) {
                dout.write(buf, 0, n);
            }
            dout.flush();
        } catch (Exception e) {
            throw new SysException("导出文件失败...");
        } finally {
            try {
                if (dout != null) {
                    dout.close();
                }
                if (out != null) {
                    out.close();
                }
                if (din != null) {
                    din.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                throw new SysException("关闭流失败...");
            }
        }


    }

    /**
     * 根据默认样式创建excel表（单个sheet）
     *
     * @param sheetName      sheet表格名称
     * @param widths         表格宽度，为空则不设置
     * @param contentReflect 设置表格头，及表格内容对象属性映射
     * @param datas          填充表格内容的数据
     * @param clazz          用于映射数据和属性的关系
     * @return HSSFWorkbook 返回一个表格对象
     **/
    public <T> HSSFWorkbook getDefaultExcel(String sheetName, int widths[], Map<String, String> contentReflect, List<T> datas, Class<T> clazz) {
        // 创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFFont font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeight((short) 180);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        HSSFSheet sheet = wb.createSheet(sheetName);

        //设置单元格宽度
        if (widths != null) {
            for (int x = 0; x < widths.length; x++) {
                sheet.setColumnWidth(x, widths[x]);
            }
        }
        // 标题样式
        HSSFCellStyle headStyle = wb.createCellStyle();
        headStyle.setFont(font);
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        // headStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        // 边框
        headStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        headStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        headStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headStyle.setRightBorderColor(HSSFColor.BLACK.index);
        headStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headStyle.setTopBorderColor(HSSFColor.BLACK.index);

        // 数据样式
        HSSFCellStyle dataStyle = wb.createCellStyle();
        dataStyle.setWrapText(true);
        dataStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 边框
        dataStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        dataStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        dataStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        dataStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        dataStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        dataStyle.setRightBorderColor(HSSFColor.BLACK.index);
        dataStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        dataStyle.setTopBorderColor(HSSFColor.BLACK.index);

        // 在索引0的位置创建行（最顶端的行）
        HSSFRow row = sheet.createRow((short) 0);
        row.setHeight((short) 400);
        // 标题
        int index = 0;
        for (Map.Entry<String, String> rkey : contentReflect.entrySet()) {
            HSSFCell cell = row.createCell(index++);
            cell.setCellValue(rkey.getKey());
            cell.setCellStyle(headStyle);
        }
        Field[] fds = clazz.getDeclaredFields();
        int rowIndex = 1;
        for (int x = 0; x < datas.size(); x++) {
            HSSFRow row1 = sheet.createRow((short) rowIndex++);
            //把数据从java对象转至map
            Map<String, Object> refMap = new HashMap<String, Object>();
            try {
                for (int y = 0; y < fds.length; y++) {
                    fds[y].setAccessible(true);
                    refMap.put(fds[y].getName(), fds[y].get(datas.get(x)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            int dataCount = 0;
            for (Map.Entry<String, String> rkey : contentReflect.entrySet()) {
                String cellAttrName = rkey.getValue();
                Object cellValue = null;
                if (cellAttrName == null) {
                    cellValue = "";
                } else if (cellAttrName.equals("")) {
                    cellValue = "";
                } else if (cellAttrName.equals("#0")) {//该符号时表示从0开始的序号
                    cellValue = x;
                } else if (cellAttrName.equals("#1")) {//表示从1开始的序号
                    cellValue = x + 1;
                } else if (cellAttrName.startsWith("%")) {//自定义字符串
                    cellValue = cellAttrName.substring(1, cellAttrName.length());
                } else if(cellAttrName.startsWith("{")){//数字字典映射 例如：{"key":"status","value":{"1":"正常","2":"异常"}}
                    JSONObject ref = (JSONObject) JSONObject.parse(cellAttrName);
                    String key = (String) ref.get("key");
                    JSONObject reflect = (JSONObject) ref.get("value");
                    String prevValue = String.valueOf(refMap.get(key));
                    cellValue = reflect.get(prevValue);
                }else if(cellAttrName.indexOf("|") > -1){//处理日期Date格式 例如：requestDate|yyyy-MM-dd HH:mm:ss
                    String [] split = cellAttrName.split("\\|");
                    SimpleDateFormat format=new SimpleDateFormat(split[1]);
                    Object res  = refMap.get(split[0]);
                    cellValue = format.format(res);
                }
                else if(cellAttrName.indexOf(".") > -1){//类中类  例如：requestUser.name:com.ycsys.smartmap.sys.entity.User
                    String [] split = cellAttrName.split(":");
                    String [] objectsplit = split[0].split("\\.");
                    String [] clazzsplit = split[1].split(",");
                   try {
                       Object res = refMap.get(objectsplit[0]);
                       for (int i = 0; i < clazzsplit.length; i++) {
                           Class clazz1 = Class.forName(clazzsplit[i]);
                           Field[] fds1 = clazz1.getDeclaredFields();
                           Map<String, Object> clazz1Map = new HashMap<String, Object>();
                           for (int y = 0; y < fds1.length; y++) {
                               fds1[y].setAccessible(true);
                               clazz1Map.put(fds1[y].getName(), fds1[y].get(res));
                           }
                           String key = objectsplit[i+1];
                           res = clazz1Map.get(key);
                       }
                       cellValue = res;
                   }catch (Exception e){
                       e.printStackTrace();
                   }
                }else {
                    cellValue = refMap.get(cellAttrName);
                }
                HSSFCell cell = row1.createCell(dataCount++);
                cell.setCellStyle(dataStyle);
                if(cellValue == null){
                    cellValue = "";
                }
                cell.setCellValue(String.valueOf(cellValue));
            }
        }
        return wb;
    }
}
