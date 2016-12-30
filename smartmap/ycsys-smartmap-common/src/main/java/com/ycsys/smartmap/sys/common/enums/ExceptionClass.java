package com.ycsys.smartmap.sys.common.enums;

/**
 * 异常类枚举
 * Created by lixiaoxin on 2016/12/26.
 */
public enum ExceptionClass {
    /****/
    ASException("com.ycsys.smartmap.sys.common.exception.ASException",9),
    DbException("com.ycsys.smartmap.sys.common.exception.DbException",8),
    GisServerException("com.ycsys.smartmap.sys.common.exception.GisServerException",7),
    NetworkException("com.ycsys.smartmap.sys.common.exception.NetworkException",6),
    ServerException("com.ycsys.smartmap.sys.common.exception.ServerException",5),
    DaoException("com.ycsys.smartmap.sys.common.exception.DaoException",4),
    ServiceException("com.ycsys.smartmap.sys.common.exception.ServiceException",3),
    HServiceException("org.hibernate.service.spi.ServiceException",2),
    SysException("com.ycsys.smartmap.sys.common.exception.SysException",1),
    Other("",0);

    private final String value;

    private final int type;

    private ExceptionClass(String value,int type) {
        this.value = value;
        this.type = type;
    }
    public String getValue(){
        return value;
    }

    public int getType(){
        return type;
    }
    public static ExceptionClass findByValue(String value){
        switch(value){
            case "com.ycsys.smartmap.sys.common.exception.ASException":
                return ASException;
            case "com.ycsys.smartmap.sys.common.exception.DaoException":
                return DaoException;
            case "com.ycsys.smartmap.sys.common.exception.DbException":
                return DbException;
            case "com.ycsys.smartmap.sys.common.exception.GisServerException":
                return GisServerException;
            case "com.ycsys.smartmap.sys.common.exception.NetworkException":
                return NetworkException;
            case "com.ycsys.smartmap.sys.common.exception.ServerException":
                return ServerException;
            case "com.ycsys.smartmap.sys.common.exception.ServiceException":
                return ServiceException;
            case "org.hibernate.service.spi.ServiceException":
                return HServiceException;
            case "com.ycsys.smartmap.sys.common.exception.SysException":
                return SysException;
            default:
                return null;
        }
    }
    public static ExceptionClass findByType(int type){
        switch (type){
            case 1:
                return SysException;
            case 2:
                return HServiceException;
            case 3:
                return ServiceException;
            case 4:
                return DaoException;
            case 5:
                return ServerException;
            case 6:
                return NetworkException;
            case 7:
                return GisServerException;
            case 8:
                return DbException;
            case 9:
                return ASException;
            default:
                return null;
        }
    }
}
