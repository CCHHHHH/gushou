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
 * @since 2020-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Commodity对象", description="")
public class Commodity implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "商品id")
    private String commodityId;

    @ApiModelProperty(value = "商品名称")
    private String name;

    @ApiModelProperty(value = "商品分类")
    private String classify;

    @ApiModelProperty(value = "子类")
    private String subClass;

    @ApiModelProperty(value = "价格")
    private Long price;

    @ApiModelProperty(value = "实际售价")
    private Long actualPrice;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "规格、单位")
    private String standard;

    @ApiModelProperty(value = "运费")
    private Long carriage;

    @ApiModelProperty(value = "所属区县")
    private String area;

    @ApiModelProperty(value = "商户名称")
    private String company;

    @ApiModelProperty(value = "商品类型 1：普通商品 2：一元捐商品")
    private Integer type;

    @ApiModelProperty(value = "与订单表关联字段")
    private String orderId;

    @ApiModelProperty(value = "商品所属专区编号")
    private String recommendType;


}
