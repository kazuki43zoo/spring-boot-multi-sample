package com.github.kazuki43zoo.sample.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity
@MapperScan("com.github.kazuki43zoo.sample.domain.repository")
@Configuration
public class DomainConfiguration {
}
