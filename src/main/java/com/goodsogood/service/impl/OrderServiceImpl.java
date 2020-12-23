package com.goodsogood.service.impl;

import com.goodsogood.domain.OrderInfo;
import com.goodsogood.mapper.OrderMapper;
import com.goodsogood.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenhao
 * @since 2020-12-21
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderInfo> implements IOrderService {

}
