package com.sparta.doom.fantasticninewebandapi.security.api;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Value("${jwt.auth}")
    private String AUTH_HEADER;

    private final SecretKey secretKey;

    @Autowired
    public TokenInterceptor(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        if (method.equalsIgnoreCase("GET")) {
            return true;
        }

        if (requestURI.startsWith("/authenticate")) {
            return true;
        }

        String jwt = request.getHeader(AUTH_HEADER);
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
            return false;
        }

        jwt = jwt.substring(7); // Remove "Bearer " prefix

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
            String permissions = claims.get("permissions", String.class);
            request.setAttribute("permissions", permissions);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return false;
        }

        return true;
    }
}