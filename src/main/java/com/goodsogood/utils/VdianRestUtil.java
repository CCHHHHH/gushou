package com.goodsogood.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * 作者：chenhao
 * 日期：12/16/20 2:40 PM
 **/
@Component
@Slf4j
public class VdianRestUtil {

    @Value("${vdian.appKey}")
    private String appKey;

    @Value("${vdian.appSecret}")
    private String appSecret;

    @Value("${vdian.apiurl}")
    private String vdianApiUrl;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取最新的token值
     * @return token
     */
    public String getToken() {
        String access_token = null;
        try {
        String url = "https://oauth.open.weidian.com/token?grant_type=client_credential&appkey=" + appKey + "&secret=" + appSecret;
        log.info("url======="+url);
        String response = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject result = jsonObject.getJSONObject("result");
        log.info("获取token返回的值："+result.toJSONString());
        access_token = result.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
        return access_token;
    }

    /**
     * 调用vdian接口
     * @param param api相关参数
     * @param publicParam 接口地址版本token之类的
     * @return
     */
    public JSONObject getRestApi(Map<String, Object> param, Map<String, Object> publicParam) {
        String paramStr = JSONObject.toJSONString(param);
        String paramPublicStr = JSONObject.toJSONString(publicParam);
        String url = vdianApiUrl + "?param={paramStr}" + "&public={paramPublicStr}";
        JSONObject results = restTemplate.getForObject(url, JSONObject.class, paramStr, paramPublicStr);

        if (results==null){
            throw new RuntimeException("调用微店接口失败！");
        }

        JSONObject status = results.getJSONObject("status");
        //判断微店接口是否调用成功
        Integer status_code = status.getInteger("status_code");
        String status_reason = status.getString("status_reason");
        if (status_code!=0){
            throw new RuntimeException(status_reason);
        }
        return results;
    }
}
