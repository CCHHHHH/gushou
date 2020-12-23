/**
 * Copyright 2020 json.cn
 */
package com.goodsogood.entity;

import lombok.Data;

/**
 * 接收微店推送订单的买家信息
 */
@Data
public class ReceiveBuyerInfo {

    private String address;
    private String post;
    private String province;
    private String phone;
    private String city;
    private String idCardNo;
    private String name;
    private String buyer_id;
    private String region;
    private String self_address;

}