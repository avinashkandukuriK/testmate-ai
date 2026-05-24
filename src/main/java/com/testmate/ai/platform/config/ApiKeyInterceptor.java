package com.testmate.ai.platform.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiKeyInterceptor implements HandlerInterceptor {

    private static final String API_KEY_HEADER = "X-TestMate-Api-Key";

    private final String configuredApiKey;

    public ApiKeyInterceptor(@Value("${app.testmate.api-key:}") String configuredApiKey) {
        this.configuredApiKey = configuredApiKey;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (configuredApiKey == null || configuredApiKey.isBlank()) {
            return true;
        }

        String providedApiKey = request.getHeader(API_KEY_HEADER);
        if (configuredApiKey.equals(providedApiKey)) {
            return true;
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.WWW_AUTHENTICATE, API_KEY_HEADER);
        return false;
    }
}
