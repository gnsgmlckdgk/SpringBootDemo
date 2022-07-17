package com.hch.demo.controller;

import com.hch.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@AllArgsConstructor
@RequestMapping("/v")
@Controller
public class VController {

    private final UserService userService;

    @GetMapping("")
    public String main(Model model) {

        log.info("VController >> main()");

        model.addAttribute("currentPage", "home");
        return "content/main";
    }

    @GetMapping("/users")
    public String users(Model model, @PageableDefault(page=0, size=5, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {

        log.info("VController >> users()");

        model.addAttribute("users", userService.findAll(pageable));
        model.addAttribute("currentPage", "user");

        return "content/user";
    }



}
