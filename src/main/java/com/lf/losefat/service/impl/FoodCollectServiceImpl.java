package com.lf.losefat.service.impl;

import com.lf.losefat.entity.FoodCollect;
import com.lf.losefat.entity.FoodList;
import com.lf.losefat.mapper.FoodCollectMapper;
import com.lf.losefat.service.IFoodCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小肚
 * @since 2024-04-22
 */
@Service
public class FoodCollectServiceImpl extends ServiceImpl<FoodCollectMapper, FoodCollect> implements IFoodCollectService {

    @Autowired
    private FoodCollectMapper foodCollectMapper;
    @Override
    public List<FoodList> MyCollectList(String userId) {
        return foodCollectMapper.MyCollectList(userId);
    }
}
