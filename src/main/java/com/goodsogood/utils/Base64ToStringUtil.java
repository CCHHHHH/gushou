package com.goodsogood.utils;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 作者：chenhao
 * 日期：12/17/20 8:57 AM
 **/
public class Base64ToStringUtil {
    //字符串转Base64
    public static String strConvertBase(String str) {
        if (null != str) {
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(str.getBytes());
        }
        return null;
    }
    //Base64转字符串
    public static String baseConvertStr(String str) {
        if (null != str) {
            Base64.Decoder decoder = Base64.getDecoder();
            return new String(decoder.decode(str.getBytes()), StandardCharsets.UTF_8);
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "xcasd陈昊fkvnldkmldkmf";
        String s = Base64ToStringUtil.strConvertBase(str);
        System.out.println(s);
        System.out.println(baseConvertStr(s));
    }
}
