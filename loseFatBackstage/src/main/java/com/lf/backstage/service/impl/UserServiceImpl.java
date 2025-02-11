package com.lf.backstage.service.impl;

import com.lf.backstage.entity.User;
import com.lf.backstage.mapper.UserMapper;
import com.lf.backstage.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小肚
 * @since 2025-02-11
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
