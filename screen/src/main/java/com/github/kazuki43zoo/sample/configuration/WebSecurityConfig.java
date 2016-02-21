package com.github.kazuki43zoo.sample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin().loginPage("/login").permitAll().and()
                .logout().logoutSuccessUrl("/?logout").permitAll().and()
                .authorizeRequests()
                .antMatchers("/todos/**").hasAnyRole("USER", "ADMIN", "ANONYMOUS");
    }

}
