package com.lf.losefat.service;

import com.lf.losefat.entity.Carousel;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 小肚
 * @since 2024-04-30
 */
public interface ICarouselService extends IService<Carousel> {

    List<Carousel> carousel();
}
