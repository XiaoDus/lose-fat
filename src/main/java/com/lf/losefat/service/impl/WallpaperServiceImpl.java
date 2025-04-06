package com.lf.losefat.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lf.losefat.entity.Wallpaper;
import com.lf.losefat.mapper.WallpaperMapper;
import com.lf.losefat.service.IWallpaperService;
import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

@Service
public class WallpaperServiceImpl extends ServiceImpl<WallpaperMapper,Wallpaper> implements IWallpaperService{

    @Resource
    private WallpaperMapper wallpaperMapper;

    @Override
    public Wallpaper getRandomOne() {
        return wallpaperMapper.getRandomOne();
    }
}
