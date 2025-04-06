package com.lf.losefat.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @since 2024-04-22
 */
@Getter
@Setter
@TableName("food_collect")
@ApiModel(value = "FoodCollect对象", description = "")
public class FoodCollect implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
      @TableId(value = "id", type = IdType.AUTO)
      private String id;

      @ApiModelProperty("用户id")
      private String userId;

      @ApiModelProperty("食物id")
      private String foodId;


}
