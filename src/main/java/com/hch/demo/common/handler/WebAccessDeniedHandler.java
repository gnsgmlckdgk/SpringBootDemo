package com.hch.demo.common.handler;

import com.hch.demo.model.entity.SecurityUser;
import com.hch.demo.model.entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class WebAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest req, HttpServletResponse res, AccessDeniedException ade)
            throws IOException, ServletException {

        res.setStatus(HttpStatus.FORBIDDEN.value());

        if(ade instanceof AccessDeniedException) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
                Set<UserRole.RoleType> roleTypes = securityUser.getRoleTypes();
                if(!roleTypes.isEmpty()) {
                    req.setAttribute("msg", "접근권한 없는 사용자입니다.");
                    if(roleTypes.contains(UserRole.RoleType.ROLE_VIEW)) {
                        req.setAttribute("nextPage", "/v");
                    }
                }
            } else {
                req.setAttribute("msg", "로그인 권한이 없는 아이디입니다.");
                req.setAttribute("nextPage", "/login");
                res.setStatus(HttpStatus.UNAUTHORIZED.value());
                SecurityContextHolder.clearContext();
            }
        } else {
            logger.info(ade.getClass().getCanonicalName());
        }
        req.getRequestDispatcher("/err/denied-page").forward(req, res);
    }

}
