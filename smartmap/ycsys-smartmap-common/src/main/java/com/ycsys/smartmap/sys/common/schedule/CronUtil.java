package com.ycsys.smartmap.sys.common.schedule;

/**
 * cron表达式工具类
 * Created by lixiaoxin on 2016/12/20.
 */
public class CronUtil {

    //根据秒数生成cron表达式(最多指定到小时)
    public static String getCronByMillions(long millions){
        //小时数
        long hour = millions/60/60 ;
        //分钟数
        long second = millions / 60  - hour * 60;

        long mills = millions  - hour * 60 *60 - second * 60;

        String hourc = hour > 0 ? "0/" + hour :"*";
        String secondc = second > 0 ? "0/" + second : hour > 0 ?"0":"*";
        String millc = mills > 0 ? "0/" + mills : hour > 0 ? "0" : second >0 ?"0":"*";
        return millc +" " + secondc + " "+ hourc + " * * ?";
    }
}
