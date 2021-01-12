package com.goodsogood.service;

import com.goodsogood.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.goodsogood.entity.UserInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chenhao
 * @since 2020-12-18
 */
public interface IUserService extends IService<User> {

    /**
     * 绑定固守与微店用户
     * @param content
     * @param info
     * @return
     */
    String register(String content, String info, UserInfo userInfo);

    /**
     * 更加微店用户id，获取固守用户
     * @param buyerId
     * @return
     */
    User getUser(String buyerId);

    /**
     * 更加固守用户openid，获取固守用户
     * @param openid
     * @return
     */
    User getBuyer(String openid);

    /**
     * 判断用户是否已绑定，如果已绑定，先更新info，再直接跳转微店首页
     * @param content
     * @param info
     * @param _h
     * @return true代表用户未绑定，需要跳转到注册页进行用户绑定。false代表用户已绑定，更新info信息，并跳转微店首页
     */
    boolean binding(String content, String info,String _h) throws Exception;
}
