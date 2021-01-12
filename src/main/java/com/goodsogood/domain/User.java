package com.goodsogood.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private String openid;

    @ApiModelProperty(value = "微店用户id")
    private String buyerId;

    @ApiModelProperty(value = "用户电话号码")
    private String phone;

    @ApiModelProperty(value = "当前用户属于的渠道： 市直、沙区等，商城系统 在拿到该信息以后需要在 之后的请求头中携带该 access_key")
    private String accessKey;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户信息")
    private String info;


}
