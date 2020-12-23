package com.goodsogood.entity;

import lombok.Data;

/**
 * 作者：chenhao
 * 日期：12/23/20 4:48 PM
 **/
@Data
public class CommodityVo {

    private String commodityId;

    private String name;

    private String classify;

    private String subClass;

    private Long price;

    private Long actualPrice;

    private Integer count;

    private String standard;

    private Long carriage;

    private String area;

    private String company;

    private Integer type;

    private String orderId;

    private String recommendType;
}
