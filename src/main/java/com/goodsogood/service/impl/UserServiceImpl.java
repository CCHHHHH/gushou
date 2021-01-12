package com.goodsogood.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.goodsogood.config.VdianGetToken;
import com.goodsogood.domain.User;
import com.goodsogood.entity.UserInfo;
import com.goodsogood.mapper.UserMapper;
import com.goodsogood.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.goodsogood.utils.DataEncryption;
import com.goodsogood.utils.VdianRestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author chenhao
 * @since 2020-12-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private VdianRestUtil vdianRestUtil;

    @Override
    public String register(String content, String info, UserInfo userInfo) {

        JSONObject contentJson = JSONObject.parseObject(content);
        String nick_name = contentJson.getString("nick_name");
        String openid = contentJson.getString("openid");
        String access_key = contentJson.getString("access_key");

        //判断用户是否第一次登录
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

        userQueryWrapper.eq("phone",userInfo.getTelephone());
        if (this.count(userQueryWrapper) > 0) {
            return "该用户号码已绑定";
        }


        //调用微店api，注册新用户
//        HashMap<String, Object> param = new HashMap<>();
//        param.put("telephone", userInfo.getTelephone());
//        param.put("noteName", userInfo.getNoteName());
//        param.put("gender", userInfo.getGender());
//        param.put("birthday", userInfo.getBirthday());
////        param.put("weixin","");
////        param.put("street","");
//
//        HashMap<String, Object> paramPublic = new HashMap<>();
//        paramPublic.put("method", "vdian.crm.addCustomer");
//        paramPublic.put("format", "json");
//        paramPublic.put("access_token", VdianGetToken.token);
//        paramPublic.put("version", "1.0");
//
//        JSONObject userJson = vdianRestUtil.getRestApi(param, paramPublic);
//        JSONObject result = userJson.getJSONObject("result");

        User user = new User();
        user.setNickName(nick_name);
        user.setAccessKey(access_key);
        user.setOpenid(openid);
        user.setInfo(info);
        user.setPhone(userInfo.getTelephone());
//        user.setBuyerId(result.getString("buyerId"));

        int insert = this.baseMapper.insert(user);

        return insert > 0 ? "用户绑定成功" : "用户绑定失败，请重试";
    }

    @Override
    public User getUser(String buyerId) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("buyer_id", buyerId);

        return this.getBaseMapper().selectOne(userQueryWrapper);
    }

    @Override
    public User getUserByPhone(String phone) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("phone", phone);
        return this.getBaseMapper().selectOne(userQueryWrapper);
    }

    @Override
    public User getBuyer(String openid) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("openid", openid);

        return this.getBaseMapper().selectOne(userQueryWrapper);
    }

    @Override
    public boolean binding(String content, String info, String _h) throws Exception{
        String data = DataEncryption.decryption(_h, content);
        JSONObject contentJson = JSONObject.parseObject(data);
        String openid = contentJson.getString("openid");

        //判断用户是否第一次登录
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("openid", openid);
        User user = this.getOne(userQueryWrapper);

        //更新用户的info信息
        if (user != null) {
            user.setInfo(info);
            this.updateById(user);
            return false;
        }

        return true;
    }
}
