package com.goodsogood.config;

import com.goodsogood.utils.VdianRestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
 
@Component
@EnableScheduling
public class VdianGetToken {

    @Autowired
    private VdianRestUtil vdianRestUtil;

    public static String token = "b0e6e162529ea55e37fafbbc3524af5f0009219503";

//    @PostConstruct
    public void init() {
        token = vdianRestUtil.getToken();
        System.out.println(token);
    }
 
    @PreDestroy
    public void destroy() {
        //系统运行结束
    }
    //每天凌晨俩点刷新一次token
//    @Scheduled(cron = "0 0 2 * * ?")
    public void refresh() {
        init();
    }
 
}