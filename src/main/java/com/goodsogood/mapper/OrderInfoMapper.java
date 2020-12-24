package com.goodsogood.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.goodsogood.domain.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author chenhao
 * @since 2020-12-21
 */
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    @Select("select * from order_info where ${conditions} order by trading_time limit(0,${count})")
    public List<OrderInfo> getOrderList(@Param("conditions") String conditions,@Param("count") Integer count);

}
