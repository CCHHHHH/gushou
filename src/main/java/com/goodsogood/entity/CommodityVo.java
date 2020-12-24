package com.goodsogood.entity;

import lombok.Data;

/**
 * 作者：chenhao
 * 日期：12/23/20 4:48 PM
 **/
@Data
public class CommodityVo {

    private String commodity_id;

    private String name;

    private String classify;

    private String sub_class;

    private Long price;

    private Long actual_price;

    private Integer count;

    private String standard;

    private Long carriage;

    private String area;

    private String company;

    private Integer type;

    private String order_id;

    private String recommend_type;
}
