package com.lf.losefat.controller;


import com.lf.losefat.common.Result;
import com.lf.losefat.entity.FoodKnowledge;
import com.lf.losefat.service.IFoodKnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

/**
 * <p>
 *  每日一答
 *  前端控制器
 * </p>
 *
 * @author 小肚
 * @since 2024-04-30
 */
@RestController
@RequestMapping("/knowledge")
public class FoodKnowledgeController {
    @Autowired
    private IFoodKnowledgeService knowledgeService;
    @Autowired
    private RedisTemplate redisTemplate;
    private static String QUESTION = "QUESTION";

    /**
     * 获取每日问题
     * @return
     */
    @GetMapping
    public Result getQuestion(){
        //从redis中获取
        FoodKnowledge o = (FoodKnowledge) redisTemplate.opsForValue().get(QUESTION);
        if (o!=null) {
            return Result.success(o);
        }
        //从数据库中选择被选择次数最小的数据
        FoodKnowledge question = knowledgeService.getQuestion();
        //调用存储过程（每天被选择的数据选择次数+1）
        knowledgeService.updateSelectNumber(question.getId());
        //保存中redis中
        redisTemplate.opsForValue().set(QUESTION,question, Duration.ofDays(1));
        return Result.success(question);
    }
}

