package com.goodsogood.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 作者：chenhao
 * 日期：12/16/20 5:15 PM
 **/
@Data
public class ItemSalesTop {
    private String commodity_id; //商品id
    private String name; //商品名称
    private String url; //商品地址 ？
    private List<String> image_url; //显示使用的缩略图的地址
    private BigDecimal price; //商品价格
}
