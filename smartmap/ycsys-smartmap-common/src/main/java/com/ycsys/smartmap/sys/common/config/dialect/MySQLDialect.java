package com.ycsys.smartmap.sys.common.config.dialect;

/**
 * Created by lixiaoxin on 2016/11/10.
 */
public class MySQLDialect extends org.hibernate.dialect.MySQLDialect{
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
