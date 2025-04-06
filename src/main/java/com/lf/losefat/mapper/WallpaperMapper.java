package com.lf.losefat.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lf.losefat.entity.Wallpaper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WallpaperMapper extends BaseMapper<Wallpaper> {

    Wallpaper getRandomOne();
}
