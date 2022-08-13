package com.hch.demo.common.config;

import com.hch.demo.exception.AuthorizationHeaderNotExistsException;
import com.hch.demo.exception.InvalidTokenException;
import com.hch.demo.exception.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.WeakKeyException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.regex.Pattern;

@Slf4j
@ConfigurationProperties(prefix = "demo.token")
@Aspect // 특정 패키지의 메서드를 실행하기 전에 헤더를 체크할 수 있음
@Component
public class AuthorizationAspect {

    @Setter
    private String apiKey;
    @Setter
    private String secretKey;


//    /**
//     * Basic Authentication 헤더 검사
//     * @param joinPoint
//     * @throws AuthorizationHeaderNotExistsException
//     * @throws InvalidTokenException
//     */
//    @Before("execution(public * com.hch.demo.controller.api.v1..*Controller.*(..)) ")
//    public void insertAdminLog(JoinPoint joinPoint) throws AuthorizationHeaderNotExistsException, InvalidTokenException {
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//
//        String authorization = request.getHeader("Authorization");
//        if(StringUtils.isBlank(authorization)){
//            throw new AuthorizationHeaderNotExistsException();
//        }
//        authorization = authorization.replaceAll("^Basic( )*", "");
//        try {
//            String decodedStr = new String(Base64.getDecoder().decode(authorization));
//            if(decodedStr.indexOf(":") < 0)
//                throw new InvalidTokenException();
//        } catch(Exception e) {
//            throw new InvalidTokenException();
//        }
//    }

    /**
     * Bearer Authentication 헤더 검사(JWT)
     * @param joinPoint
     * @throws WeakKeyException
     * @throws UnsupportedEncodingException
     * @throws TokenExpiredException
     */
    @Before("execution(public * com.hch.demo.controller.api.v1..*Controller.*(..)) ")
    public void insertAdminLog(JoinPoint joinPoint) throws WeakKeyException, UnsupportedEncodingException, TokenExpiredException {

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes("UTF-8"));

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorization = request.getHeader("Authorization");

        if(StringUtils.isBlank(authorization)){
            throw new AuthorizationHeaderNotExistsException();
        }

        if(Pattern.matches("^Bearer .*", authorization)) {
            authorization = authorization.replaceAll("^Bearer( )*", "");
            Jws<Claims> jwsClaims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(authorization);

            if(jwsClaims.getBody() != null) {
                Claims claims = jwsClaims.getBody();
                if(!claims.containsKey("apiKey") || !apiKey.equals(claims.get("apiKey").toString())
                        || claims.getExpiration() == null) {
                    throw new InvalidTokenException();
                }
                long exp = claims.getExpiration().getTime();
                if(exp < new Date().getTime()) {
                    throw new TokenExpiredException();
                }
            }
        } else {
            throw new InvalidTokenException();
        }
    }

}