package com.lf.backstage.service;

import com.lf.backstage.entity.AvatarFiles;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 文件管理 服务类
 * </p>
 *
 * @author 小肚
 * @since 2025-03-18
 */
public interface IFileService extends IService<AvatarFiles> {

    List<AvatarFiles> getList();
}
