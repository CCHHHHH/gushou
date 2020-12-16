package com.goodsogood.entity;

import lombok.Data;

/**
 * 商品分类
 */
@Data
public class Cates {

    private long cate_id; //商品分类id
    private String cate_name; //分类名称
    private int sort_num; //分类排序
}