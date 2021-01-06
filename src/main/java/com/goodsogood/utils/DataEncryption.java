package com.goodsogood.utils;

import com.alibaba.fastjson.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

public class DataEncryption {

    public static void main(String[] args) throws Exception {

//        String content = "{\"page_no\":1,\"page_size\":20}";
//        String content = "{\"nick_name\":\"安安\",\"openid\":\"o3wYk1bTQajjG3cFTi_1qb6Y6UGg\",\"access_key\":\"659202aded10458380b0a97a460c474e\"}";
//        String content = "{\"commodity_id\":\"3454229114\"}";
//        String content = "{\"token\":\"d2019178afeb4fb8aaccfa21fa389a6e\"}";
        String content = "{\"type\":1,\"count\":10}";

        //加密KEY
        List<String> jiami = indexAndDetail(content);
//        //加密头消息
        Map<String, Object> map = new HashMap<>();
        System.out.println("key:" + jiami.get(0));
        map.put("_hs", jiami.get(0));
        map.put("access_key", "659202aded10458380b0a97a460c474e");
        String obj = JSONObject.toJSONString(map);
        String re = URLEncoder.encode(Base64.getEncoder().encodeToString(obj.getBytes("utf-8")), "utf-8");
        System.out.println("加密后的_h参数：" + re);

//        decryption("eyJhY2Nlc3Nfa2V5IjoiNjU5MjAyYWRlZDEwNDU4MzgwYjBhOTdhNDYwYzQ3NGUiLCJfaHMiOiIxMDc4MzkzMzUxIn0%3D","ZHfeWH6xONshKM6ochKbwplogiwol5lBZLS%2BZOxNKB7TGDnE4uaKZpOMwYX0VLy%2BwX%2Fhc0RsOIuw7BatDsQnljDgcy7y8EvHZ4HbOEvXrEEJfIE5Leo%2BB5lEXzVRFOJ7R13w%2Fxvv1E%2By2WCzn%2BSd3C%2F1JKDXL5HHx4Dce5bT2XcX4FkXsV4MTDqomQqyaVne%2Bx%2Bbs1fmHwNQmMS9OjaOmbft08iCNnYqODtnnfbyYYNoNqk%2BWt6F0CjebpEggs61zDoqsDmMjER37pFGW0CHeYZfGhU%2FDECiaiwZCOOg4rYvaK9twey1yTg1jwgSeMczhVUOzyrbqtjd6NsedpuvAhuweQavul8B%2FxRtIuFiaV0TnnMSlkwh5AghMex92kykahyjmw5ZnADt4xdeyfOGs2X0iHNcE8DZqTRhtkfu6vgXCvq6J1Nc9Ci3HxVo02wPUFFemCrW7YYF9nU4gR169UW0nn944%2BS%2B75zcr73L%2B%2Bo9lTMFwICGEyt2VTqI%2FopAqvnXv6dprIaaWRDw6Da6%2ByfxAGTYHxubXRsqzbLQ3kAgZRyzAOnTtuDO%2FhnQsL%2FcdrN%2BaZsKWeGy44d6FWLHUzhrS5tguzHgzQDforaaf%2FMrOzhI42pVPCJNMu2QQ%2FLOusDI7FtTozmmPB3Flv8KaZk3fLa9DAmf");

    }

    /**
     * 首页、详情页跳转，url携带参数加密
     *
     * @throws Exception
     */
    public static List<String> indexAndDetail(String content){


        System.out.println("原文长度：" + content.length());
        ArrayList<String> list = new ArrayList<>();

        Integer r = null;
        try {
            System.out.println("原文：" + content);
            //生成随机hash  TODO 生成九位数hash值
//            Integer randomHash = Math.abs(DataEncryption.SDBMHash(DataEncryption.getRandomStr(8)));
            Integer randomHash = 123781021;

            System.out.println("randomHash:"+randomHash);
            //约定的偏移量(测试环境与正式环境不一样,由固守方提供)
            Integer salt = 9824191;
            //使用随机生成的hash减去约定偏移量取绝对值，作为真正加密KEY
            Integer keyHash = Math.abs(randomHash-salt);
            System.out.println("keyHash:"+keyHash);
            //生成key
            String key = DataEncryption.appedZero(String.valueOf(keyHash),8);
            System.out.println("key："+ key);
            //加密内容
            String encrypt = DESUtils.encryption(content, key);
            System.out.println("加密："+ encrypt);

            //url encode
            String encode = URLEncoder.encode(encrypt, "utf-8");
            System.out.println("加密后的消息体 : " + encode);

            //hash密文
            Integer encryptHash =  Math.abs(DataEncryption.SDBMHash(encrypt));
            System.out.println("hash密文："+encryptHash);
            //传递key为加密后报文
            r = randomHash - encryptHash;
            System.out.println("传递key："+r);


            list.add(r.toString());
            list.add(encode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //解密
    public static String decryption(String re,String body) throws Exception {

        String header = Base64ToStringUtil.baseConvertStr(URLDecoder.decode(re, "UTF-8"));
        Map<String, String> map = JSONObject.parseObject(header, Map.class);
        String key = map.get("_hs");
        System.out.println(key);
//        String body = "1%2FlETfls1AGRGlUfGDCy7Uip2PJ58JUmv0LN%2Bh1zcl2h3pnHZOHOd2r81jXDI4tDrRXv48AkR8pYVhreNeHrIarntqiivu732Ay5k2nnMwIMkU%2FGNMfii7Fj0DqIv%2Fg6BqLWIvFNmMQJIyAYKD%2BqnCHVxv%2F6rkq3MzEFeFbSV8WLdpp5m%2FvcthtGOyij4zqmyp3mklvxppzmySsbnGcMVTnrR5BrXE4JLBjzvu8xYR9QBM7Q6rqSCfWidgf9QursN8PNnnm1BtWFjAMv6Kq4MbvFx1lKdMna%2BgV%2Bp4BxXUZ2%2F%2F99xvV9x72AqdN9hi32idFB6%2BcAQSKdcAR%2Bn0Gc5OXXwBvEEmYofe06ZtgFVHVKi49IQcbV%2B7vJO6QGeJXW1tcz5YAxoXo9ojq3JWyVRd1lWvEMKcBh6PywxIEOqJRdo9N6AeigqpJX%2BRdn8Ta3fbzBUMJuhaOCZOf1mgBal5IduRrMBiHLTpnwec9jX1WjgmfsTVXQyh%2Ft%2BlsMSF1nrZp2x7DVwwBjyIkD699BveAjsYq1IMbYUMRZSIB0dzzgeb9WyIzynUQL%2Bn82AlWGF5glAAnHhdQ%3D";
        body = URLDecoder.decode(body, "UTF-8");
        //hash密文
        Integer encryptHash =  Math.abs(DataEncryption.SDBMHash(body));
        //随机key
//        Integer randomHash = 715103070;
        Integer randomHash = Integer.valueOf(key) + encryptHash;
        //约定的偏移量
        Integer salt = 9824191;
        //使用随机生成的hash减去约定偏移量取绝对值，作为真正加密KEY
        Integer keyHash = Math.abs(randomHash-salt);
        //根据加盐Hash生成key
        key =  DataEncryption.appedZero(String.valueOf(keyHash),8);
        body = DESUtils.decryption(body, key);
        System.out.println(body);

        return body;
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
