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
            String restApi = gushouRestUtil.getRestApi(content, info,"/poverty/push/user/order");

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
        orderVo.setOrderId(orderId);
        orderVo.setToken(order.getToken());
        orderVo.setTradingTime(simpleDateFormat.format(order.getTradingTime()));
        orderVo.setOrderType(order.getOrderType());

        QueryWrapper<Commodity> commodityQueryWrapper = new QueryWrapper<>();
        commodityQueryWrapper.eq("order_id",orderId);
        List<Commodity> commodities = commodityService.list(commodityQueryWrapper);
        List<CommodityVo> commodityVos = new ArrayList<>();

        for (Commodity commodity : commodities) {
            CommodityVo commodityVo = new CommodityVo();
            BeanUtils.copyProperties(commodity,commodityVo);
            commodityVos.add(commodityVo);
        }

        orderVo.setOrder_commodity(commodityVos);

        return orderVo;
    }

}
