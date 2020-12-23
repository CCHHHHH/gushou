package com.goodsogood.service;

import com.goodsogood.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

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
    boolean register(String content, String info);

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
}
