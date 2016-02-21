package com.github.kazuki43zoo.sample;

import org.h2.server.web.DbStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Profile("database")
@SpringBootApplication
public class DatabaseApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
    }

    @Bean
    DbStarter dbStarter() {
        return new DbStarter();
    }

}
