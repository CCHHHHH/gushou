package com.goodsogood.response;

/**
 * 作者：chenhao
 * 日期：12/15/20 8:53 AM
 **/
public enum K3ResponseCodeEnum {

    SUCCESS_RESPONSE(200,"成功"),
    AUTH_CODE_INCORRECT(1201,"授权码参数不正确"),
    AUTH_CODE_NOTFOUND(1202,"不存在的授权码"),
    AUTH_CODE_NOLEDGER(1203,"授权码没有对应的账套"),
    TOKEN_LOGIN_FAILED(1204,"Token登录失败"),
    TOKEN_NOTFOUND(1205,"Token不存在"),
    TOKEN_NOT_AGREE(1206,"IP地址与创建Token时的IP不一致"),
    TOKEN_INVALID(1207,"无效的Token"),
    TOKEN_OVERDUE(1208,"Token已过期"),
    TOKEN_NOT_CONTAIN(1209,"未包含Token"),

    WITHOUT_PERMISSION(1301,"没有权限"),
    LIMIT_ACCESS_TIMES(1302,"限制访问次数"),

    DUPLICATE_DOCUMENT_NUMBER(1401,"单据编号重复"),
    DOCUMENT_NUMBER_DOES_NOT_EXIST(1402,"单据编号不存在"),
    DOCUMENT_NUMBER_DOES_NOT_EMPTY(1403,"单据编号不能为空"),
    DOCUMENT_NUMBER_CHECK_FAILED(1404,"单据校验失败"),
    DOCUMENT_NUMBER_SAVE_FAILED(1410,"单据保存失败"),
    DOCUMENT_NUMBER_UPDATE_FAILED(1411,"单据更新失败"),
    DOCUMENT_NUMBER_DELETE_FAILED(1412,"单据删除失败"),
    DOCUMENT_NUMBER_SELECT_FAILED(1413,"获取单据数据错误"),

    BASIC_DATA_DOES_NOT_EXIST(1502,"基础资料不存在"),
    BASIC_DATA_CHECK_FAILED(1504,"基础资料校验失败"),
    BASIC_DATA_SAVE_FAILED(1510,"基础资料保存失败"),
    BASIC_DATA_UPDATE_FAILED(1511,"基础资料更新失败"),
    BASIC_DATA_DELETE_FAILED(1512,"基础资料删除失败"),
    BASIC_DATA_SELECT_FAILED(1513,"获取基础资料失败"),

    BASIC_DATA_GROUP_DOES_NOT_EXIST(1602,"基础资料组不存在"),
    BASIC_DATA_GROUP_CHECK_FAILED(1604,"基础资料组校验失败"),
    BASIC_DATA_GROUP_SAVE_FAILED(1610,"基础资料组保存失败"),
    BASIC_DATA_GROUP_UPDATE_FAILED(1611,"基础资料组更新失败"),
    BASIC_DATA_GROUP_DELETE_FAILED(1612,"基础资料组删除失败"),
    BASIC_DATA_GROUP_SELECT_FAILED(1613,"获取基础资料组数据错误"),


    ENCRYPTION_FAILED(1701,"加密失败"),
    DECRYPTION_FAILED(1702,"解密失败"),

    AUDITING_FAILED(1801,"启动审核失败"),
    UNSUPPORTED_APPROVAL_FLOW(1802,"不支持的审批流"),

    REPORT_ACQUISITION_FAILED(1911,"报表获取失败");

    private Integer code;
    private String desc;

    K3ResponseCodeEnum(Integer code, String desc) {
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
