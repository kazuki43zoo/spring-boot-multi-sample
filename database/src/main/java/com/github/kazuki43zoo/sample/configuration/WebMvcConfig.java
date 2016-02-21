package com.github.kazuki43zoo.sample.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Profile("database")
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    H2ConsoleProperties h2ConsoleProperties;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/", h2ConsoleProperties.getPath());
    }

}
