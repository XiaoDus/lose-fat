package com.lf.losefat.mapper;

import com.lf.losefat.entity.FoodKnowledge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import okhttp3.Call;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 小肚
 * @since 2024-04-30
 */
public interface FoodKnowledgeMapper extends BaseMapper<FoodKnowledge> {

    FoodKnowledge getQuestion();

    /**
     *定义存储过程
     * DELIMITER
     *
     * CREATE PROCEDURE updateSelectNumber(IN id_param INT)
     * BEGIN
     *     UPDATE food_knowledge
     *     SET select_number = select_number + 1
     *     WHERE id = id_param;
     * END
     *
     * DELIMITER ;
     */
    @Select(value = {"call updateSelectNumber(#{id})"})
    void updateSelectNumber(@Param("id") Integer id);
}
