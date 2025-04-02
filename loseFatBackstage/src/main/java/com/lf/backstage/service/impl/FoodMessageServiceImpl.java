package com.lf.backstage.service.impl;

import com.lf.backstage.entity.FoodMessage;
import com.lf.backstage.mapper.FoodMessageMapper;
import com.lf.backstage.service.IFoodMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 食物信息表 服务实现类
 * </p>
 *
 * @author 小肚
 * @since 2025-03-26
 */
@Service
public class FoodMessageServiceImpl extends ServiceImpl<FoodMessageMapper, FoodMessage> implements IFoodMessageService {

}
