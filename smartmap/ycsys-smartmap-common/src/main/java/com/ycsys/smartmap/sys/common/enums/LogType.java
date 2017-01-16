package com.ycsys.smartmap.sys.common.enums;

/**
 * 日志类型
 * Created by lixiaoxin on 2017/1/4.
 */
public enum LogType {
    System(1,"基础类"),
    Server(2,"服务管理类"),
    Resource(3,"资源管理类"),
    Statistics(4,"统计分析类"),
    Layer(5,"图层类"),
    Monitor(6,"监控类");

    private final String value;

    private final int type;

    private LogType(int type,String value) {
        this.value = value;
        this.type = type;
    }
    public String getValue(){
        return value;
    }

    public int getType(){
        return type;
    }
    
    /**
     * 通过类型代码找相应的值
     * @param type
     * @return
     */
    public static String findValueByType(int type) {
        switch (type){
            case 1:
                return System.value;
            case 2:
                return Server.value;
            case 3:
                return Resource.value;
            case 4:
                return Statistics.value;
            case 5:
                return Layer.value;
            case 6:
                return Monitor.value;
            default:
                return "";
        }
    }
}
