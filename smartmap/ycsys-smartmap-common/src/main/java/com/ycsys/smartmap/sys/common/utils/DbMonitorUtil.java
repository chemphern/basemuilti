package com.ycsys.smartmap.sys.common.utils;

import com.ycsys.smartmap.sys.util.DbUtils;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 获取统计和监控关心的数据库信息
 * Created by lixiaoxin on 2016/12/28.
 */
public class DbMonitorUtil {
    //数组： 0-oracle , 1-mysql
    public static final String [] thread_connect = {"select count(*) as Value from v$session where username is not null",
                                            "SHOW STATUS LIKE 'Threads_connected'"};
    public static Map<String,Object> getDbMsg(String driver,String url,String username,String password){
        String type = "";
        int x = 0;
        if(driver.indexOf("oracle") > -1){
            x = 0;
            type="oracle";
        }else if(driver.indexOf("mysql") > -1){
            x = 1;
            type="mysql";
        }
        Map<String,Object> res = new HashMap<String,Object>();
        DbUtils db = new DbUtils();
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url,username , password);
            Map<String, Object> ts = db.query(conn,thread_connect[x], new MapHandler(),null);
            String connect = (String) ts.get("Value");
            res.put("connect",connect);
            res.put("code","1");
            res.put("type",type);
        } catch (Exception e) {
            res.put("code","0");
            res.put("msg",e.getMessage());
            return res;
        }finally {
            db.close(conn);
        }
        return res;
    }

    public static void main(String [] args) throws Exception {
        String file = "db.properties";
        InputStream is = DbMonitorUtil.class.getResource("/" + file).openStream();
        Properties properties = new Properties();
        properties.load(is);
        String driver = properties.getProperty("jdbc.driver");
        getDbMsg(driver,properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
        is.close();
    }
}
