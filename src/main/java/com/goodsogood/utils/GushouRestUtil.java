package com.goodsogood.utils;

import org.apache.http.client.methods.HttpHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    public String getToken(){

//        /poverty/push/user/order

        HttpHeaders headers = new HttpHeaders();
        headers.add("access_key",access_key);

        String url = gushouApiUrl + "K3API/Token/Create?access_key=" + access_key;
//        String response = restTemplate.getForObject(url, String.class);
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        System.out.println(exchange);

        return null;
    }

}
