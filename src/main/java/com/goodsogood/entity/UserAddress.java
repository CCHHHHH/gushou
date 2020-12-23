package com.goodsogood.entity;

import lombok.Data;

/**
 * 作者：chenhao
 * 日期：12/18/20 9:16 PM
 **/
@Data
public class UserAddress {
    private String token;
    private String openid;
    private Integer out_address_id;
    private String user_name;
    private String user_phone;
    private String province_code;
    private String province;
    private String city_code;
    private String city;
    private String area_code;
    private String area;
    private String street_code;
    private String street;
    private String address;
    private String post_code;
    private Integer default_flag;
    private Integer operation_type;
}
