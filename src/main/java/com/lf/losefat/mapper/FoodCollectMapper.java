package com.lf.losefat.mapper;

import com.lf.losefat.entity.FoodCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lf.losefat.entity.FoodList;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 小肚
 * @since 2024-04-22
 */
public interface FoodCollectMapper extends BaseMapper<FoodCollect> {

    @Select("SELECT * FROM food_list WHERE food_id IN (SELECT food_id FROM food_collect WHERE user_id = #{userId});")
    List<FoodList> MyCollectList(String userId);
}
