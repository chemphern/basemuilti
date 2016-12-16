package com.ycsys.smartmap.sys.common.result;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * ligerGrid返回实体
 * Created by lixiaoxin on 2016/11/10.
 */
public class Grid<T> {

    @JsonProperty("Total")
    private long total;

    @JsonProperty("Rows")
    private List<T> rows;

    public Grid(){

    }

    public Grid(List<T> rows){
        this.rows = rows;
        if(rows != null){
            this.total = rows.size();
        }
    }

    public Grid(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
        if(rows != null) {
            this.total = rows.size();
        }
    }
}
