package com.lf.backstage.entity.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EditFoodKnowledge {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("问题")
    private String question;

    @ApiModelProperty("错误答案")
    private String wrongAnswer;

    @ApiModelProperty("正确答案")
    private String correctAnswer;

}
