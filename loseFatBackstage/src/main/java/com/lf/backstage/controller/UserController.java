package com.lf.backstage.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lf.backstage.common.Constants;
import com.lf.backstage.common.Result;
import com.lf.backstage.entity.User;
import com.lf.backstage.entity.dto.UserLoginRequests;
import com.lf.backstage.service.IUserService;
import com.lf.backstage.utils.AESUtil;
import com.lf.backstage.utils.Md5Util;
import com.lf.backstage.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Random;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 小肚
 * @since 2025-02-11
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate; //redis

    @PostMapping("/list")
    public Result userList() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 筛选出不是管理员的用户
        queryWrapper.ne("status", "0");
        List<User> list = userService.list(queryWrapper);
        return Result.success(list);
    }

    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable String id) {
        userService.removeById(id);
        return Result.success();
    }

    @PostMapping("/edit")
    public Result edit(@RequestBody User user) {
        User currentUser = TokenUtils.getCurrentUser();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_phone", user.getUserPhone());
        User one = userService.getOne(queryWrapper);
        if (one != null ) {
            if (currentUser.getUserPhone().equals(user.getUserPhone())) {
                userService.updateById(user);
                return Result.success();
            }
            return Result.error("409", "手机号已存在");
        }

        userService.updateById(user);
        return Result.success();
    }
}

