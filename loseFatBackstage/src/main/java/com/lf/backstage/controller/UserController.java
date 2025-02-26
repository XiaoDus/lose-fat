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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Random;

/**
 * <p>
 *  前端控制器
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
    @GetMapping
    public Result verification( HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        // hutool生成验证码
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(160, 40, 4, 20);
        String encryptCode = AESUtil.encrypt(captcha.getCode());
        stringRedisTemplate.opsForValue().set(session.getId(), encryptCode, Duration.ofMinutes(5));
        return Result.success(encryptCode);
    }

    @SneakyThrows
    @PostMapping("/login")
    public Result login(@RequestBody UserLoginRequests userInfo, HttpServletRequest request) {
        String username = userInfo.getUsername();
        String password = userInfo.getPassword();
        String md5Pwd = Md5Util.getMD5String(password);
        String verification = userInfo.getVerification();
        String encryptCode = AESUtil.encrypt(verification);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_phone", username);
        queryWrapper.eq("user_password", md5Pwd);
        String redisCode = stringRedisTemplate.opsForValue().get(request.getSession().getId());
        if (StrUtil.isNotBlank(redisCode) && encryptCode.equals(redisCode) ) {
            stringRedisTemplate.delete(request.getSession().getId());
            User user = userService.getOne(queryWrapper);
            if (user == null) {
                return Result.error(Constants.CODE_400,"用户名或密码错误");
            }
            if(user.getStatus().equals("1")){
                return Result.success(user);
            }
            return Result.error(Constants.CODE_401,"用户无权限");
        }else {
            return Result.error(Constants.CODE_400,"验证码错误");
        }
    }
}

