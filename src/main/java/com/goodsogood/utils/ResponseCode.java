package com.goodsogood.utils;

/**
 * 作者：chenhao
 * 日期：12/14/20 11:45 AM
 **/
public class ResponseCode {
    //成功
    public static Integer SUCCESS_CODE = 0;
    //请求头错误
    public static Integer REQUEST_HEADER_ERROR = 9900;
    //系统错误
    public static Integer SYSTEM_ERROR = 9901;
    //请求过多，服务器繁忙
    public static Integer SERVER_BUSY = 9902;
    //资源未找到
    public static Integer RESOURCES_NOT_FOUND = 9404;
    //请求参数错误
    public static Integer REQUEST_PARAM_ERROR = 9906;
    //远程调用服务错误
    public static Integer REMOTE_INVOCATION_SERVICE_ERROR = 9907;
    //数据重复推送
    public static Integer REPEATED_PUSH_OF_DATA = 164;

}
