package com.lf.losefat.service.impl;

import com.lf.losefat.entity.Carousel;
import com.lf.losefat.mapper.CarouselMapper;
import com.lf.losefat.service.ICarouselService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小肚
 * @since 2024-04-30
 */
@Service
public class CarouselServiceImpl extends ServiceImpl<CarouselMapper, Carousel> implements ICarouselService {

    @Autowired
    private CarouselMapper carouselMapper;
    @Override
    public List<Carousel> carousel() {
        return carouselMapper.carousel();
    }
}
