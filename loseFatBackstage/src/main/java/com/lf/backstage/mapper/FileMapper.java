package com.lf.backstage.mapper;

import com.lf.backstage.entity.AvatarFiles;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 文件管理 Mapper 接口
 * </p>
 *
 * @author 小肚
 * @since 2025-03-18
 */
public interface FileMapper extends BaseMapper<AvatarFiles> {
    @Select("SELECT file.url, file.id, u.user_name as userName FROM file JOIN lose_fat.user u ON file.user_id = u.user_id;")
    List<AvatarFiles> getList();
}
