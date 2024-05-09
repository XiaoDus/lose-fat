package com.lf.losefat.service.impl;

import com.lf.losefat.entity.User;
import com.lf.losefat.mapper.UserMapper;
import com.lf.losefat.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 小肚
 * @since 2024-03-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
