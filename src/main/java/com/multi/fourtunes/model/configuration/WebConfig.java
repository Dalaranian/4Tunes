package com.multi.fourtunes.model.configuration;

import com.multi.fourtunes.model.intercepter.SessionInvalidationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인 되지 않았을 떄, Session을 invalidate 하여 SpringSecurity 인증정보 날리기
        registry.addInterceptor(new SessionInvalidationInterceptor()).addPathPatterns("/**");
    }
}
