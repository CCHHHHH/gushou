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

    public static String token = "fde2d764210d0b630eeb4802fc29de000009220298";

    //TODO 上线别忘了放开注释
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
    //TODO 这里的注释也要放开
//    @Scheduled(cron = "0 0 2 * * ?")
    public void refresh() {
        init();
    }
 
}