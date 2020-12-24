package com.goodsogood;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.goodsogood.mapper")
public class GushouApplication {
    public static void main(String[] args) {
        SpringApplication.run(GushouApplication.class, args);
    }

}
