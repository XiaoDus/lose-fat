package com.lf.backstage.controller;


import cn.hutool.Hutool;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lf.backstage.common.Result;
import com.lf.backstage.entity.FoodList;
import com.lf.backstage.entity.FoodMessage;
import com.lf.backstage.entity.dto.ResFoodList;
import com.lf.backstage.service.IFoodListService;
import com.lf.backstage.service.IFoodMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小肚
 * @since 2025-03-20
 */
@RestController
@RequestMapping("/food")
public class FoodListController {
    @Autowired
    private IFoodListService foodListService;
    @Autowired
    IFoodMessageService foodMessageService;
    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("/list")
    public Result list(){
        Object o = redisTemplate.opsForValue().get("foodList");
        if (o!=null){
            return Result.success((List<ResFoodList>) o);
        }
        List<ResFoodList> list = foodListService.getList();
        redisTemplate.opsForValue().set("foodList",list);
        return Result.success(list);
    }
    @PostMapping("/edit")
    public Result edit(@RequestBody FoodList foodList){
        FoodList foodList1 = new FoodList();
        BeanUtil.copyProperties(foodList, foodList1);
        boolean b = foodListService.updateById(foodList1);
        if (b){
            redisTemplate.delete("foodList");
        }
        return Result.success();
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        foodListService.removeById(id);
        redisTemplate.delete("foodList");
        return Result.success();
    }
}

