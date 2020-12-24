package com.goodsogood.service.impl;

import com.goodsogood.domain.OrderInfo;
import com.goodsogood.mapper.OrderInfoMapper;
import com.goodsogood.service.IOrderInfoService;
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
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements IOrderInfoService {

}
