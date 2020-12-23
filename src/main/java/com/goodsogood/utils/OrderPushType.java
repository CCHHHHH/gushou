package com.goodsogood.utils;

/**
 * 作者：chenhao
 * 日期：12/23/20 2:17 PM
 **/
public class OrderPushType {

    //待付款（直接到账+担保交易）
    public final static String WEIDIAN_ORDER_NON_PAYMENT = "weidian.order.non_payment";

    //已付款（直接到账）/已付款待发货（担保交易）
    public final static String WEIDIAN_ORDER_ALREADY_PAYMENT = "weidian.order.already_payment";
}
