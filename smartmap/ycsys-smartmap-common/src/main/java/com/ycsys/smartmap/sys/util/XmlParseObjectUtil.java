package com.ycsys.smartmap.sys.util;

import com.ycsys.smartmap.sys.common.config.parseobject.user.UserRootXmlObject;
import com.ycsys.smartmap.sys.common.exception.SysException;
import com.ycsys.smartmap.sys.common.utils.JaxbMapper;

import java.io.*;

/**
 * 权限工具类
 * Created by lixiaoxin on 2016/11/2.
 */
public class XmlParseObjectUtil {

    /**
     * 读取权限.xml文件，返回java实体
     * @param filePath 路径
     * @param  encoding 文件解析编码
     */
    public static <T> T getXmlObjectByFile(String filePath,String encoding,Class<T> t) throws Exception{
        InputStream is = new FileInputStream(filePath);
        BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = br.readLine();
            if (line == null){
                break;
            }
            sb.append(line).append("\r\n");
        }
        if (is != null) {
            is.close();
        }
        if (br != null) {
            br.close();
        }
       return JaxbMapper.fromXml(sb.toString(), t);
    }

    /**
     * 读取权限.xml文件，返回java实体
     * @param  is 文件流
     * @param  encoding 文件解析编码
     */
    public static <T> T getXmlObjectByStream(InputStream is,String encoding,Class<T> t) throws Exception{
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is, encoding));
        try {
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line).append("\r\n");
            }
            if (is != null) {
                is.close();
            }
            if (br != null) {
                br.close();
            }
        }catch (Exception e){
            is.close();
            br.close();
            throw new SysException("文件解析错误：" + e.getMessage() );
        }
        return JaxbMapper.fromXml(sb.toString(),t);
    }

    public static void main(String [] args){
        try {
           String ab = XmlParseObjectUtil.class.getClassLoader().getResource("").getPath();
            InputStream is = new FileInputStream(ab + "user.xml");
            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = br.readLine();
                if (line == null){
                    break;
                }
                sb.append(line).append("\r\n");
            }
            if (is != null) {
                is.close();
        }
            if (br != null) {
                br.close();
            }
            UserRootXmlObject org = JaxbMapper.fromXml(sb.toString(), UserRootXmlObject.class);
            System.out.println(org);
            is.close();
            br.close();
//            String code  = p.getModuleXmlObjectList().get(0).getMenuXmlObject().get(0).getUrlXmlObjects().get(0).getFuncXmlObjects().get(0).getCode();
//            String codes = code.trim().replaceAll("\n","").replaceAll(" ","");
//            System.out.println(codes);
        }catch (Exception e){
                e.printStackTrace();
        }
    }
}
