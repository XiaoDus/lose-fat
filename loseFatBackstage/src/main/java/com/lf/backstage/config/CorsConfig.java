package com.lf.backstage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    // 当前跨域请求最大有效时长。这里默认1天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 使用 allowedOriginPatterns 来设置多个允许的来源，避免使用 "*"
        corsConfiguration.addAllowedOriginPattern("http://localhost:8080");  // 前端地址
        corsConfiguration.addAllowedOriginPattern("http://localhost:5173");      // 可以添加多个来源
        corsConfiguration.addAllowedOriginPattern("http://localhost:8081");      // 可以添加多个来源

        // 允许所有的请求头
        corsConfiguration.addAllowedHeader("*");

        // 允许所有的请求方法
        corsConfiguration.addAllowedMethod("*");

        // 设置最大有效时长
        corsConfiguration.setMaxAge(MAX_AGE);

        // 允许携带凭证
        corsConfiguration.setAllowCredentials(true);

        // 注册跨域配置
        source.registerCorsConfiguration("/**", corsConfiguration);

        // 返回跨域过滤器
        return new CorsFilter(source);
    }
}
