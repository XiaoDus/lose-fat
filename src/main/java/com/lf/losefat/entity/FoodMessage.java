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
 * 食物信息表
 * </p>
 *
 * @author 小肚
 * @since 2024-04-22
 */
@Getter
@Setter
  @TableName("food_message")
@ApiModel(value = "FoodMessage对象", description = "食物信息表")
public class FoodMessage implements Serializable {

    private static final long serialVersionUID = 1L;

      @ApiModelProperty("id")
        @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

      @ApiModelProperty("食物id")
      private String foodId;

      @ApiModelProperty("食物名称")
      private String name;

      @ApiModelProperty("健康等级")
      private Integer healthLight;

      @ApiModelProperty("热量值")
      private String calory;

      @ApiModelProperty("千卡")
      private String caloryUnit;

      @ApiModelProperty("热量千焦值")
      private String joule;

      @ApiModelProperty("千焦")
      private String jouleUnit;

      @ApiModelProperty("蛋白质值")
      private String protein;

      @ApiModelProperty("克")
      private String proteinUnit;

      @ApiModelProperty("脂肪")
      private String fat;

      @ApiModelProperty("克")
      private String fatUnit;

      @ApiModelProperty("饱和脂肪值")
      private String saturatedFat;

      @ApiModelProperty("克")
      private String saturatedFatUnit;

      @ApiModelProperty("反式脂肪值")
      private String fattyAcid;

      @ApiModelProperty("克")
      private String fattyAcidUnit;

      @ApiModelProperty("单不饱和脂肪值")
      private String mufa;

      @ApiModelProperty("克")
      private String mufaUnit;

      @ApiModelProperty("多不饱和脂肪值")
      private String pufa;

      @ApiModelProperty("克")
      private String pufaUnit;

      @ApiModelProperty("胆固醇值")
      private String cholesterol;

      @ApiModelProperty("毫克")
      private String cholesterolUnit;

      @ApiModelProperty("碳水化合物值")
      private String carbohydrate;

      @ApiModelProperty("克")
      private String carbohydrateUnit;

      @ApiModelProperty("糖值")
      private String sugar;

      @ApiModelProperty("克")
      private String sugarUnit;

      @ApiModelProperty("膳食纤维值")
      private String fiberDietary;

      @ApiModelProperty("克")
      private String fiberDietaryUnit;

      @ApiModelProperty("钠值")
      private String natrium;

      @ApiModelProperty("毫克")
      private String natriumUnit;

      @ApiModelProperty("酒精度值")
      private String alcohol;

      @ApiModelProperty("%vol")
      private String alcoholUnit;

      @ApiModelProperty("维生素A")
      private String vitaminA;

      @ApiModelProperty("微克RAE")
      private String vitaminAUnit;

      @ApiModelProperty("胡萝卜素")
      private String carotene;

      @ApiModelProperty("微克")
      private String caroteneUnit;

      @ApiModelProperty("维生素B")
      private String vitaminD;

      @ApiModelProperty("微克")
      private String vitaminDUnit;

      @ApiModelProperty("维生素E")
      private String vitaminE;

      @ApiModelProperty("微克")
      private String vitaminEUnit;

      @ApiModelProperty("维生素K")
      private String vitaminK;

      @ApiModelProperty("微克")
      private String vitaminKUnit;

      @ApiModelProperty("维生素B1")
      private String thiamine;

      @ApiModelProperty("毫克")
      private String thiamineUnit;

      @ApiModelProperty("维生素B2")
      private String lactoflavin;

      @ApiModelProperty("毫克")
      private String lactoflavinUnit;

      @ApiModelProperty("维生素B6")
      private String vitaminB6;

      @ApiModelProperty("毫克")
      private String vitaminB6Unit;

      @ApiModelProperty("维生素B12")
      private String vitaminB12;

      @ApiModelProperty("微克")
      private String vitaminB12Unit;

      @ApiModelProperty("维生素C")
      private String vitaminC;

      @ApiModelProperty("毫克")
      private String vitaminCUnit;

      @ApiModelProperty("烟酸")
      private String niacin;

      @ApiModelProperty("毫克")
      private String niacinUnit;

      @ApiModelProperty("叶酸")
      private String folacin;

      @ApiModelProperty("微克")
      private String folacinUnit;

      @ApiModelProperty("泛酸")
      private String pantothenic;

      @ApiModelProperty("毫克")
      private String pantothenicUnit;

      @ApiModelProperty("生物素")
      private String biotin;

      @ApiModelProperty("微克")
      private String biotinUnit;

      @ApiModelProperty("胆碱值")
      private String choline;

      @ApiModelProperty("毫克")
      private String cholineUnit;

      @ApiModelProperty("磷值")
      private String phosphor;

      @ApiModelProperty("毫克")
      private String phosphorUnit;

      @ApiModelProperty("钾值")
      private String kalium;

      @ApiModelProperty("毫克")
      private String kaliumUnit;

      @ApiModelProperty("镁值")
      private String magnesium;

      @ApiModelProperty("毫克")
      private String magnesiumUnit;

      @ApiModelProperty("钙值")
      private String calcium;

      @ApiModelProperty("毫克")
      private String calciumUnit;

      @ApiModelProperty("铁值")
      private String iron;

      @ApiModelProperty("毫克")
      private String ironUnit;

      @ApiModelProperty("锌值")
      private String zinc;

      @ApiModelProperty("毫克")
      private String zincUnit;

      @ApiModelProperty("碘值")
      private String iodine;

      @ApiModelProperty("微克")
      private String iodineUnit;

      @ApiModelProperty("硒值")
      private String selenium;

      @ApiModelProperty("微克")
      private String seleniumUnit;

      @ApiModelProperty("铜值")
      private String copper;

      @ApiModelProperty("毫克")
      private String copperUnit;

      @ApiModelProperty("氟值")
      private String fluorine;

      @ApiModelProperty("毫克")
      private String fluorineUnit;

      @ApiModelProperty("锰")
      private String manganese;

      @ApiModelProperty("毫克")
      private String manganeseUnit;

      @ApiModelProperty("健康描述")
      private String healthTips;

      @ApiModelProperty("健康建议")
      private String healthSuggest;


}
