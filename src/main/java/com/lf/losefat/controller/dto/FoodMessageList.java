package com.lf.losefat.controller.dto;

import com.lf.losefat.entity.FoodMessage;
import lombok.Data;

import java.util.List;
@Data
public class FoodMessageList {
    private List<FoodMessage> foodMessageList;
}
