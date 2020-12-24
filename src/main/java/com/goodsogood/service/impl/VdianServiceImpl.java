package com.goodsogood.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.goodsogood.config.VdianGetToken;
import com.goodsogood.domain.Commodity;
import com.goodsogood.domain.OrderInfo;
import com.goodsogood.domain.User;
import com.goodsogood.entity.*;
import com.goodsogood.push.PushMsgToGushou;
import com.goodsogood.service.ICommodityService;
import com.goodsogood.service.IOrderInfoService;
import com.goodsogood.service.IUserService;
import com.goodsogood.service.IVdianService;
import com.goodsogood.utils.GushouRestUtil;
import com.goodsogood.utils.VdianRestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 作者：chenhao
 * 日期：12/15/20 9:52 AM
 **/
@Service
@Slf4j
public class VdianServiceImpl implements IVdianService {

    @Autowired
    private VdianRestUtil vdianRestUtil;

    @Autowired
    private GushouRestUtil gushouRestUtil;

    @Autowired
    private IUserService userService;

    @Autowired
    private IOrderInfoService orderService;

    @Autowired
    private ICommodityService commodityService;

    @Autowired
    private PushMsgToGushou pushMsgToGushou;

    @Override
    public List<ItemSalesTop> getItemSalesTop(String page_no, String page_size) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("page_size",page_size);
        param.put("status","1");
        param.put("orderby","3");
        param.put("page_num",page_no);

        HashMap<String, Object> paramPublic = new HashMap<>();
        paramPublic.put("method","vdian.item.list.get");
        paramPublic.put("format","json");
        paramPublic.put("access_token", VdianGetToken.token);
        paramPublic.put("version","1.1");
        JSONObject results = vdianRestUtil.getRestApi(param, paramPublic);

        ItemSales result = JSONObject.toJavaObject(results.getJSONObject("result"), ItemSales.class);
        List<Items> items = result.getItems();
        List<ItemSalesTop> itemSalesTops = new ArrayList<>();

        for (Items item : items) {
            ItemSalesTop itemSalesTop = new ItemSalesTop();
            itemSalesTop.setCommodity_id(item.getItemid());
            itemSalesTop.setName(item.getItem_name());
            itemSalesTop.setUrl("");
            itemSalesTop.setImage_url(item.getThumb_imgs());
            itemSalesTop.setPrice((long)(Double.valueOf(item.getPrice())*100));
            itemSalesTop.setCarriage(0L);
            List<Cates> cates = item.getCates();
            String cateNames = "";
            for (Cates cate : cates) {
                cateNames += cate.getCate_name() + ",";
            }
            itemSalesTop.setClassify(cateNames.substring(0,cateNames.length()-1));
            itemSalesTop.setSub_class("");
            itemSalesTop.setDescription(item.getItem_desc());
            itemSalesTop.setArea("");
            itemSalesTop.setCompany("");
            itemSalesTop.setCompany_logo("");
            itemSalesTop.setStandard("");
            itemSalesTop.setMemo("");
            itemSalesTop.setDetail("");
            itemSalesTop.setPublish(item.getStatus()==2?0:item.getStatus()==1?1:3);
            itemSalesTops.add(itemSalesTop);
        }


