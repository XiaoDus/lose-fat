package com.lf.losefat.controller.dto;

import com.lf.losefat.entity.FoodClass;
import lombok.Data;

import java.util.ArrayList;

@Data
public class Foods {
    private ArrayList<FoodClass> foodClasses;
}
