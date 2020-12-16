package com.goodsogood.controller;

import com.goodsogood.response.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * 作者：chenhao
 * 日期：12/14/20 10:10 AM
 **/
@Api(tags = {"固守接口"})
@RestController
@CrossOrigin
@RequestMapping("/rest/gushou")
public class GushouController {

    @CrossOrigin
    @ApiOperation(value = "测试接口", notes = "测试接口")
    @ResponseBody
    @RequestMapping(value = "/refreshToken", method = RequestMethod.POST)
    public BaseResponse getToken() {
        try {

            return BaseResponse.initSuccessBaseResponse("获取Token成功");
        } catch (Exception e) {
            return BaseResponse.initErrorBaseResponse("获取Token异常："+e.getMessage());
        }
    }
}
