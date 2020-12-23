package com.goodsogood.gushou;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.goodsogood.GushouApplication;
import com.goodsogood.config.VdianGetToken;
import com.goodsogood.domain.Commodity;
import com.goodsogood.domain.OrderInfo;
import com.goodsogood.domain.User;
import com.goodsogood.entity.CommodityVo;
import com.goodsogood.entity.ItemSalesTop;
import com.goodsogood.entity.OrderVo;
import com.goodsogood.push.PushMsgToGushou;
import com.goodsogood.service.*;
import com.goodsogood.utils.GushouRestUtil;
import com.goodsogood.utils.VdianRestUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= GushouApplication.class)
class GushouApplicationTests {

    @Autowired
    private VdianRestUtil vdianRestUtil;

    @Autowired
    private IVdianService IVdianService;

    @Autowired
    private GushouRestUtil gushouRestUtil;

    @Autowired
    private IGushouService gushouService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private PushMsgToGushou pushMsgToGushou;


    @Test
    void contextLoads() {
        String token = vdianRestUtil.getToken();
        System.out.println(token);
    }

    @Test
    void getRest(){
        HashMap<String, Object> param = new HashMap<>();
        HashMap<String, Object> paramPublic = new HashMap<>();
//{"page_size":"40","status":1,"orderby":"1","page_num":"1","orderby":"5"}
        param.put("page_size","5");
        param.put("status",1);
        param.put("orderby","1");
        param.put("page_num","1");
//{"method":"vdian.item.list.get","format":"json","access_token":"c6b28ddee7069ba42eb998fc0abb05700009219503","version":"1.1"}
        paramPublic.put("method","vdian.item.list.get");
        paramPublic.put("format","json");
        paramPublic.put("access_token",VdianGetToken.token);
        paramPublic.put("version","1.1");
        JSONObject results = vdianRestUtil.getRestApi(param, paramPublic);
        JSONObject result = results.getJSONObject("result");

        System.out.println(result);

    }

    @Test
    public void getItemDetail(){
        ItemSalesTop itemDetail = IVdianService.getItemDetail("4235279917");
        System.out.println(itemDetail);
    }

    @Test
    public void getGushouToken() throws Exception {
//        HashMap<String, Object> param = new HashMap<>();
//        param.put("commodity_id","1");
//        String restApi = gushouRestUtil.getRestApi(param);
//        System.out.println(restApi);
    }

