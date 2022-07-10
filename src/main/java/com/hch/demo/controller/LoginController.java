package com.hch.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("")
    public String login(@AuthenticationPrincipal User user) {
        if(user != null) {
            if(user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_VIEW"))) {
                return "redirect:/v";
            }
        }
        return "redirect:/login";
    }

}
