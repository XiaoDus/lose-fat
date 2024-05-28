package com.lf.losefat.service;

import com.lf.losefat.entity.FoodCollect;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lf.losefat.entity.FoodList;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小肚
 * @since 2024-04-22
 */
public interface IFoodCollectService extends IService<FoodCollect> {

    List<FoodList> MyCollectList(String userId);
}
