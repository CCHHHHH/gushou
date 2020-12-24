package com.goodsogood.push;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.goodsogood.domain.Commodity;
import com.goodsogood.domain.OrderInfo;
import com.goodsogood.domain.User;
import com.goodsogood.entity.CommodityVo;
import com.goodsogood.entity.OrderVo;
import com.goodsogood.response.ResponseCodeEnum;
import com.goodsogood.service.ICommodityService;
import com.goodsogood.service.IOrderInfoService;
import com.goodsogood.service.IUserService;
import com.goodsogood.service.IVdianService;
import com.goodsogood.utils.GushouRestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 作者：chenhao
 * 日期：12/23/20 4:44 PM
 **/
@Component
@Slf4j
public class PushMsgToGushou {

    @Autowired
    private GushouRestUtil gushouRestUtil;

    @Autowired
    private IOrderInfoService orderService;

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IVdianService vdianService;

    /**
     * 将订单推送到固守
     * @param orderVo 推送的数据
     */
    @Async
    public void pushOrder(OrderVo orderVo,String info){
        try {
            String content = JSONObject.toJSONString(orderVo);
            String restApi = gushouRestUtil.getRestApi(content, "%251F%25C2%258B%2508%2500%2500%2500%2500%2500%2500%2500%2505%25C3%2581G%25C2%2582%25C2%25820%2500%2500%25C3%2580%2507%25C3%25A5%25C2%2580%25C2%2584%257E%25C2%25A4%252BU%25C3%25AABn%2540%25C3%25A8%25C2%25A2%25C2%2594%25C2%2580%25C3%2582%25C3%25ABwF%25C2%2588%25C2%259C%25C2%2586f%25C3%25A1%25C3%25A7%25C2%2580%25C3%25A3%25C3%258B%25C3%25AC%25C2%2581Q%2527%25C2%25A9%25C3%25A6%25C3%2588%25C2%25B4%25C3%258A%25C3%2591%25C3%2595%257D%250C%25C2%2596%25C2%25B2%25C2%25991%25C2%25AFU%25C2%25A5%25C2%2594%25C2%25BF%25C3%25B2%253D7%25C3%25B6%2522%251B%25C3%2586%25C3%25B5%25C3%2599%25C3%2590%25C3%25BA%25C3%25B2%25C2%2593%25C3%25AC%25C3%259E%25C2%2582%2527%25C2%2586%25C3%2584%25C3%25BA%25C3%2590t%25C3%2598%253D%25C3%25A68w%2516%25C2%2589*%25C3%2597H%25C2%2584%25C3%25AFZ*D%25C3%25A1%25C2%25A2%2522P%25C2%259E%25C3%25B8*%2507%25C2%25AD%25C3%25BE%25C3%25B8%253F%25C3%25A7%25C2%25BB%25C2%25BAb%25C3%2586%2511%25C2%25B5%25C2%25AC%25C3%25BD%25C2%25BC%25C3%2597%250E%252Cw%25C2%2590%252F%25C3%259D%2519%25C3%258A%25C2%2587J%25C2%2584%25C3%258B*%257E%25C3%25A8%25C3%2588%2501%253D%25C2%25B9%25C3%2599%257E%25C3%2588%2507%25C2%2588Zm%2500u%25C2%258B%25C3%25BEr%25C2%25B1zY%25C3%25BE%2517%25C3%259B%25C3%25A7%25C2%259A%251F%25C3%25AD%25C2%258C%25C2%25B8%252F%257B%25C2%25BF%25C3%259D%2514%253Cw%25C2%2596%25C3%2584%25C3%25AE%250B%2507%25C3%25A76I%25C3%25B80%25C2%25B7m%25C2%25A8%2500%25C2%258D%2516%25C3%259E%25C3%2586%25C2%25902%250Br%25C2%258DX%2506%25C3%2593%25C2%2586e%25C3%25B3%25C2%25B6%25C3%258A%25C3%2591%25C2%25A3x%25C3%2590%25C3%2597%2516%25071_JW%25C3%2587%25C3%2584%2528S%25C2%25B3%253E%2514%25C3%25BC%255D*%25C3%2588%25058%251E%255C%253F%25C3%2597%25C3%25AB%25C3%2588Z%25C3%25AD%25C3%25BD%2504%2518Sz%25C2%25A9%253C%25C3%2599j%250Amu%25C2%2587%257E%2505%25C3%25B4%25C3%25B8%2504%25C3%2594T%2510P%25C3%258E%25C2%25B1%253DX%25C3%2581f%25C3%2598%25C3%25ABSG%25C3%2580%25C3%25AEC%25C2%258B%2513%25C3%2584p%2527%25C3%259F%25C2%259C%250F%25C2%25AAFs%250A%25C2%259B5%255B%25C3%2581G%25C3%258A%2512%25C2%25A1%25C3%259B%25C3%259B%253F%2515%2514%25C2%25A5%25C3%25B4%2525%253C%2509%25C3%258A%25C3%25B0%25C3%2599%25C3%2580%2513%257C%25C2%2594%2525%2521%25105%25C3%2597%25C2%2585%25C3%25AE%25C2%25AB%25C2%259A%25C2%25BAF%25C3%25A5%253E%257FG%25C2%25AD%253E%2523%25C2%25BE%25C3%25BD*%257Fv-%25C2%25A4%25C3%25B1Qm%2500%25C3%25AF%25C3%2593%25C2%25A3%25C3%2585%25C2%25AAw%25C3%258A%25C2%25BD%25C2%25BBI%25C2%25A4%25C2%2582%25C2%25A6%251E-%25C3%25B3%257E%257B%25C2%258Ek1%25C2%25B1%25C3%2588%2505%253D3%257C9%25C2%25BA%25C3%25AD%25C3%2585%25C2%258F%25C3%25B2%25C3%258C%25C2%25A3nY6e%25C3%2582%25C3%2594%255B%25C3%25BDY%25C3%25A3%2504%257C%25C2%25BE.u%25C3%25860%25C2%2590%25C2%25BB9%25C2%25959%25C2%25B2q%25C2%25A6%25C3%25BD%2511N%25C3%25A1%251C%252F%257Dx%25C2%259A%257B%25C3%25B6a%2506%25C3%25BC%25C3%25A6%25C2%258F%25C2%25BB%25C2%25AC%25C2%25BE%25C3%25B7%25C2%259C%2540we%25C2%259B4%25C3%25ACqo%25C3%2587%253B%25C3%25B2YS%257D%25C3%25B1%25C3%25A1%25C3%259A%25C2%258Ct%25C2%2592%25C2%25BF%25C3%25872%2509P%25C2%2580%25C2%25BAK0%25C2%2584A%257D%250B%2503%25C2%2590I%25C3%258C%25C2%25A3%25C3%25B1%25C2%25BC9%25C2%2598%2523%25C2%25AC%251A%25C2%25A5%25C2%258Bn%25C3%259A%250A%25C2%25B3O%25C3%2580%25C3%259E%25C2%259B0%25C2%25BE%25C3%25A9%25C3%25A0%25C2%2583%2509%25C3%2595%257Da%25C2%2581G%2525%25C2%259B%25C3%2585%25C3%2588%25C2%25BD%2517%25C2%25AD%25C3%25AFD%25C2%25AC%2527-6%25C3%25A2%253D8p%25C2%259A%250EG%250B%25C2%25B3%257D%25C2%25AC%25C2%25A2%257F%25C2%2592I%25C2%25A5%2512%25C2%2580%2502%2500%2500","/poverty/push/user/order");
            System.out.println(restApi);
            JSONObject jsonObject = JSONObject.parseObject(restApi);
            switch (jsonObject.getInteger("code")){
                case 0:
                    log.info(ResponseCodeEnum.SUCCESS_RESPONSE.getDesc());
                    break;
                case 9900:
                    log.error(ResponseCodeEnum.REQUEST_HEADER_ERROR.getDesc());
                    break;
                case 9901:
                    log.error(ResponseCodeEnum.SYSTEM_ERROR.getDesc());
                    break;
                case 9902:
                    log.error(ResponseCodeEnum.SERVER_BUSY.getDesc());
                    break;
                case 9404:
                    log.error(ResponseCodeEnum.RESOURCES_NOT_FOUND.getDesc());
                    break;
                case 9906:
                    log.error(ResponseCodeEnum.REQUEST_PARAM_ERROR.getDesc());
                    break;
                case 9907:
                    log.error(ResponseCodeEnum.REMOTE_INVOCATION_SERVICE_ERROR.getDesc());
                    break;
                case 164:
                    //如果返回的状态码是164，代表数据重复推送，将订单推送状态改成已推送即可
                    vdianService.callback(orderVo.getToken());
                    log.error(ResponseCodeEnum.REPEATED_PUSH_OF_DATA.getDesc());
                    break;
                default:
                    log.error("推送到固守接口错误。错误信息："+jsonObject.getString("message"));
            }
        } catch (Exception e) {
            log.error("推送到固守接口错误。",e);
        }
    }


