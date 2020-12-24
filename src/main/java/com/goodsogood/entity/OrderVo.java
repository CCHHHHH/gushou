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

    private String orderId;

    private Long donation;

    private Date tradingTime;

    private String orderType;

    private List<CommodityVo> order_commodity;
}
