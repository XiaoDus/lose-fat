package com.lf.losefat.config;


import com.lf.losefat.common.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor())
                .addPathPatterns("/**") // 拦截所有请求，通过判断token是否合法来决定是否需要登录
                .excludePathPatterns( //登录注册
                        "/user/wx_login",
                        "/user/password_login",
                        "/user/getCode",
                        "/user/sms_login"
                        )
                .excludePathPatterns("/file/**") //文件
                .excludePathPatterns("/carousel/**") //轮播图
                .excludePathPatterns("/knowledge/**") //每日一题
                .excludePathPatterns("/foods/**") //食物搜索
                .excludePathPatterns( // swagger3
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/v3/**",
                        "/swagger-ui.html/**");

    }
    @Bean
    public HandlerInterceptor jwtInterceptor(){
        return new JwtInterceptor();
    }
}
