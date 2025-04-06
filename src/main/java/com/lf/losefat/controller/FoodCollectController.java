package com.lf.losefat.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lf.losefat.common.Result;
import com.lf.losefat.entity.FoodCollect;
import com.lf.losefat.entity.FoodList;
import com.lf.losefat.entity.User;
import com.lf.losefat.service.IFoodCollectService;
import com.lf.losefat.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  收藏控制器
 * </p>
 *
 * @author 小肚
 * @since 2024-04-22
 */
@RestController
@RequestMapping("/collect")
public class FoodCollectController {
    @Autowired
    private IFoodCollectService foodCollectService;

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 查找指定食物是否id
     * @param foodId 食物id
     * @return
     */
    @GetMapping("/get_collect")
    public Result getCollect(String foodId){
        User currentUser = TokenUtils.getCurrentUser();
        QueryWrapper<FoodCollect> foodCollectQueryWrapper = new QueryWrapper<>();
        foodCollectQueryWrapper.eq("food_id",foodId);
        foodCollectQueryWrapper.eq("user_id",currentUser.getUserId());
        FoodCollect food = foodCollectService.getOne(foodCollectQueryWrapper);
        if(food !=null){
            return Result.success(1);
        }
        return Result.success(0);
    }

    /**
     * 修改或添加收藏
     * @param foodCollect
     * @return
     */
    @GetMapping("/edit_collect")
    public Result addCollect(FoodCollect foodCollect){
        User currentUser = TokenUtils.getCurrentUser();
        String KEY = currentUser.getUserId() + "-CollectList";
        foodCollect.setUserId(currentUser.getUserId());
        QueryWrapper<FoodCollect> foodCollectQueryWrapper = new QueryWrapper<>();
        foodCollectQueryWrapper.eq("food_id",foodCollect.getFoodId());
        foodCollectQueryWrapper.eq("user_id",foodCollect.getUserId());
        FoodCollect food = foodCollectService.getOne(foodCollectQueryWrapper);
        if(food !=null){
            foodCollectService.removeById(food.getId());
            redisTemplate.delete(KEY);
            return Result.success(0);
        }else {
            boolean save = foodCollectService.save(foodCollect);
            redisTemplate.delete(KEY);
            if (save){
                return Result.success(1);
            }else {
                return Result.error();
            }
        }

    }

    /**
     * 我的收藏
     * @return
     */
    @GetMapping("/MyCollectList")
    public Result MyCollectList(){
        User currentUser = TokenUtils.getCurrentUser();
        String KEY = currentUser.getUserId() + "-CollectList";
        Object o = redisTemplate.opsForValue().get(KEY);
        if (o!=null) {
            return Result.success((List<FoodList>) o);
        }
        List<FoodList> myCollectList = foodCollectService.MyCollectList(currentUser.getUserId());
        redisTemplate.opsForValue().set(KEY,myCollectList);
        return Result.success(myCollectList);
    }


}

