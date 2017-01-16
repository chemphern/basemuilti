package com.ycsys.smartmap.sys.common.config.dialect;

/**
 * Created by lixiaoxin on 2016/11/10.
 */
public class MySQLDialect extends org.hibernate.dialect.MySQL5Dialect{
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
