package com.lf.losefat.service;

import com.lf.losefat.entity.FoodKnowledge;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小肚
 * @since 2024-04-30
 */
public interface IFoodKnowledgeService extends IService<FoodKnowledge> {

    FoodKnowledge getQuestion();

    void updateSelectNumber(Integer id);
}
