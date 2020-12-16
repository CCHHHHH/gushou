package com.goodsogood.entity;

import lombok.Data;

import java.util.List;

@Data
public class ItemSales {
    private int item_num; //列表返回的商品数
    private int total_num; //微店中的商品总数
    private List<Items> items; //商品数组

}