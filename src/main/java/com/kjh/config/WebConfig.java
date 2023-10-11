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

        registry.addInterceptor(loginCheckInterceptor) //LoginCheckInterceptor 등록
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/main/index", "/favicon.svg", "/images/**", "/js/**", "/css/**", "/sign/**", "/user/signin", "/error");

        registry.addInterceptor(menuInterceptor) // menuInterceptor 등록
                .order(3)                        // 이것만 컨트롤러에 적용, 나머진 전역.
                .addPathPatterns("/**")
                .excludePathPatterns("/js/**", "/css/**", "/images/**", "/error", "/favicon.svg");

    }

}