package com.github.kazuki43zoo.sample.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class GlobalSecurityConfig extends GlobalAuthenticationConfigurerAdapter {
    @Override
    public void init(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                // System Administrators
                .withUser("admin").password("password").roles("ADMIN", "USER").and()
                // OAuth Resource Owners
                .withUser("user").password("password").roles("USER").and()
                .withUser("kazuki43zoo").password("password").roles("USER").and()
                // OAuth Clients
                .withUser("sample-client").password("password").roles("CLIENT").and()
                // OAuth Resource Servers
                .withUser("sample-resource").password("password").roles("RESOURCE");

    }
}
