package com.sparta.doom.fantasticninewebandapi.security.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyInterceptor(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        String apiKey = request.getHeader("DOOM-API-KEY");
        System.out.println(apiKey);
        if (apiKey == null) {
            if ("GET".equals(request.getMethod())) {
                return true;
            }
        }

        Optional<ApiKeyModel> key = apiKeyRepository.findByKey(apiKey);

        if (key.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }

        request.setAttribute("role", key.get().getAccessLevel());
        return true;
    }
}