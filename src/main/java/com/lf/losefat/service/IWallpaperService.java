package com.lf.losefat.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lf.losefat.entity.Wallpaper;

public interface IWallpaperService extends IService<Wallpaper> {
    Wallpaper getRandomOne();
}