    @Test
    public void getUsers(){
        List<User> users = userService.list();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void getOrderList(){
        HashMap<String, Object> param = new HashMap<>();
        param.put("is_wei_order","0");
        param.put("order_type","all");
        param.put("page_size","1");

        JSONObject orderList = IVdianService.getOrderList(param);
        System.out.println(orderList);
    }

    @Test
    public void showPushMsg(){
        String content = "{\n" +
                "    \"message\":{\n" +
                "    \"full_gift_items\": [],\n" +
                "    \"group_status\": -1,\n" +
                "    \"phone_recharge\": null,\n" +
                "    \"g_seller_id\": \"\",\n" +
                "    \"express\": \"中通速递\",\n" +
                "    \"express_no\": \"5111195496\",\n" +
                "    \"seller_name\": \"1111旗舰店\",\n" +
                "    \"supplier_seller_id\": null,\n" +
                "    \"price\": \"40.00\",\n" +
                "    \"total_fee\": \"\",\n" +
                "    \"refund_status_ori\": \"0\",\n" +
                "    \"seller_phone\": \"1111179236\",\n" +
                "    \"order_type\": \"3\",\n" +
                "    \"seller_id\": \"1111111172\",\n" +
                "    \"fans2_id\": null,\n" +
                "    \"buyer_info\": {\n" +
                "        \"address\": \"浙江省 1111翔路169号\",\n" +
                "        \"post\": null,\n" +
                "        \"province\": \"浙江\",\n" +
                "        \"phone\": \"111111508\",\n" +
                "        \"city\": \"温州\",\n" +
                "        \"idCardNo\": \"\",\n" +
                "        \"name\": \"时林娜\",\n" +
                "        \"buyer_id\": \"179000159\",\n" +
                "        \"region\": \"鹿城区\",\n" +
                "        \"self_address\": \"1111期鹿翔路169号\"\n" +
                "    },\n" +
                "    \"is_close\": 0,\n" +
                "    \"finish_time\": \"2020-11-30 10:51:33\",\n" +
                "    \"pay_time\": \"2020-11-04 16:31:09\",\n" +
                "    \"order_type_des\": null,\n" +
                "    \"express_note\": \"\",\n" +
                "    \"express_fee_num\": \"8.00\",\n" +
                "    \"weixin\": \"\",\n" +
                "    \"send_time\": \"2020-11-23 10:51:31\",\n" +
                "    \"buyer_identity_id\": null,\n" +
                "    \"trade_no\": null,\n" +
                "    \"fans1_id\": null,\n" +
                "    \"order_id\": \"1111130292248\",\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"sku_merchant_code\": null,\n" +
                "            \"item_total_supplier_price\": null,\n" +
                "            \"img\": \"https://si.geilicdn.com/pcit3272883-00105-unadjust_800_800.jpeg?w=110&h=110&cp=1\",\n" +
                "            \"merchant_code\": \"VF201001A\",\n" +
                "            \"quantity\": \"1\",\n" +
                "            \"total_price\": \"40.00\",\n" +
                "            \"item_id\": \"4038199354\",\n" +
                "            \"is_delivered\": 1,\n" +
                "            \"refund_info\": {\n" +
                "                \"refund_status\": \"\",\n" +
                "                \"refund_status_str\": \"\",\n" +
                "                \"item_id\": \"411119354\",\n" +
                "                \"item_sku_id\": \"0\",\n" +
                "                \"refund_status_desc\": \"\",\n" +
                "                \"refund_kind\": \"0\",\n" +
                "                \"refund_no\": \"\",\n" +
                "                \"refund_fee\": \"\",\n" +
                "                \"refund_item_fee\": \"\",\n" +
                "                \"refund_express_fee\": \"\",\n" +
                "                \"can_refund\": \"1\"\n" +
                "            },\n" +
                "            \"deliver_id\": \"0\",\n" +
                "            \"sku_id\": \"0\",\n" +
                "            \"item_name\": \"限时预售 Vogue Film（2020\",\n" +
                "            \"sub_order_id\": 611111075672,\n" +
                "            \"url\": \"https://weidian.com/i/4111111354\",\n" +
                "            \"fx_fee_rate\": \"\",\n" +
                "            \"fans1_money\": null,\n" +
                "            \"sku_title\": \"\",\n" +
                "            \"warehouseId\": null,\n" +
                "            \"price\": \"40.00\",\n" +
                "            \"deliver_status_desc\": \"已发货\",\n" +
                "            \"fans2_money\": null,\n" +
                "            \"id\": 11111574075672,\n" +
                "            \"can_deliver\": 0\n" +
                "        }\n" +
                "    ],\n" +
                "    \"fx_fee_value\": \"\",\n" +
                "    \"status\": \"finish\",\n" +
                "    \"wei_supplier_price\": \"\",\n" +
                "    \"customInfos\": null,\n" +
                "    \"note\": \"摩托支持xxx\",\n" +
                "    \"modify_price_enable\": \"0\",\n" +
                "    \"express_fee\": \"8.00\",\n" +
                "    \"argue_flag\": 0,\n" +
                "    \"is_wei_order\": 0,\n" +
                "    \"discount_list\": [],\n" +
                "    \"total\": \"48.00\",\n" +
                "    \"status_ori\": \"50\",\n" +
                "    \"sk\": null,\n" +
                "    \"user_phone\": \"11111508\",\n" +
                "    \"original_total_price\": \"\",\n" +
                "    \"real_income_price\": null,\n" +
                "    \"quantity\": \"1\",\n" +
                "    \"f_shop_name\": \"\",\n" +
                "    \"status_desc\": \"已完成\",\n" +
                "    \"confirm_expire\": \"\",\n" +
                "    \"fans2_money_total\": null,\n" +
                "    \"f_phone\": \"\",\n" +
                "    \"f_seller_id\": \"0\",\n" +
                "    \"refund_info\": {\n" +
                "        \"refund_time\": null,\n" +
                "        \"buyer_refund_fee\": null\n" +
                "    },\n" +
                "    \"status2\": null,\n" +
                "    \"fans1_money_total\": null,\n" +
                "    \"platform_fee\": null,\n" +
                "    \"add_time\": \"2020-11-04 16:30:51\",\n" +
                "    \"return_code\": null,\n" +
                "    \"express_type\": \"3\"\n" +
                "},\n" +
                "    \"type\":\"weidian.order.non_payment\"\n" +
                "}";
        IVdianService.paresReceiveMsg(content);
    }

    @Test
    public void addUser(){
        String content = "{" +
                "\"nick_name\":\"安安\"," +
                "\"openid\": \"o3wYk1bTQajjG3cFTi_1qb6Y6UGg\"," +
                "\"access_key\": \"659202aded10458380b0a97a460c474e\"" +
                "}";
        String info = "xxxxxxxxxxxxxxxxxxxxx";
        boolean register = userService.register(content, info);
        System.out.println(register);
    }

    @Test
    public void pushMsg(){
        List<OrderInfo> list = orderService.list();

        for (OrderInfo order : list) {
            OrderVo orderVo = new OrderVo();
            String orderId = order.getOrderId();
            orderVo.setDonation(order.getDonation());
            orderVo.setOpenid(order.getOpenid());
            orderVo.setOrderId(orderId);
            orderVo.setToken(order.getToken());
            orderVo.setTradingTime(order.getTradingTime());


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
            User buyer = userService.getBuyer(order.getOpenid());

            pushMsgToGushou.alreadyPayment(orderVo,buyer.getInfo());
        }


    }
}
