/**
 * Copyright 2020 json.cn
 */
package com.goodsogood.entity;

import lombok.Data;

import java.util.List;
import java.util.Date;

/**
 * 接收微店推送的整个消息
 */
@Data
public class ReceiveMessage {

    private List<String> full_gift_items;
    private int group_status;
    private String phone_recharge;
    private String g_seller_id;
    private String express;
    private String express_no;
    private String seller_name;
    private String supplier_seller_id;
    private String price;
    private String total_fee;
    private String refund_status_ori;
    private String seller_phone;
    private String order_type;
    private String seller_id;
    private String fans2_id;
    private ReceiveBuyerInfo buyer_info;
    private int is_close;
    private Date finish_time;
    private Date pay_time;
    private String order_type_des;
    private String express_note;
    private String express_fee_num;
    private String weixin;
    private Date send_time;
    private String buyer_identity_id;
    private String trade_no;
    private String fans1_id;
    private String order_id;
    private List<ReceiveOrderItems> items;
    private String fx_fee_value;
    private String status;
    private String wei_supplier_price;
    private String customInfos;
    private String note;
    private String modify_price_enable;
    private String express_fee;
    private int argue_flag;
    private int is_wei_order;
    private List<String> discount_list;
    private String total;
    private String status_ori;
    private String sk;
    private String user_phone;
    private String original_total_price;
    private String real_income_price;
    private String quantity;
    private String f_shop_name;
    private String status_desc;
    private String confirm_expire;
    private String fans2_money_total;
    private String f_phone;
    private String f_seller_id;
    private ReceiveOrderRefundInfo refund_info;
    private String status2;
    private String fans1_money_total;
    private String platform_fee;
    private Date add_time;
    private String return_code;
    private String express_type;

}