        return itemSalesTops;
    }

    @Override
    public ItemSalesTop getItemDetail(String commodity_id) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("itemid",commodity_id);

        HashMap<String, Object> paramPublic = new HashMap<>();
        paramPublic.put("method","vdian.item.get");
        paramPublic.put("format","json");
        paramPublic.put("access_token", VdianGetToken.token);
        paramPublic.put("version","1.0");
        JSONObject results = vdianRestUtil.getRestApi(param, paramPublic);

        JSONObject result = results.getJSONObject("result");

        ItemSalesTop itemSalesTop = new ItemSalesTop();
        itemSalesTop.setCommodity_id(result.getString("itemid"));
        itemSalesTop.setName(result.getString("item_name"));
        itemSalesTop.setUrl(result.getString(""));

        JSONArray thumb_imgs = result.getJSONArray("thumb_imgs");
        ArrayList<String> images = new ArrayList<>();
        for (Object thumb_img : thumb_imgs) {
            images.add(thumb_img.toString());
        }
        itemSalesTop.setImage_url(images);
        itemSalesTop.setPrice((long)(Double.valueOf(result.getString("price"))*100));
        itemSalesTop.setCarriage(0L);

        JSONArray cates = result.getJSONArray("cates");
        String cateNames = "";
        for (Object cate : cates) {
            JSONObject jsonObject = JSONObject.parseObject(cate.toString());
            cateNames += jsonObject.getString("cate_name") + ",";
        }

        itemSalesTop.setClassify(cateNames.substring(0,cateNames.length()-1));
        itemSalesTop.setSub_class("");
        itemSalesTop.setDescription("");
        itemSalesTop.setArea("");
        itemSalesTop.setCompany("");
        itemSalesTop.setCompany_logo("");
        itemSalesTop.setStandard("");
        itemSalesTop.setMemo("");
        itemSalesTop.setDetail("");
        itemSalesTop.setPublish(result.getString("status").equals("instock")?0:result.getString("status").equals("onsale")?1:3);

        return itemSalesTop;
    }

    @Transactional
    @Override
    public void paresReceiveMsg(String content) {
        JSONObject contentJson = JSONObject.parseObject(content);
        String type = contentJson.getString("type");
        ReceiveMessage receiveMessage = JSONObject.toJavaObject(contentJson.getJSONObject("message"), ReceiveMessage.class);
        OrderInfo order = new OrderInfo();
        List<Commodity> commodities = new ArrayList<>();

        User user = userService.getUser(receiveMessage.getBuyer_info().getBuyer_id());

        if (user == null){
            log.error("推送的订单所属用户未绑定。微店用户id："+receiveMessage.getBuyer_info().getBuyer_id());
            throw new RuntimeException("推送的订单所属用户未绑定。");
        }

        order.setOpenid(user.getOpenid());
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        order.setToken(token);
        order.setOrderId(receiveMessage.getOrder_id());
        order.setTradingTime(receiveMessage.getPay_time());
        order.setOrderType(type);
        order.setPush(0);
        order.setDonation(0L);

        List<ReceiveOrderItems> items = receiveMessage.getItems();
        for (ReceiveOrderItems item : items) {
            Commodity commodity = new Commodity();

            commodity.setOrderId(receiveMessage.getOrder_id());
            commodity.setCommodityId(item.getItem_id());
            commodity.setName(item.getItem_name());
            commodity.setClassify("");
            commodity.setSubClass("");
            commodity.setPrice((long)(Double.valueOf(item.getPrice())*100));
            commodity.setActualPrice((long)(Double.valueOf(item.getTotal_price())*100));
            commodity.setCount(Integer.valueOf(item.getQuantity()));
            commodity.setStandard("");
            commodity.setCarriage(Long.valueOf(item.getQuantity()));
            commodity.setArea("");
            commodity.setCompany(receiveMessage.getSeller_name());
            commodity.setType(-1);
            commodity.setRecommendType("");
            commodities.add(commodity);
        }

        //写入到数据库
        orderService.getBaseMapper().insert(order);
        commodityService.saveBatch(commodities);

        //组装数据，推送到固守
        pushMsgToGushou.pushOrder(pushMsgToGushou.assembly(order), user.getInfo());

    }

    @Override
    public boolean callback(String token) {
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        orderInfoQueryWrapper.eq("token",token);
        OrderInfo orderInfo = orderService.getOne(orderInfoQueryWrapper);
        orderInfo.setPush(1);

        return orderService.updateById(orderInfo);
    }

    @Override
    public List<OrderVo> getUserOrder(Integer type, Integer count) {
        QueryWrapper<OrderInfo> orderInfoQueryWrapper = new QueryWrapper<>();
        switch (type){
            case 1:
                orderInfoQueryWrapper.in("push",1,0);
                break;
            case 2:
                orderInfoQueryWrapper.in("push",1);
                break;
            case 3:
                orderInfoQueryWrapper.in("push",0);
                break;
            default:
                throw new RuntimeException("请输入正确的订单类型。");
        }

        orderInfoQueryWrapper.orderByDesc("trading_time");
        orderInfoQueryWrapper.last(" limit 0,"+count);

        List<OrderInfo> orderInfos = orderService.list(orderInfoQueryWrapper);

        List<OrderVo> orderVos = new ArrayList<>();
        for (OrderInfo orderInfo : orderInfos) {

            OrderVo orderVo = new OrderVo();
            BeanUtils.copyProperties(orderInfo,orderVo);

            QueryWrapper<Commodity> commodityQueryWrapper = new QueryWrapper<>();
            commodityQueryWrapper.eq("order_id",orderInfo.getOrderId());
            List<Commodity> commodities = commodityService.list(commodityQueryWrapper);
            List<CommodityVo> commodityVos = new ArrayList<>();
            for (Commodity commodity : commodities) {
                CommodityVo commodityVo = new CommodityVo();
                BeanUtils.copyProperties(commodity,commodityVo);
                commodityVos.add(commodityVo);
            }
            orderVo.setOrder_commodity(commodityVos);

            orderVos.add(orderVo);

        }

        return orderVos;
    }
}
