package com.lf.losefat.mapper;

import com.lf.losefat.entity.Carousel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 小肚
 * @since 2024-04-30
 */
public interface CarouselMapper extends BaseMapper<Carousel> {


     List<Carousel> carousel();
}
