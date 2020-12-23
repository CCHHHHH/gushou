/**
 * Copyright 2020 json.cn
 */
package com.goodsogood.entity;

import lombok.Data;

/**
 * 接收微店推送订单的商品退款信息
 */
@Data
public class ReceiveItemsRefundInfo {

    private String refund_status;
    private String refund_status_str;
    private String item_id;
    private String item_sku_id;
    private String refund_status_desc;
    private String refund_kind;
    private String refund_no;
    private String refund_fee;
    private String refund_item_fee;
    private String refund_express_fee;
    private String can_refund;

}