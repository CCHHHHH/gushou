package com.goodsogood.domain;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chenhao
 * @since 2020-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Order对象", description="")
public class OrderInfo implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "每次推送生成的唯一 的推送数据标识")
    private String token;

    @ApiModelProperty(value = "用户id")
    private String openid;

    @ApiModelProperty(value = "订单id")
    private String orderId;

    @ApiModelProperty(value = "订单推送类型")
    private String orderType;

    @ApiModelProperty(value = "捐款金额")
    private Long donation;

    @ApiModelProperty(value = "交易时间")
    private Date tradingTime;

    @ApiModelProperty(value = "0：该未推送给固守；1：该已推送给固守")
    private Integer push;


}
