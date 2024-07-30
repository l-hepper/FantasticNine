package com.sparta.doom.fantasticninewebandapi.security.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    private final ApiKeyRepository apiKeyRepository;

    public ApiKeyInterceptor(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
        String apiKey = request.getHeader("DOOM-API-KEY");
        if (apiKey == null) {
            if ("GET".equals(request.getMethod())) {
                return true;
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }

        ApiKeyModel key = apiKeyRepository.findByKey(apiKey);
        if (key == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return false;
        }

        request.setAttribute("role", key.getAccessLevel());
        return true;
    }
}
