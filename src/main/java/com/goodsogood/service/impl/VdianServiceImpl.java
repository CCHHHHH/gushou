package com.goodsogood.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.goodsogood.config.VdianGetToken;
import com.goodsogood.entity.Cates;
import com.goodsogood.entity.ItemSales;
import com.goodsogood.entity.ItemSalesTop;
import com.goodsogood.entity.Items;
import com.goodsogood.service.VdianService;
import com.goodsogood.utils.VdianRestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        ItemSales result = JSONObject.toJavaObject(results.getJSONObject("result"), ItemSales.class);
        List<Items> items = result.getItems();
        List<ItemSalesTop> itemSalesTops = new ArrayList<>();

        //TODO
        for (Items item : items) {
            ItemSalesTop itemSalesTop = new ItemSalesTop();
            itemSalesTop.setCommodity_id(item.getItemid());
            itemSalesTop.setName(item.getItem_name());
            itemSalesTop.setUrl("");
            itemSalesTop.setImage_url(item.getThumb_imgs());
            itemSalesTop.setPrice(item.getPrice());
            itemSalesTop.setCarriage("");
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


        return result;
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
        itemSalesTop.setPrice(result.getString("price"));
        itemSalesTop.setCarriage(result.getString(""));

        JSONArray cates = result.getJSONArray("cates");
        String cateNames = "";
        for (Object cate : cates) {
            JSONObject jsonObject = JSONObject.parseObject(cate.toString());
            cateNames += jsonObject.getString("cate_name") + ",";
        }

        itemSalesTop.setClassify(cateNames.substring(0,cateNames.length()-1));
        itemSalesTop.setSub_class(result.getString(""));
        itemSalesTop.setDescription(result.getString("item_desc"));
        itemSalesTop.setArea(result.getString(""));
        itemSalesTop.setCompany(result.getString(""));
        itemSalesTop.setCompany_logo(result.getString(""));
        itemSalesTop.setStandard(result.getString(""));
        itemSalesTop.setMemo(result.getString(""));
        itemSalesTop.setDetail(result.getString(""));
        itemSalesTop.setPublish(result.getString("status").equals("instock")?0:result.getString("status").equals("onsale")?1:3);

        return itemSalesTop;
    }
}
