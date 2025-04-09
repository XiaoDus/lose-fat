package com.lf.backstage.mapper;

import com.lf.backstage.entity.FoodList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lf.backstage.entity.dto.ResFoodList;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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

    @Select("SELECT fc.id, fc.name, COUNT(fl.food_id) AS value " +
            "FROM food_class fc " +
            "LEFT JOIN food_list fl ON fc.id = fl.class_id " +
            "GROUP BY fc.id, fc.name " +
            "ORDER BY fc.id")
    List<Map<String, Object>> getFoodCategoryDistribution();
}
