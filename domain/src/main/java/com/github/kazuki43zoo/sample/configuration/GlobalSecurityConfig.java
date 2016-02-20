package com.github.kazuki43zoo.sample.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class GlobalSecurityConfig extends GlobalAuthenticationConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

//        auth.inMemoryAuthentication()
//                // System Administrators
//                .withUser("admin").password("password").roles("ADMIN", "USER").and()
//                // OAuth Resource Owners
//                .withUser("user").password("password").roles("USER").and()
//                .withUser("kazuki43zoo").password("password").roles("USER").and()
//                // OAuth Clients
//                .withUser("sample-client").password("password").roles("CLIENT").and();

    }
}
