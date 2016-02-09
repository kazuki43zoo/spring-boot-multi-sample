package com.github.kazuki43zoo.sample.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.Map;

@RestController
public class WelcomeRestController {

    @RequestMapping("/")
    public Map<String, Object> hello() {
        return Collections.singletonMap("message", "Hello API!!");
    }

}
