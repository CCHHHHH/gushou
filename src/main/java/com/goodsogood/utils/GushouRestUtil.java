package com.goodsogood.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：chenhao
 * 日期：12/17/20 4:24 PM
 **/
@Component
@Slf4j
public class GushouRestUtil {

    @Value("${gushou.access_key}")
    private String access_key;

    @Value("${gushou.apiurl}")
    private String gushouApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    public String getRestApi(String content, String info, String url) {

        String re = null;
        List<String> ser;
        try {
            ser = DataEncryption.indexAndDetail(content);
            //加密头消息
            Map<String, Object> map = new HashMap<>();
            map.put("_hs", ser.get(0));
            map.put("access_key", access_key);
            String obj = JSONObject.toJSONString(map);
            re = URLEncoder.encode(Base64.getEncoder().encodeToString(obj.getBytes("utf-8")), "utf-8");
        } catch (Exception e) {
            log.error("数据加密错误", e);
            throw new RuntimeException("数据加密错误");
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("data", ser.get(1));
        System.out.println("加密的消息体：" + ser.get(1));

        String url1 = gushouApiUrl + url + "?_h=" + re + "&info=" + info;
        System.out.println(re);
        System.out.println(url1);

        HttpEntity<MultiValueMap<String, String>> objectHttpEntity = new HttpEntity<>(map, headers);

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url1, objectHttpEntity, String.class);

        return stringResponseEntity.getBody();
    }

}
