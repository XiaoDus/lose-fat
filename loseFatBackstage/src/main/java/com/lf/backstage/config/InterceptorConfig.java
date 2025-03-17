package com.lf.backstage.config;

import com.lf.backstage.common.Interceptor.JwtInterceptor;
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
                .excludePathPatterns("/user/**")
                .excludePathPatterns("/user/")
                .excludePathPatterns("/file/**") //文件
                .excludePathPatterns( // swagger3
                        "swagger-ui/index.html",
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
