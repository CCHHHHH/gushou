package com.goodsogood.service;

import com.alibaba.fastjson.JSONObject;
import com.goodsogood.entity.ItemSales;
import com.goodsogood.entity.ItemSalesTop;
import com.goodsogood.entity.Items;

import java.util.List;
import java.util.Map;

/**
 * 作者：chenhao
 * 日期：12/15/20 9:39 AM
 **/
public interface IVdianService {

    /**
     * 返回用户购买的排行榜，根据参数返回具体的top值。 排序方式：单个商品的购买总数降序
     * @param page_no
     * @param page_size
     * @return
     */
    public List<ItemSalesTop> getItemSalesTop(String page_no, String page_size);

    /**
     * 获取商品详情
     * @param commodity_id 商品id
     * @return
     */
    public ItemSalesTop getItemDetail(String commodity_id);

    /**
     * 获取订单列表  暂不实现
     * @param param
     * @return
     */
    public JSONObject getOrderList(Map<String,Object> param);

    /**
     * 接收微店推送数据
     * @param content
     */
    void paresReceiveMsg(String content);

    boolean callback(String token);

}
