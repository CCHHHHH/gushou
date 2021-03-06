package com.goodsogood.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 作者：chenhao
 * 日期：12/23/20 4:46 PM
 **/
@Data
public class OrderVo {

    private String token;

    private String openid;

    private String order_id;

    private Long total_price;

    private Long logistics;

    private Long donation;

    private String trading_time;

    private String order_type;

    private List<CommodityVo> order_commodity;

    private String info;
}
