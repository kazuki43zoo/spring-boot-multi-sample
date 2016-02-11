package com.github.kazuki43zoo.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ConditionalOnEnabledResourceChain;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

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
