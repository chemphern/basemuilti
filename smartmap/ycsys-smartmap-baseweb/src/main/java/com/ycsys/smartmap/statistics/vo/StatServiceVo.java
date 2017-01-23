package com.ycsys.smartmap.statistics.vo;

/**
 * 服务统计Vo
 * @author liweixiong
 * @date   2017年1月17日
 */
public class StatServiceVo {
	private String name; 
	private long operateCount;// 操作次数
	public StatServiceVo(String name, long operateCount) {
		super();
		this.name = name;
		this.operateCount = operateCount;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getOperateCount() {
		return operateCount;
	}
	public void setOperateCount(long operateCount) {
		this.operateCount = operateCount;
	}
	
}
