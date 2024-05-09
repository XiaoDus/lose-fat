package com.lf.losefat.entity;

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
 * @since 2024-04-11
 */
@Getter
@Setter
  @TableName("food_class")
@ApiModel(value = "FoodClass对象", description = "")
public class FoodClass implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("食物分类id")
        private Integer id;

      @ApiModelProperty("食物名称")
      private String name;

      @ApiModelProperty("分类图片")
      private String icon;


}
