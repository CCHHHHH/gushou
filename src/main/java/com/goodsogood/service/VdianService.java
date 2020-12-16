package com.goodsogood.service;

import com.alibaba.fastjson.JSONObject;
import com.goodsogood.entity.ItemSales;

/**
 * 作者：chenhao
 * 日期：12/15/20 9:39 AM
 **/
public interface VdianService {

    /**
     * 返回用户购买的排行榜，根据参数返回具体的top值。 排序方式：单个商品的购买总数降序
     * @param page_no
     * @param page_size
     * @return
     */
    public ItemSales getItemSalesTop(String page_no, String page_size);

}
