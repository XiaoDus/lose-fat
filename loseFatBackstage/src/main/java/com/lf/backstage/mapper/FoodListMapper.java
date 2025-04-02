package com.lf.backstage.mapper;

import com.lf.backstage.entity.FoodList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lf.backstage.entity.dto.ResFoodList;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 小肚
 * @since 2025-03-20
 */
public interface FoodListMapper extends BaseMapper<FoodList> {

    @Select("SELECT f.food_id, f.name, f.health_level, f.calory,f.class_id, fc.name AS className " +
            "FROM food_list f " +
            "JOIN food_class fc ON f.class_id = fc.id ")
    List<ResFoodList> getList();

}
