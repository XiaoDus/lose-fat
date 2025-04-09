package com.lf.backstage.controller;


import cn.hutool.core.bean.BeanUtil;
import com.lf.backstage.common.Constants;
import com.lf.backstage.common.Result;
import com.lf.backstage.entity.FoodKnowledge;
import com.lf.backstage.entity.FoodList;
import com.lf.backstage.entity.dto.EditFoodKnowledge;
import com.lf.backstage.service.IFoodKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 小肚
 * @since 2025-04-06
 */
@RestController
@RequestMapping("/knowledge")
public class FoodKnowledgeController {
    @Autowired
    private IFoodKnowledgeService fkService;

    @Autowired
    private RedisTemplate redisTemplate;
    @PostMapping("/list")
    public Result list(){
        Object o = redisTemplate.opsForValue().get("knowledge");
        if (o != null){
            return Result.success((List<FoodKnowledge>) o);
        }
        List<FoodKnowledge> list = fkService.list();
        redisTemplate.opsForValue().set("knowledge", list);
        return Result.success(list);
    }
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        fkService.removeById(id);
        redisTemplate.delete("knowledge");
        return Result.success();
    }
    @PostMapping("/edit")
    public Result delete(@RequestBody EditFoodKnowledge fk) {
        FoodKnowledge foodKnowledge1 = new FoodKnowledge();
        BeanUtil.copyProperties(fk, foodKnowledge1);
        boolean b = fkService.updateById(foodKnowledge1);
        if (b){
            redisTemplate.delete("knowledge");
            return Result.success();
        }

        return Result.error(Constants.CODE_400,"修改失败！");
    }
    @PostMapping("/addTopic")
    public Result addTopic(@RequestBody FoodKnowledge fk) {
        boolean b = fkService.save(fk);
        if (b){
            redisTemplate.delete("knowledge");
            System.out.println();
            return Result.success(fk.getId());
        }

        return Result.error(Constants.CODE_400,"保存失败！");
    }
}

