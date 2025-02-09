package com.lf.losefat.common;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lf.losefat.entity.User;
import com.lf.losefat.exception.ServiceException;
import com.lf.losefat.service.IUserService;
import jakarta.annotation.Resource;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;


public class JwtInterceptor implements HandlerInterceptor {
    @Resource
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String Authorization = request.getHeader("Authorization"); //获取请求头 Authorization
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        if (StrUtil.isBlank(Authorization)) {
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        // 获取 Authorization 中的 id
        String user_id;
        try {
            user_id = JWT.decode(Authorization).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
        }
        //根据Authorization中的id查询数据库
        QueryWrapper<User> userinfoQueryWrapper = new QueryWrapper<>();
        userinfoQueryWrapper.eq("user_id",user_id);
        User user = userService.getOne(userinfoQueryWrapper);
        if (user == null) {
             throw new ServiceException(Constants.CODE_401,"用户不存在，请重新登录");
        }
        // 用户密码加签验证 Authorization
//        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
//        try {
//            jwtVerifier.verify(Authorization);
//        } catch (JWTVerificationException e) {
//            throw new ServiceException(Constants.CODE_401,"无token，请重新登录");
//        }

        return true;
    }
}