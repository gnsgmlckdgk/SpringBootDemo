package com.hch.demo.common.config;

import com.hch.demo.exception.AuthorizationHeaderNotExistsException;
import com.hch.demo.exception.InvalidTokenException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

@Aspect // 특정 패키지의 메서드를 실행하기 전에 헤더를 체크할 수 있음
@Component
public class AuthorizationAspect {

    /**
     * API 호출 시 보안 체크 설정
     * @param joinPoint
     * @throws AuthorizationHeaderNotExistsException
     * @throws InvalidTokenException
     */
    @Before("execution(public * com.hch.demo.controller.api.v1..*Controller.*(..)) ")
    public void insertAdminLog(JoinPoint joinPoint) throws AuthorizationHeaderNotExistsException, InvalidTokenException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        String authorization = request.getHeader("Authorization");
        if(StringUtils.isBlank(authorization)){
            throw new AuthorizationHeaderNotExistsException();
        }
        authorization = authorization.replaceAll("^Basic( )*", "");
        try {
            String decodedStr = new String(Base64.getDecoder().decode(authorization));
            if(decodedStr.indexOf(":") < 0)
                throw new InvalidTokenException();
        } catch(Exception e) {
            throw new InvalidTokenException();
        }
    }

}