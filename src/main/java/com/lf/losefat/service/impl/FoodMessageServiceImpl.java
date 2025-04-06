package com.lf.losefat.service.impl;

import com.lf.losefat.entity.FoodMessage;
import com.lf.losefat.mapper.FoodMessageMapper;
import com.lf.losefat.service.IFoodMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 食物信息表 服务实现类
 * </p>
 *
 * @author 小肚
 * @since 2024-04-22
 */
@Service
public class FoodMessageServiceImpl extends ServiceImpl<FoodMessageMapper, FoodMessage> implements IFoodMessageService {

}
