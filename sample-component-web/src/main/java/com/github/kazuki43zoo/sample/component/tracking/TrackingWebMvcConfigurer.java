package com.github.kazuki43zoo.sample.component.tracking;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class TrackingWebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(trackingIdHandlerMethodArgumentResolver());
    }

    @Bean
    TrackingIdHandlerMethodArgumentResolver trackingIdHandlerMethodArgumentResolver() {
        return new TrackingIdHandlerMethodArgumentResolver();
    }

}
