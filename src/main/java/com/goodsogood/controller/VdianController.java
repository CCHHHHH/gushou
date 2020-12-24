package com.goodsogood.controller;

import com.goodsogood.config.VdianGetToken;
import com.goodsogood.entity.ItemSalesTop;
import com.goodsogood.entity.OrderVo;
import com.goodsogood.response.BaseResponse;
import com.goodsogood.service.IUserService;
import com.goodsogood.service.IVdianService;
import com.goodsogood.utils.VdianRestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作者：chenhao
 * 日期：12/16/20 3:28 PM
 **/
@Api(tags = {"微店接口"})
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/rest/vdian")
public class VdianController {

    @Autowired
    private VdianRestUtil vdianRestUtil;

    @Autowired
    private IVdianService IVdianService;

    @Autowired
    private IUserService userService;

    @CrossOrigin
    @ApiOperation(value = "绑定用户", notes = "绑定用户")
    @RequestMapping(value = "/registerUser", method = RequestMethod.GET)
    public BaseResponse getToken(@RequestParam String query_param, @RequestParam String info) {
        try {

            boolean flag = userService.register(query_param,info);

            if (flag){
                return BaseResponse.initSuccessBaseResponse(flag);
            }else {
                return BaseResponse.initErrorBaseResponse("绑定用户失败，请重试");
            }
        } catch (Exception e) {
            log.error("绑定用户失败",e);
            return BaseResponse.initErrorBaseResponse("绑定用户失败："+e.getMessage());
        }
    }

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
            log.error("获取Token异常",e);
            return BaseResponse.initErrorBaseResponse("获取Token异常："+e.getMessage());
        }
    }

    @CrossOrigin
    @ApiOperation(value = "商品top", notes = "返回用户购买的排行榜，根据参数返回具体的top值。 排序方式：单个商品的购买总数降序")
    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public BaseResponse getItemSalesTop(@RequestParam String page_no,@RequestParam String page_size) {
        try {
            List<ItemSalesTop> itemSalesTop = IVdianService.getItemSalesTop(page_no, page_size);
            return BaseResponse.initSuccessBaseResponse(itemSalesTop);
        } catch (Exception e) {
            log.error("获取数据异常",e);
            return BaseResponse.initErrorBaseResponse("获取数据异常："+e.getMessage());
        }
    }

    @CrossOrigin
    @ApiOperation(value = "商品详情", notes = "根据商品id查询商品详情")
    @RequestMapping(value = "/commodityDetail", method = RequestMethod.GET)
    public BaseResponse getItemDetail(@RequestParam String commodity_id) {
        try {
            ItemSalesTop itemDetail = IVdianService.getItemDetail(commodity_id);
            return BaseResponse.initSuccessBaseResponse(itemDetail);
        } catch (Exception e) {
            log.error("获取数据异常",e);
            return BaseResponse.initErrorBaseResponse("获取数据异常："+e.getMessage());
        }
    }

    @CrossOrigin
    @ApiOperation(value = "订阅微店消息", notes = "接收微店订单消息的推送")
    @RequestMapping(value = "/push/receive",method = {RequestMethod.GET,RequestMethod.POST})
    public String getVdianPush(@RequestParam String content) {
        try {
            log.info(content);
            IVdianService.paresReceiveMsg(content);

            return "{\"status\":\"success\"}";
        } catch (Exception e) {
            log.error("接收推送消息异常",e);
            return "{\"status\":\"failed\"}";
        }
    }

    @CrossOrigin
    @ApiOperation(value = "订单推送回调接口", notes = "固守成功处理推送的消息后，调用改回调接口")
    @RequestMapping(value = "/orderCallback",method = {RequestMethod.GET})
    public BaseResponse callback(@RequestParam String token) {
        try {
            boolean callback = IVdianService.callback(token);
            return BaseResponse.initSuccessBaseResponse("成功");
        } catch (Exception e) {
            log.error("回调接口异常",e);
            return BaseResponse.initErrorBaseResponse("失败"+e.getMessage());
        }
    }

    @CrossOrigin
    @ApiOperation(value = "查询用户订单接口", notes = "根据条件返回订单")
    @RequestMapping(value = "/userOrder",method = {RequestMethod.GET})
    public BaseResponse userOrder(@RequestParam Integer type,@RequestParam Integer count) {
        try {
            OrderVo orderVo = IVdianService.getUserOrder(type,count);
            return BaseResponse.initSuccessBaseResponse(orderVo);
        } catch (Exception e) {
            log.error("回调接口异常",e);
            return BaseResponse.initErrorBaseResponse("失败"+e.getMessage());
        }
    }
}
