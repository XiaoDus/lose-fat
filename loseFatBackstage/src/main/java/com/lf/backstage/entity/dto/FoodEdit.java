package com.lf.backstage.entity.dto;

import lombok.Data;

@Data
public class FoodEdit {
    private String foodId;
    private String  name;
    private String classId;
    private String healthLevel;
    private String  calory;

}
