/**
 * Copyright 2020 json.cn
 */
package com.goodsogood.entity;

import lombok.Data;

/**
 * 接收微店推送订单的商品信息
 */
@Data
public class ReceiveOrderItems {

    private String sku_merchant_code;
    private String item_total_supplier_price;
    private String img;
    private String merchant_code;
    private String quantity;
    private String total_price;
    private String item_id;
    private int is_delivered;
    private ReceiveItemsRefundInfo refund_info;
    private String deliver_id;
    private String sku_id;
    private String item_name;
    private long sub_order_id;
    private String url;
    private String fx_fee_rate;
    private String fans1_money;
    private String sku_title;
    private String warehouseId;
    private String price;
    private String deliver_status_desc;
    private String fans2_money;
    private long id;
    private int can_deliver;

}