    //定时推送未推送的订单
    //TODO 上线放开注释
//    @Scheduled(cron = "0 0 2 * * ?")
    public void taskPushMsg(){
        //查询未推送的订单
        try {
            QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
            orderInfoQueryWrapper.eq("push",0);
            List<OrderInfo> list = orderService.list(orderInfoQueryWrapper);

            for (OrderInfo order : list) {
                OrderVo orderVo = assembly(order);
                User buyer = userService.getBuyer(order.getOpenid());

                //将订单推送到固守
                pushOrder(orderVo,buyer.getInfo());
            }
        } catch (Exception e) {
            log.error("定时推送数据至固守异常",e);
        }
    }

    //重新将数据组装成固守所需要的数据
    public OrderVo assembly(OrderInfo order){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        OrderVo orderVo = new OrderVo();
        String orderId = order.getOrderId();
        orderVo.setDonation(order.getDonation());
        orderVo.setOpenid(order.getOpenid());
        orderVo.setOrder_id(orderId);
        orderVo.setToken(order.getToken());
        orderVo.setTrading_time(simpleDateFormat.format(order.getTradingTime()));
        orderVo.setTotal_price(order.getTotalPrice());
        orderVo.setLogistics(order.getLogistics());

        QueryWrapper<Commodity> commodityQueryWrapper = new QueryWrapper<>();
        commodityQueryWrapper.eq("order_id",orderId);
        List<Commodity> commodities = commodityService.list(commodityQueryWrapper);
        List<CommodityVo> commodityVos = new ArrayList<>();

        for (Commodity commodity : commodities) {
            CommodityVo commodityVo = new CommodityVo();
            BeanUtils.copyProperties(commodity,commodityVo);
            commodityVo.setActual_price(commodity.getActualPrice());
            commodityVo.setCommodity_id(commodity.getCommodityId());
            commodityVo.setOrder_id(commodity.getOrderId());
            commodityVo.setRecommend_type(commodity.getRecommendType());
            commodityVo.setSub_class(commodity.getSubClass());
            commodityVos.add(commodityVo);
        }

        orderVo.setOrder_commodity(commodityVos);

        return orderVo;
    }

}
