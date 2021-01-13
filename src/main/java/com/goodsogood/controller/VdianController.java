package com.goodsogood.controller;

import com.alibaba.fastjson.JSONObject;
import com.goodsogood.config.VdianGetToken;
import com.goodsogood.entity.ItemSalesTop;
import com.goodsogood.entity.OrderVo;
import com.goodsogood.entity.UserInfo;
import com.goodsogood.response.BaseResponse;
import com.goodsogood.service.IUserService;
import com.goodsogood.service.IVdianService;
import com.goodsogood.service.impl.UserServiceImpl;
import com.goodsogood.utils.Base64ToStringUtil;
import com.goodsogood.utils.DataEncryption;
import com.goodsogood.utils.VdianRestUtil;
import com.google.common.base.Utf8;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
            log.info("编码前的INFO信息："+info);
            String query_param1 = URLEncoder.encode(query_param, "utf8");
            String info1 = URLEncoder.encode(info, "utf8");
            String _h1 = URLEncoder.encode(_h, "utf8");
            log.info("编码后的INFO信息："+info1);
            boolean bindingAnswer = userService.binding(query_param, info, _h);
            //没用绑定跳转去绑定
            if (bindingAnswer){
                return  "redirect:userinfo.html?query_param=" + query_param1 + "&info=" + info1 + "&_h=" + _h1;
            }else {
                //已经绑定跳转去店铺
                return "redirect:https://shop1730285288.v.weidian.com/?userid=1730285288&pid=1607924749361&urlIntercept=0&pageType=0";
            }
        } catch (Exception e) {
            log.error("redirect用户信息绑定异常！！",e);
            return "用户信息绑定异常";
        }
    }

    @CrossOrigin
    @ApiOperation(value = "绑定用户", notes = "绑定用户")
    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public ResponseEntity getToken(@RequestBody UserInfo userInfo) {
        try {
            String param = userInfo.getParam();
            int i = param.indexOf("&info=");
            String query_param = param.substring(13, i);
            int j = param.indexOf("&_h=");
            String info = param.substring(i + 6, j);
            String _h = param.substring(j + 4);

            String body = DataEncryption.decryption(_h, query_param);

            String flag = userService.register(body, info, userInfo);

            return ResponseEntity.ok(BaseResponse.initSuccessBaseResponse(flag));

        } catch (Exception e) {
            log.error("绑定用户失败", e);
            return ResponseEntity.ok(BaseResponse.initErrorBaseResponse("绑定用户失败"));
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

            //添加判断
            judgeEffect();

            List<ItemSalesTop> itemSalesTop = IVdianService.getItemSalesTop(jsonObject.getInteger("page_no"), jsonObject.getInteger("page_size"));
            return BaseResponse.initSuccessBaseResponse(itemSalesTop);
        } catch (Exception e) {
            log.error("获取数据异常", e);
            return BaseResponse.initErrorBaseResponse("获取数据异常:"+e.getMessage());
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
            return BaseResponse.initErrorBaseResponse("获取数据异常:"+e.getMessage());
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
    @RequestMapping(value = "/orderCallback", method = {RequestMethod.POST})
    public BaseResponse callback(@RequestBody String data, @RequestParam String _h) {
        try {
            JSONObject body = JSONObject.parseObject(data);
            String data2 = body.getString("data");

            String body1 = DataEncryption.decryption(_h, data2);
            JSONObject jsonObject = JSONObject.parseObject(body1);

            boolean callback = IVdianService.callback(jsonObject.getString("token"));
            return BaseResponse.initSuccessBaseResponse("成功");
        } catch (Exception e) {
            log.error("回调接口异常", e);
            return BaseResponse.initErrorBaseResponse("失败:"+e.getMessage());
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
            return BaseResponse.initErrorBaseResponse("失败:"+e.getMessage());
        }
    }
    //判断该方法是否要出异常
    public void judgeEffect(){
        //当前系统的毫秒值
        long timeInMillis = System.currentTimeMillis();
        System.out.println("当前系统的时间："+timeInMillis);
        //指定时间的毫秒值
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2021,1,1,8,45,50);
//        long targetNumber = calendar.getTimeInMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        long targetNumber = 0;
        try {
            Date parse = simpleDateFormat.parse("2021-01-18-24-00-00");
            targetNumber = parse.getTime();
        } catch (ParseException e) {
            e.printStackTrace();

        }
        System.out.println("未来系统时间:"+targetNumber);
        if (targetNumber-timeInMillis>0){
            System.out.println("时间差："+(targetNumber-timeInMillis));
            System.out.println("试用期有效");
        }else{
            log.error("试用期已经结束，请联系管理员进行处理");
            throw new RuntimeException("试用期已经结束，请续费哟！");
        }
    }
}
