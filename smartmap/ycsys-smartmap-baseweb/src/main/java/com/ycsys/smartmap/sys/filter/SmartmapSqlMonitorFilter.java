package com.ycsys.smartmap.sys.filter;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.JdbcParameter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.alibaba.druid.stat.JdbcSqlStat;
import com.alibaba.druid.support.json.JSONWriter;
import com.alibaba.druid.support.profile.ProfileEntry;
import com.alibaba.druid.support.profile.Profiler;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;

/**
 * Created by Administrator on 2016/10/28.
 */
public class SmartmapSqlMonitorFilter extends FilterEventAdapter {
//    @Override
//    public void dataSource_releaseConnection(FilterChain chain, DruidPooledConnection conn) throws SQLException {
//        JdbcDataSourceStat dataSourceStat = chain.getDataSource().getDataSourceStat();
//    }

    private final void internalAfterStatementExecute(StatementProxy statement, boolean firstResult,
                                                     int... updateCountArray) {
        final long nowNano = System.nanoTime();
        final long nanos = nowNano - statement.getLastExecuteStartNano();

        final JdbcSqlStat sqlStat = statement.getSqlStat();

        if (sqlStat != null) {
                long millis = nanos / (1000 * 1000);

                String parameters = buildSlowParameters(statement);
                 ProfileEntry x = Profiler.current();
                System.out.println("=======================smart_log======================:" + millis);
                System.out.println("=======================smart_log======================:" + statement.getLastExecuteSql());
                System.out.println("=======================smart_log======================:" + parameters);
            }
    }

    private String buildSlowParameters(StatementProxy statement) {
        JSONWriter out = new JSONWriter();

        out.writeArrayStart();
        for (int i = 0, parametersSize = statement.getParametersSize(); i < parametersSize; ++i) {
            JdbcParameter parameter = statement.getParameter(i);
            if (i != 0) {
                out.writeComma();
            }
            if (parameter == null) {
                continue;
            }

            Object value = parameter.getValue();
            if (value == null) {
                out.writeNull();
            } else if (value instanceof String) {
                String text = (String) value;
                if (text.length() > 100) {
                    out.writeString(text.substring(0, 97) + "...");
                } else {
                    out.writeString(text);
                }
            } else if (value instanceof Number) {
                out.writeObject(value);
            } else if (value instanceof java.util.Date) {
                out.writeObject(value);
            } else if (value instanceof Boolean) {
                out.writeObject(value);
            } else if (value instanceof InputStream) {
                out.writeString("<InputStream>");
            } //else if (value instanceof NClob) {
               // out.writeString("<NClob>");
            //}
            else if (value instanceof Clob) {
                out.writeString("<Clob>");
            } else if (value instanceof Blob) {
                out.writeString("<Blob>");
            } else {
                out.writeString('<' + value.getClass().getName() + '>');
            }
        }
        out.writeArrayEnd();

        return out.toString();
    }


    @Override
    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        internalAfterStatementExecute(statement, false, updateCount);
    }


    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        internalAfterStatementExecute(statement, true);
    }


    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean firstResult) {
        internalAfterStatementExecute(statement, firstResult);
    }
}
