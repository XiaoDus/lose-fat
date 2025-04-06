package com.lf.losefat.service.impl;

import com.lf.losefat.entity.FoodKnowledge;
import com.lf.losefat.mapper.FoodKnowledgeMapper;
import com.lf.losefat.service.IFoodKnowledgeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小肚
 * @since 2024-04-30
 */
@Service
public class FoodKnowledgeServiceImpl extends ServiceImpl<FoodKnowledgeMapper, FoodKnowledge> implements IFoodKnowledgeService {

    @Autowired
    private FoodKnowledgeMapper knowledgeMapper;
    @Override
    public FoodKnowledge getQuestion() {
        return knowledgeMapper.getQuestion();
    }

    @Override
    public void updateSelectNumber(Integer id) {
        knowledgeMapper.updateSelectNumber(id);
    }
}
