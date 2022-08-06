package com.hch.demo.controller;

import com.hch.demo.service.StoreService;
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
    private final StoreService storeService;

    @GetMapping("")
    public String main(Model model) {


        model.addAttribute("currentPage", "home");
        return "content/main";
    }

    @GetMapping("/users")
    public String users(Model model, @PageableDefault(page=0, size=5, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {


        model.addAttribute("users", userService.findAll(pageable));
        model.addAttribute("currentPage", "user");

        return "content/user";
    }


    @GetMapping("/stores")
    public String selectStores(Model model, @PageableDefault(page=0, size=5, sort="id", direction = Sort.Direction.DESC) Pageable pageable) {
        model.addAttribute("stores", storeService.findAll(pageable));
        model.addAttribute("currentPage", "store");
        return "content/store";
    }



}
