package com.sparta.doom.fantasticninewebandapi.config;

import com.sparta.doom.fantasticninewebandapi.security.api.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfig implements WebMvcConfigurer {
    private final TokenInterceptor tokenInterceptor;

    @Autowired
    public ApiConfig(TokenInterceptor jwtInterceptor) {
        this.tokenInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor);
    }
}
