package com.lf.losefat.controller;


import com.lf.losefat.common.Result;
import com.lf.losefat.entity.Wallpaper;
import com.lf.losefat.service.IWallpaperService;
import jakarta.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 壁纸
 */
@RestController
@RequestMapping("/wallpaper")
public class WallpaperController {

    @Resource
    private IWallpaperService wallpaperService;

    @GetMapping
    public Result getWallpaper() {
        Wallpaper wallpaper = wallpaperService.getRandomOne();
        return Result.success(wallpaper);
    }
}
