package com.lf.losefat.entity;

import java.io.Serializable;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * @since 2024-03-22
 */
@Getter
@Setter
@ApiModel(value = "User对象", description = "")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @ApiModelProperty("用户id")
    @TableId
    private String  userId;

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

    @JsonIgnore
    @ApiModelProperty("openId")
    private String openId;

    @ApiModelProperty("头像")
    private String avatar;

    @JsonIgnore
    @ApiModelProperty("用户密码")
    private String userPassword;

    @ApiModelProperty("性别")
    private String userSex;

    @TableField(exist = false)
    private String token;



}
