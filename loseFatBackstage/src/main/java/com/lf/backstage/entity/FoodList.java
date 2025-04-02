package com.lf.backstage.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

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
 * @since 2025-03-20
 */
@Getter
@Setter
@TableName("food_list")
@ApiModel(value = "FoodList对象", description = "")
public class FoodList implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("食物id")
    @TableId("food_id")
    private String foodId;

    @ApiModelProperty("食物名称")
    private String name;

    @ApiModelProperty("健康等级（1:推荐 2:适量 3:少吃）")
    private Integer healthLevel;

    @ApiModelProperty("热量/千卡/100g/100ml")
    private String calory;

    @ApiModelProperty("分类id")
    private Integer classId;


}
