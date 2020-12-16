package com.goodsogood.response;

/**
 * 作者：chenhao
 * 日期：12/15/20 9:30 AM
 **/
public enum ResponseCodeEnum {

    SUCCESS_RESPONSE(0,"成功"),
    REQUEST_HEADER_ERROR(9900,"请求头错误"),
    SYSTEM_ERROR(9901,"系统错误"),
    SERVER_BUSY(9902,"请求过多，服务器繁忙"),
    RESOURCES_NOT_FOUND(9404,"资源未找到"),
    REQUEST_PARAM_ERROR(9906,"请求参数错误"),
    REMOTE_INVOCATION_SERVICE_ERROR(9907,"远程调用服务错误"),
    REPEATED_PUSH_OF_DATA(164,"数据重复推送"),
    ;

    private Integer code;
    private String desc;

    ResponseCodeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
