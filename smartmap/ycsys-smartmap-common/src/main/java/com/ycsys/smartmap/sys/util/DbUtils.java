package com.ycsys.smartmap.sys.util;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * 简单数据库连接工具
 * Created by lixiaoxin on 2016/11/3.
 */
public class DbUtils {
    private static final Logger logger = LoggerFactory.getLogger(DbUtils.class);

    private QueryRunner query = new QueryRunner();

    public Connection openConnection(DataSource dataSource, boolean autoCommit)
    {
        try {
            Connection conn = dataSource.getConnection();

            conn.setAutoCommit(autoCommit);

            this.query = new QueryRunner();

            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> queryForList(Connection conn, String sql, Object[] params)
    {
        try
        {
            return (List)this.query.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public <T> T query(Connection conn, String sql, ResultSetHandler<T> rsh, Object[] params)
    {
        try
        {
            return this.query.query(conn, sql, rsh, params);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public int execute(Connection conn, String sql, Object[] params)
    {
        try
        {
            return this.query.update(conn, sql, params);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public void commit(Connection conn)
    {
        try
        {
            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollback(Connection conn)
    {
        try
        {
            if (conn != null)
                conn.rollback();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close(Connection conn)
    {
        try
        {
            if (conn != null)
                conn.close();
        }
        catch (SQLException e)
        {
        }
    }
}
