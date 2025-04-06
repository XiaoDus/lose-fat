package com.lf.losefat.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lf.losefat.common.Result;
import com.lf.losefat.entity.Carousel;
import com.lf.losefat.service.ICarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.List;

/**
 * <p>
 *  轮播图控制器
 * </p>
 *
 * @author 小肚
 * @since 2024-04-30
 */
@RestController
@RequestMapping("/carousel")
public class CarouselController {
    @Autowired
    private ICarouselService carouselService;

    @Autowired
    private RedisTemplate redisTemplate;

    private static String REDISKEY = "CAROUSEL";

    /**
     * 随机三张图片轮播图
     * @return
     */
    @GetMapping
    public Result getCarousel(){
        //从redis中获取
        List<Carousel> o = (List<Carousel>) redisTemplate.opsForValue().get(REDISKEY);
        if (o!=null){
            return Result.success(o);
        }
        List<Carousel> carousels = carouselService.carousel();
        //保存在redis中，一天过期
        redisTemplate.opsForValue().set(REDISKEY,carousels, Duration.ofDays(1));
        return Result.success(carousels);
    }
}

