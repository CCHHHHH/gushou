package com.goodsogood.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 商品
 */
@Data
public class Items {

    private Date add_time; //创建时间
    private List<Cates> cates; //商品分类
    private String fx_fee_rate; //分销分成比例
    private List<String> imgs; //商品主图（只返回商品主图）
    private int istop; //是否店长推荐：1-是，0-不是
    private String item_desc; //商品描述
    private String item_name; //商品名称
    private String itemid; //商品id
    private String merchant_code; //商品编号
    private String price; //价格
    private String seller_id; //卖家id
    private List<Skus> skus; //商品sku，无型号的商品skus为空
    private int sold; //商品销量
    private int status; //statusNUMBER商品状态 1: 正常 2: 下架 3:删除
    private int stock; //库存量
    private List<String> thumb_imgs; //商品缩略图
    private Date update_time; //商品更新时间 如：2014-12-24 10:45:40 注：商品卖出后会更新此字段
}