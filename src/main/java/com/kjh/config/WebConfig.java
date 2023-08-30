package com.kjh.config;

import com.kjh.interceptor.LogInterceptor;
import com.kjh.interceptor.LoginCheckInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Component
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()) // LogInterceptor 등록
                .order(1)	// 적용할 필터 순서 설정
                .addPathPatterns("/**")
                .excludePathPatterns("/error"); // 인터셉터에서 제외할 패턴

        registry.addInterceptor(new LoginCheckInterceptor()) //LoginCheckInterceptor 등록
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/main/index", "/images/**", "/js/**", "/css/**", "/sign/**", "/user/signin");
    }
}