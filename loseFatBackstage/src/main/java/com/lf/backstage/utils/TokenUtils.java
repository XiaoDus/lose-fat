package com.lf.backstage.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.lf.backstage.entity.User;
import com.lf.backstage.service.IUserService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

@Component
public class TokenUtils {
    private static IUserService staticUserLoginService;
    @Resource
    private  IUserService userLoginService;

    @PostConstruct
    public void setUserLoginService(){
        staticUserLoginService = userLoginService;
    }


    /**
     * 生成token
     * @param userId
     * @param sign
     * @return
     */
    public static String genToken(String userId,String sign){
        return JWT.create().withAudience(userId) // 将 user id 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(),2)) //两小时后token过期（DateUtil.offsetHour：hutool工具）
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥

    }

    /**
     * 获取当前登录用户信息
     * @return
     */
    public static User getCurrentUser(){
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader("token");
            if (StrUtil.isNotBlank(token)){
                String userId = JWT.decode(token).getAudience().get(0);
                return staticUserLoginService.getById(userId);
            }
        }catch (Exception e){
            return null;
        }

        return null;
    }
}
