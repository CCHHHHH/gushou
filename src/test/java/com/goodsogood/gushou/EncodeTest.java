package com.goodsogood.gushou;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * 作者：chenhao
 * 日期：12/15/20 10:16 AM
 **/
public class EncodeTest {
    @Test
    public void test1(){
        ByteBuffer encode = Charset.forName("UTF-8").encode("陈昊");
        byte[] array = encode.array();
        for (byte b : array) {
            System.out.print(b);
        }
        //https://oauth.open.weidian.com/token?grant_type=client_credential&appkey=3703027904&secret=2be1fb189aa9123ed77ff9041a1a9494
        //param={"page_size":"40","status":1,"orderby":"1","page_num":"1","update_start":"2012-11-12 16:36:08","update_end":"2019-11-12 16:36:08"}&public={"method":"vdian.item.list.get","format":"json","access_token":"c6b28ddee7069ba42eb998fc0abb05700009219503","version":"1.1"}
        //c6b28ddee7069ba42eb998fc0abb05700009219503

    }

    @Test
    public void bytes(){
        byte[] bytes = {(byte) 0x13,(byte) 0x01};
        String s = new String(bytes);
        System.out.println(s);
    }
}
