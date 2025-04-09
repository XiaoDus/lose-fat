package com.lf.backstage.service.impl;

import com.lf.backstage.entity.FoodList;
import com.lf.backstage.entity.dto.ResFoodList;
import com.lf.backstage.mapper.FoodListMapper;
import com.lf.backstage.service.IFoodListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小肚
 * @since 2025-03-20
 */
@Service
public class FoodListServiceImpl extends ServiceImpl<FoodListMapper, FoodList> implements IFoodListService {

    @Autowired
    private FoodListMapper FoodListMapper;
    @Override
    public List<ResFoodList> getList() {
        return FoodListMapper.getList();
    }

    @Override
    public List<Map<String, Object>> getFoodCategoryDistribution() {
        return  FoodListMapper.getFoodCategoryDistribution();
    }


}
