package com.lf.backstage.controller;

import com.lf.backstage.common.Result;
import com.lf.backstage.entity.FoodClass;
import com.lf.backstage.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomeController {
    @Autowired
    private IFoodListService foodListService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IFileService fileService;
    @Autowired
    private IFoodKnowledgeService knowledgeService;
    @Autowired
    private IFoodClassService foodClassService;

    @GetMapping
    public Result getCount() {
        Map<String, Object> resultMap = new HashMap<>();

        // 各模块总数
        int userCount = (int) userService.count();
        int foodCount = (int) foodListService.count();
        int fileCount = (int) fileService.count();
        int knowledgeCount = (int) knowledgeService.count();

        resultMap.put("userCount", userCount);
        resultMap.put("foodCount", foodCount);
        resultMap.put("fileCount", fileCount);
        resultMap.put("knowledgeCount", knowledgeCount);

        // 食物分类统计（按 name 分组）
        List<Map<String, Object>> foodCategoryDistribution = foodListService.getFoodCategoryDistribution();
        resultMap.put("foodCategoryDistribution", foodCategoryDistribution);

        return Result.success(resultMap);
    }
}
