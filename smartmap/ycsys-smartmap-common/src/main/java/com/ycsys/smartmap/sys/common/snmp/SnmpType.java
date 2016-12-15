package com.ycsys.smartmap.sys.common.snmp;

/**
 * Created by lixiaoxin on 16-12-14.
 */  
public enum SnmpType {  
    WINDOWS("windows"),LINUX("linux"),ESXI("esxi");  
  
    private final String type;
  
    private SnmpType(String type) {
        this.type = type;  
    }  
  
    public String getType() {
        return this.type;  
    }  
}