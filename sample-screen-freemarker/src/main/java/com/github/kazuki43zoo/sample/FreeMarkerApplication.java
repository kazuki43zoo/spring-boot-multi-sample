package com.github.kazuki43zoo.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class FreeMarkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeMarkerApplication.class, args);
    }

}
