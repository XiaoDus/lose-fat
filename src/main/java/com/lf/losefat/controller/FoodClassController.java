package com.lf.losefat.controller;


import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lf.losefat.common.Result;
import com.lf.losefat.controller.dto.FoodLists;
import com.lf.losefat.controller.dto.Foods;
import com.lf.losefat.entity.FoodClass;
import com.lf.losefat.entity.FoodList;
import com.lf.losefat.entity.FoodMessage;
import com.lf.losefat.service.IFoodClassService;
import com.lf.losefat.service.IFoodListService;
import com.lf.losefat.service.IFoodMessageService;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 小肚
 * @since 2024-04-11
 */
@RestController
@RequestMapping("/foods")
public class FoodClassController {

    @Autowired
    private IFoodClassService foodClassService;

    @Autowired
    private IFoodListService foodListService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IFoodMessageService foodMessageService;

    /**
     * 添加分类
     *
     * @param foods
     * @return
     */
    @GetMapping("/class")
    public Result food_class(@RequestBody Foods foods) {
//        foodClassService.save(foodClass);

        foodClassService.saveBatch(foods.getFoodClasses());
        return Result.success();
    }

    /**
     * 添加食物信息
     *
     * @param foodLists
     * @return
     */
    @PostMapping("/list")
    public Result food_list(@RequestBody FoodLists foodLists) {
        foodListService.saveBatch(foodLists.getFoodLists());
        return Result.success();
    }

    /**
     * 获取食物分类列表
     * @return
     */
    @GetMapping("/get_food_class")
    public Result getFoodClass() {
        return Result.success(foodClassService.list());
    }

    /**
     * 分页查询指定食物分类中的食物
     *
     * @param id       食物分类id
     * @return
     */
    @GetMapping("/get_foodbyclass/")
    public Result getFoodByClass(@RequestParam Integer id) {
        List<FoodList> foods;
        Object list = redisTemplate.opsForValue().get("" + id + "-foods");
        if (ObjectUtil.isEmpty(list)) {

            QueryWrapper<FoodList> foodListQueryWrapper = new QueryWrapper<>();
            foodListQueryWrapper.eq("class_id", id);
             foods = foodListService.list(foodListQueryWrapper);
            redisTemplate.opsForValue().set("" + id  + "-foods" ,foods, Duration.ofMinutes(30));
        } else {
            foods = (List<FoodList>) list;
        }
        return Result.success(foods);
    }


    /**
     * 模糊查询食物
     *
     * @param key      查询食物的关键字
     * @return
     */
    @GetMapping("/getfoodbykey")
    public Result getFoodByKey(@RequestParam String key) {
        List<FoodList> foodsList;
            Object list = redisTemplate.opsForValue().get("" + key);
        if (ObjectUtil.isEmpty(list)) {
            QueryWrapper<FoodList> foodListQueryWrapper = new QueryWrapper<>();
            foodListQueryWrapper.like("name", key);
            foodsList = foodListService.list(foodListQueryWrapper);
            redisTemplate.opsForValue().set("" + key, foodsList, Duration.ofMinutes(30));
        } else {
            foodsList = (List<FoodList>) list;
        }
        return Result.success(foodsList);
    }

    /**
     * 获取指定食物信息
     * @param foodId 食物ID
     * @return
     */
    @GetMapping("/food_message")
    public Result getFoodById(@RequestParam String foodId){
        QueryWrapper<FoodMessage> foodMessageQueryWrapper = new QueryWrapper<>();
        foodMessageQueryWrapper.eq("food_id",foodId);
        return Result.success(foodMessageService.getOne(foodMessageQueryWrapper)) ;


    }
}

