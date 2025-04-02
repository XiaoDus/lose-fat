package com.lf.backstage.entity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResFoodList {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("食物id")
    private String foodId;

    @ApiModelProperty("食物名称")
    private String name;

    @ApiModelProperty("健康等级（1:推荐 2:适量 3:少吃）")
    private Integer healthLevel;

    @ApiModelProperty("热量/千卡/100g/100ml")
    private String calory;

    @ApiModelProperty("分类名称")
    private String className;

    @ApiModelProperty("分类id")
    private Integer classId;

}
