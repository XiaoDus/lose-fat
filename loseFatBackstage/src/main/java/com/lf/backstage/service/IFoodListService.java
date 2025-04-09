package com.lf.backstage.service;

import com.lf.backstage.entity.FoodList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lf.backstage.entity.dto.ResFoodList;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小肚
 * @since 2025-03-20
 */
public interface IFoodListService extends IService<FoodList> {


    List<ResFoodList> getList();

    List<Map<String, Object>> getFoodCategoryDistribution();
}
