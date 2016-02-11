package com.github.kazuki43zoo.sample;

import freemarker.ext.jsp.TaglibFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

import java.util.Arrays;
import java.util.List;

@ServletComponentScan
@SpringBootApplication
public class FreeMarkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreeMarkerApplication.class, args);
    }

    @Bean
    ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }

}
