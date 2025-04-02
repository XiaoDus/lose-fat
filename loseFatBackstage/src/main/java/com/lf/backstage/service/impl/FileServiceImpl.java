package com.lf.backstage.service.impl;

import com.lf.backstage.entity.AvatarFiles;
import com.lf.backstage.mapper.FileMapper;
import com.lf.backstage.service.IFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 文件管理 服务实现类
 * </p>
 *
 * @author 小肚
 * @since 2025-03-18
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, AvatarFiles> implements IFileService {

    @Autowired
    private FileMapper fileMapper;
    @Override
    public List<AvatarFiles> getList() {
        return fileMapper.getList();
    }
}
