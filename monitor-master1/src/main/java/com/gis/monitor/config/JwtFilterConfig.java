package com.gis.monitor.config;

import com.gis.monitor.controller.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
    拦截器配置
    如未登陆拦截请求，转向/auth/failure
    允许通过/auth/login
          /auth/failure
 */
@Configuration
public class JwtFilterConfig implements WebMvcConfigurer {
    // 注入 拦截器
    @Bean
    public JwtInterceptor myInterceptor(){
        return new JwtInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 具体权限设定
        registry.addInterceptor(myInterceptor())
                .excludePathPatterns("/auth/login")
                .excludePathPatterns("/auth/failure")
                .addPathPatterns("/**");
    }
}
