package com.goodsogood.gushou;

import com.alibaba.fastjson.JSONObject;
import com.goodsogood.GushouApplication;
import com.goodsogood.config.VdianGetToken;
import com.goodsogood.domain.User;
import com.goodsogood.entity.ItemSalesTop;
import com.goodsogood.service.IUserService;
import com.goodsogood.service.VdianService;
import com.goodsogood.service.impl.UserServiceImpl;
import com.goodsogood.utils.GushouRestUtil;
import com.goodsogood.utils.VdianRestUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= GushouApplication.class)
class GushouApplicationTests {

    @Autowired
    private VdianRestUtil vdianRestUtil;

    @Autowired
    private VdianService vdianService;

    @Autowired
    private GushouRestUtil gushouRestUtil;

    @Autowired
    private IUserService userService;


    @Test
    void contextLoads() {
        String token = vdianRestUtil.getToken();
        System.out.println(token);
    }

    @Test
    void getRest(){
        HashMap<String, Object> param = new HashMap<>();
        HashMap<String, Object> paramPublic = new HashMap<>();
//{"page_size":"40","status":1,"orderby":"1","page_num":"1","orderby":"5"}
        param.put("page_size","5");
        param.put("status",1);
        param.put("orderby","1");
        param.put("page_num","1");
//{"method":"vdian.item.list.get","format":"json","access_token":"c6b28ddee7069ba42eb998fc0abb05700009219503","version":"1.1"}
        paramPublic.put("method","vdian.item.list.get");
        paramPublic.put("format","json");
        paramPublic.put("access_token",VdianGetToken.token);
        paramPublic.put("version","1.1");
        JSONObject results = vdianRestUtil.getRestApi(param, paramPublic);
        JSONObject result = results.getJSONObject("result");

        System.out.println(result);

    }

    @Test
    public void getItemDetail(){
        ItemSalesTop itemDetail = vdianService.getItemDetail("4235279917");
        System.out.println(itemDetail);
    }

    @Test
    public void getGushouToken(){
        String token = gushouRestUtil.getToken();
        System.out.println(token);
    }

    @Test
    public void getUsers(){
        List<User> users = userService.list();
        for (User user : users) {
            System.out.println(user);
        }
    }

}
