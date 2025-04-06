package com.lf.backstage.entity;

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
 * @since 2025-04-06
 */
@Getter
@Setter
  @TableName("food_knowledge")
@ApiModel(value = "FoodKnowledge对象", description = "")
public class FoodKnowledge implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("问题")
      private String question;

      @ApiModelProperty("错误答案")
      private String wrongAnswer;

      @ApiModelProperty("正确答案")
      private String correctAnswer;

      @ApiModelProperty("被选择的次数")
      private Integer selectNumber;


}
