package com.hch.demo.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("web")
@Controller
public class TestWebController {

    @GetMapping("/index")
    public String index(Model model) {
        log.debug("TestWebController >> index()");
        model.addAttribute("testStr", "타임리프");
        return "index";
    }

}
