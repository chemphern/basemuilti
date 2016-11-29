package com.ycsys.smartmap.sys.entity;

import java.io.Serializable;

/**
 *分页实体
 * @author lixiaoxin
 */
public class PageHelper implements Serializable{
	private static final long serialVersionUID = 4016086252369104759L;
	private int pageCount;
	private Integer rowCount = 0;
	private int pagesize = 10;
	private int page = 1;
	private int endIndex;
	private int beginIndex;
	private boolean usePager = true;
	
	private Object parameter;
	
	public void parse(int row){
		rowCount = row;
		if (rowCount > 0){
			if(pagesize <= 0) pagesize = 15;
			if(page <= 0)page = 1;
			pageCount = (rowCount - 1) / pagesize + 1;
		}
		else {
			pagesize = 0;
			page = 0;
			return;
		}
		if (page * pagesize > rowCount) {
			page = pageCount;
			endIndex = rowCount;
		} else {
			endIndex = page * pagesize;
		}
		beginIndex = (page - 1) * pagesize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getRowCount() {
		return rowCount;
	}

	public void setRowCount(Integer rowCount) {
		this.rowCount = rowCount;
	}

	public int getPagesize() {
		return pagesize;
	}

	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}

	public int getBeginIndex() {
		return beginIndex;
	}

	public void setBeginIndex(int beginIndex) {
		this.beginIndex = beginIndex;
	}

	public boolean isUsePager() {
		return usePager;
	}

	public void setUsePager(boolean usePager) {
		this.usePager = usePager;
	}

	public Object getParameter() {
		return parameter;
	}

	public void setParameter(Object parameter) {
		this.parameter = parameter;
	}

	@Override
	public String toString() {
		return "PageHelper{" +
				"pageCount=" + pageCount +
				", rowCount=" + rowCount +
				", pagesize=" + pagesize +
				", page=" + page +
				", endIndex=" + endIndex +
				", beginIndex=" + beginIndex +
				", usePager=" + usePager +
				", parameter=" + parameter +
				'}';
	}

	public int getFirstResult() {
		return (this.page - 1) * this.pagesize;
	}

	public int getMaxResults() {
		return this.pagesize;
	}
}
