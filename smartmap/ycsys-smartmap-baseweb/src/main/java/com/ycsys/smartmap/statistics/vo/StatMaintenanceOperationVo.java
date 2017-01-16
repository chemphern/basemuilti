package com.ycsys.smartmap.statistics.vo;



/**
 * 运维操作vo
 * @author liweixiong
 * @date   2017年1月16日
 */
public class StatMaintenanceOperationVo {
	private long operateCount; // 操作次数
	private String operateType; //操作分类
	
	public long getOperateCount() {
		return operateCount;
	}
	public void setOperateCount(long operateCount) {
		this.operateCount = operateCount;
	}
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	
	
}
