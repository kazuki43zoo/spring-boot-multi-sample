package com.github.kazuki43zoo.sample.screen.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/login")
@Controller
public class LoginController {

    @RequestMapping
    public String index() {
        return "login";
    }

}
