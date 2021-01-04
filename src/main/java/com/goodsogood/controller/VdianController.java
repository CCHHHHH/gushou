package com.goodsogood.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodsogood.config.VdianGetToken;
import com.goodsogood.entity.ItemSalesTop;
import com.goodsogood.entity.OrderVo;
import com.goodsogood.entity.UserInfo;
import com.goodsogood.response.BaseResponse;
import com.goodsogood.service.IUserService;
import com.goodsogood.service.IVdianService;
import com.goodsogood.utils.DataEncryption;
import com.goodsogood.utils.VdianRestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 作者：chenhao
 * 日期：12/16/20 3:28 PM
 **/
@Api(tags = {"微店接口"})
//@RestController
@Controller
@CrossOrigin
@Slf4j
//@RequestMapping("/rest/vdian")
public class VdianController {

    @Autowired
    private VdianRestUtil vdianRestUtil;

    @Autowired
    private IVdianService IVdianService;

    @Autowired
    private IUserService userService;


    @CrossOrigin
    @ApiOperation(value = "重定向到静态页面", notes = "绑定用户")
    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String redirect(@RequestParam String query_param, @RequestParam String info, @RequestParam String _h) {
        try {

            return "redirect:index.html?query_param=" + query_param + "&info=" + info + "&_h=" + _h;

        } catch (Exception e) {
            return "";
        }
    }

    @CrossOrigin
    @ApiOperation(value = "绑定用户", notes = "绑定用户")
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public String getToken(@RequestBody UserInfo userInfo) {
        try {
            String param = userInfo.getParam();
            int i = param.indexOf("&info=");
            String query_param = param.substring(13, i);
            int j = param.indexOf("&_h=");
            String info = param.substring(i + 6, j);
            String _h = param.substring(j + 4);

            String body = DataEncryption.decryption(_h, query_param);

            String flag = userService.register(body, info, userInfo);
            return "redirect:https://shop1730285288.v.weidian.com/?userid=1730285288&pid=1607924749361&urlIntercept=0&pageType=0";

        } catch (Exception e) {
            log.error("绑定用户失败", e);
            return "绑定用户失败";
        }
    }

    @CrossOrigin
    @ApiOperation(value = "刷新token", notes = "刷新token")
    @ResponseBody
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    public BaseResponse getToken() {
        try {
            String token = vdianRestUtil.getToken();
            VdianGetToken.token = token;
            if (StringUtils.isNotBlank(token)) {
                return BaseResponse.initSuccessBaseResponse("刷新Token成功");
            }
            return BaseResponse.initErrorBaseResponse("刷新Token失败");
        } catch (Exception e) {
            log.error("获取Token异常", e);
            return BaseResponse.initErrorBaseResponse("获取Token异常");
        }
    }

    @CrossOrigin
    @ApiOperation(value = "商品top", notes = "返回用户购买的排行榜，根据参数返回具体的top值。 排序方式：单个商品的购买总数降序")
    @ResponseBody
    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public BaseResponse getItemSalesTop(@RequestParam String query_param, @RequestParam String _h) {
        try {

            String body = DataEncryption.decryption(_h, query_param);
            JSONObject jsonObject = JSONObject.parseObject(body);

            List<ItemSalesTop> itemSalesTop = IVdianService.getItemSalesTop(jsonObject.getInteger("page_no"), jsonObject.getInteger("page_size"));
            return BaseResponse.initSuccessBaseResponse(itemSalesTop);
        } catch (Exception e) {
            log.error("获取数据异常", e);
            return BaseResponse.initErrorBaseResponse("获取数据异常");
        }
    }

    @CrossOrigin
    @ApiOperation(value = "商品详情", notes = "根据商品id查询商品详情")
    @ResponseBody
    @RequestMapping(value = "/commodity/detail", method = RequestMethod.GET)
    public BaseResponse getItemDetail(@RequestParam String query_param, @RequestParam String _h) {
        try {

            String body = DataEncryption.decryption(_h, query_param);
            JSONObject jsonObject = JSONObject.parseObject(body);

            ItemSalesTop itemDetail = IVdianService.getItemDetail(jsonObject.getString("commodity_id"));
            return BaseResponse.initSuccessBaseResponse(itemDetail);
        } catch (Exception e) {
            log.error("获取数据异常", e);
            return BaseResponse.initErrorBaseResponse("获取数据异常");
        }
    }

    @CrossOrigin
    @ApiOperation(value = "订阅微店消息", notes = "接收微店订单消息的推送")
    @ResponseBody
    @RequestMapping(value = "/push/receive", method = {RequestMethod.GET, RequestMethod.POST})
    public String getVdianPush(@RequestParam String content) {
        try {
            log.info(content);
            IVdianService.paresReceiveMsg(content);

            return "{\"status\":\"success\"}";
        } catch (Exception e) {
            log.error("接收推送消息异常", e);
            return "{\"status\":\"failed\"}";
        }
    }

    @CrossOrigin
    @ApiOperation(value = "订单推送回调接口", notes = "固守成功处理推送的消息后，调用改回调接口")
    @ResponseBody
    @RequestMapping(value = "/orderCallback", method = {RequestMethod.GET})
    public BaseResponse callback(@RequestParam String query_param, @RequestParam String _h) {
        try {
            String body = DataEncryption.decryption(_h, query_param);
            JSONObject jsonObject = JSONObject.parseObject(body);

            boolean callback = IVdianService.callback(jsonObject.getString("token"));
            return BaseResponse.initSuccessBaseResponse("成功");
        } catch (Exception e) {
            log.error("回调接口异常", e);
            return BaseResponse.initErrorBaseResponse("失败");
        }
    }

    @CrossOrigin
    @ApiOperation(value = "拉取用户下单数据", notes = "根据条件返回订单")
    @ResponseBody
    @RequestMapping(value = "/pull/user/order", method = {RequestMethod.GET})
    public BaseResponse userOrder(@RequestParam String query_param, @RequestParam String _h) {
        try {

            String body = DataEncryption.decryption(_h, query_param);
            JSONObject jsonObject = JSONObject.parseObject(body);

            List<OrderVo> orderVos = IVdianService.getUserOrder(jsonObject.getInteger("type"), jsonObject.getInteger("count"));
            return BaseResponse.initSuccessBaseResponse(orderVos);
        } catch (Exception e) {
            log.error("查询用户订单接口", e);
            return BaseResponse.initErrorBaseResponse("失败");
        }
    }
}
