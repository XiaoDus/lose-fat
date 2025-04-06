package com.lf.losefat.controller.dto;

import com.lf.losefat.entity.FoodList;
import lombok.Data;

import java.util.ArrayList;

@Data
public class FoodLists {
    private ArrayList<FoodList> foodLists;
}
