package com.goodsogood.push;

import com.alibaba.fastjson.JSONObject;
import com.goodsogood.entity.OrderVo;
import com.goodsogood.utils.GushouRestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 作者：chenhao
 * 日期：12/23/20 4:44 PM
 **/
@Component
public class PushMsgToGushou {

    @Autowired
    private GushouRestUtil gushouRestUtil;

    /**
     * 已付款（直接到账）/已付款待发货（担保交易）订单推送到固守
     * @param orderVo 推送的数据
     */
    public void alreadyPayment(OrderVo orderVo,String info){
        String content = JSONObject.toJSONString(orderVo);
        String restApi = gushouRestUtil.getRestApi(content, info,"/poverty/push/user/order");
        System.out.println(restApi);
    }

    public void taskPushMsg(){

    }

}
