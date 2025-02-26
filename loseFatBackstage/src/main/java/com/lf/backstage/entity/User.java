package com.lf.backstage.entity;

import java.io.Serializable;
import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 *
 * </p>
 *
 * @author 小肚
 * @since 2025-02-11
 */
@Getter
@Setter
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    private String userId;

    @ApiModelProperty("昵称")
    private String userName;

    @ApiModelProperty("手机号")
    private String userPhone;

    @ApiModelProperty("年龄")
    private Integer userAge;

    @ApiModelProperty("生日")
    private LocalDate userBirthday;

    @ApiModelProperty("身高（单位：cm）")
    private String userHeight;

    @ApiModelProperty("体重（单位：斤）")
    private String userWeight;

    @ApiModelProperty("标准体重（单位：斤）")
    private String standardWeigth;

    @ApiModelProperty("基础代谢（单位：千卡）")
    private String basalMetabolism;

    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户密码")
    private String userPassword;

    @ApiModelProperty("性别")
    private String userSex;
    @ApiModelProperty("身份")
    private String status;


}
