package com.github.kazuki43zoo.sample;

import org.h2.server.web.DbStarter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DatabaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatabaseApplication.class, args);
    }

    @Bean
    DbStarter dbStarter() {
        return new DbStarter();
    }

}
