package com.github.kazuki43zoo.sample.configuration;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@MapperScan("com.github.kazuki43zoo.sample.domain.repository")
@Configuration
public class RepositoryConfiguration {
}
