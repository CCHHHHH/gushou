package com.goodsogood.service.impl;

import com.goodsogood.domain.Address;
import com.goodsogood.mapper.AddressMapper;
import com.goodsogood.service.IAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chenhao
 * @since 2020-12-18
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements IAddressService {

}
