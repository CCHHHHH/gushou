package com.goodsogood.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodsogood.config.VdianGetToken;
import com.goodsogood.entity.ItemSales;
import com.goodsogood.entity.ItemSalesTop;
import com.goodsogood.entity.Items;
import com.goodsogood.response.BaseResponse;
import com.goodsogood.service.VdianService;
import com.goodsogood.utils.VdianRestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * 作者：chenhao
 * 日期：12/16/20 3:28 PM
 **/
@Api(tags = {"微店接口"})
@RestController
@CrossOrigin
@RequestMapping("/rest/vdian")
public class VdianController {

    @Autowired
    private VdianRestUtil vdianRestUtil;

    @Autowired
    private VdianService vdianService;

    @CrossOrigin
    @ApiOperation(value = "刷新token", notes = "刷新token")
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    public BaseResponse getToken() {
        try {
            String token = vdianRestUtil.getToken();
            VdianGetToken.token = token;
            if (StringUtils.isNotBlank(token)){
                return BaseResponse.initSuccessBaseResponse("刷新Token成功");
            }
            return BaseResponse.initErrorBaseResponse("刷新Token失败");
        } catch (Exception e) {
            return BaseResponse.initErrorBaseResponse("获取Token异常："+e.getMessage());
        }
    }

    @CrossOrigin
    @ApiOperation(value = "商品top", notes = "返回用户购买的排行榜，根据参数返回具体的top值。 排序方式：单个商品的购买总数降序")
    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public BaseResponse getItemSalesTop(@RequestParam String page_no,@RequestParam String page_size) {
        try {
            ItemSales itemSalesTop = vdianService.getItemSalesTop(page_no, page_size);
            return BaseResponse.initSuccessBaseResponse(itemSalesTop);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.initErrorBaseResponse("获取数据异常："+e.getMessage());
        }
    }

    @CrossOrigin
    @ApiOperation(value = "商品详情", notes = "根据商品id查询商品详情")
    @RequestMapping(value = "/commodity/detail", method = RequestMethod.GET)
    public BaseResponse getItemDetail(@RequestParam String commodity_id) {
        try {
            ItemSalesTop itemDetail = vdianService.getItemDetail(commodity_id);
            return BaseResponse.initSuccessBaseResponse(itemDetail);
        } catch (Exception e) {
            e.printStackTrace();
            return BaseResponse.initErrorBaseResponse("获取数据异常："+e.getMessage());
        }
    }

    @CrossOrigin
    @ApiOperation(value = "商品详情", notes = "根据商品id查询商品详情")
    @RequestMapping(value = "/push/receive",method = {RequestMethod.GET,RequestMethod.POST})
    public String getVdianPush(@RequestParam String content) {
        try {
            System.out.println("接收push消息");
            System.out.println("content:"+content);
            return "{\"status\":\"success\"}";
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"status\":\"false\"}";
        }
    }
}
