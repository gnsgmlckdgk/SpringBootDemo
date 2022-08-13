package com.hch.demo.common.config;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;

/**
 * CSRF 체크 제외 예외처리
 */
public class CsrfRequireMatcher implements RequestMatcher {
    private static final Pattern ALLOWED_METHODS = Pattern.compile("^(GET|HEAD|TRACE|OPTIONS)$");

    @Override
    public boolean matches(HttpServletRequest request) {
        if (ALLOWED_METHODS.matcher(request.getMethod()).matches())
            return false;

        final String referer = request.getHeader("Referer");

        // Swagger에서 테스트시에는 csrf 체크를 하지 않게함
        if (referer != null && referer.contains("/swagger-ui")) {
            return false;
        }
        return true;
    }
}
