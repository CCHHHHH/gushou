/**
  * Copyright 2020 json.cn 
  */
package com.goodsogood.entity;

import lombok.Data;

/**
 * 接收微店推送订单的退款信息
 */
@Data
public class ReceiveOrderRefundInfo {

    private String refund_time;
    private String buyer_refund_fee;
}