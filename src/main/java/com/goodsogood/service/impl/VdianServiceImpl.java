package com.goodsogood.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.goodsogood.config.VdianGetToken;
import com.goodsogood.entity.ItemSales;
import com.goodsogood.service.VdianService;
import com.goodsogood.utils.VdianRestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * 作者：chenhao
 * 日期：12/15/20 9:52 AM
 **/
@Service
public class VdianServiceImpl implements VdianService {

    @Autowired
    private VdianRestUtil vdianRestUtil;

    @Override
    public ItemSales getItemSalesTop(String page_no, String page_size) {
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
        return JSONObject.toJavaObject(results.getJSONObject("result"),ItemSales.class);
    }
}
