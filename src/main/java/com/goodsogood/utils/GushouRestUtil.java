package com.goodsogood.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
            re = Base64.getEncoder().encodeToString(obj.getBytes("utf-8"));
        } catch (Exception e) {
            log.error("数据加密错误", e);
            throw new RuntimeException("数据加密错误");
        }


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json;charset=UTF-8"));

//        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
//        map.add("data", ser.get(1));

        HashMap<String, String> map1 = new HashMap<>();
        map1.put("data", ser.get(1));
//        map1.put("data", "ynAbVi4jCVvmGF7AVvZfvFs3RULWL6%2BsPN6Lrs2BZHxqRUDiyQ572eZW8Nq%2BBre0IqXq%2Fo1%2FTUYHxeyqbJ9XdHTNnrC33CSGjN7wKvrpZZ5KdYmoLs95zvJ8gg0kWHZhE8yP5pGOOR%2BR6eR31fErV4H7Ih8O35o5Bxhaedpawh3Q1e%2BWcYoJUBsEgUPDnPxhlWXwzNqSgZuBMQacViHB3vcK72Z2OU6QHg39RZRhcoSA64HFtZyl1KA9KEuJH9DWFUozAAIQ3P3hVC2WUEQaFF9fRNgffUv4DEGDg%2F2RLStfuYacqwva6Dzj8TuxdEsLGQ12V1DUwbCDq9oclV41%2FvSb%2BooZ3oRyogmTnqp34%2BBHtd%2BFyE8u%2B8NtRYaVJIB74%2FveESIdN%2FVavdc2T4g5TVcUUZxOe2pDbgCwtLWRHRuYMvtM%2BcdeL%2FslGR8990GY3lknGu%2B2YAmGGu8EyhNP8p8B%2BCBO%2FY7u00s0EjLyP%2BQBXqsMQJZNcgQh0I2LobMiWB472GJofMGLsH9BWbw7wCmjFWKOvBpMEMOtXaZVLzOY9lHgUNkDpkz7duOwUiHs4tvwiNIX%2BbbL4ru9QHwav4v91PerH%2FuUgFeyH1p%2Fz%2FwaJSJ855autYncJlzcSUA3l4B6aucJswUE9lb9d57ETm5jqcp6iJdVZEtNerbom0MEemqfpzcyMw%3D%3D");
        String s = JSONObject.toJSONString(map1);


        String url1 = gushouApiUrl + url + "?_h=" + re + "&info=" + info;
        System.out.println(s);
        System.out.println(re);
        System.out.println(url1);

        HttpEntity<String> objectHttpEntity = new HttpEntity<>(s, headers);

        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity(url1, objectHttpEntity, String.class);
        return stringResponseEntity.getBody();
    }

}
