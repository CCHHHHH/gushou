package com.goodsogood.domain;

import java.io.Serializable;

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
 * @since 2020-12-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Address对象", description="")
public class Address implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "收货人联系方式")
    private String phone;

    @ApiModelProperty(value = "收货人详细地址")
    private String location;

    @ApiModelProperty(value = "收货人邮编")
    private String postCode;

    @ApiModelProperty(value = "1：是 2：否")
    private Integer isDefault;

    @ApiModelProperty(value = "该 id 为固守数据唯一 标识")
    private String addressId;

    @ApiModelProperty(value = "收货人姓名")
    private String name;

    @ApiModelProperty(value = "与用户表关联字段")
    private String openid;


}
