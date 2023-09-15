package com.kjh.config;

import com.kjh.interceptor.LogInterceptor;
import com.kjh.interceptor.LoginCheckInterceptor;
import com.kjh.interceptor.MenuInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    @Autowired
    private LogInterceptor logInterceptor;

    @Autowired
    private MenuInterceptor menuInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(logInterceptor) // LogInterceptor 등록
                .order(1)	// 적용할 필터 순서 설정
                .addPathPatterns("/**")
                .excludePathPatterns("/error"); // 인터셉터에서 제외할 패턴

        registry.addInterceptor(menuInterceptor) //menuInterceptor 등록
                .order(2)
                .addPathPatterns("/**");

        registry.addInterceptor(loginCheckInterceptor) //LoginCheckInterceptor 등록
                .order(3)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/main/index", "/images/**", "/js/**", "/css/**", "/sign/**", "/user/signin");
    }

}