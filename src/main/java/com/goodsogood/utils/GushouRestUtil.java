package com.goodsogood.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者：chenhao
 * 日期：12/17/20 4:24 PM
 **/
@Component
public class GushouRestUtil {

    @Value("${gushou.access_key}")
    private String access_key;

    @Value("${gushou.apiurl}")
    private String gushouApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    public String getRestApi(String content, String info, String url) {

        String re = null;
        try {
            String key = DataEncryption.indexAndDetail(content);
            //加密头消息
            Map<String, Object> map = new HashMap<>();
            map.put("_hs", key);
            map.put("access_key", access_key);
            String obj = JSONObject.toJSONString(map);
            re = URLEncoder.encode(Base64.getEncoder().encodeToString(obj.getBytes("utf-8")), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("数据加密错误");
        }

        MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<String, Object>();
        paramMap.add("data", re);
        paramMap.add("info", info);

        HttpHeaders headers = new HttpHeaders();
        headers.add("access_key", access_key);

        String url1 = gushouApiUrl + url + "?_h="+re;
//        String url1 = gushouApiUrl + url;

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<MultiValueMap<String, Object>>(paramMap,headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url1, HttpMethod.POST, httpEntity, String.class);
        System.out.println(exchange);

        return null;
    }

}
