package com.hch.demo.controller;


import com.hch.demo.model.entity.SecurityUser;
import com.hch.demo.model.entity.UserRole;
import com.hch.demo.model.value.UserValue;
import com.hch.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Controller
public class LoginController {

    private final UserService userService;

    @GetMapping("/")
    public String main() {
        return "redirect:/login";
    }

    /**
     * 로그인 페이지 이동
     * @param securityUser
     * @return
     */
    @GetMapping("/login")
    public String login(@AuthenticationPrincipal SecurityUser securityUser) {
        if(securityUser != null) {
            if(securityUser.getRoleTypes().contains(UserRole.RoleType.ROLE_VIEW)) {
                return "redirect:/v";
            }
        }
        return "login/login";
    }

    /**
     * 회원가입 페이지 이동
     * @param securityUser
     * @return
     */
    @GetMapping("/join")
    public String joinForm(@AuthenticationPrincipal SecurityUser securityUser) {
        if(securityUser != null && securityUser.getRoleTypes().contains(UserRole.RoleType.ROLE_VIEW)) {
            return "redirect:/v";
        }
        return "login/join";
    }

    /**
     * 회원가입
     * @param value
     * @return
     */
    @ResponseBody
    @PostMapping("/join")
    public Map<String, Object> join(@RequestBody UserValue value) {

        log.info("UserValue = {}", value);

        Map<String, Object> response = new HashMap<>();

        if(userService.findByEmail(value.getEmail()).isPresent()) {
            response.put("duplicate", true);
            return response;
        }

        response.put("success", userService.join(value) != null ? true : false);

        return response;
    }


    @GetMapping(value = "/err/denied-page")
    public String accessDenied(){
        return "err/deniedPage";
    }

}
