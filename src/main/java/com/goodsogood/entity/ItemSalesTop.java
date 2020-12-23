package com.goodsogood.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 作者：chenhao
 * 日期：12/16/20 5:15 PM
 * top接口返回实体,固守商品信息
 **/
@Data
public class ItemSalesTop {
    private String commodity_id; //商品id
    private String name; //商品名称
    private String url; //商品地址 ？
    private List<String> image_url; //显示使用的缩略图的地址
    private Long price; //商品价格
    private Long carriage; //运费
    private String classify; //分类
    private String sub_class; //子类
    private String description; //描述商品简介
    private String area; //所属区县
    private String company; //商户名称
    private String company_logo; //商户logo
    private String standard; //规格单位
    private String memo; //备注
    private String detail; //商品详情
    private Integer publish; //商品上下架 0：下架 1：上架
}
