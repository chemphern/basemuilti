package com.ycsys.smartmap.sys.support;

import com.ycsys.smartmap.sys.util.DbUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.PropertiesFactoryBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义properties文件支持
 * 将系统配置表的信息加载到properties中
 * Created by lixiaoxin on 2016/11/3.
 */
public class MyPropertiesLoaderSupport extends PropertiesFactoryBean {

    private Pattern pattern = Pattern.compile("\\$\\{(.+?)}");

    private String type;

    private String sys_config_table = "sys_system_config";

    @Override
    protected Properties mergeProperties() throws IOException {
        Properties properties = super.mergeProperties();
        for (Iterator i$ = properties.keySet().iterator(); i$.hasNext(); ) { Object key = i$.next();
            properties.put(key, getParserProperties(properties, key.toString()));
        }

        String dialect = properties.getProperty("hibernate.dialect");
        String useDbProperties = properties.getProperty("use.dbProperties");
        if ((!"org.hibernate.dialect.H2Dialect".equals(dialect)) && ("true".equals(useDbProperties)) && (StringUtils.isNotEmpty(this.type))) {
            loadDbProperties(properties);
        }
        return properties;
    }

    @Override
    protected void loadProperties(Properties props) throws IOException {
        super.loadProperties(props);
    }

    private void loadDbProperties(Properties properties) {
        DbUtils db = new DbUtils();
        Connection conn = null;
        try {
            Class.forName(properties.getProperty("jdbc.driver"));
            conn = DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
            List<Map<String,Object>> list = db.queryForList(conn, "select * from " + sys_config_table + " where type = ?", new Object[] { this.type });
            for (Map<String,Object> map : list)
                properties.setProperty((String)map.get("key"), (String)map.get("value"));
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        finally {
            db.close(conn);
        }
    }



    private String getParserProperties(Properties properties, String key) {
        String o = properties.getProperty(key);
        if ((o != null) && (o.toString().indexOf("${") > -1)) {
            Matcher matcher = this.pattern.matcher(o);
            while (matcher.find()) {
                if (key.equals(matcher.group(1))) {
                    return "";
                }

                Object nv = getParserProperties(properties, matcher.group(1));
                if (nv == null)
                {
                    nv = System.getProperty(matcher.group(1));
                }

                o = o.replaceFirst("\\$\\{" + matcher.group(1) + "}", nv != null ? nv.toString() : "");
            }
        }
        return o;
    }

    public void setType(String type) {
        this.type = type;
    }
}
