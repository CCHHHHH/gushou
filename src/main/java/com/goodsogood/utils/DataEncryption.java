package com.goodsogood.utils;

import com.alibaba.fastjson.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DataEncryption {

    public static void main(String[] args) throws Exception {

        String content = "{" +
                "\"nick_name\":\"安安\"," +
                "\"openid\": \"o3wYk1bTQajjG3cFTi_1qb6Y6UGg\"," +
                "\"access_key\": \"659202aded10458380b0a97a460c474e\"" +
                "}";

        //加密KEY
        String key = indexAndDetail(content);
        //加密头消息
        Map<String, Object> map = new HashMap<>();
        System.out.println("key:" + key);
        map.put("_hs", key);
        map.put("access_key", "659202aded10458380b0a97a460c474e");
        String obj = JSONObject.toJSONString(map);
        String re = URLEncoder.encode(Base64.getEncoder().encodeToString(obj.getBytes("utf-8")), "utf-8");
        System.out.println("加密后的_h参数：" + re);

        String text = indexAndDetail1("eyJhY2Nlc3Nfa2V5IjoiNjU5MjAyYWRlZDEwNDU4MzgwYjBhOTdhNDYwYzQ3NGUiLCJfaHMiOiIxNTMwMTYxNjczIn0%3D");
    }

    /**
     * 首页、详情页跳转，url携带参数加密
     *
     * @throws Exception
     */
    public static String indexAndDetail(String content) throws Exception {

//          System.out.println("原文长度：" + content.length());

        System.out.println("原文：" + content);
        //生成随机hash
        Integer randomHash = Math.abs(DataEncryption.SDBMHash(DataEncryption.getRandomStr(16)));
//          System.out.println("randomHash:"+randomHash);
        //约定的偏移量(测试环境与正式环境不一样,由固守方提供)
        Integer salt = 9824191;
        //使用随机生成的hash减去约定偏移量取绝对值，作为真正加密KEY
        Integer keyHash = Math.abs(randomHash - salt);
//          System.out.println("keyHash:"+keyHash);
        //生成key
        String key = DataEncryption.appedZero(String.valueOf(keyHash), 8);
          System.out.println("key："+ key);
        //加密内容
        String encrypt = DESUtil.encryption(content, key);
        System.out.println("加密：" + encrypt);

        String decryption = DESUtil.decryption(encrypt, key);
        System.out.println("解密：" + decryption);

        //url encode
        String encode = URLEncoder.encode(encrypt, "utf-8");
        System.out.println("加密后的消息体 : " + encode);

        //hash密文
        Integer encryptHash = Math.abs(DataEncryption.SDBMHash(encrypt));
//          System.out.println("hash密文："+encryptHash);
        //传递key为加密后报文
        Integer r = randomHash - encryptHash;
//        System.out.println("传递key：" + r);
        return r.toString();
    }

    //解密
    public static String indexAndDetail1(String re) throws Exception {
        String s = Base64ToStringUtil.baseConvertStr(URLDecoder.decode(re, "UTF-8"));
        System.out.println("解密：" + s);

        Map<String,String> map = JSONObject.parseObject(s, Map.class);
        String key = map.get("_hs");

        System.out.println(key);
        String body = re;
        body = URLDecoder.decode(body, "UTF-8");
        //hash密文
        Integer encryptHash = Math.abs(DataEncryption.SDBMHash(body));
        //随机key
        Integer randomHash = Integer.valueOf(key) + encryptHash;
        //约定的偏移量
        Integer salt = 9824191;
        //使用随机生成的hash减去约定偏移量取绝对值，作为真正加密KEY
        Integer keyHash = Math.abs(randomHash - salt);
        //根据加盐Hash生成key
        key = DataEncryption.appedZero(String.valueOf(keyHash), 8);
        System.out.println(key);
        body = DESUtil.decryption(body, key);
        System.out.println(body);

        return "";
    }


    /**
     * 不足8位补零
     *
     * @param str
     * @param length
     * @return
     */
    public static String appedZero(String str, int length) {
        return str.length() > length ? str.substring(0, length) : str.length() < length ? String.format("%" + length + "s", str).replace(" ", "0") : str;
    }

    /**
     * SDBM算法
     */
    public static int SDBMHash(String str) {
        int hash = 0;
        for (int i = 0; i < str.length(); i++) {
            hash = str.charAt(i) + (hash << 6) + (hash << 16) - hash;
        }
        return (hash & 0x7FFFFFFF);
    }


    /**
     * 获取随机数字符串
     *
     * @param length 获取字符串长度
     * @return 返回随机字符串
     */
    public static String getRandomStr(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
